package com.example.data.model

enum class IntentType(
  val labelTamil: String,
  val isEscalationRequired: Boolean
) {
  // Support & Operational Intents (AI handles directly)
  CONSULTATION_FEE("ஆலோசனை கட்டணம்", false),
  CONTACT_TIMING("தொடர்பு கொள்ளும் நேரம்", false),
  APPOINTMENT_BOOKING("முன்பதிவு முறை", false),
  APPOINTMENT_RESCHEDULE("முன்பதிவு மாற்றம்", false),
  APPOINTMENT_CANCEL("முன்பதிவு ரத்து", false),
  PAYMENT_METHOD("கட்டண முறைகள்", false),
  OFFICE_LOCATION("அலுவலக முகவரி", false),
  ONLINE_CONSULTATION("ஆன்லைன் ஆலோசனை", false),
  SERVICES("ஆலோசனை சேவைகள்", false),
  REQUIRED_BIRTH_DETAILS("தேவையான குறிப்புகள்", false),
  CONSULTATION_DURATION("ஆலோசனை கால அளவு", false),
  CONTACT_INFORMATION("தொடர்பு விவரங்கள்", false),
  GENERAL_FAQ("பொதுவான கேள்விகள்", false),

  // Personal Astrology Prediction Intents (Must escalate to Astrologer)
  ASTROLOGY_PREDICTION("தனிப்பட்ட ஜோதிட கணிப்பு", true),
  ASTROLOGY_INTERPRETATION("கிரக நிலை ஆய்வு", true),
  HOROSCOPE_ANALYSIS("ஜாதக ஆய்வு", true),

  // Fallback / Unknown
  UNKNOWN("பொதுவான உதவி", false)
}
