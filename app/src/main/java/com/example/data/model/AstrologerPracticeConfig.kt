package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "astrologer_config")
data class AstrologerPracticeConfig(
  @PrimaryKey
  val id: Int = 1,
  val astrologerNameTamil: String = "ஜோதிடர் ஸ்ரீ ராஜகோபால்",
  val astrologerNameEnglish: String = "Jodhida Sri Rajagopal",
  val practiceNameTamil: String = "ஸ்ரீ விட்டல் ஜோதிடாலயம்",
  val practiceNameEnglish: String = "Sri Vittal Jothidalayam",
  val appNameTamil: String = "ஸ்ரீ விட்டல் ஜோதிடம்",
  val appNameEnglish: String = "Sri Vittal Astrology",
  val titleTamil: String = "பாரம்பரிய ஜோதிட நிபுணர் & வாஸ்து ஆலோசகர்",
  val titleEnglish: String = "Traditional Astrological Expert & Vastu Consultant",
  val experienceYears: String = "28+ ஆண்டுகள் அனுபவம்",
  val experienceYearsEnglish: String = "28+ Years of Experience",
  val consultationFeeAmount: String = "₹500",
  val morningTimingsTamil: String = "காலை 09.30 மணி முதல் மதியம் 01.00 மணி வரை",
  val morningTimingsEnglish: String = "09:30 AM to 01:00 PM",
  val breakTimingsTamil: String = "மதியம் 01.00 மணி முதல் மாலை 04.00 மணி வரை (இடைவேளை)",
  val breakTimingsEnglish: String = "01:00 PM to 04:00 PM (Break)",
  val eveningTimingsTamil: String = "மாலை 04.00 மணி முதல் இரவு 08.30 மணி வரை",
  val eveningTimingsEnglish: String = "04:00 PM to 08:30 PM",
  val workingDaysTamil: String = "திங்கள் முதல் சனிக்கிழமை வரை",
  val workingDaysEnglish: String = "Monday to Saturday",
  val isSundayOpen: Boolean = false,
  val onlineConsultationsEnabled: Boolean = true,
  val inPersonConsultationsEnabled: Boolean = true,
  val phoneConsultationsEnabled: Boolean = true,
  val blockedDatesCsv: String = "",
  val blockedSlotsCsv: String = "",
  val holidaysListCsv: String = "அமாவாசை, பௌர்ணமி சுப தினங்கள்",
  val defaultDurationMinutes: Int = 45,
  val bufferTimeMinutes: Int = 15,
  val maxAppointmentsPerDay: Int = 12,
  val adminPin: String = "9787",
  val adminEmail: String = "srivittal.jothidam@gmail.com",
  val adminPhone: String = "9787908717",
  val officeAddressTamil: String = "எண். 24, ஸ்ரீ விட்டல் ஜோதிடாலயம், கோவில் தெரு, மயிலாப்பூர், சென்னை - 600004",
  val officeAddressEnglish: String = "No. 24, Sri Vittal Jothidalayam, Temple Street, Mylapore, Chennai - 600004",
  val landmarkTamil: String = "மயிலாப்பூர் கபாலீஸ்வரர் கோவில் அருகில்",
  val landmarkEnglish: String = "Near Mylapore Kapaleeshwarar Temple",
  val phoneContact: String = "+91 97879 08717",
  val whatsappContact: String = "+91 97879 08717",
  val emailContact: String = "srivittal.jothidam@gmail.com",
  val consultationDurationTamil: String = "30 முதல் 45 நிமிடங்கள் வரை",
  val consultationDurationEnglish: String = "30 to 45 Minutes",
  val paymentMethodsTamil: String = "GPay, PhonePe, Paytm, UPI, வங்கி பரிமாற்றம் மற்றும் நேரடி ரொக்கம்",
  val paymentMethodsEnglish: String = "GPay, PhonePe, Paytm, UPI, Net Banking & Direct Cash",
  val upiId: String = "9787908717@upi"
) {
  val phoneNumber: String get() = adminPhone
  val whatsappNumber: String get() = whatsappContact
  val addressTamil: String get() = officeAddressTamil
  val addressEnglish: String get() = officeAddressEnglish
  val maxBookingsPerDay: Int get() = maxAppointmentsPerDay
  val consultationDurationMinutes: Int get() = defaultDurationMinutes
  val minAdvanceNoticeHours: Int get() = 2
  val isAvailableForInstantConsultation: Boolean get() = onlineConsultationsEnabled
}
