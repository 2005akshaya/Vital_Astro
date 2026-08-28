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
import com.example.ui.viewmodel.AdminSubTab
import com.example.ui.viewmodel.AstrologyViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminHomeScreen(
  viewModel: AstrologyViewModel,
  onNavigateToTab: (AdminSubTab) -> Unit,
  onOpenNewAppointmentDialog: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val analytics by viewModel.analytics.collectAsState()
  val appointments by viewModel.appointmentsList.collectAsState()
  val config by viewModel.practiceConfig.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()
  val customers by viewModel.customersList.collectAsState()
  val notifications by viewModel.notificationsList.collectAsState()

  val todayDateStr = remember {
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
  }
  val todayFormattedDisplay = remember {
    SimpleDateFormat("dd MMMM yyyy, EEEE", Locale.getDefault()).format(Date())
  }

  val todayAppointments = remember(appointments, todayDateStr) {
    appointments.filter { it.preferredDate == todayDateStr || it.preferredDate.contains("இன்று") || it.preferredDate.contains("Today") }
  }

  val pendingAppointments = remember(appointments) {
    appointments.filter { it.status == AppointmentStatus.PENDING }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(GeometricBackground)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Father Greeting Header & Instant Availability Switch
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = GeometricSurface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.35f))
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              TraditionalMotifBadge(
                text = if (appLanguage == AppLanguage.TAMIL) "அப்பா நிர்வாக பலகை" else "Father's Dashboard"
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = if (appLanguage == AppLanguage.TAMIL) "வணக்கம் அப்பா!" else "Welcome, Astrologer!",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = GeometricTextPrimary
                )
              )
              Text(
                text = "${config.astrologerNameTamil} • $todayFormattedDisplay",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = GeometricGoldDark,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium
                )
              )
            }

            IconButton(
              onClick = { viewModel.adminLogout() },
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(GeometricSurfaceSub)
            ) {
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Logout",
                tint = GeometricTextSecondary,
                modifier = Modifier.size(20.dp)
              )
            }
          }

          HorizontalDivider(thickness = 1.dp, color = GeometricDivider)

          // Instant Consultation Availability Switch
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Box(
                modifier = Modifier
                  .size(10.dp)
                  .clip(CircleShape)
                  .background(
                    if (config.isAvailableForInstantConsultation) GeometricStatusLiveGreen else GeometricMaroon
                  )
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = if (appLanguage == AppLanguage.TAMIL) "உடனடி ஆலோசனை தயார் நிலை" else "Instant Consultation Availability",
                  style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = GeometricTextPrimary,
                    fontSize = 13.sp
                  )
                )
                Text(
                  text = if (config.isAvailableForInstantConsultation)
                    (if (appLanguage == AppLanguage.TAMIL) "நேரடி / அழைப்பு ஆலோசனைக்கு தயார்" else "Available for calls & direct visits")
                  else
                    (if (appLanguage == AppLanguage.TAMIL) "தற்போது ஓய்வு / நிறுத்தம்" else "Currently busy / unavailable"),
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = if (config.isAvailableForInstantConsultation) GeometricForestGreen else GeometricTextTertiary,
                    fontSize = 11.sp
                  )
                )
              }
            }

            Switch(
              checked = config.isAvailableForInstantConsultation,
              onCheckedChange = { viewModel.toggleInstantAvailability() },
              colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = GeometricForestGreen,
                uncheckedThumbColor = GeometricTextSecondary,
                uncheckedTrackColor = GeometricSurfaceSub
              )
            )
          }
        }
      }
    }

    // 2. Key Metrics Grid
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          AdminMetricCard(
            title = if (appLanguage == AppLanguage.TAMIL) "இன்றைய முன்பதிவு" else "Today's Appts",
            count = "${todayAppointments.size}",
            subtext = "${appointments.size} மொத்த முன்பதிவுகள்",
            color = GeometricGoldPrimary,
            icon = Icons.Default.Today,
            modifier = Modifier.weight(1f),
            onClick = { onNavigateToTab(AdminSubTab.APPOINTMENTS) }
          )

          AdminMetricCard(
            title = if (appLanguage == AppLanguage.TAMIL) "இன்றைய வருமானம்" else "Today's Revenue",
            count = "₹${analytics?.todayRevenue?.toInt() ?: 1000}",
            subtext = "மொத்தம்: ₹${analytics?.totalRevenue?.toInt() ?: 4500}",
            color = GeometricForestGreen,
            icon = Icons.Default.CurrencyRupee,
            modifier = Modifier.weight(1f),
            onClick = { onNavigateToTab(AdminSubTab.PAYMENTS) }
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          AdminMetricCard(
            title = if (appLanguage == AppLanguage.TAMIL) "நிலுவை கோரிக்கைகள்" else "Pending Requests",
            count = "${pendingAppointments.size}",
            subtext = if (pendingAppointments.isNotEmpty()) "உறுதி செய்ய காத்திருக்கிறது" else "அனைத்தும் முடிந்தது",
            color = if (pendingAppointments.isNotEmpty()) GeometricMaroon else GeometricGoldDark,
            icon = Icons.Default.HourglassTop,
            modifier = Modifier.weight(1f),
            onClick = { onNavigateToTab(AdminSubTab.APPOINTMENTS) }
          )

          AdminMetricCard(
            title = if (appLanguage == AppLanguage.TAMIL) "வாடிக்கையாளர்கள்" else "Total Clients",
            count = "${customers.size}",
            subtext = "${customers.count { it.notes.isNotBlank() }} ஜாதகக் குறிப்புகள்",
            color = GeometricGoldDark,
            icon = Icons.Default.People,
            modifier = Modifier.weight(1f),
            onClick = { onNavigateToTab(AdminSubTab.CUSTOMERS) }
          )
        }
      }
    }

    // 3. Quick Action Row for Father
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
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "விரைவு நடவடிக்கைகள் (Quick Actions)" else "Quick Actions",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            QuickActionButton(
              title = if (appLanguage == AppLanguage.TAMIL) "புதிய பதிவு" else "Add Appt",
              icon = Icons.Default.AddCircle,
              color = GeometricGoldPrimary,
              modifier = Modifier.weight(1f),
              onClick = onOpenNewAppointmentDialog
            )

            QuickActionButton(
              title = if (appLanguage == AppLanguage.TAMIL) "நாட்காட்டி" else "Calendar",
              icon = Icons.Default.CalendarMonth,
              color = GeometricForestGreen,
              modifier = Modifier.weight(1f),
              onClick = { onNavigateToTab(AdminSubTab.CALENDAR) }
            )

            QuickActionButton(
              title = if (appLanguage == AppLanguage.TAMIL) "WhatsApp" else "WhatsApp",
              icon = Icons.Default.Chat,
              color = GeometricForestGreen,
              modifier = Modifier.weight(1f),
              onClick = { onNavigateToTab(AdminSubTab.COMMUNICATION) }
            )

            QuickActionButton(
              title = if (appLanguage == AppLanguage.TAMIL) "கட்டணம்" else "Revenue",
              icon = Icons.Default.Payments,
              color = GeometricGoldDark,
              modifier = Modifier.weight(1f),
              onClick = { onNavigateToTab(AdminSubTab.PAYMENTS) }
            )
          }
        }
      }
    }

    // 4. Pending Booking Requests (Priority Alert)
    if (pendingAppointments.isNotEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = GeometricMaroonContainer.copy(alpha = 0.6f)),
          border = CardDefaults.outlinedCardBorder().copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(GeometricMaroon.copy(alpha = 0.40f))
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.NotificationsActive,
                  contentDescription = null,
                  tint = GeometricMaroon,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (appLanguage == AppLanguage.TAMIL) "புதிய முன்பதிவு கோரிக்கைகள் (${pendingAppointments.size})" else "New Booking Requests (${pendingAppointments.size})",
                  style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = GeometricMaroon
                  )
                )
              }

              TextButton(onClick = { onNavigateToTab(AdminSubTab.APPOINTMENTS) }) {
                Text(
                  text = if (appLanguage == AppLanguage.TAMIL) "அனைத்தும்" else "View All",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = GeometricMaroon
                  )
                )
              }
            }

            pendingAppointments.take(2).forEach { apt ->
              Surface(
                color = GeometricSurface,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(10.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(
                      text = apt.clientName,
                      style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = GeometricTextPrimary
                      )
                    )
                    Text(
                      text = apt.phoneNumber,
                      style = MaterialTheme.typography.bodySmall.copy(
                        color = GeometricTextSecondary,
                        fontSize = 12.sp
                      )
                    )
                  }

                  Text(
                    text = "${apt.serviceType} • ${apt.preferredDate} (${apt.preferredTimeSlot})",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = GeometricGoldDark,
                      fontSize = 11.5.sp
                    )
                  )

                  Spacer(modifier = Modifier.height(8.dp))

                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Button(
                      onClick = { viewModel.updateAppointmentStatus(apt.id, AppointmentStatus.CONFIRMED) },
                      colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
                      shape = RoundedCornerShape(6.dp),
                      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                      modifier = Modifier.weight(1f)
                    ) {
                      Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
                      Spacer(modifier = Modifier.width(4.dp))
                      Text(
                        text = if (appLanguage == AppLanguage.TAMIL) "உறுதி செய்" else "Confirm",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                      )
                    }

                    OutlinedButton(
                      onClick = { AdminIntentHelper.dialPhoneNumber(context, apt.phoneNumber) },
                      shape = RoundedCornerShape(6.dp),
                      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                      modifier = Modifier.weight(0.7f)
                    ) {
                      Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp), tint = GeometricForestGreen)
                      Spacer(modifier = Modifier.width(4.dp))
                      Text("அழை", fontSize = 11.sp, color = GeometricForestGreen)
                    }

                    OutlinedButton(
                      onClick = {
                        val msg = "வணக்கம் ${apt.clientName}, ஸ்ரீ விட்டல் ஜோதிடாலயம் சார்பாக உங்கள் முன்பதிவு (${apt.preferredDate}, ${apt.preferredTimeSlot}) பரிசீலனையில் உள்ளது."
                        AdminIntentHelper.openWhatsAppChat(context, apt.phoneNumber, msg)
                      },
                      shape = RoundedCornerShape(6.dp),
                      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                      modifier = Modifier.weight(0.8f)
                    ) {
                      Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp), tint = GeometricForestGreen)
                      Spacer(modifier = Modifier.width(4.dp))
                      Text("WhatsApp", fontSize = 11.sp, color = GeometricForestGreen)
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    // 5. Today's Appointments Schedule
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GeometricSurface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.30f))
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = if (appLanguage == AppLanguage.TAMIL) "இன்றைய ஆலோசனைகள் அட்டவணை" else "Today's Schedule",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = GeometricTextPrimary
                )
              )
              Text(
                text = if (appLanguage == AppLanguage.TAMIL) "இன்று திட்டமிடப்பட்ட அனைத்து சந்திப்புகள்" else "All scheduled consultations for today",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = GeometricTextSecondary,
                  fontSize = 11.sp
                )
              )
            }

            TextButton(onClick = { onNavigateToTab(AdminSubTab.APPOINTMENTS) }) {
              Text(
                text = if (appLanguage == AppLanguage.TAMIL) "முழு அட்டவணை" else "Full List",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = GeometricGoldPrimary,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          val displayList = if (todayAppointments.isNotEmpty()) todayAppointments else appointments.take(4)

          if (displayList.isEmpty()) {
            Surface(
              color = GeometricSurfaceSub,
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = if (appLanguage == AppLanguage.TAMIL) "இன்று முன்பதிவுகள் எதுவும் திட்டமிடப்படவில்லை." else "No appointments scheduled for today.",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = GeometricTextTertiary,
                  fontSize = 12.sp
                ),
                modifier = Modifier.padding(16.dp)
              )
            }
          } else {
            displayList.forEach { apt ->
              AdminAppointmentRowItem(
                appointment = apt,
                appLanguage = appLanguage,
                onStatusChange = { newStatus -> viewModel.updateAppointmentStatus(apt.id, newStatus) },
                onCall = { AdminIntentHelper.dialPhoneNumber(context, apt.phoneNumber) },
                onWhatsApp = {
                  val msg = "வணக்கம் ${apt.clientName}, ஸ்ரீ விட்டல் ஜோதிடாலயத்தில் தங்களின் ஜோதிட ஆலோசனை (${apt.preferredDate}, ${apt.preferredTimeSlot}) திட்டமிடப்பட்டுள்ளது."
                  AdminIntentHelper.openWhatsAppChat(context, apt.phoneNumber, msg)
                },
                onSelectDetails = {
                  viewModel.selectAppointmentForDetails(apt)
                  onNavigateToTab(AdminSubTab.APPOINTMENTS)
                }
              )
              HorizontalDivider(thickness = 1.dp, color = GeometricDivider, modifier = Modifier.padding(vertical = 6.dp))
            }
          }
        }
      }
    }

    // 6. Recent Notifications Summary
    if (notifications.isNotEmpty()) {
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
              Text(
                text = if (appLanguage == AppLanguage.TAMIL) "அறிவிப்புகள் & நினைவூட்டல்கள்" else "Notifications & Alerts",
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = GeometricTextPrimary
                )
              )

              if (notifications.any { !it.isRead }) {
                TextButton(onClick = { viewModel.markAllNotificationsRead() }) {
                  Text(
                    text = if (appLanguage == AppLanguage.TAMIL) "அனைத்தும் வாசி" else "Mark Read",
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = GeometricGoldPrimary,
                      fontWeight = FontWeight.Bold
                    )
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            notifications.take(3).forEach { notif ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (!notif.isRead) GeometricGoldPrimary else GeometricTextTertiary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = notif.titleTamil,
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontWeight = if (!notif.isRead) FontWeight.Bold else FontWeight.Normal,
                      color = GeometricTextPrimary
                    )
                  )
                  Text(
                    text = notif.messageTamil,
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontSize = 11.sp,
                      color = GeometricTextSecondary
                    )
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun AdminMetricCard(
  title: String,
  count: String,
  subtext: String,
  color: Color,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {}
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
      .clickable { onClick() }
      .testTag("admin_metric_${title}")
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelSmall.copy(
            color = GeometricTextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
        )
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = color,
          modifier = Modifier.size(16.dp)
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = count,
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
        ),
        maxLines = 1
      )
    }
  }
}

@Composable
fun QuickActionButton(
  title: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  color: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = GeometricSurfaceSub,
    border = CardDefaults.outlinedCardBorder().copy(
      width = 1.dp,
      brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldBorderSubtle)
    ),
    modifier = modifier
      .height(72.dp)
      .clickable { onClick() }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(6.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = color,
        modifier = Modifier.size(22.dp)
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 10.5.sp,
          fontWeight = FontWeight.Bold,
          color = GeometricTextPrimary
        ),
        maxLines = 1
      )
    }
  }
}

@Composable
fun AdminAppointmentRowItem(
  appointment: AppointmentEntity,
  appLanguage: AppLanguage,
  onStatusChange: (AppointmentStatus) -> Unit,
  onCall: () -> Unit,
  onWhatsApp: () -> Unit,
  onSelectDetails: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onSelectDetails() }
      .padding(vertical = 4.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = appointment.clientName,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary
            )
          )
          Spacer(modifier = Modifier.width(6.dp))
          Surface(
            color = when (appointment.consultationMode) {
              ConsultationMode.DIRECT_VISIT -> GeometricGoldContainer
              ConsultationMode.PHONE_CALL -> GeometricForestGreenContainer
              ConsultationMode.WHATSAPP_CHAT -> GeometricForestGreenContainer
              ConsultationMode.GOOGLE_MEET -> GeometricMaroonContainer
            },
            shape = RoundedCornerShape(4.dp)
          ) {
            Text(
              text = appointment.consultationMode.labelTamil,
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = when (appointment.consultationMode) {
                  ConsultationMode.DIRECT_VISIT -> GeometricGoldDark
                  ConsultationMode.PHONE_CALL -> GeometricForestGreen
                  ConsultationMode.WHATSAPP_CHAT -> GeometricForestGreen
                  ConsultationMode.GOOGLE_MEET -> GeometricMaroon
                }
              ),
              modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
            )
          }
        }

        Text(
          text = "${appointment.serviceType} • ${appointment.preferredDate} (${appointment.preferredTimeSlot})",
          style = MaterialTheme.typography.bodySmall.copy(
            color = GeometricTextSecondary,
            fontSize = 11.5.sp
          )
        )
      }

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
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = when (appointment.status) {
              AppointmentStatus.CONFIRMED -> GeometricForestGreen
              AppointmentStatus.COMPLETED -> GeometricGoldDark
              AppointmentStatus.CANCELLED -> GeometricMaroon
              AppointmentStatus.PENDING -> GeometricTextPrimary
              AppointmentStatus.RESCHEDULED -> GeometricGoldDark
            }
          ),
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(6.dp))

    // Quick Action Bar for the appointment
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        IconButton(
          onClick = onCall,
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(GeometricForestGreenContainer)
        ) {
          Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Call",
            tint = GeometricForestGreen,
            modifier = Modifier.size(16.dp)
          )
        }

        IconButton(
          onClick = onWhatsApp,
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(GeometricForestGreenContainer)
        ) {
          Icon(
            imageVector = Icons.Default.Chat,
            contentDescription = "WhatsApp",
            tint = GeometricForestGreen,
            modifier = Modifier.size(16.dp)
          )
        }
      }

      Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        if (appointment.status != AppointmentStatus.CONFIRMED && appointment.status != AppointmentStatus.COMPLETED) {
          Button(
            onClick = { onStatusChange(AppointmentStatus.CONFIRMED) },
            colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
            modifier = Modifier.height(28.dp)
          ) {
            Text("உறுதி செய்", fontSize = 10.5.sp, color = Color.White, fontWeight = FontWeight.Bold)
          }
        }

        if (appointment.status != AppointmentStatus.COMPLETED) {
          OutlinedButton(
            onClick = { onStatusChange(AppointmentStatus.COMPLETED) },
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
            modifier = Modifier.height(28.dp)
          ) {
            Text("நிறைவு", fontSize = 10.5.sp, color = GeometricGoldDark, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
