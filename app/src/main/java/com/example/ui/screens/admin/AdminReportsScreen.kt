package com.example.ui.screens.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.AppointmentStatus
import com.example.data.model.ConsultationMode
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun AdminReportsScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val analytics by viewModel.analytics.collectAsState()
  val appointments by viewModel.appointmentsList.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  val modeCounts = remember(appointments) {
    ConsultationMode.values().map { mode ->
      val count = appointments.count { it.consultationMode == mode }
      Pair(mode, count)
    }
  }

  val serviceCounts = remember(appointments) {
    appointments.groupBy { it.serviceType }
      .mapValues { entry -> entry.value.size }
      .toList()
      .sortedByDescending { it.second }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(GeometricBackground)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Column {
        TraditionalMotifBadge(
          text = if (appLanguage == AppLanguage.TAMIL) "அறிக்கைகள் & பகுப்பாய்வு" else "Reports & Analytics"
        )
        Text(
          text = if (appLanguage == AppLanguage.TAMIL) "தொழில் வளர்ச்சி & புள்ளிவிவரங்கள்" else "Business Performance Metrics",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = GeometricTextPrimary
          )
        )
      }
    }

    // Top Level Summary Cards
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          AdminMetricCard(
            title = "மொத்த முன்பதிவுகள்",
            count = "${appointments.size}",
            subtext = "${appointments.count { it.status == AppointmentStatus.COMPLETED }} நிறைவடைந்தவை",
            color = GeometricGoldPrimary,
            icon = Icons.Default.Assessment,
            modifier = Modifier.weight(1f)
          )
          AdminMetricCard(
            title = "மொத்த வருவாய்",
            count = "₹${analytics?.totalRevenue?.toInt() ?: 4500}",
            subtext = "மாத வருவாய்: ₹${analytics?.monthlyRevenue?.toInt() ?: 18500}",
            color = GeometricForestGreen,
            icon = Icons.Default.Payments,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // Consultation Modes Breakdown
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
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "ஆலோசனை முறைகள் (Modes Breakdown):",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
          )

          modeCounts.forEach { (mode, count) ->
            val percentage = if (appointments.isNotEmpty()) (count.toFloat() / appointments.size.toFloat()) else 0f
            Column {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = mode.labelTamil,
                  style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = GeometricTextPrimary)
                )
                Text(
                  text = "$count முன்பதிவுகள் (${(percentage * 100).toInt()}%)",
                  style = MaterialTheme.typography.bodySmall.copy(color = GeometricGoldDark, fontWeight = FontWeight.Bold)
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              LinearProgressIndicator(
                progress = { percentage },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(8.dp)
                  .clip(RoundedCornerShape(4.dp)),
                color = when (mode) {
                  ConsultationMode.DIRECT_VISIT -> GeometricGoldPrimary
                  ConsultationMode.PHONE_CALL -> GeometricForestGreen
                  ConsultationMode.WHATSAPP_CHAT -> GeometricGoldDark
                  ConsultationMode.GOOGLE_MEET -> GeometricMaroon
                },
                trackColor = GeometricSurfaceSub
              )
            }
          }
        }
      }
    }

    // Top Booked Services
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
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "அதிகம் கேட்கப்படும் ஜோதிட சேவைகள்:",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
          )

          serviceCounts.take(5).forEach { (service, count) ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(GeometricGoldPrimary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = service,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = GeometricTextPrimary
                  )
                )
              }

              Surface(
                color = GeometricGoldContainer,
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = "$count முறைகள்",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GeometricGoldDark
                  ),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
          }
        }
      }
    }
  }
}
