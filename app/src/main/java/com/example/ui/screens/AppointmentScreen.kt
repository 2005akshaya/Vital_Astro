package com.example.ui.screens

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ConsultationMode
import com.example.ui.components.TamilSacredDivider
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun AppointmentScreen(
  viewModel: AstrologyViewModel,
  onBookingSuccess: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val formState by viewModel.appointmentForm.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  val serviceOptions = if (appLanguage == AppLanguage.ENGLISH) {
    listOf(
      "General Horoscope Consultation",
      "Marriage & Matchmaking",
      "Career & Job Path Consultation",
      "Business & Financial Growth",
      "Education & Children's Future",
      "Vastu & Dosha Remedies"
    )
  } else {
    listOf(
      "பொது ஜாதக ஆலோசனை",
      "திருமண ஆலோசனை",
      "தொழில் & பணிப்பாதை ஆலோசனை",
      "வணிக & தொழில் வளர்ச்சி ஆலோசனை",
      "கல்வி & குழந்தைகள் எதிர்கால ஆலோசனை",
      "வாஸ்து & கிரக சாந்தி ஆலோசனை"
    )
  }

  val timeSlotOptions = if (appLanguage == AppLanguage.ENGLISH) {
    listOf(
      "10:30 AM - 11:30 AM",
      "11:30 AM - 12:30 PM",
      "04:30 PM - 05:30 PM",
      "05:30 PM - 06:30 PM",
      "06:30 PM - 07:30 PM"
    )
  } else {
    listOf(
      "காலை 10:30 - 11:30",
      "காலை 11:30 - 12:30",
      "மாலை 4:30 - 5:30",
      "மாலை 5:30 - 6:30",
      "மாலை 6:30 - 7:30"
    )
  }

  val dateOptions = if (appLanguage == AppLanguage.ENGLISH) {
    listOf("Today", "Tomorrow", "Day after tomorrow", "This Week")
  } else {
    listOf("இன்று", "நாளை", "நாளை மறுநாள்", "இந்த வாரம்")
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("appointment_screen"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Column {
        TraditionalMotifBadge(text = AppStrings.bookingBadge(appLanguage))
        Text(
          text = AppStrings.bookingTitle(appLanguage),
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = AppStrings.bookingSubtitle(appLanguage),
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 18.sp
          )
        )
      }
    }

    // SUCCESS CONFIRMATION STATE
    if (formState.successReferenceNumber != null) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = GeometricForestGreenContainer),
          border = CardDefaults.outlinedCardBorder().copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(GeometricForestGreen.copy(alpha = 0.3f))
          ),
          modifier = Modifier.fillMaxWidth().testTag("appointment_success_card")
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(GeometricForestGreen),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(22.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = AppStrings.bookingSuccessTitle(appLanguage),
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GeometricForestGreenText
                  )
                )
                Text(
                  text = "${AppStrings.bookingRefLabel(appLanguage)}: #${formState.successReferenceNumber}",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = GeometricForestGreen
                  )
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = AppStrings.bookingSuccessDesc(appLanguage),
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp
              )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = {
                viewModel.resetAppointmentForm()
                onBookingSuccess()
              },
              colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(AppStrings.goHomeBtn(appLanguage), color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // FORM FIELDS CARD
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          // Validation Error Notice
          AnimatedVisibility(visible = formState.errorMessage != null) {
            Surface(
              color = GeometricMaroonContainer,
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Warning,
                  contentDescription = null,
                  tint = GeometricMaroon,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = formState.errorMessage ?: "",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = GeometricMaroonText,
                    fontWeight = FontWeight.Bold
                  )
                )
              }
            }
          }

          // 1. Client Name
          OutlinedTextField(
            value = formState.clientName,
            onValueChange = { viewModel.updateClientName(it) },
            label = { Text("${AppStrings.labelName(appLanguage)}*") },
            placeholder = { Text(if (appLanguage == AppLanguage.ENGLISH) "e.g. Karthikeyan" else "எ.கா. கார்த்திகேயன்") },
            leadingIcon = {
              Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            },
            modifier = Modifier.fillMaxWidth().testTag("input_client_name"),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
          )

          // 2. Phone Number
          OutlinedTextField(
            value = formState.phoneNumber,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            label = { Text("${AppStrings.labelPhone(appLanguage)}*") },
            placeholder = { Text(if (appLanguage == AppLanguage.ENGLISH) "e.g. 9876543210" else "எ.கா. 9876543210") },
            leadingIcon = {
              Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            },
            modifier = Modifier.fillMaxWidth().testTag("input_phone_number"),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
          )

          // 3. Service Selection Dropdown / Chips
          Column {
            Text(
              text = "${AppStrings.labelServiceType(appLanguage)}*",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              items(serviceOptions) { srv ->
                val isSelected = formState.serviceType == srv
                FilterChip(
                  selected = isSelected,
                  onClick = { viewModel.updateServiceType(srv) },
                  label = {
                    Text(
                      text = srv,
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                      )
                    )
                  },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurface
                  ),
                  border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                  ),
                  shape = RoundedCornerShape(16.dp)
                )
              }
            }
          }

          // 4. Consultation Mode Selection
          Column {
            Text(
              text = "${AppStrings.labelMode(appLanguage)}*",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ConsultationMode.values().forEach { mode ->
                val isSelected = formState.consultationMode == mode
                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = if (isSelected) GeometricForestGreenContainer else MaterialTheme.colorScheme.surfaceVariant,
                  border = CardDefaults.outlinedCardBorder().copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(
                      if (isSelected) GeometricForestGreen else MaterialTheme.colorScheme.outline
                    )
                  ),
                  modifier = Modifier
                    .weight(1f)
                    .clickable { viewModel.updateConsultationMode(mode) }
                ) {
                  Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                    Icon(
                      imageVector = when (mode) {
                        ConsultationMode.DIRECT_VISIT -> Icons.Default.Storefront
                        ConsultationMode.ONLINE_VIDEO -> Icons.Default.Videocam
                        ConsultationMode.PHONE_CALL -> Icons.Default.PhoneInTalk
                      },
                      contentDescription = null,
                      tint = if (isSelected) GeometricForestGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                      modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                      text = when (mode) {
                        ConsultationMode.DIRECT_VISIT -> if (appLanguage == AppLanguage.ENGLISH) "Direct" else "நேரில்"
                        ConsultationMode.ONLINE_VIDEO -> if (appLanguage == AppLanguage.ENGLISH) "Video" else "வீடியோ"
                        ConsultationMode.PHONE_CALL -> if (appLanguage == AppLanguage.ENGLISH) "Call" else "அழைப்பு"
                      },
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) GeometricForestGreenText else MaterialTheme.colorScheme.onSurface
                      )
                    )
                  }
                }
              }
            }
          }

          // 5. Preferred Date Selection
          Column {
            Text(
              text = "${AppStrings.labelDate(appLanguage)}*",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              items(dateOptions) { dt ->
                val isSelected = formState.preferredDate == dt
                FilterChip(
                  selected = isSelected,
                  onClick = { viewModel.updatePreferredDate(dt) },
                  label = {
                    Text(
                      text = dt,
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                      )
                    )
                  },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurface
                  ),
                  border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                  ),
                  shape = RoundedCornerShape(16.dp)
                )
              }
            }
          }

          // 6. Preferred Time Slot Selection
          Column {
            Text(
              text = "${AppStrings.labelTimeSlot(appLanguage)}*",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              items(timeSlotOptions) { slot ->
                val isSelected = formState.preferredTimeSlot == slot
                FilterChip(
                  selected = isSelected,
                  onClick = { viewModel.updatePreferredTimeSlot(slot) },
                  label = {
                    Text(
                      text = slot,
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                      )
                    )
                  },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = GeometricForestGreen,
                    selectedLabelColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurface
                  ),
                  border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = if (isSelected) GeometricForestGreen else MaterialTheme.colorScheme.outline
                  ),
                  shape = RoundedCornerShape(16.dp)
                )
              }
            }
          }

          // 7. Birth Details Notes
          OutlinedTextField(
            value = formState.birthDetailsNotes,
            onValueChange = { viewModel.updateBirthDetailsNotes(it) },
            label = { Text(AppStrings.labelBirthNotes(appLanguage)) },
            placeholder = { Text(AppStrings.placeholderBirthNotes(appLanguage)) },
            modifier = Modifier.fillMaxWidth().height(100.dp),
            shape = RoundedCornerShape(10.dp),
            maxLines = 4
          )

          TamilSacredDivider(modifier = Modifier.padding(vertical = 4.dp))

          // SUBMIT BUTTON
          Button(
            onClick = { viewModel.submitAppointment() },
            enabled = !formState.isSubmitting,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("submit_booking_button")
          ) {
            if (formState.isSubmitting) {
              CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
              Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = AppStrings.btnConfirmBooking(appLanguage),
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              )
            }
          }
        }
      }
    }
  }
}
