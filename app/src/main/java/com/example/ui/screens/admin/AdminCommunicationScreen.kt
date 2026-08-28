package com.example.ui.screens.admin

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.util.AdminIntentHelper
import com.example.ui.viewmodel.AstrologyViewModel

data class WhatsAppTemplate(
  val id: String,
  val titleTamil: String,
  val titleEnglish: String,
  val icon: androidx.compose.ui.graphics.vector.ImageVector,
  val templateText: (clientName: String, date: String, time: String, astrologer: String, phone: String, address: String) -> String
)

@Composable
fun AdminCommunicationScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val appointments by viewModel.appointmentsList.collectAsState()
  val config by viewModel.practiceConfig.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  var recipientPhone by remember { mutableStateOf("") }
  var recipientName by remember { mutableStateOf("") }
  var customMessage by remember { mutableStateOf("") }

  val templates = remember(config) {
    listOf(
      WhatsAppTemplate(
        id = "confirm",
        titleTamil = "1. முன்பதிவு உறுதிப்படுத்தல் (Confirmation)",
        titleEnglish = "1. Appointment Confirmation",
        icon = Icons.Default.CheckCircle,
        templateText = { name, date, time, ast, phone, _ ->
          "வணக்கம் $name,\n\nஸ்ரீ விட்டல் ஜோதிடாலயத்தில் தங்களின் ஜோதிட ஆலோசனை முன்பதிவு உறுதி செய்யப்பட்டது.\n\n📅 தேதி: $date\n⏰ நேரம்: $time\n🔮 ஜோதிடர்: $ast\n📞 தொடர்பு: $phone\n\nநன்றி!"
        }
      ),
      WhatsAppTemplate(
        id = "reminder",
        titleTamil = "2. ஆலோசனை நினைவூட்டல் (Reminder)",
        titleEnglish = "2. Consultation Reminder",
        icon = Icons.Default.Alarm,
        templateText = { name, date, time, ast, phone, _ ->
          "வணக்கம் $name,\n\nநினைவூட்டல்: ஸ்ரீ விட்டல் ஜோதிடாலயத்தில் தங்களின் ஜோதிட ஆலோசனை இன்று/நாளை $time மணிக்கு திட்டமிடப்பட்டுள்ளது.\n\nஜாதகக் குறிப்புகளுடன் தயாராக இருக்கவும்.\n- $ast ($phone)"
        }
      ),
      WhatsAppTemplate(
        id = "payment",
        titleTamil = "3. கட்டண UPI விவரம் (Payment Details)",
        titleEnglish = "3. Payment & GPay Info",
        icon = Icons.Default.CurrencyRupee,
        templateText = { name, _, _, ast, phone, _ ->
          "வணக்கம் $name,\n\nஸ்ரீ விட்டல் ஜோதிடாலய ஆலோசனை கட்டணத்தை கீழ்க்கண்ட UPI / GPay எண்ணுக்கு செலுத்தலாம்:\n\n📱 GPay / PhonePe: $phone\nபெயர்: $ast\n\nபணம் செலுத்தியதும் ரசீதை இந்த எண்ணுக்கு அனுப்பவும்."
        }
      ),
      WhatsAppTemplate(
        id = "birth_request",
        titleTamil = "4. பிறப்பு விவரங்கள் கோரிக்கை (Birth Details)",
        titleEnglish = "4. Request Birth Info",
        icon = Icons.Default.EditNote,
        templateText = { name, _, _, _, _, _ ->
          "வணக்கம் $name,\n\nதுல்லியமான ஜாதக கணிப்பிற்கு கீழ்க்கண்ட விவரங்களை அனுப்பவும்:\n1. முழு பெயர்:\n2. பிறந்த தேதி (DD/MM/YYYY):\n3. பிறந்த நேரம் (AM/PM):\n4. பிறந்த ஊர்/மாவட்டம்:\n5. உங்கள் முக்கிய கேள்விகள்:"
        }
      ),
      WhatsAppTemplate(
        id = "location",
        titleTamil = "5. மைய முகவரி (Office Address)",
        titleEnglish = "5. Center Location",
        icon = Icons.Default.LocationOn,
        templateText = { name, _, _, ast, phone, addr ->
          "வணக்கம் $name,\n\nஸ்ரீ விட்டல் ஜோதிடாலயம் முகவரி:\n\n📍 $addr\nஜோதிடர்: $ast\n📞 $phone\n\nவருகைக்கு முன் தயவுசெய்து அழைக்கவும்."
        }
      )
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
      Column {
        TraditionalMotifBadge(
          text = if (appLanguage == AppLanguage.TAMIL) "தொடர்பு & குறுஞ்செய்திகள்" else "Communication Center"
        )
        Text(
          text = if (appLanguage == AppLanguage.TAMIL) "WhatsApp வார்ப்புருக்கள் & அழைப்பு" else "WhatsApp Templates & Call",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = GeometricTextPrimary
          )
        )
      }
    }

    // Recipient Selector / Input Card
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
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Text(
            text = "வாடிக்கையாளர் தொலைபேசி எண் (Recipient Phone):",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
          )

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
              value = recipientPhone,
              onValueChange = { recipientPhone = it },
              placeholder = { Text("எண்: 9787908717") },
              leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = GeometricGoldPrimary) },
              modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
              value = recipientName,
              onValueChange = { recipientName = it },
              placeholder = { Text("பெயர் (விருப்பத்தேர்வு)") },
              modifier = Modifier.weight(1f)
            )
          }

          // Quick Recent Customer Chips
          if (appointments.isNotEmpty()) {
            Text("சமீபத்திய வாடிக்கையாளர்கள்:", style = MaterialTheme.typography.labelSmall.copy(color = GeometricTextTertiary))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              appointments.take(3).forEach { apt ->
                Surface(
                  color = GeometricSurfaceSub,
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier
                    .clickable {
                      recipientPhone = apt.phoneNumber
                      recipientName = apt.clientName
                    }
                ) {
                  Text(
                    text = apt.clientName,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontSize = 11.sp,
                      color = GeometricGoldDark,
                      fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }
            }
          }

          // Action Buttons: Call & Send
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = { AdminIntentHelper.dialPhoneNumber(context, recipientPhone) },
              colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("நேரடி அழைப்பு", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = {
                AdminIntentHelper.openWhatsAppChat(context, recipientPhone, customMessage)
              },
              colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("WhatsApp அனுப்பு", color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // Editable Message Box
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GeometricSurface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldBorderSubtle)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "செய்தி முன்னோட்டம் (Message Preview):",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
          )

          OutlinedTextField(
            value = customMessage,
            onValueChange = { customMessage = it },
            placeholder = { Text("கீழேயுள்ள வார்ப்புருவைத் தேர்வு செய்தால் செய்தி இங்கே தோன்றும் அல்லது சொந்தமாக தட்டச்சு செய்யலாம்...") },
            modifier = Modifier.fillMaxWidth().height(120.dp),
            maxLines = 6
          )
        }
      }
    }

    // Ready Templates List
    item {
      Text(
        text = "தயாரான WhatsApp வார்ப்புருக்கள் (Templates):",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
      )
    }

    items(templates) { tmpl ->
      val msgText = tmpl.templateText(
        recipientName.ifEmpty { "வாடிக்கையாளர்" },
        "இன்று / நாளை",
        "முற்பகல் 11:00",
        config.astrologerNameTamil,
        config.phoneNumber,
        config.addressTamil
      )

      Card(
        shape = RoundedCornerShape(14.dp),
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
              Icon(imageVector = tmpl.icon, contentDescription = null, tint = GeometricGoldPrimary, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = tmpl.titleTamil,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
              )
            }

            Button(
              onClick = {
                customMessage = msgText
                AdminIntentHelper.openWhatsAppChat(context, recipientPhone, msgText)
              },
              colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
              modifier = Modifier.height(32.dp)
            ) {
              Text("WhatsApp அனுப்பு", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          Surface(
            color = GeometricSurfaceSub,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { customMessage = msgText }
          ) {
            Text(
              text = msgText,
              style = MaterialTheme.typography.bodySmall.copy(
                color = GeometricTextSecondary,
                fontSize = 11.5.sp,
                lineHeight = 17.sp
              ),
              modifier = Modifier.padding(10.dp)
            )
          }
        }
      }
    }
  }
}
