package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.theme.GeometricForestGreen

@Composable
fun WhatsAppFloatingButton(
  phoneNumber: String,
  appLanguage: AppLanguage = AppLanguage.TAMIL,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  FloatingActionButton(
    onClick = {
      try {
        val cleanPhone = phoneNumber.replace("[^0-9+]".toRegex(), "")
        val message = if (appLanguage == AppLanguage.ENGLISH) {
          "Hello%20Astrologer,%20I%20would%20like%20to%20know%20about%20consultation."
        } else {
          "வணக்கம்%20ஜோதிடரே,%20ஆலோசனை%20குறித்து%20அறிய%20விரும்புகிறேன்."
        }
        val uri = Uri.parse("https://wa.me/${cleanPhone.replace("+", "")}?text=$message")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
      } catch (e: Exception) {
        // Fallback dialer
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        context.startActivity(dialIntent)
      }
    },
    containerColor = GeometricForestGreen,
    contentColor = Color.White,
    shape = RoundedCornerShape(24.dp),
    modifier = modifier.testTag("whatsapp_floating_button")
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.Chat,
        contentDescription = if (appLanguage == AppLanguage.ENGLISH) "WhatsApp Contact" else "WhatsApp தொடர்பு",
        modifier = Modifier.size(18.dp),
        tint = Color.White
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "WhatsApp",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          color = Color.White,
          fontSize = 12.5.sp
        )
      )
    }
  }
}
