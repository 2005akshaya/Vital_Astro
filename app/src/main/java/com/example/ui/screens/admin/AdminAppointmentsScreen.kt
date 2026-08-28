package com.example.ui.screens.admin

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.data.model.*
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.util.AdminIntentHelper
import com.example.ui.viewmodel.AdminAppointmentFilter
import com.example.ui.viewmodel.AstrologyViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminAppointmentsScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val appointments by viewModel.appointmentsList.collectAsState()
  val activeFilter by viewModel.adminAppointmentFilter.collectAsState()
  val searchQuery by viewModel.adminAppointmentSearch.collectAsState()
  val selectedAppointment by viewModel.selectedAppointment.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()
  val services by viewModel.servicesList.collectAsState()

  var showAddEditDialog by remember { mutableStateOf(false) }
  var appointmentToEdit by remember { mutableStateOf<AppointmentEntity?>(null) }
  var showRescheduleDialog by remember { mutableStateOf(false) }
  var rescheduleDate by remember { mutableStateOf("") }
  var rescheduleSlot by remember { mutableStateOf("முற்பகல் 11:00 - 12:00") }

  val todayDateStr = remember {
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
  }

  // Filter & Search Logic
  val filteredAppointments = remember(appointments, activeFilter, searchQuery, todayDateStr) {
    appointments.filter { apt ->
      val matchesSearch = if (searchQuery.isBlank()) true else {
        apt.clientName.contains(searchQuery, ignoreCase = true) ||
          apt.phoneNumber.contains(searchQuery, ignoreCase = true) ||
          apt.referenceNumber.contains(searchQuery, ignoreCase = true) ||
          apt.serviceType.contains(searchQuery, ignoreCase = true) ||
          apt.preferredDate.contains(searchQuery, ignoreCase = true)
      }

      val matchesFilter = when (activeFilter) {
        AdminAppointmentFilter.ALL -> true
        AdminAppointmentFilter.TODAY -> apt.preferredDate == todayDateStr || apt.preferredDate.contains("இன்று") || apt.preferredDate.contains("Today")
        AdminAppointmentFilter.UPCOMING -> apt.status == AppointmentStatus.CONFIRMED || apt.status == AppointmentStatus.PENDING
        AdminAppointmentFilter.PENDING -> apt.status == AppointmentStatus.PENDING
        AdminAppointmentFilter.CONFIRMED -> apt.status == AppointmentStatus.CONFIRMED
        AdminAppointmentFilter.COMPLETED -> apt.status == AppointmentStatus.COMPLETED
        AdminAppointmentFilter.CANCELLED -> apt.status == AppointmentStatus.CANCELLED
      }

      matchesSearch && matchesFilter
    }
  }

  // Add / Edit Appointment Dialog
  if (showAddEditDialog) {
    AdminAddEditAppointmentDialog(
      appointment = appointmentToEdit,
      services = services,
      appLanguage = appLanguage,
      onDismiss = {
        showAddEditDialog = false
        appointmentToEdit = null
      },
      onSave = { entity ->
        if (entity.id == 0L) {
          viewModel.updateClientName(entity.clientName)
          viewModel.updatePhoneNumber(entity.phoneNumber)
          viewModel.updateEmail(entity.email)
          viewModel.updateDateOfBirth(entity.dateOfBirth)
          viewModel.updateTimeOfBirth(entity.timeOfBirth)
          viewModel.updatePlaceOfBirth(entity.placeOfBirth)
          viewModel.updateServiceType(entity.serviceType)
          viewModel.updatePreferredDate(entity.preferredDate)
          viewModel.updatePreferredTimeSlot(entity.preferredTimeSlot)
          viewModel.updateConsultationMode(entity.consultationMode)
          viewModel.updateBirthDetailsNotes(entity.birthDetailsNotes)
          viewModel.updateAdminNotes(entity.adminPrivateNotes)
          viewModel.updateAppointmentAmount(entity.amount)
          viewModel.updateAppointmentPaymentMethod(entity.paymentMethod)
          viewModel.updateAppointmentPaymentStatus(entity.paymentStatus)
          viewModel.updateAppointmentStatusForm(entity.status)
          viewModel.submitAppointment()
        } else {
          viewModel.populateFormForEdit(entity)
          viewModel.submitAppointment()
        }
        showAddEditDialog = false
        appointmentToEdit = null
      }
    )
  }

  // Reschedule Dialog
  if (showRescheduleDialog && selectedAppointment != null) {
    val currentApt = selectedAppointment!!
    AlertDialog(
      onDismissRequest = { showRescheduleDialog = false },
      title = {
        Text(
          text = if (appLanguage == AppLanguage.TAMIL) "முன்பதிவை மறுதிட்டமிடுக" else "Reschedule Appointment",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
        )
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "${currentApt.clientName} - ${currentApt.serviceType}",
            style = MaterialTheme.typography.bodyMedium.copy(color = GeometricGoldDark, fontWeight = FontWeight.SemiBold)
          )
          OutlinedTextField(
            value = rescheduleDate,
            onValueChange = { rescheduleDate = it },
            label = { Text(if (appLanguage == AppLanguage.TAMIL) "புதிய நாள் (YYYY-MM-DD)" else "New Date") },
            placeholder = { Text("2026-08-30") },
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = rescheduleSlot,
            onValueChange = { rescheduleSlot = it },
            label = { Text(if (appLanguage == AppLanguage.TAMIL) "புதிய நேரம்" else "New Time Slot") },
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (rescheduleDate.isNotBlank()) {
              viewModel.rescheduleAppointment(currentApt.id, rescheduleDate, rescheduleSlot)
              showRescheduleDialog = false
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary)
        ) {
          Text(if (appLanguage == AppLanguage.TAMIL) "மறுதிட்டமிடு" else "Reschedule", color = Color.White, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showRescheduleDialog = false }) {
          Text(if (appLanguage == AppLanguage.TAMIL) "ரத்து" else "Cancel", color = GeometricTextSecondary)
        }
      },
      containerColor = GeometricSurface,
      shape = RoundedCornerShape(16.dp)
    )
  }

  // Appointment Details Modal Sheet / Dialog
  if (selectedAppointment != null) {
    val apt = selectedAppointment!!
    AdminAppointmentDetailsDialog(
      appointment = apt,
      appLanguage = appLanguage,
      onDismiss = { viewModel.selectAppointmentForDetails(null) },
      onStatusChange = { newStatus -> viewModel.updateAppointmentStatus(apt.id, newStatus) },
      onPaymentStatusChange = { newPayStatus -> viewModel.updateAppointmentPaymentStatus(apt.id, newPayStatus) },
      onSaveNotes = { notes -> viewModel.updateAppointmentNotes(apt.id, notes) },
      onEdit = {
        appointmentToEdit = apt
        showAddEditDialog = true
      },
      onReschedule = {
        rescheduleDate = apt.preferredDate
        rescheduleSlot = apt.preferredTimeSlot
        showRescheduleDialog = true
      },
      onDelete = {
        viewModel.deleteAppointment(apt.id)
      },
      onCall = { AdminIntentHelper.dialPhoneNumber(context, apt.phoneNumber) },
      onWhatsApp = {
        val msg = "வணக்கம் ${apt.clientName}, ஸ்ரீ விட்டல் ஜோதிடாலயத்தில் தங்களின் முன்பதிவு விவரம்: ${apt.serviceType}, ${apt.preferredDate} (${apt.preferredTimeSlot})."
        AdminIntentHelper.openWhatsAppChat(context, apt.phoneNumber, msg)
      }
    )
  }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          appointmentToEdit = null
          showAddEditDialog = true
        },
        containerColor = GeometricGoldPrimary,
        contentColor = Color.White,
        modifier = Modifier.testTag("admin_add_appointment_fab")
      ) {
        Icon(Icons.Default.Add, contentDescription = "Add Appointment")
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
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          TraditionalMotifBadge(
            text = if (appLanguage == AppLanguage.TAMIL) "முன்பதிவு மேலாண்மை" else "Appointments Center"
          )
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "அனைத்து முன்பதிவுகள்" else "Appointments List",
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
            text = "${filteredAppointments.size} / ${appointments.size}",
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
        onValueChange = { viewModel.setAdminAppointmentSearch(it) },
        placeholder = {
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "பெயர், தொலைபேசி, எண் தேடுக..." else "Search name, phone, ref...",
            style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextTertiary)
          )
        },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = "Search", tint = GeometricGoldPrimary)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { viewModel.setAdminAppointmentSearch("") }) {
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

      // Filter Chips Row
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(AdminAppointmentFilter.values()) { filter ->
          val isSelected = activeFilter == filter
          FilterChip(
            selected = isSelected,
            onClick = { viewModel.setAdminAppointmentFilter(filter) },
            label = {
              Text(
                text = if (appLanguage == AppLanguage.TAMIL) filter.labelTamil else filter.labelEnglish,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  fontSize = 11.sp
                )
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = GeometricGoldPrimary,
              selectedLabelColor = Color.White,
              containerColor = GeometricSurfaceSub,
              labelColor = GeometricTextPrimary
            ),
            border = FilterChipDefaults.filterChipBorder(
              enabled = true,
              selected = isSelected,
              borderColor = if (isSelected) GeometricGoldPrimary else GeometricGoldBorderSubtle
            ),
            shape = RoundedCornerShape(16.dp)
          )
        }
      }

      // Appointment Items List
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        if (filteredAppointments.isEmpty()) {
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
                Icon(
                  imageVector = Icons.Default.EventBusy,
                  contentDescription = null,
                  tint = GeometricTextTertiary,
                  modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = if (appLanguage == AppLanguage.TAMIL) "பொருத்தமான முன்பதிவுகள் இல்லை" else "No matching appointments found",
                  style = MaterialTheme.typography.bodyMedium.copy(color = GeometricTextSecondary)
                )
              }
            }
          }
        } else {
          items(filteredAppointments) { apt ->
            AdminAppointmentCard(
              appointment = apt,
              appLanguage = appLanguage,
              onClick = { viewModel.selectAppointmentForDetails(apt) },
              onCall = { AdminIntentHelper.dialPhoneNumber(context, apt.phoneNumber) },
              onWhatsApp = {
                val msg = "வணக்கம் ${apt.clientName}, ஸ்ரீ விட்டல் ஜோதிடாலயம்: உங்கள் முன்பதிவு ${apt.serviceType} (${apt.preferredDate}, ${apt.preferredTimeSlot})."
                AdminIntentHelper.openWhatsAppChat(context, apt.phoneNumber, msg)
              },
              onStatusChange = { newStatus -> viewModel.updateAppointmentStatus(apt.id, newStatus) }
            )
          }
        }
      }
    }
  }
}

@Composable
fun AdminAppointmentCard(
  appointment: AppointmentEntity,
  appLanguage: AppLanguage,
  onClick: () -> Unit,
  onCall: () -> Unit,
  onWhatsApp: () -> Unit,
  onStatusChange: (AppointmentStatus) -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = GeometricSurface),
    border = CardDefaults.outlinedCardBorder().copy(
      width = 1.dp,
      brush = androidx.compose.ui.graphics.SolidColor(
        when (appointment.status) {
          AppointmentStatus.CONFIRMED -> GeometricForestGreen.copy(alpha = 0.35f)
          AppointmentStatus.PENDING -> GeometricGoldMetallic.copy(alpha = 0.35f)
          AppointmentStatus.COMPLETED -> GeometricGoldDark.copy(alpha = 0.30f)
          AppointmentStatus.CANCELLED -> GeometricMaroon.copy(alpha = 0.35f)
          AppointmentStatus.RESCHEDULED -> GeometricGoldDark.copy(alpha = 0.35f)
        }
      )
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
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = appointment.clientName,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary
            )
          )
          Text(
            text = "Ref: ${appointment.referenceNumber}",
            style = MaterialTheme.typography.bodySmall.copy(
              color = GeometricTextTertiary,
              fontSize = 10.5.sp
            )
          )
        }

        // Status Tag
        Surface(
          color = when (appointment.status) {
            AppointmentStatus.CONFIRMED -> GeometricForestGreenContainer
            AppointmentStatus.COMPLETED -> GeometricGoldContainer
            AppointmentStatus.CANCELLED -> GeometricMaroonContainer
            AppointmentStatus.PENDING -> GeometricSurfaceSub
            AppointmentStatus.RESCHEDULED -> GeometricGoldContainer
          },
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = appointment.status.labelTamil,
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = when (appointment.status) {
                AppointmentStatus.CONFIRMED -> GeometricForestGreen
                AppointmentStatus.COMPLETED -> GeometricGoldDark
                AppointmentStatus.CANCELLED -> GeometricMaroon
                AppointmentStatus.PENDING -> GeometricTextPrimary
                AppointmentStatus.RESCHEDULED -> GeometricGoldDark
              }
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "🔮 ${appointment.serviceType}",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = GeometricGoldDark,
          fontWeight = FontWeight.SemiBold
        )
      )

      Row(
        modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "📅 ${appointment.preferredDate}",
          style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary, fontSize = 12.sp)
        )
        Text(
          text = "⏰ ${appointment.preferredTimeSlot}",
          style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary, fontSize = 12.sp)
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "📍 ${appointment.consultationMode.labelTamil}",
          style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary, fontSize = 12.sp)
        )
        Text(
          text = "💰 ₹${appointment.amount.toInt()} • ${appointment.paymentStatus.labelTamil}",
          style = MaterialTheme.typography.bodySmall.copy(
            color = if (appointment.paymentStatus == PaymentStatus.PAID) GeometricForestGreen else GeometricGoldDark,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        )
      }

      if (appointment.dateOfBirth.isNotBlank() || appointment.placeOfBirth.isNotBlank()) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "பிறப்பு விவரம்: ${appointment.dateOfBirth} ${appointment.timeOfBirth} (${appointment.placeOfBirth})",
          style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextTertiary, fontSize = 11.sp)
        )
      }

      if (appointment.adminPrivateNotes.isNotBlank()) {
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          color = GeometricGoldContainer.copy(alpha = 0.35f),
          shape = RoundedCornerShape(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "அப்பா குறிப்பு: ${appointment.adminPrivateNotes}",
            style = MaterialTheme.typography.bodySmall.copy(
              color = GeometricGoldDark,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(6.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Bottom Row with Quick Actions
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          IconButton(
            onClick = onCall,
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(GeometricForestGreenContainer)
          ) {
            Icon(Icons.Default.Call, contentDescription = "Call", tint = GeometricForestGreen, modifier = Modifier.size(16.dp))
          }

          IconButton(
            onClick = onWhatsApp,
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(GeometricForestGreenContainer)
          ) {
            Icon(Icons.Default.Chat, contentDescription = "WhatsApp", tint = GeometricForestGreen, modifier = Modifier.size(16.dp))
          }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          if (appointment.status == AppointmentStatus.PENDING) {
            Button(
              onClick = { onStatusChange(AppointmentStatus.CONFIRMED) },
              colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
              shape = RoundedCornerShape(6.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
              modifier = Modifier.height(30.dp)
            ) {
              Text("உறுதி செய்", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
          }

          if (appointment.status == AppointmentStatus.CONFIRMED) {
            Button(
              onClick = { onStatusChange(AppointmentStatus.COMPLETED) },
              colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary),
              shape = RoundedCornerShape(6.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
              modifier = Modifier.height(30.dp)
            ) {
              Text("நிறைவு", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

@Composable
fun AdminAppointmentDetailsDialog(
  appointment: AppointmentEntity,
  appLanguage: AppLanguage,
  onDismiss: () -> Unit,
  onStatusChange: (AppointmentStatus) -> Unit,
  onPaymentStatusChange: (PaymentStatus) -> Unit,
  onSaveNotes: (String) -> Unit,
  onEdit: () -> Unit,
  onReschedule: () -> Unit,
  onDelete: () -> Unit,
  onCall: () -> Unit,
  onWhatsApp: () -> Unit
) {
  var notesInput by remember(appointment) { mutableStateOf(appointment.adminPrivateNotes) }
  var isEditingNotes by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = appointment.clientName,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
          )
          Text(
            text = "Ref: ${appointment.referenceNumber}",
            style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextTertiary)
          )
        }
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
        // Contact Buttons
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
            Text("அழைக்க (${appointment.phoneNumber})", fontSize = 11.sp, color = Color.White)
          }

          Button(
            onClick = onWhatsApp,
            colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(0.8f)
          ) {
            Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("WhatsApp", fontSize = 11.sp, color = Color.White)
          }
        }

        HorizontalDivider(thickness = 1.dp, color = GeometricDivider)

        // Details Grid
        Text(text = "சேவை: ${appointment.serviceType}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = GeometricGoldDark))
        Text(text = "தேதி & நேரம்: ${appointment.preferredDate} (${appointment.preferredTimeSlot})", style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextPrimary))
        Text(text = "ஆலோசனை முறை: ${appointment.consultationMode.labelTamil}", style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextPrimary))
        if (appointment.email.isNotBlank()) {
          Text(text = "மின்னஞ்சல்: ${appointment.email}", style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary))
        }

        // Horoscope birth info
        if (appointment.dateOfBirth.isNotBlank() || appointment.timeOfBirth.isNotBlank() || appointment.placeOfBirth.isNotBlank()) {
          Surface(
            color = GeometricSurfaceSub,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("ஜாதக பிறப்பு விவரங்கள்:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GeometricGoldDark))
              Text("• பிறந்த தேதி: ${appointment.dateOfBirth.ifEmpty { "தெரியவில்லை" }}", style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextPrimary))
              Text("• பிறந்த நேரம்: ${appointment.timeOfBirth.ifEmpty { "தெரியவில்லை" }}", style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextPrimary))
              Text("• பிறந்த ஊர்: ${appointment.placeOfBirth.ifEmpty { "தெரியவில்லை" }}", style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextPrimary))
            }
          }
        }

        // Client Question / Notes
        if (appointment.birthDetailsNotes.isNotBlank()) {
          Surface(
            color = GeometricSurfaceSub,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text("வாடிக்கையாளர் கேள்வி / விவரம்:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextSecondary))
              Text(appointment.birthDetailsNotes, style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextPrimary))
            }
          }
        }

        // Payment status & amount
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text("கட்டணம்: ₹${appointment.amount.toInt()}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = GeometricForestGreen))
            Text("முறை: ${appointment.paymentMethod}", style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextTertiary))
          }

          FilterChip(
            selected = appointment.paymentStatus == PaymentStatus.PAID,
            onClick = {
              val next = if (appointment.paymentStatus == PaymentStatus.PAID) PaymentStatus.PENDING else PaymentStatus.PAID
              onPaymentStatusChange(next)
            },
            label = { Text(appointment.paymentStatus.labelTamil, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = GeometricForestGreenContainer,
              selectedLabelColor = GeometricForestGreen,
              containerColor = GeometricGoldContainer,
              labelColor = GeometricGoldDark
            )
          )
        }

        // Astrologer Private Notes Box
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("ஜோதிடர் தனிப்பட்ட குறிப்புகள் (Private Notes):", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GeometricGoldDark))
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
              placeholder = { Text("கிரக நிலை, பரிகாரங்கள், ஆலோசனைக் குறிப்புகள்...") },
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
                text = notesInput.ifEmpty { "குறிப்புகள் ஏதுமில்லை. சேர்க்க 'திருத்து' அழுத்தவும்." },
                style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextPrimary),
                modifier = Modifier.padding(10.dp)
              )
            }
          }
        }

        // Status change row
        Column {
          Text("முன்பதிவு நிலையை மாற்றுக:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextSecondary))
          Spacer(modifier = Modifier.height(4.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            AppointmentStatus.values().forEach { st ->
              val isSel = appointment.status == st
              Surface(
                color = if (isSel) GeometricGoldPrimary else GeometricSurfaceSub,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                  .weight(1f)
                  .clickable { onStatusChange(st) }
              ) {
                Text(
                  text = st.labelTamil,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.5.sp,
                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSel) Color.White else GeometricTextSecondary
                  ),
                  modifier = Modifier.padding(vertical = 6.dp),
                  textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
              }
            }
          }
        }
      }
    },
    confirmButton = {
      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        OutlinedButton(
          onClick = onReschedule,
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("மறுதிட்டமிடு", fontSize = 11.sp, color = GeometricGoldDark)
        }

        Button(
          onClick = onEdit,
          colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("முழு விவரம் திருத்து", fontSize = 11.sp, color = Color.White)
        }
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
fun AdminAddEditAppointmentDialog(
  appointment: AppointmentEntity?,
  services: List<ServiceEntity>,
  appLanguage: AppLanguage,
  onDismiss: () -> Unit,
  onSave: (AppointmentEntity) -> Unit
) {
  var name by remember(appointment) { mutableStateOf(appointment?.clientName ?: "") }
  var phone by remember(appointment) { mutableStateOf(appointment?.phoneNumber ?: "") }
  var email by remember(appointment) { mutableStateOf(appointment?.email ?: "") }
  var dob by remember(appointment) { mutableStateOf(appointment?.dateOfBirth ?: "") }
  var tob by remember(appointment) { mutableStateOf(appointment?.timeOfBirth ?: "") }
  var pob by remember(appointment) { mutableStateOf(appointment?.placeOfBirth ?: "") }
  var serviceType by remember(appointment) { mutableStateOf(appointment?.serviceType ?: (services.firstOrNull()?.titleTamil ?: "பொது ஜாதக ஆலோசனை")) }
  var date by remember(appointment) {
    mutableStateOf(appointment?.preferredDate ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
  }
  var timeSlot by remember(appointment) { mutableStateOf(appointment?.preferredTimeSlot ?: "முற்பகல் 11:00 - 12:00") }
  var mode by remember(appointment) { mutableStateOf(appointment?.consultationMode ?: ConsultationMode.DIRECT_VISIT) }
  var birthNotes by remember(appointment) { mutableStateOf(appointment?.birthDetailsNotes ?: "") }
  var adminNotes by remember(appointment) { mutableStateOf(appointment?.adminPrivateNotes ?: "") }
  var amountStr by remember(appointment) { mutableStateOf((appointment?.amount ?: 500.0).toInt().toString()) }
  var paymentStatus by remember(appointment) { mutableStateOf(appointment?.paymentStatus ?: PaymentStatus.PENDING) }
  var paymentMethod by remember(appointment) { mutableStateOf(appointment?.paymentMethod ?: "GPay / PhonePe") }
  var status by remember(appointment) { mutableStateOf(appointment?.status ?: AppointmentStatus.CONFIRMED) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = if (appointment != null) "முன்பதிவைத் திருத்துக" else "புதிய முன்பதிவு சேர்க்க",
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
          label = { Text("வாடிக்கையாளர் பெயர் (Name)*") },
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
            value = dob,
            onValueChange = { dob = it },
            label = { Text("பிறந்த தேதி") },
            placeholder = { Text("1995-05-15") },
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = tob,
            onValueChange = { tob = it },
            label = { Text("பிறந்த நேரம்") },
            placeholder = { Text("06:30 AM") },
            modifier = Modifier.weight(1f)
          )
        }

        OutlinedTextField(
          value = pob,
          onValueChange = { pob = it },
          label = { Text("பிறந்த ஊர் / இடம்") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = serviceType,
          onValueChange = { serviceType = it },
          label = { Text("சேவை வகை") },
          modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("ஆலோசனை நாள்") },
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = timeSlot,
            onValueChange = { timeSlot = it },
            label = { Text("நேரம்") },
            modifier = Modifier.weight(1f)
          )
        }

        // Mode selection
        Text("ஆலோசனை முறை:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          ConsultationMode.values().forEach { m ->
            val isSel = mode == m
            Surface(
              color = if (isSel) GeometricGoldPrimary else GeometricSurfaceSub,
              shape = RoundedCornerShape(6.dp),
              modifier = Modifier
                .weight(1f)
                .clickable { mode = m }
            ) {
              Text(
                text = m.labelTamil,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 9.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSel) Color.White else GeometricTextSecondary
                ),
                modifier = Modifier.padding(vertical = 6.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
              )
            }
          }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          OutlinedTextField(
            value = amountStr,
            onValueChange = { amountStr = it },
            label = { Text("தொகை (₹)") },
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = paymentMethod,
            onValueChange = { paymentMethod = it },
            label = { Text("கட்டண முறை") },
            modifier = Modifier.weight(1f)
          )
        }

        OutlinedTextField(
          value = birthNotes,
          onValueChange = { birthNotes = it },
          label = { Text("வாடிக்கையாளர் கேள்விகள்") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = adminNotes,
          onValueChange = { adminNotes = it },
          label = { Text("அப்பா தனிப்பட்ட குறிப்புகள்") },
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (name.isNotBlank() && phone.isNotBlank()) {
            val entity = AppointmentEntity(
              id = appointment?.id ?: 0L,
              referenceNumber = appointment?.referenceNumber ?: "SVJ-${(1000..9999).random()}",
              clientName = name.trim(),
              phoneNumber = phone.trim(),
              email = email.trim(),
              dateOfBirth = dob.trim(),
              timeOfBirth = tob.trim(),
              placeOfBirth = pob.trim(),
              serviceType = serviceType.trim(),
              preferredDate = date.trim(),
              preferredTimeSlot = timeSlot.trim(),
              consultationMode = mode,
              birthDetailsNotes = birthNotes.trim(),
              adminPrivateNotes = adminNotes.trim(),
              amount = amountStr.toDoubleOrNull() ?: 500.0,
              paymentStatus = paymentStatus,
              paymentMethod = paymentMethod.trim(),
              status = status,
              createdAt = appointment?.createdAt ?: System.currentTimeMillis()
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
