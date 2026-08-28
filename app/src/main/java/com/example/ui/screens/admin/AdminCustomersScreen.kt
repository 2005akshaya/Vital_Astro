package com.example.ui.screens.admin

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.CustomerEntity
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.util.AdminIntentHelper
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun AdminCustomersScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val customers by viewModel.customersList.collectAsState()
  val searchQuery by viewModel.customerSearchQuery.collectAsState()
  val selectedCustomer by viewModel.selectedCustomer.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  var showAddEditDialog by remember { mutableStateOf(false) }
  var customerToEdit by remember { mutableStateOf<CustomerEntity?>(null) }

  val filteredCustomers = remember(customers, searchQuery) {
    if (searchQuery.isBlank()) customers else {
      customers.filter { c ->
        c.fullName.contains(searchQuery, ignoreCase = true) ||
          c.phoneNumber.contains(searchQuery, ignoreCase = true) ||
          c.rashiTamil.contains(searchQuery, ignoreCase = true) ||
          c.nakshatraTamil.contains(searchQuery, ignoreCase = true) ||
          c.placeOfBirth.contains(searchQuery, ignoreCase = true)
      }
    }
  }

  // Add / Edit Customer Dialog
  if (showAddEditDialog) {
    AdminAddEditCustomerDialog(
      customer = customerToEdit,
      appLanguage = appLanguage,
      onDismiss = {
        showAddEditDialog = false
        customerToEdit = null
      },
      onSave = { entity ->
        viewModel.saveCustomer(entity)
        showAddEditDialog = false
        customerToEdit = null
      }
    )
  }

  // Customer Details Dialog
  if (selectedCustomer != null) {
    val cust = selectedCustomer!!
    AdminCustomerDetailsDialog(
      customer = cust,
      appLanguage = appLanguage,
      onDismiss = { viewModel.selectCustomerForDetails(null) },
      onEdit = {
        customerToEdit = cust
        showAddEditDialog = true
      },
      onDelete = {
        viewModel.deleteCustomer(cust.id)
      },
      onCall = { AdminIntentHelper.dialPhoneNumber(context, cust.phoneNumber) },
      onWhatsApp = {
        val msg = "வணக்கம் ${cust.fullName}, ஸ்ரீ விட்டல் ஜோதிடாலயம்: தங்களின் ஜாதகக் கோப்புகள் எங்கள் ஆவணத்தில் பதிவு செய்யப்பட்டுள்ளன."
        AdminIntentHelper.openWhatsAppChat(context, cust.phoneNumber, msg)
      },
      onSaveNotes = { notes ->
        viewModel.saveCustomer(cust.copy(notes = notes))
      }
    )
  }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          customerToEdit = null
          showAddEditDialog = true
        },
        containerColor = GeometricGoldPrimary,
        contentColor = Color.White,
        modifier = Modifier.testTag("admin_add_customer_fab")
      ) {
        Icon(Icons.Default.PersonAdd, contentDescription = "Add Customer")
      }
    },
    containerColor = GeometricBackground
  ) { paddingVals ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(paddingVals)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          TraditionalMotifBadge(
            text = if (appLanguage == AppLanguage.TAMIL) "வாடிக்கையாளர் ஆவணம்" else "Client Directory"
          )
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "ஜாதகக் குறிப்பேடு" else "Customer Profiles",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary
            )
          )
        }

        Surface(
          color = GeometricGoldContainer,
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = "${filteredCustomers.size} வாடிக்கையாளர்கள்",
            style = MaterialTheme.typography.labelSmall.copy(
              color = GeometricGoldDark,
              fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      // Search Bar
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { viewModel.setCustomerSearchQuery(it) },
        placeholder = {
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "பெயர், தொலைபேசி, ராசி, நட்சத்திரம்..." else "Search name, phone, rashi, star...",
            style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextTertiary)
          )
        },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = "Search", tint = GeometricGoldPrimary)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { viewModel.setCustomerSearchQuery("") }) {
              Icon(Icons.Default.Clear, contentDescription = "Clear", tint = GeometricTextSecondary)
            }
          }
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = GeometricSurface,
          unfocusedContainerColor = GeometricSurface,
          focusedBorderColor = GeometricGoldPrimary,
          unfocusedBorderColor = GeometricGoldBorderSubtle
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().height(52.dp)
      )

      // Customers List
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        if (filteredCustomers.isEmpty()) {
          item {
            Card(
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = GeometricSurface),
              modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
            ) {
              Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Icon(Icons.Default.PeopleOutline, contentDescription = null, tint = GeometricTextTertiary, modifier = Modifier.size(36.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = if (appLanguage == AppLanguage.TAMIL) "வாடிக்கையாளர்கள் எதுவும் கிடைக்கவில்லை" else "No customers found",
                  style = MaterialTheme.typography.bodyMedium.copy(color = GeometricTextSecondary)
                )
              }
            }
          }
        } else {
          items(filteredCustomers) { cust ->
            AdminCustomerCard(
              customer = cust,
              appLanguage = appLanguage,
              onClick = { viewModel.selectCustomerForDetails(cust) },
              onCall = { AdminIntentHelper.dialPhoneNumber(context, cust.phoneNumber) },
              onWhatsApp = {
                val msg = "வணக்கம் ${cust.fullName}, ஸ்ரீ விட்டல் ஜோதிடாலயம் சார்பாக ஜோதிடர் ஸ்ரீ ராஜகோபால் பேசுகிறேன்."
                AdminIntentHelper.openWhatsAppChat(context, cust.phoneNumber, msg)
              }
            )
          }
        }
      }
    }
  }
}

@Composable
fun AdminCustomerCard(
  customer: CustomerEntity,
  appLanguage: AppLanguage,
  onClick: () -> Unit,
  onCall: () -> Unit,
  onWhatsApp: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = GeometricSurface),
    border = CardDefaults.outlinedCardBorder().copy(
      width = 1.dp,
      brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.30f))
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(GeometricGoldContainer),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = customer.fullName.take(1).ifEmpty { "C" },
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = GeometricGoldDark
              )
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = customer.fullName,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = GeometricTextPrimary
              )
            )
            Text(
              text = customer.phoneNumber,
              style = MaterialTheme.typography.bodySmall.copy(
                color = GeometricTextSecondary,
                fontSize = 11.5.sp
              )
            )
          }
        }

        Surface(
          color = GeometricForestGreenContainer,
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = "${customer.totalConsultations} ஆலோசனைகள்",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = GeometricForestGreen
            ),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Astrology info badges
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        if (customer.rashiTamil.isNotBlank()) {
          Surface(
            color = GeometricSurfaceSub,
            shape = RoundedCornerShape(4.dp)
          ) {
            Text(
              text = "ராசி: ${customer.rashiTamil}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.5.sp,
                color = GeometricGoldDark,
                fontWeight = FontWeight.SemiBold
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        if (customer.nakshatraTamil.isNotBlank()) {
          Surface(
            color = GeometricSurfaceSub,
            shape = RoundedCornerShape(4.dp)
          ) {
            Text(
              text = "நட்சத்திரம்: ${customer.nakshatraTamil}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.5.sp,
                color = GeometricGoldDark,
                fontWeight = FontWeight.SemiBold
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        if (customer.placeOfBirth.isNotBlank()) {
          Surface(
            color = GeometricSurfaceSub,
            shape = RoundedCornerShape(4.dp)
          ) {
            Text(
              text = "ஊர்: ${customer.placeOfBirth}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.5.sp,
                color = GeometricTextSecondary
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }

      if (customer.notes.isNotBlank()) {
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          color = GeometricGoldContainer.copy(alpha = 0.35f),
          shape = RoundedCornerShape(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "குறிப்பு: ${customer.notes}",
            style = MaterialTheme.typography.bodySmall.copy(
              color = GeometricGoldDark,
              fontSize = 11.sp
            ),
            modifier = Modifier.padding(6.dp),
            maxLines = 2
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (customer.dateOfBirth.isNotBlank()) "பிறப்பு: ${customer.dateOfBirth}" else "",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = GeometricTextTertiary)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          IconButton(
            onClick = onCall,
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(GeometricForestGreenContainer)
          ) {
            Icon(Icons.Default.Call, contentDescription = "Call", tint = GeometricForestGreen, modifier = Modifier.size(16.dp))
          }

          IconButton(
            onClick = onWhatsApp,
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(GeometricForestGreenContainer)
          ) {
            Icon(Icons.Default.Chat, contentDescription = "WhatsApp", tint = GeometricForestGreen, modifier = Modifier.size(16.dp))
          }
        }
      }
    }
  }
}

@Composable
fun AdminCustomerDetailsDialog(
  customer: CustomerEntity,
  appLanguage: AppLanguage,
  onDismiss: () -> Unit,
  onEdit: () -> Unit,
  onDelete: () -> Unit,
  onCall: () -> Unit,
  onWhatsApp: () -> Unit,
  onSaveNotes: (String) -> Unit
) {
  var notesInput by remember(customer) { mutableStateOf(customer.notes) }
  var isEditingNotes by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = customer.fullName,
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
        )
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = GeometricTextSecondary)
        }
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = onCall,
            colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
          ) {
            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("அழைக்க", color = Color.White)
          }

          Button(
            onClick = onWhatsApp,
            colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(0.9f)
          ) {
            Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("WhatsApp", color = Color.White)
          }
        }

        HorizontalDivider(thickness = 1.dp, color = GeometricDivider)

        Text("தொலைபேசி எண்: ${customer.phoneNumber}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary))
        if (customer.email.isNotBlank()) {
          Text("மின்னஞ்சல்: ${customer.email}", style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary))
        }

        Surface(
          color = GeometricSurfaceSub,
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("ஜாதக பிறப்பு விவரங்கள்:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GeometricGoldDark))
            Text("• ராசி: ${customer.rashiTamil.ifEmpty { "குறிப்பிடப்படவில்லை" }}", style = MaterialTheme.typography.bodySmall)
            Text("• நட்சத்திரம்: ${customer.nakshatraTamil.ifEmpty { "குறிப்பிடப்படவில்லை" }}", style = MaterialTheme.typography.bodySmall)
            Text("• பிறந்த தேதி: ${customer.dateOfBirth.ifEmpty { "குறிப்பிடப்படவில்லை" }}", style = MaterialTheme.typography.bodySmall)
            Text("• பிறந்த நேரம்: ${customer.timeOfBirth.ifEmpty { "குறிப்பிடப்படவில்லை" }}", style = MaterialTheme.typography.bodySmall)
            Text("• பிறந்த இடம்: ${customer.placeOfBirth.ifEmpty { "குறிப்பிடப்படவில்லை" }}", style = MaterialTheme.typography.bodySmall)
          }
        }

        // Astrologer Notes
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("அப்பா ஜாதகக் குறிப்பு:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GeometricGoldDark))
            if (!isEditingNotes) {
              TextButton(onClick = { isEditingNotes = true }) {
                Text("திருத்து", fontSize = 11.sp, color = GeometricGoldPrimary)
              }
            }
          }

          if (isEditingNotes) {
            OutlinedTextField(
              value = notesInput,
              onValueChange = { notesInput = it },
              placeholder = { Text("கிரக பலன்கள், ஆலோசனை வரலாறு...") },
              modifier = Modifier.fillMaxWidth().height(80.dp),
              maxLines = 3
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
              onClick = {
                onSaveNotes(notesInput)
                isEditingNotes = false
              },
              colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary),
              shape = RoundedCornerShape(6.dp),
              modifier = Modifier.align(Alignment.End)
            ) {
              Text("சேமி", fontSize = 11.sp, color = Color.White)
            }
          } else {
            Surface(
              color = GeometricGoldContainer.copy(alpha = 0.35f),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = notesInput.ifEmpty { "குறிப்புகள் ஏதுமில்லை." },
                style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextPrimary),
                modifier = Modifier.padding(10.dp)
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = onEdit,
        colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary),
        shape = RoundedCornerShape(8.dp)
      ) {
        Text("விவரம் திருத்து", fontSize = 11.sp, color = Color.White)
      }
    },
    dismissButton = {
      TextButton(onClick = onDelete) {
        Text("நீக்கு", color = GeometricMaroon, fontSize = 11.sp)
      }
    },
    containerColor = GeometricSurface,
    shape = RoundedCornerShape(18.dp)
  )
}

@Composable
fun AdminAddEditCustomerDialog(
  customer: CustomerEntity?,
  appLanguage: AppLanguage,
  onDismiss: () -> Unit,
  onSave: (CustomerEntity) -> Unit
) {
  var name by remember(customer) { mutableStateOf(customer?.fullName ?: "") }
  var phone by remember(customer) { mutableStateOf(customer?.phoneNumber ?: "") }
  var email by remember(customer) { mutableStateOf(customer?.email ?: "") }
  var dob by remember(customer) { mutableStateOf(customer?.dateOfBirth ?: "") }
  var tob by remember(customer) { mutableStateOf(customer?.timeOfBirth ?: "") }
  var pob by remember(customer) { mutableStateOf(customer?.placeOfBirth ?: "") }
  var rashi by remember(customer) { mutableStateOf(customer?.rashiTamil ?: "") }
  var nakshatra by remember(customer) { mutableStateOf(customer?.nakshatraTamil ?: "") }
  var notes by remember(customer) { mutableStateOf(customer?.notes ?: "") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = if (customer != null) "வாடிக்கையாளர் விவரங்களைத் திருத்துக" else "புதிய வாடிக்கையாளர் சேர்க்க",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
      )
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("முழுப் பெயர் (Full Name)*") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = phone,
          onValueChange = { phone = it },
          label = { Text("தொலைபேசி எண் (Phone)*") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("மின்னஞ்சல் (Email - விருப்பத்தேர்வு)") },
          modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          OutlinedTextField(
            value = rashi,
            onValueChange = { rashi = it },
            label = { Text("ராசி (Rashi)") },
            placeholder = { Text("மேஷம்") },
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = nakshatra,
            onValueChange = { nakshatra = it },
            label = { Text("நட்சத்திரம்") },
            placeholder = { Text("அசுவினி") },
            modifier = Modifier.weight(1f)
          )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          OutlinedTextField(
            value = dob,
            onValueChange = { dob = it },
            label = { Text("பிறந்த தேதி") },
            placeholder = { Text("1992-10-24") },
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = tob,
            onValueChange = { tob = it },
            label = { Text("பிறந்த நேரம்") },
            placeholder = { Text("08:15 AM") },
            modifier = Modifier.weight(1f)
          )
        }

        OutlinedTextField(
          value = pob,
          onValueChange = { pob = it },
          label = { Text("பிறந்த இடம் / ஊர்") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = notes,
          onValueChange = { notes = it },
          label = { Text("அப்பா ஜாதகக் குறிப்புகள்") },
          modifier = Modifier.fillMaxWidth().height(80.dp),
          maxLines = 3
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (name.isNotBlank() && phone.isNotBlank()) {
            val entity = CustomerEntity(
              id = customer?.id ?: 0L,
              fullName = name.trim(),
              phoneNumber = phone.trim(),
              email = email.trim(),
              dateOfBirth = dob.trim(),
              timeOfBirth = tob.trim(),
              placeOfBirth = pob.trim(),
              rashiTamil = rashi.trim(),
              nakshatraTamil = nakshatra.trim(),
              notes = notes.trim(),
              totalConsultations = customer?.totalConsultations ?: 1,
              lastConsultationDate = customer?.lastConsultationDate ?: "2026-08-28"
            )
            onSave(entity)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary)
      ) {
        Text("சேமி", color = Color.White, fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("ரத்து", color = GeometricTextSecondary)
      }
    },
    containerColor = GeometricSurface,
    shape = RoundedCornerShape(18.dp)
  )
}
