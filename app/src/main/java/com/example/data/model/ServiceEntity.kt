package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "astrology_services")
data class ServiceEntity(
  @PrimaryKey
  val id: String,
  val titleTamil: String,
  val titleEnglish: String,
  val descriptionTamil: String,
  val descriptionEnglish: String,
  val feeTamil: String,
  val feeEnglish: String,
  val durationTamil: String,
  val durationEnglish: String,
  val priceAmount: Double = 500.0,
  val onlineAvailable: Boolean = true,
  val inPersonAvailable: Boolean = true,
  val isActive: Boolean = true,
  val iconType: String = "star",
  val displayOrder: Int = 0
) {
  val category: String get() = "ஜோதிடம்"
}
