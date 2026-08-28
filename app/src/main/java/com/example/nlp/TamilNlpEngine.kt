package com.example.nlp

import com.example.data.model.AppLanguage
import com.example.data.model.AstrologerPracticeConfig
import com.example.data.model.IntentType
import com.example.data.model.KnowledgeBaseEntity

data class NlpResult(
  val detectedIntent: IntentType,
  val tamilResponse: String,
  val isEscalation: Boolean,
  val matchedKnowledge: KnowledgeBaseEntity? = null,
  val confidenceScore: Float = 1.0f
)

object TamilNlpEngine {

  // Astrology prediction keywords (Tamil, Tanglish, English) that strictly require escalation
  private val predictionKeywords = listOf(
    // Marriage / Love / Relationships
    "marriage eppo", "en marriage", "kalyanam eppo", "marriage eppo nadakkum", "love marriage", "arranged marriage",
    "marriage life", "second marriage", "thirumanam eppo", "திருமணம் எப்போது", "திருமணம் நடக்கும்", "கல்யாணம் எப்போது",
    "பொருத்தம் எப்படி", "மாங்கல்ய பலம்", "வரன் எப்போது", "enaku marriage", "en kalyanam", "marry", "when will i marry",
    "marriage prediction", "match horoscope", "matching score",

    // Career / Job / Foreign / Exam
    "job eppo", "government job", "gov job", "govt job", "job kidaikkuma", "promotion eppo", "foreign chance",
    "foreign travel", "visa kidaikkuma", "abroad pogalama", "velai kidaikkuma", "வேலை எப்போது", "அரசு வேலை",
    "உத்தியோகம்", "பணி உயர்வு", "வெளிநாடு செல்வேனா", "வெளிநாடு வாய்ப்பு", "பரீட்சை பாஸ் ஆவேனா", "exam pass",
    "when will i get job", "career prediction", "will i go abroad",

    // Wealth / Business / Loss
    "business la profit", "business la loss", "money eppo varum", "kadan theeruma", "panam varuma", "lottery",
    "தொழில் லாபம்", "கடன் தீருமா", "பண வரவு", "தொழில் எப்படி இருக்கும்", "சொத்து சேருமா", "when will i get rich",

    // Health / Children
    "health epdi", "health problem", "udambu sariyaguma", "kuzhandhai eppo", "child birth", "pregnancy",
    "குழந்தை பிறக்குமா", "உடல் நலம்", "நோய் குணமாகுமா", "புத்திர பாக்கியம்", "child prediction",

    // Horoscope & Planetary Predictions
    "jathagam paathu sollunga", "en jathagam epdi", "horoscope check panni sollunga", "rasi palan",
    "en rasi", "en nakshathiram", "sani peyarchi", "guru peyarchi", "rahu kethu", "dosham irukka",
    "sevvaai dosham", "ragu kethu dosham", "sani enna palan tharum", "dasa puthi palan",
    "ஜாதகம் பார்த்து சொல்லுங்க", "என் ஜாதகம் எப்படி இருக்கு", "சனி என்ன பலன் தரும்", "ராகு கேது பலன்",
    "குரு பெயர்ச்சி", "சனி பெயர்ச்சி", "தோஷம் இருக்கா", "தசா புத்தி என்ன", "ராசி பலன்", "கிரக பலன்",
    "predict", "future", "horoscope prediction", "future reading", "read my chart", "astrology prediction"
  )

  // Tanglish / English / Tamil keyword mapping to Support Intents
  private val feeKeywords = listOf(
    "fee", "fees", "cost", "charge", "charges", "price", "rate", "evlo", "evvalavu", "fees evlo sir", "fee evlo",
    "consultation fee", "consultation charge", "consultation ku evlo", "jathagam paaka evlo", "how much fee", "how much cost",
    "கட்டணம்", "எவ்வளவு கட்டணம்", "ஆலோசனை கட்டணம்", "பணம் எவ்வளவு", "பீஸ் எவ்வளவு"
  )

  private val timingKeywords = listOf(
    "time", "timings", "timing", "hours", "schedule", "working hours", "eppo", "eppo contact", "eppo call", "when can i call", "when to contact",
    "call panlam", "contact panlam", "office time", "opening time", "closing time", "available time", "when are you open",
    "நேரம்", "எப்போது தொடர்பு கொள்ளலாம்", "எந்த நேரம்", "தொடர்பு நேரம்", "ஆலோசனை நேரம்", "எப்போது பேசலாம்"
  )

  private val bookingKeywords = listOf(
    "appointment", "book", "booking", "how to book", "epdi book panradhu", "slot", "slot book", "reserve",
    "date available", "pre booking", "முன்பதிவு", "முன்பதிவு செய்வது எப்படி", "பதிவு செய்ய", "அப்பாய்ண்ட்மென்ட்"
  )

  private val rescheduleKeywords = listOf(
    "reschedule", "change time", "change date", "postpone", "time mathalama", "date mathalama",
    "cancel", "ரத்து", "நேரம் மாற்ற", "தேதி மாற்றம்", "ரத்து செய்ய"
  )

  private val onlineKeywords = listOf(
    "online", "video call", "gmeet", "google meet", "zoom", "phone consultation", "remote", "virtual",
    "online consultation", "outstation", "abroad consultation", "video consultation", "whatsapp call",
    "ஆன்லைன்", "வீடியோ கால்", "தொலைபேசி ஆலோசனை", "வீட்டில் இருந்தே"
  )

  private val detailsKeywords = listOf(
    "details", "documents", "birth details", "enna details theva", "what details needed", "dob", "what to bring",
    "birth time", "place of birth", "jathaga kurippu", "requirements", "தேவையான தகவல்கள்", "என்ன விவரங்கள் தேவை", "குறிப்புகள்"
  )

  private val addressKeywords = listOf(
    "address", "location", "office", "where", "enga irukku", "office address", "landmark", "route",
    "map", "place", "how to reach", "directions", "எங்கே உள்ளது", "முகவரி", "அலுவலகம் எங்குள்ளது", "அட்ரஸ்"
  )

  private val paymentKeywords = listOf(
    "payment", "pay", "gpay", "phonepe", "paytm", "upi", "qr code", "cash", "bank transfer", "mode of payment",
    "epdi pay panradhu", "payment method", "payment options", "கட்டண முறைகள்", "பணம் செலுத்துவது எப்படி", "கூகுள் பே"
  )

  private val servicesKeywords = listOf(
    "services", "service", "what services", "what do you offer", "enna seivargal", "list of services", "sevai",
    "சேவைகள்", "என்னென்ன ஜோதிடம் பார்ப்பீர்கள்", "ஆலோசனை சேவைகள்"
  )

  private val durationKeywords = listOf(
    "duration", "how much time", "evlo neram", "minutes", "neram evlo", "how long", "time limit",
    "கால அளவு", "எவ்வளவு நேரம் ஆகும்"
  )

  private val contactKeywords = listOf(
    "contact", "phone number", "whatsapp number", "mobile number", "call", "email", "phone", "reach",
    "எண்", "தொலைபேசி எண்", "வாட்ஸ்அப் எண்", "தொடர்பு கொள்ள"
  )

  fun processQuery(
    rawQuery: String,
    knowledgeList: List<KnowledgeBaseEntity>,
    config: AstrologerPracticeConfig,
    language: AppLanguage = AppLanguage.TAMIL
  ): NlpResult {
    val normalized = rawQuery.trim().lowercase()

    // 1. STRICT CHECK: Personal Astrology Prediction / Planetary Reading Escalation
    if (isAstrologyPrediction(normalized)) {
      val escalationResponse = if (language == AppLanguage.ENGLISH) {
        "This question involves personal horoscope predictions and planetary chart analysis. The AI Assistant only provides informational support regarding services, fees, and appointment bookings.\n\nPlease contact Astrologer Sri Rajagopal directly or book an appointment using the options below."
      } else {
        "இந்தக் கேள்விக்கு தனிப்பட்ட ஜாதகம் மற்றும் கிரக நிலை ஆய்வு தேவைப்படுகிறது. AI உதவியாளர் தனிப்பட்ட ஜோதிட கணிப்புகளை வழங்காது.\n\nஜோதிட ஸ்ரீ ராஜகோபால் அவர்களை நேரடியாகத் தொடர்புகொண்டு ஆலோசனை பெறவும்."
      }

      return NlpResult(
        detectedIntent = IntentType.ASTROLOGY_PREDICTION,
        tamilResponse = escalationResponse,
        isEscalation = true
      )
    }

    // 2. Identify Support Intents
    val intent = classifyIntent(normalized)

    // 3. Search Knowledge Base for intent matching or keyword overlap
    val matchedKb = findBestMatchInKnowledgeBase(normalized, intent, knowledgeList)

    if (matchedKb != null && language == AppLanguage.TAMIL) {
      return NlpResult(
        detectedIntent = intent,
        tamilResponse = matchedKb.answerTamil,
        isEscalation = false,
        matchedKnowledge = matchedKb
      )
    }

    // 4. Dynamic Response Generation based on Practice Config and Language
    val dynamicResponse = generateDynamicConfigResponse(intent, config, language)
    if (dynamicResponse != null) {
      return NlpResult(
        detectedIntent = intent,
        tamilResponse = dynamicResponse,
        isEscalation = false
      )
    }

    // 5. Default Helpful Fallback
    val fallbackResponse = if (language == AppLanguage.ENGLISH) {
      "Namaste. For detailed information regarding consultations, please contact Astrologer Sri Rajagopal or use the 'Book Appointment' section to reserve your slot.\n\nContact: ${config.phoneContact} (${config.morningTimingsEnglish} & ${config.eveningTimingsEnglish})."
    } else {
      "வணக்கம். நீங்கள் கேட்ட கேள்விக்குரிய விவரங்களை நேரடியாக அறிந்துகொள்ள ஜோதிட ஸ்ரீ ராஜகோபால் அவர்களைத் தொடர்புகொள்ளலாம் அல்லது 'ஆலோசனை முன்பதிவு' மூலம் உங்கள் நேரத்தைப் பதிவு செய்யலாம்.\n\nதொடர்பு எண்: ${config.phoneContact} (காலை 10.00 - இரவு 8.00)."
    }

    return NlpResult(
      detectedIntent = IntentType.UNKNOWN,
      tamilResponse = fallbackResponse,
      isEscalation = false
    )
  }

  private fun isAstrologyPrediction(query: String): Boolean {
    return predictionKeywords.any { keyword -> query.contains(keyword, ignoreCase = true) }
  }

  private fun classifyIntent(query: String): IntentType {
    return when {
      feeKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.CONSULTATION_FEE
      timingKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.CONTACT_TIMING
      bookingKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.APPOINTMENT_BOOKING
      rescheduleKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.APPOINTMENT_RESCHEDULE
      onlineKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.ONLINE_CONSULTATION
      detailsKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.REQUIRED_BIRTH_DETAILS
      addressKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.OFFICE_LOCATION
      paymentKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.PAYMENT_METHOD
      servicesKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.SERVICES
      durationKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.CONSULTATION_DURATION
      contactKeywords.any { query.contains(it, ignoreCase = true) } -> IntentType.CONTACT_INFORMATION
      else -> IntentType.GENERAL_FAQ
    }
  }

  private fun findBestMatchInKnowledgeBase(
    query: String,
    intent: IntentType,
    knowledgeList: List<KnowledgeBaseEntity>
  ): KnowledgeBaseEntity? {
    // Exact intent match first
    val byIntent = knowledgeList.firstOrNull { it.intentKey == intent.name }
    if (byIntent != null && intent != IntentType.GENERAL_FAQ && intent != IntentType.UNKNOWN) {
      return byIntent
    }

    // Keyword matching
    val words = query.split("\\s+".toRegex()).filter { it.length > 2 }
    return knowledgeList.maxByOrNull { kb ->
      var score = 0
      val triggers = kb.triggerKeywords.lowercase().split(",")
      for (trigger in triggers) {
        val trimmed = trigger.trim()
        if (query.contains(trimmed)) score += 5
        for (word in words) {
          if (trimmed.contains(word)) score += 2
        }
      }
      if (kb.questionTamil.contains(query)) score += 10
      score
    }?.takeIf { kb ->
      // Ensure at least minimal relevance
      kb.triggerKeywords.split(",").any { query.contains(it.trim().lowercase()) } ||
          kb.questionTamil.contains(query)
    }
  }

  private fun generateDynamicConfigResponse(
    intent: IntentType,
    config: AstrologerPracticeConfig,
    language: AppLanguage
  ): String? {
    if (language == AppLanguage.ENGLISH) {
      return when (intent) {
        IntentType.CONSULTATION_FEE ->
          "The consultation fee is ${config.consultationFeeAmount}.\n\nYou can book an appointment using the button below or contact directly via WhatsApp."

        IntentType.CONTACT_TIMING ->
          "Astrologer Sri Rajagopal is available from ${config.morningTimingsEnglish} and ${config.eveningTimingsEnglish}.\n\n${config.workingDaysEnglish}."

        IntentType.APPOINTMENT_BOOKING ->
          "To book an appointment, please use the 'Appointment' tab in this app or send your details via WhatsApp to ${config.whatsappContact}."

        IntentType.OFFICE_LOCATION ->
          "Office Location:\n${config.officeAddressEnglish}\n(Landmark: ${config.landmarkEnglish})."

        IntentType.PAYMENT_METHOD ->
          "Payment options accepted: ${config.paymentMethodsEnglish}."

        IntentType.CONTACT_INFORMATION ->
          "Contact Details:\nPhone / WhatsApp: ${config.phoneContact}\nEmail: ${config.emailContact}\nAddress: ${config.officeAddressEnglish}."

        IntentType.CONSULTATION_DURATION ->
          "A consultation session typically lasts around ${config.consultationDurationEnglish}."

        IntentType.ONLINE_CONSULTATION ->
          "Yes! Online consultations are available via Google Meet, WhatsApp Video Call, or direct phone call for clients worldwide."

        IntentType.REQUIRED_BIRTH_DETAILS ->
          "Required details for consultation:\n1. Exact Date of Birth\n2. Accurate Time of Birth\n3. Place of Birth (City / Town)\n4. Existing horoscope chart copy (if available)\n5. Specific questions to discuss."

        IntentType.APPOINTMENT_RESCHEDULE ->
          "Yes, appointments can be rescheduled. Please notify us at least 2 hours in advance via phone or WhatsApp at ${config.phoneContact} to pick an alternate time slot."

        IntentType.SERVICES ->
          "Available Services:\n1. General Horoscope Reading\n2. Marriage Compatibility & Muhurtham\n3. Career & Job Progression\n4. Business & Partnership Guidance\n5. Children Education & Prospects\n6. Vastu Shastra Consultation."

        else -> null
      }
    }

    return when (intent) {
      IntentType.CONSULTATION_FEE ->
        "ஆலோசனைக்கான கட்டணம் ${config.consultationFeeAmount}.\n\nமுன்பதிவு செய்ய கீழே உள்ள பொத்தானைத் தேர்வு செய்யலாம் அல்லது நேரடியாக வாட்ஸ்அப் மூலம் தொடர்பு கொள்ளலாம்."

      IntentType.CONTACT_TIMING ->
        "ஜோதிடர் ஸ்ரீ ராஜகோபால் அவர்களை ${config.morningTimingsTamil} வரையும், ${config.eveningTimingsTamil} வரையும் தொடர்புகொள்ளலாம்.\n\n${config.workingDaysTamil}."

      IntentType.APPOINTMENT_BOOKING ->
        "ஆலோசனை முன்பதிவு செய்ய செயலியின் 'முன்பதிவு' பக்கத்தைப் பயன்படுத்தலாம் அல்லது ${config.whatsappContact} என்ற WhatsApp எண்ணில் நேரடியாகத் தொடர்பு கொள்ளலாம்."

      IntentType.OFFICE_LOCATION ->
        "அலுவலக முகவரி:\n${config.officeAddressTamil}\n(${config.landmarkTamil})."

      IntentType.PAYMENT_METHOD ->
        "கட்டணங்களை ${config.paymentMethodsTamil} மூலமாக செலுத்தலாம்."

      IntentType.CONTACT_INFORMATION ->
        "தொடர்பு விவரங்கள்:\nதொலைபேசி / WhatsApp: ${config.phoneContact}\nமின்னஞ்சல்: ${config.emailContact}\nமுகவரி: ${config.officeAddressTamil}."

      IntentType.CONSULTATION_DURATION ->
        "ஒரு ஆலோசனையின் கால அளவு பொதுவாக ${config.consultationDurationTamil} ஆகும்."

      IntentType.ONLINE_CONSULTATION ->
        "ஆம், நேரடி வருகை தர இயலாத வாடிக்கையாளர்களுக்கு Google Meet, WhatsApp Video Call அல்லது தொலைபேசி வழியாக ஆன்லைன் ஆலோசனை வசதி உள்ளது."

      IntentType.REQUIRED_BIRTH_DETAILS ->
        "ஆலோசனைக்கு பின்வரும் குறிப்புகள் அவசியம்:\n1. துல்லியமான பிறந்த தேதி\n2. பிறந்த நேரம்\n3. பிறந்த ஊர்\n4. ஏற்கனவே உள்ள ஜாதகக் குறிப்பு (இருப்பின்)\n5. கேட்க வேண்டிய கேள்விகள்."

      IntentType.APPOINTMENT_RESCHEDULE ->
        "ஆம், மாற்றிக் கொள்ளலாம். உங்கள் முன்பதிவு நேரத்திற்கு குறைந்தபட்சம் 2 மணி நேரத்திற்கு முன்பாக ${config.phoneContact} என்ற எண்ணிற்கு அழைத்து அல்லது WhatsApp மூலம் தெரிவித்து மாற்று நேரத்தைத் தேர்வு செய்து கொள்ளலாம்."

      IntentType.SERVICES ->
        "வழங்கப்படும் சேவைகள்:\n1. பொது ஜாதக பலன் ஆய்வு\n2. திருமணப் பொருத்தம் & முகூர்த்தம்\n3. தொழில், வேலைவாய்ப்பு & உத்தியோக உயர்வு\n4. வணிகம் & தொழில் கூட்டாண்மை ஆலோசனை\n5. குழந்தைகள் கல்வி & உயர் படிப்பு வழிகாட்டல்\n6. வாஸ்து சாஸ்திர ஆலோசனை."

      else -> null
    }
  }
}
