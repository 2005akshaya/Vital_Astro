package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.data.model.AstrologerPracticeConfig
import com.example.ui.theme.*
import com.example.ui.util.AppStrings

@Composable
fun EscalationCard(
  config: AstrologerPracticeConfig,
  appLanguage: AppLanguage = AppLanguage.TAMIL,
  onNavigateToBooking: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp)
      .testTag("escalation_action_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = CardDefaults.outlinedCardBorder().copy(
      width = 1.dp,
      brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(
      modifier = Modifier.padding(14.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(24.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = AppStrings.escalationTitle(appLanguage),
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = AppStrings.escalationDesc(appLanguage),
        style = MaterialTheme.typography.bodySmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 20.sp,
          fontSize = 12.5.sp
        )
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Primary Call Button: ஜோதிடரைத் தொடர்புகொள்ளுங்கள் / Contact Astrologer
        Button(
          onClick = {
            try {
              val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${config.phoneContact.replace(" ", "")}")
              }
              context.startActivity(intent)
            } catch (e: Exception) {
              // Intent fallback
            }
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = GeometricForestGreen
          ),
          shape = RoundedCornerShape(10.dp),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
          modifier = Modifier
            .weight(1.1f)
            .testTag("contact_astrologer_button")
        ) {
          Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Call",
            modifier = Modifier.size(16.dp),
            tint = Color.White
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = AppStrings.contactAstrologerBtn(appLanguage),
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              color = Color.White
            )
          )
        }

        // Secondary Booking Button: ஆலோசனை முன்பதிவு / Book Appointment
        OutlinedButton(
          onClick = onNavigateToBooking,
          shape = RoundedCornerShape(10.dp),
          border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
          ),
          contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
          modifier = Modifier
            .weight(0.9f)
            .testTag("book_appointment_escalation_button")
        ) {
          Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = "Booking",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = AppStrings.heroBookingCta(appLanguage),
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
          )
        }
      }
    }
  }
}
