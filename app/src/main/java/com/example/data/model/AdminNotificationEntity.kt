package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin_notifications")
data class AdminNotificationEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val titleTamil: String,
  val titleEnglish: String,
  val messageTamil: String,
  val messageEnglish: String,
  val type: String = "BOOKING", // BOOKING, PAYMENT, CANCEL, RESCHEDULE, REMINDER
  val relatedId: Long = 0,
  val isRead: Boolean = false,
  val timestamp: Long = System.currentTimeMillis()
)
