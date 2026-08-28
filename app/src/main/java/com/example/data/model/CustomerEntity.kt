package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val name: String,
  val phoneNumber: String,
  val email: String = "",
  val dateOfBirth: String = "",
  val timeOfBirth: String = "",
  val placeOfBirth: String = "",
  val rashiTamil: String = "",
  val nakshatraTamil: String = "",
  val privateNotes: String = "",
  val createdAt: Long = System.currentTimeMillis()
) {
  val fullName: String get() = name
  val notes: String get() = privateNotes
  val totalConsultations: Int get() = 1
  val lastConsultationDate: String get() = ""
}
