package com.example.ui.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.net.URLEncoder

object AdminIntentHelper {

  fun dialPhoneNumber(context: Context, phoneNumber: String) {
    val cleanNumber = phoneNumber.replace("[^0-9+]".toRegex(), "")
    if (cleanNumber.isBlank()) {
      Toast.makeText(context, "தொலைபேசி எண் இல்லை / Phone number missing", Toast.LENGTH_SHORT).show()
      return
    }
    try {
      val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$cleanNumber")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "அழைப்பைத் தொடங்க முடியவில்லை: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
  }

  fun openWhatsAppChat(context: Context, phoneNumber: String, message: String = "") {
    val cleanNumber = phoneNumber.replace("[^0-9]".toRegex(), "")
    val formattedNumber = if (cleanNumber.startsWith("91")) cleanNumber else "91$cleanNumber"
    try {
      val encodedMsg = URLEncoder.encode(message, "UTF-8")
      val uriString = if (message.isNotBlank()) {
        "https://wa.me/$formattedNumber?text=$encodedMsg"
      } else {
        "https://wa.me/$formattedNumber"
      }
      val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(uriString)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
      context.startActivity(intent)
    } catch (e: Exception) {
      Toast.makeText(context, "WhatsApp திறக்க முடியவில்லை: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
  }
}
