package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class AppointmentStatus(val labelTamil: String, val labelEnglish: String) {
  PENDING("மதிப்பாய்வில் உள்ளது", "Pending"),
  CONFIRMED("உறுதிசெய்யப்பட்டது", "Confirmed"),
  IN_PROGRESS("நடைபெறுகிறது", "In Progress"),
  COMPLETED("நிறைவுற்றது", "Completed"),
  CANCELLED("ரத்து செய்யப்பட்டது", "Cancelled"),
  RESCHEDULED("மறுதிட்டமிடப்பட்டது", "Rescheduled"),
  NO_SHOW("வரவில்லை", "No Show")
}

enum class ConsultationMode(val labelTamil: String, val labelEnglish: String) {
  DIRECT_VISIT("நேரடி சந்திப்பு (அலுவலகம்)", "Direct In-Person (Office)"),
  ONLINE_VIDEO("ஆன்லைன் வீடியோ (Google Meet)", "Online Video (Google Meet)"),
  PHONE_CALL("தொலைபேசி அழைப்பு", "Phone Call"),
  WHATSAPP_CALL("WhatsApp அழைப்பு", "WhatsApp Call")
}

enum class PaymentStatus(val labelTamil: String, val labelEnglish: String) {
  PENDING("நிலுவையில் உள்ளது", "Pending"),
  PAID("செலுத்தப்பட்டது", "Paid"),
  FAILED("தோல்வியடைந்தது", "Failed"),
  REFUNDED("திருப்பி அளிக்கப்பட்டது", "Refunded")
}

@Entity(tableName = "appointments")
data class AppointmentEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val clientName: String,
  val phoneNumber: String,
  val email: String = "",
  val dateOfBirth: String = "",
  val timeOfBirth: String = "",
  val placeOfBirth: String = "",
  val serviceType: String,
  val preferredDate: String,
  val preferredTimeSlot: String,
  val consultationMode: ConsultationMode = ConsultationMode.DIRECT_VISIT,
  val birthDetailsNotes: String = "",
  val adminPrivateNotes: String = "",
  val status: AppointmentStatus = AppointmentStatus.PENDING,
  val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
  val paymentMethod: String = "GPay / UPI",
  val amount: Double = 500.0,
  val referenceNumber: String = "",
  val createdAt: Long = System.currentTimeMillis()
)

