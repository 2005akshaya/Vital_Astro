package com.example.ui.screens.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.PaymentEntity
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AstrologyViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminPaymentsScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val analytics by viewModel.analytics.collectAsState()
  val payments by viewModel.paymentsList.collectAsState()
  val config by viewModel.practiceConfig.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  var showAddPaymentDialog by remember { mutableStateOf(false) }

  if (showAddPaymentDialog) {
    AdminAddPaymentDialog(
      appLanguage = appLanguage,
      onDismiss = { showAddPaymentDialog = false },
      onSave = { entity ->
        viewModel.savePayment(entity)
        showAddPaymentDialog = false
      }
    )
  }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = { showAddPaymentDialog = true },
        containerColor = GeometricForestGreen,
        contentColor = Color.White,
        modifier = Modifier.testTag("admin_add_payment_fab")
      ) {
        Icon(Icons.Default.Add, contentDescription = "Add Payment")
      }
    },
    containerColor = GeometricBackground
  ) { paddingVals ->
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .padding(paddingVals)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            TraditionalMotifBadge(
              text = if (appLanguage == AppLanguage.TAMIL) "வருமானம் & கட்டணங்கள்" else "Revenue & Payments"
            )
            Text(
              text = if (appLanguage == AppLanguage.TAMIL) "கட்டணக் கணக்கு விவரங்கள்" else "Financial Overview",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = GeometricTextPrimary
              )
            )
          }
        }
      }

      // Revenue Metrics Grid
      item {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            RevenueCard(
              title = if (appLanguage == AppLanguage.TAMIL) "இன்றைய வருமானம்" else "Today's Income",
              amount = "₹${analytics?.todayRevenue?.toInt() ?: 1000}",
              subtext = "இன்றைய வரவு",
              color = GeometricForestGreen,
              modifier = Modifier.weight(1f)
            )
            RevenueCard(
              title = if (appLanguage == AppLanguage.TAMIL) "இந்த வார வருமானம்" else "This Week",
              amount = "₹${analytics?.weeklyRevenue?.toInt() ?: 4500}",
              subtext = "7 நாட்கள் வரவு",
              color = GeometricGoldDark,
              modifier = Modifier.weight(1f)
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            RevenueCard(
              title = if (appLanguage == AppLanguage.TAMIL) "இந்த மாத வருமானம்" else "This Month",
              amount = "₹${analytics?.monthlyRevenue?.toInt() ?: 18500}",
              subtext = "நடப்பு மாதம்",
              color = GeometricGoldPrimary,
              modifier = Modifier.weight(1f)
            )
            RevenueCard(
              title = if (appLanguage == AppLanguage.TAMIL) "மொத்த வருமானம்" else "Total Revenue",
              amount = "₹${analytics?.totalRevenue?.toInt() ?: 4500}",
              subtext = "${payments.size} பரிவர்த்தனைகள்",
              color = GeometricForestGreen,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }

      // Astrologer UPI Information Banner
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = GeometricSurface),
          border = CardDefaults.outlinedCardBorder().copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.35f))
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.QrCode, contentDescription = null, tint = GeometricGoldPrimary, modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "UPI / GPay / PhonePe: ${config.phoneNumber}",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
              )
              Text(
                text = "கணக்கு பெயர்: ${config.astrologerNameTamil} (${config.titleTamil})",
                style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary, fontSize = 11.5.sp)
              )
            }
          }
        }
      }

      // Payments Log Section
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "பரிவர்த்தனை பதிவுகள் (${payments.size})" else "Payment Records (${payments.size})",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary
            )
          )
        }
      }

      items(payments) { payment ->
        AdminPaymentCard(payment = payment, appLanguage = appLanguage)
      }
    }
  }
}

@Composable
fun RevenueCard(
  title: String,
  amount: String,
  subtext: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = GeometricSurface),
    border = CardDefaults.outlinedCardBorder().copy(
      width = 1.dp,
      brush = androidx.compose.ui.graphics.SolidColor(color.copy(alpha = 0.35f))
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
          color = GeometricTextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = amount,
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.Bold,
          color = color,
          fontSize = 20.sp
        )
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = subtext,
        style = MaterialTheme.typography.bodySmall.copy(
          color = GeometricTextTertiary,
          fontSize = 10.sp
        )
      )
    }
  }
}

@Composable
fun AdminPaymentCard(
  payment: PaymentEntity,
  appLanguage: AppLanguage
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = GeometricSurface),
    border = CardDefaults.outlinedCardBorder().copy(
      width = 1.dp,
      brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldBorderSubtle)
    ),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = payment.customerName,
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = GeometricTextPrimary
          )
        )
        Text(
          text = "${payment.serviceName} • ${payment.paymentMethod}",
          style = MaterialTheme.typography.bodySmall.copy(
            color = GeometricTextSecondary,
            fontSize = 11.5.sp
          )
        )
        Text(
          text = payment.paymentDate,
          style = MaterialTheme.typography.bodySmall.copy(
            color = GeometricTextTertiary,
            fontSize = 10.5.sp
          )
        )
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = "₹${payment.amount.toInt()}",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = GeometricForestGreen
          )
        )
        Surface(
          color = if (payment.status == "COMPLETED") GeometricForestGreenContainer else GeometricGoldContainer,
          shape = RoundedCornerShape(4.dp)
        ) {
          Text(
            text = if (payment.status == "COMPLETED") "பெறப்பட்டது" else "நிலுவை",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = if (payment.status == "COMPLETED") GeometricForestGreen else GeometricGoldDark
            ),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }
    }
  }
}

@Composable
fun AdminAddPaymentDialog(
  appLanguage: AppLanguage,
  onDismiss: () -> Unit,
  onSave: (PaymentEntity) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var service by remember { mutableStateOf("பொது ஜாதக ஆலோசனை") }
  var amountStr by remember { mutableStateOf("500") }
  var method by remember { mutableStateOf("நேரடி ரொக்கம் (Cash)") }
  var dateStr by remember {
    mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = "புதிய கட்டண வரவு சேர்க்க",
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
          label = { Text("வாடிக்கையாளர் பெயர்*") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = service,
          onValueChange = { service = it },
          label = { Text("சேவை பெயர்*") },
          modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          OutlinedTextField(
            value = amountStr,
            onValueChange = { amountStr = it },
            label = { Text("தொகை (₹)*") },
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = method,
            onValueChange = { method = it },
            label = { Text("கட்டண முறை") },
            modifier = Modifier.weight(1f)
          )
        }

        OutlinedTextField(
          value = dateStr,
          onValueChange = { dateStr = it },
          label = { Text("தேதி (YYYY-MM-DD)") },
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (name.isNotBlank()) {
            val entity = PaymentEntity(
              id = 0L,
              appointmentId = 0L,
              customerName = name.trim(),
              serviceName = service.trim(),
              amount = amountStr.toDoubleOrNull() ?: 500.0,
              paymentMethod = method.trim(),
              status = "COMPLETED",
              transactionRef = "MANUAL-${System.currentTimeMillis().toString().takeLast(6)}",
              paymentDate = dateStr.trim(),
              createdAt = System.currentTimeMillis()
            )
            onSave(entity)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen)
      ) {
        Text("வரவு வை", color = Color.White, fontWeight = FontWeight.Bold)
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
