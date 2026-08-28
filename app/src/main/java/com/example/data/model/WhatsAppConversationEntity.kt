package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class WhatsAppReviewStatus(val labelTamil: String) {
  PENDING_REVIEW("மதிப்பாய்வுக்காக காத்திருப்பவை"),
  APPROVED("அங்கீகரிக்கப்பட்டது"),
  REJECTED("நிராகரிக்கப்பட்டது")
}

@Entity(tableName = "whatsapp_conversations")
data class WhatsAppConversationEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val senderPhone: String,
  val rawMessage: String,
  val normalizedTamilQuery: String,
  val detectedIntent: String,
  val proposedTamilAnswer: String,
  val categoryTamil: String,
  val status: WhatsAppReviewStatus = WhatsAppReviewStatus.PENDING_REVIEW,
  val timestamp: Long = System.currentTimeMillis()
)
