package com.example.ui.screens.admin

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.util.AdminIntentHelper
import com.example.ui.viewmodel.AstrologyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AdminCalendarScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val appointments by viewModel.appointmentsList.collectAsState()
  val selectedDate by viewModel.selectedCalendarDate.collectAsState()
  val config by viewModel.practiceConfig.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  val calendar = Calendar.getInstance()
  val dateList = remember {
    val list = mutableListOf<Triple<String, String, String>>() // (yyyy-MM-dd, EEE, dd MMM)
    val sdfKey = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val sdfDay = SimpleDateFormat("EEE", Locale.getDefault())
    val sdfDate = SimpleDateFormat("dd MMM", Locale.getDefault())
    for (i in 0..14) {
      val d = calendar.time
      list.add(Triple(sdfKey.format(d), sdfDay.format(d), sdfDate.format(d)))
      calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    list
  }

  // Pre-defined time slots from practice hours
  val morningSlots = listOf(
    "முற்பகல் 10:00 - 11:00",
    "முற்பகல் 11:00 - 12:00",
    "முற்பகல் 12:00 - 01:00"
  )

  val eveningSlots = listOf(
    "பிற்பகல் 04:00 - 05:00",
    "மாலை 05:00 - 06:00",
    "மாலை 06:00 - 07:00",
    "இரவு 07:00 - 08:00"
  )

  val dayAppointments = remember(appointments, selectedDate) {
    appointments.filter { it.preferredDate == selectedDate }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(GeometricBackground)
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
            text = if (appLanguage == AppLanguage.TAMIL) "நாட்காட்டி & அட்டவணை" else "Calendar & Schedule"
          )
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "தினசரி முன்பதிவு நேரம்" else "Daily Time Slots",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary
            )
          )
        }

        Surface(
          color = GeometricGoldContainer,
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = selectedDate,
            style = MaterialTheme.typography.labelSmall.copy(
              color = GeometricGoldDark,
              fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }
    }

    // 14-day Date Carousel
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(dateList) { (key, day, dateStr) ->
          val isSelected = selectedDate == key
          val countForDay = appointments.count { it.preferredDate == key }

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isSelected) GeometricGoldPrimary else GeometricSurface,
            border = CardDefaults.outlinedCardBorder().copy(
              width = 1.dp,
              brush = androidx.compose.ui.graphics.SolidColor(
                if (isSelected) GeometricGoldPrimary else GeometricGoldBorderSubtle
              )
            ),
            modifier = Modifier
              .width(76.dp)
              .clickable { viewModel.setSelectedCalendarDate(key) }
          ) {
            Column(
              modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = day,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = if (isSelected) Color.White.copy(alpha = 0.9f) else GeometricTextSecondary
                )
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = dateStr,
                style = MaterialTheme.typography.titleSmall.copy(
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.White else GeometricTextPrimary
                )
              )
              if (countForDay > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                  color = if (isSelected) Color.White else GeometricGoldPrimary,
                  shape = CircleShape
                ) {
                  Text(
                    text = "$countForDay",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontSize = 9.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (isSelected) GeometricGoldPrimary else Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                  )
                }
              }
            }
          }
        }
      }
    }

    // Morning Time Slots
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GeometricSurface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.25f))
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.WbSunny, contentDescription = null, tint = GeometricGoldPrimary, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (appLanguage == AppLanguage.TAMIL) "காலை அமர்வுகள் (${config.morningTimingsTamil})" else "Morning Sessions (${config.morningTimingsEnglish})",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          morningSlots.forEach { slot ->
            val matchingApt = dayAppointments.find { it.preferredTimeSlot.contains(slot) || slot.contains(it.preferredTimeSlot) }
            CalendarSlotItem(
              slotName = slot,
              appointment = matchingApt,
              appLanguage = appLanguage,
              onCall = { matchingApt?.let { AdminIntentHelper.dialPhoneNumber(context, it.phoneNumber) } },
              onWhatsApp = {
                matchingApt?.let {
                  val msg = "வணக்கம் ${it.clientName}, உங்கள் காலை நேர ஜோதிட ஆலோசனை: ${it.preferredDate} ($slot)."
                  AdminIntentHelper.openWhatsAppChat(context, it.phoneNumber, msg)
                }
              },
              onSelectAppointment = { matchingApt?.let { viewModel.selectAppointmentForDetails(it) } }
            )
            Spacer(modifier = Modifier.height(6.dp))
          }
        }
      }
    }

    // Lunch / Break Hours Indicator
    item {
      Surface(
        color = GeometricSurfaceSub,
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldBorderSubtle)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Restaurant, contentDescription = null, tint = GeometricGoldDark, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = if (appLanguage == AppLanguage.TAMIL) "மதிய உணவு & ஓய்வு நேரம் (01:00 PM - 04:00 PM)" else "Lunch Break & Rest (01:00 PM - 04:00 PM)",
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
            )
            Text(
              text = if (appLanguage == AppLanguage.TAMIL) "இந்த நேரத்தில் முன்பதிவுகள் அனுமதிக்கப்படாது" else "No consultation slots available during this interval",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = GeometricTextTertiary)
            )
          }
        }
      }
    }

    // Evening Time Slots
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GeometricSurface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.25f))
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.NightlightRound, contentDescription = null, tint = GeometricGoldDark, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (appLanguage == AppLanguage.TAMIL) "மாலை அமர்வுகள் (${config.eveningTimingsTamil})" else "Evening Sessions (${config.eveningTimingsEnglish})",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          eveningSlots.forEach { slot ->
            val matchingApt = dayAppointments.find { it.preferredTimeSlot.contains(slot) || slot.contains(it.preferredTimeSlot) }
            CalendarSlotItem(
              slotName = slot,
              appointment = matchingApt,
              appLanguage = appLanguage,
              onCall = { matchingApt?.let { AdminIntentHelper.dialPhoneNumber(context, it.phoneNumber) } },
              onWhatsApp = {
                matchingApt?.let {
                  val msg = "வணக்கம் ${it.clientName}, உங்கள் மாலை நேர ஜோதிட ஆலோசனை: ${it.preferredDate} ($slot)."
                  AdminIntentHelper.openWhatsAppChat(context, it.phoneNumber, msg)
                }
              },
              onSelectAppointment = { matchingApt?.let { viewModel.selectAppointmentForDetails(it) } }
            )
            Spacer(modifier = Modifier.height(6.dp))
          }
        }
      }
    }
  }
}

@Composable
fun CalendarSlotItem(
  slotName: String,
  appointment: AppointmentEntity?,
  appLanguage: AppLanguage,
  onCall: () -> Unit,
  onWhatsApp: () -> Unit,
  onSelectAppointment: () -> Unit
) {
  val isBooked = appointment != null

  Surface(
    color = if (isBooked) GeometricGoldContainer.copy(alpha = 0.45f) else GeometricSurfaceSub,
    shape = RoundedCornerShape(10.dp),
    border = CardDefaults.outlinedCardBorder().copy(
      width = 1.dp,
      brush = androidx.compose.ui.graphics.SolidColor(
        if (isBooked) GeometricGoldPrimary.copy(alpha = 0.5f) else GeometricDivider
      )
    ),
    modifier = Modifier
      .fillMaxWidth()
      .clickable(enabled = isBooked) { onSelectAppointment() }
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(if (isBooked) GeometricStatusLiveGreen else GeometricTextTertiary)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = slotName,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary,
              fontSize = 12.sp
            )
          )
        }

        if (isBooked) {
          Text(
            text = "${appointment!!.clientName} • ${appointment.serviceType}",
            style = MaterialTheme.typography.bodySmall.copy(
              color = GeometricGoldDark,
              fontSize = 11.5.sp,
              fontWeight = FontWeight.SemiBold
            )
          )
        } else {
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "முன்பதிவுக்கு கிடைக்கும்" else "Available slot",
            style = MaterialTheme.typography.bodySmall.copy(
              color = GeometricTextTertiary,
              fontSize = 11.sp
            )
          )
        }
      }

      if (isBooked) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          IconButton(onClick = onCall, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Call, contentDescription = "Call", tint = GeometricForestGreen, modifier = Modifier.size(16.dp))
          }
          IconButton(onClick = onWhatsApp, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Chat, contentDescription = "WhatsApp", tint = GeometricForestGreen, modifier = Modifier.size(16.dp))
          }
        }
      } else {
        Surface(
          color = GeometricForestGreenContainer,
          shape = RoundedCornerShape(4.dp)
        ) {
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "வெற்றிடம்" else "FREE",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = GeometricForestGreen
            ),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }
    }
  }
}
