package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class PaymentEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val paymentReference: String,
  val appointmentId: Long = 0,
  val clientName: String,
  val phoneNumber: String,
  val serviceName: String,
  val amount: Double,
  val paymentMethod: String = "GPay / UPI",
  val paymentDate: String,
  val status: PaymentStatus = PaymentStatus.PAID,
  val notes: String = "",
  val timestamp: Long = System.currentTimeMillis()
) {
  val customerName: String get() = clientName
  val transactionRef: String get() = paymentReference
  val createdAt: Long get() = timestamp
}
