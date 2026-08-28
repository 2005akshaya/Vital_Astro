package com.example.ui.screens.admin

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.AstrologerPracticeConfig
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun AdminSettingsScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val config by viewModel.practiceConfig.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  var isEditingProfile by remember { mutableStateOf(false) }
  var nameTa by remember(config) { mutableStateOf(config.astrologerNameTamil) }
  var nameEn by remember(config) { mutableStateOf(config.astrologerNameEnglish) }
  var centerTa by remember(config) { mutableStateOf(config.titleTamil) }
  var centerEn by remember(config) { mutableStateOf(config.titleEnglish) }
  var phone by remember(config) { mutableStateOf(config.phoneNumber) }
  var whatsapp by remember(config) { mutableStateOf(config.whatsappNumber) }
  var addressTa by remember(config) { mutableStateOf(config.addressTamil) }

  var isEditingRules by remember { mutableStateOf(false) }
  var morningHours by remember(config) { mutableStateOf(config.morningTimingsTamil) }
  var eveningHours by remember(config) { mutableStateOf(config.eveningTimingsTamil) }
  var maxPerDay by remember(config) { mutableStateOf(config.maxBookingsPerDay.toString()) }
  var durationMins by remember(config) { mutableStateOf(config.consultationDurationMinutes.toString()) }

  var showChangePinDialog by remember { mutableStateOf(false) }
  var newPin by remember { mutableStateOf("") }
  var confirmPin by remember { mutableStateOf("") }

  // Change PIN Dialog
  if (showChangePinDialog) {
    AlertDialog(
      onDismissRequest = { showChangePinDialog = false },
      title = {
        Text("நிர்வாக கடவுச்சொல்லை மாற்றுக", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text("புதிய 4 இலக்க PIN உள்ளிடவும்:")
          OutlinedTextField(
            value = newPin,
            onValueChange = { if (it.length <= 4) newPin = it },
            label = { Text("புதிய PIN (New 4-digit PIN)") },
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = confirmPin,
            onValueChange = { if (it.length <= 4) confirmPin = it },
            label = { Text("உறுதி செய்க (Confirm PIN)") },
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (newPin.length == 4 && newPin == confirmPin) {
              viewModel.updateAdminPin(newPin)
              showChangePinDialog = false
              Toast.makeText(context, "கடவுச்சொல் வெற்றிகரமாக மாற்றப்பட்டது!", Toast.LENGTH_SHORT).show()
            } else {
              Toast.makeText(context, "4 இலக்க எண்கள் பொருந்தவில்லை!", Toast.LENGTH_SHORT).show()
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary)
        ) {
          Text("மாற்று", color = Color.White, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showChangePinDialog = false }) {
          Text("ரத்து", color = GeometricTextSecondary)
        }
      },
      containerColor = GeometricSurface,
      shape = RoundedCornerShape(16.dp)
    )
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
            text = if (appLanguage == AppLanguage.TAMIL) "அமைப்புகள் & நிர்வாகம்" else "Settings & Practice"
          )
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "ஜோதிடாலய அமைப்புகள்" else "Center Configurations",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary
            )
          )
        }

        Button(
          onClick = { viewModel.adminLogout() },
          colors = ButtonDefaults.buttonColors(containerColor = GeometricMaroonContainer),
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(Icons.Default.Logout, contentDescription = null, tint = GeometricMaroon, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("வெளியேறு", color = GeometricMaroon, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // 1. Astrologer Profile & Center Information
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
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Person, contentDescription = null, tint = GeometricGoldPrimary, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "ஜோதிடர் & மைய விவரங்கள்",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
              )
            }

            if (!isEditingProfile) {
              TextButton(onClick = { isEditingProfile = true }) {
                Text("திருத்து", color = GeometricGoldPrimary, fontWeight = FontWeight.Bold)
              }
            }
          }

          if (isEditingProfile) {
            OutlinedTextField(
              value = nameTa,
              onValueChange = { nameTa = it },
              label = { Text("ஜோதிடர் பெயர் (தமிழ்)") },
              modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
              value = centerTa,
              onValueChange = { centerTa = it },
              label = { Text("மைய பெயர் (தமிழ்)") },
              modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
              value = phone,
              onValueChange = { phone = it },
              label = { Text("தொலைபேசி எண் (Mobile)") },
              modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
              value = whatsapp,
              onValueChange = { whatsapp = it },
              label = { Text("WhatsApp எண்") },
              modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
              value = addressTa,
              onValueChange = { addressTa = it },
              label = { Text("மையத்தின் முகவரி") },
              modifier = Modifier.fillMaxWidth()
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.End,
              verticalAlignment = Alignment.CenterVertically
            ) {
              TextButton(onClick = { isEditingProfile = false }) {
                Text("ரத்து", color = GeometricTextSecondary)
              }
              Spacer(modifier = Modifier.width(8.dp))
              Button(
                onClick = {
                  viewModel.updateAstrologerProfile(nameTa, nameEn, centerTa, centerEn, phone, whatsapp, addressTa)
                  isEditingProfile = false
                  Toast.makeText(context, "விவரங்கள் புதுப்பிக்கப்பட்டன!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary)
              ) {
                Text("சேமி", color = Color.White, fontWeight = FontWeight.Bold)
              }
            }
          } else {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("• ஜோதிடர்: ${config.astrologerNameTamil} (${config.astrologerNameEnglish})", style = MaterialTheme.typography.bodyMedium.copy(color = GeometricTextPrimary))
              Text("• மையம்: ${config.titleTamil}", style = MaterialTheme.typography.bodyMedium.copy(color = GeometricTextPrimary))
              Text("• தொலைபேசி: ${config.phoneNumber}", style = MaterialTheme.typography.bodyMedium.copy(color = GeometricGoldDark, fontWeight = FontWeight.SemiBold))
              Text("• WhatsApp: ${config.whatsappNumber}", style = MaterialTheme.typography.bodyMedium.copy(color = GeometricForestGreen, fontWeight = FontWeight.SemiBold))
              Text("• முகவரி: ${config.addressTamil}", style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary))
            }
          }
        }
      }
    }

    // 2. Practice Timings & Rules
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
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.AccessTime, contentDescription = null, tint = GeometricGoldPrimary, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "வேலை நேரம் & முன்பதிவு விதிகள்",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
              )
            }

            if (!isEditingRules) {
              TextButton(onClick = { isEditingRules = true }) {
                Text("திருத்து", color = GeometricGoldPrimary, fontWeight = FontWeight.Bold)
              }
            }
          }

          if (isEditingRules) {
            OutlinedTextField(
              value = morningHours,
              onValueChange = { morningHours = it },
              label = { Text("காலை நேரம் (Morning Timings)") },
              modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
              value = eveningHours,
              onValueChange = { eveningHours = it },
              label = { Text("மாலை நேரம் (Evening Timings)") },
              modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              OutlinedTextField(
                value = maxPerDay,
                onValueChange = { maxPerDay = it },
                label = { Text("ஒரு நாள் அதிகபட்ச முன்பதிவு") },
                modifier = Modifier.weight(1f)
              )
              OutlinedTextField(
                value = durationMins,
                onValueChange = { durationMins = it },
                label = { Text("ஆலோசனை நிமிடம்") },
                modifier = Modifier.weight(1f)
              )
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.End,
              verticalAlignment = Alignment.CenterVertically
            ) {
              TextButton(onClick = { isEditingRules = false }) {
                Text("ரத்து", color = GeometricTextSecondary)
              }
              Spacer(modifier = Modifier.width(8.dp))
              Button(
                onClick = {
                  viewModel.updatePracticeRules(
                    morningHours,
                    eveningHours,
                    durationMins.toIntOrNull() ?: 30,
                    maxPerDay.toIntOrNull() ?: 12,
                    config.minAdvanceNoticeHours
                  )
                  isEditingRules = false
                  Toast.makeText(context, "விதிகள் புதுப்பிக்கப்பட்டன!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary)
              ) {
                Text("சேமி", color = Color.White, fontWeight = FontWeight.Bold)
              }
            }
          } else {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("• காலை அமர்வு: ${config.morningTimingsTamil}", style = MaterialTheme.typography.bodyMedium.copy(color = GeometricTextPrimary))
              Text("• மாலை அமர்வு: ${config.eveningTimingsTamil}", style = MaterialTheme.typography.bodyMedium.copy(color = GeometricTextPrimary))
              Text("• ஒரு ஆலோசனை நேரம்: ${config.consultationDurationMinutes} நிமிடங்கள்", style = MaterialTheme.typography.bodyMedium.copy(color = GeometricTextPrimary))
              Text("• ஒரு நாள் அதிகபட்ச முன்பதிவு: ${config.maxBookingsPerDay} நபர்கள்", style = MaterialTheme.typography.bodyMedium.copy(color = GeometricTextPrimary))
            }
          }
        }
      }
    }

    // 3. Security & PIN Change
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
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Security, contentDescription = null, tint = GeometricGoldPrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "பாதுகாப்பு & PIN கடவுச்சொல்",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
            )
          }

          Text(
            text = "நிர்வாக பலகையைத் திறப்பதற்கான 4 இலக்க PIN எண்ணை மாற்றலாம்.",
            style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary)
          )

          Button(
            onClick = {
              newPin = ""
              confirmPin = ""
              showChangePinDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = GeometricSurfaceSub),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.LockReset, contentDescription = null, tint = GeometricGoldPrimary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("PIN கடவுச்சொல்லை மாற்று (Change PIN)", color = GeometricTextPrimary, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
