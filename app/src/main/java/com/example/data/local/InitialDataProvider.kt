package com.example.data.local

import com.example.data.model.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object InitialDataProvider {

  private val todayStr: String by lazy {
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
  }

  val defaultServices = listOf(
    ServiceEntity(
      id = "general_horoscope",
      titleTamil = "பொது ஜாதக ஆலோசனை",
      titleEnglish = "General Horoscope Consultation",
      descriptionTamil = "உங்கள் ஜாதகம் மற்றும் கிரக நிலைகள் தொடர்பான விரிவான தனிப்பட்ட ஆலோசனை.",
      descriptionEnglish = "Comprehensive personal reading of your birth chart, planetary alignments, and upcoming phases.",
      feeTamil = "₹500",
      feeEnglish = "₹500",
      durationTamil = "30-40 நிமிடங்கள்",
      durationEnglish = "30-40 Minutes",
      priceAmount = 500.0,
      onlineAvailable = true,
      inPersonAvailable = true,
      isActive = true,
      iconType = "star",
      displayOrder = 1
    ),
    ServiceEntity(
      id = "marriage_consultation",
      titleTamil = "திருமண ஆலோசனை & பொருத்தம்",
      titleEnglish = "Marriage & Kundali Matching",
      descriptionTamil = "திருமணம் தொடர்பான கேள்விகளுக்கான தனிப்பட்ட ஜோதிட ஆலோசனை மற்றும் பொருத்த ஆய்வு.",
      descriptionEnglish = "Detailed astrological matching, 10 poruthams check, dosha analysis, and auspicious marriage timing.",
      feeTamil = "₹750",
      feeEnglish = "₹750",
      durationTamil = "45 நிமிடங்கள்",
      durationEnglish = "45 Minutes",
      priceAmount = 750.0,
      onlineAvailable = true,
      inPersonAvailable = true,
      isActive = true,
      iconType = "favorite",
      displayOrder = 2
    ),
    ServiceEntity(
      id = "career_consultation",
      titleTamil = "தொழில் & பணிப்பாதை ஆலோசனை",
      titleEnglish = "Career & Job Consultation",
      descriptionTamil = "தொழில் மற்றும் உத்தியோக வளர்ச்சி தொடர்பான துல்லியமான ஜோதிட வழிகாட்டுதல்.",
      descriptionEnglish = "Precise astrological guidance for job stability, promotions, career switches, and foreign opportunities.",
      feeTamil = "₹500",
      feeEnglish = "₹500",
      durationTamil = "30-40 நிமிடங்கள்",
      durationEnglish = "30-40 Minutes",
      priceAmount = 500.0,
      onlineAvailable = true,
      inPersonAvailable = true,
      isActive = true,
      iconType = "work",
      displayOrder = 3
    ),
    ServiceEntity(
      id = "business_consultation",
      titleTamil = "வணிக & தொழில் வளர்ச்சி ஆலோசனை",
      titleEnglish = "Business & Trade Growth Consultation",
      descriptionTamil = "புதிய வணிகம் தொடங்குதல் மற்றும் வர்த்தக மேம்பாடு தொடர்பான தனிப்பட்ட ஆலோசனை.",
      descriptionEnglish = "Specialized astrological advice for new ventures, partnership compatibility, and financial growth.",
      feeTamil = "₹1,000",
      feeEnglish = "₹1,000",
      durationTamil = "45-60 நிமிடங்கள்",
      durationEnglish = "45-60 Minutes",
      priceAmount = 1000.0,
      onlineAvailable = true,
      inPersonAvailable = true,
      isActive = true,
      iconType = "trending_up",
      displayOrder = 4
    ),
    ServiceEntity(
      id = "education_consultation",
      titleTamil = "கல்வி & குழந்தைகள் எதிர்கால ஆலோசனை",
      titleEnglish = "Education & Children's Future Guidance",
      descriptionTamil = "குழந்தைகளின் கல்வித் துறை, உயர் படிப்பு மற்றும் எதிர்கால நல்வாழ்விற்கான வழிகாட்டல்.",
      descriptionEnglish = "Guidance on suitable study streams, higher education, memory enhancement, and children's prosperity.",
      feeTamil = "₹500",
      feeEnglish = "₹500",
      durationTamil = "30 நிமிடங்கள்",
      durationEnglish = "30 Minutes",
      priceAmount = 500.0,
      onlineAvailable = true,
      inPersonAvailable = true,
      isActive = true,
      iconType = "school",
      displayOrder = 5
    ),
    ServiceEntity(
      id = "vastu_consultation",
      titleTamil = "வாஸ்து & கிரக சாந்தி ஆலோசனை",
      titleEnglish = "Vastu Shastra & Planetary Remedies",
      descriptionTamil = "வீடு, மனை மற்றும் வணிக கட்டிடங்களுக்கான பாரம்பரிய வாஸ்து சாஸ்திர வழிகாட்டுதல்.",
      descriptionEnglish = "Traditional Vastu guidance for home, plot, and commercial buildings without demolition.",
      feeTamil = "₹1,500",
      feeEnglish = "₹1,500",
      durationTamil = "நேரில் அல்லது வரைபட ஆய்வு",
      durationEnglish = "In-Person or Blueprint Review",
      priceAmount = 1500.0,
      onlineAvailable = true,
      inPersonAvailable = true,
      isActive = true,
      iconType = "home",
      displayOrder = 6
    )
  )

  val defaultCustomers = listOf(
    CustomerEntity(
      id = 1,
      name = "சுரேஷ் குமார்",
      phoneNumber = "+91 98401 98765",
      email = "suresh.kumar@gmail.com",
      dateOfBirth = "14-07-1994",
      timeOfBirth = "காலை 06:30",
      placeOfBirth = "திருச்சி",
      rashiTamil = "கன்னி",
      nakshatraTamil = "ஹஸ்தம்",
      privateNotes = "வரன் ஜாதகப் பொருத்தம் பார்க்க வேண்டும். செவ்வாய் தோஷ பரிகாரம் பரிந்துரைக்கப்பட்டது.",
      createdAt = System.currentTimeMillis() - 86400000L * 2
    ),
    CustomerEntity(
      id = 2,
      name = "கார்த்திகா மகாலிங்கம்",
      phoneNumber = "+91 97910 22334",
      email = "karthika.m@yahoo.com",
      dateOfBirth = "22-11-1998",
      timeOfBirth = "இரவு 08:15",
      placeOfBirth = "மதுரை",
      rashiTamil = "தனுசு",
      nakshatraTamil = "மூலம்",
      privateNotes = "குரு தசா நடக்கிறது. நல்ல பலன்கள் உண்டு. ராகு பரிகாரம் தேவை.",
      createdAt = System.currentTimeMillis() - 86400000L * 3
    ),
    CustomerEntity(
      id = 3,
      name = "வெங்கடேசன் ஆர்.",
      phoneNumber = "+91 94432 55667",
      email = "venkat.r@gmail.com",
      dateOfBirth = "05-03-1988",
      timeOfBirth = "பிற்பகல் 02:40",
      placeOfBirth = "சேலம்",
      rashiTamil = "கும்பம்",
      nakshatraTamil = "சதயம்",
      privateNotes = "புதிய நிறுவனம் துவங்க நல்ல நேரம் கேட்டுள்ளார். பத்தாம் அதிபதி சுப பலம் பெற்றுள்ளார்.",
      createdAt = System.currentTimeMillis() - 86400000L * 5
    ),
    CustomerEntity(
      id = 4,
      name = "மகேந்திரன் பி.",
      phoneNumber = "+91 98841 33445",
      email = "mahen.p@outlook.com",
      dateOfBirth = "19-09-1985",
      timeOfBirth = "காலை 10:15",
      placeOfBirth = "சென்னை",
      rashiTamil = "துலாம்",
      nakshatraTamil = "சுவாதி",
      privateNotes = "வீட்டு வாஸ்து திருத்தம் தொடர்பாக ஆலோசித்தார். வடக்கு திசை வாசல் சிறப்பு.",
      createdAt = System.currentTimeMillis() - 86400000L * 8
    )
  )

  val defaultAppointments = listOf(
    AppointmentEntity(
      id = 1,
      clientName = "சுரேஷ் குமார்",
      phoneNumber = "+91 98401 98765",
      email = "suresh.kumar@gmail.com",
      dateOfBirth = "14-07-1994",
      timeOfBirth = "06:30 AM",
      placeOfBirth = "திருச்சி",
      serviceType = "திருமண ஆலோசனை & பொருத்தம்",
      preferredDate = "Today",
      preferredTimeSlot = "முற்பகல் 11:30 - 12:30",
      consultationMode = ConsultationMode.DIRECT_VISIT,
      birthDetailsNotes = "பிறந்த தேதி: 14-07-1994, நேரம்: காலை 06.30, இடம்: திருச்சி. வரன் ஜாதகப் பொருத்தம் பார்க்க வேண்டும்.",
      adminPrivateNotes = "வரன் ஜாதகம் வந்துள்ளது. 10க்கு 8 பொருத்தங்கள் உள்ளன.",
      status = AppointmentStatus.CONFIRMED,
      paymentStatus = PaymentStatus.PAID,
      paymentMethod = "GPay / PhonePe",
      amount = 750.0,
      referenceNumber = "SVJ-20260828-01"
    ),
    AppointmentEntity(
      id = 2,
      clientName = "கார்த்திகா மகாலிங்கம்",
      phoneNumber = "+91 97910 22334",
      email = "karthika.m@yahoo.com",
      dateOfBirth = "22-11-1998",
      timeOfBirth = "08:15 PM",
      placeOfBirth = "மதுரை",
      serviceType = "பொது ஜாதக ஆலோசனை",
      preferredDate = "Today",
      preferredTimeSlot = "மாலை 04:30 - 05:30",
      consultationMode = ConsultationMode.ONLINE_VIDEO,
      birthDetailsNotes = "பிறந்த தேதி: 22-11-1998, நேரம்: இரவு 08.15, இடம்: மதுரை. தசா புத்தி மற்றும் பரிகாரங்கள் அறிய விரும்புகிறேன்.",
      adminPrivateNotes = "Google Meet இணைப்பு அனுப்பப்பட்டுள்ளது.",
      status = AppointmentStatus.CONFIRMED,
      paymentStatus = PaymentStatus.PAID,
      paymentMethod = "GPay / UPI",
      amount = 500.0,
      referenceNumber = "SVJ-20260828-02"
    ),
    AppointmentEntity(
      id = 3,
      clientName = "வெங்கடேசன் ஆர்.",
      phoneNumber = "+91 94432 55667",
      email = "venkat.r@gmail.com",
      dateOfBirth = "05-03-1988",
      timeOfBirth = "02:40 PM",
      placeOfBirth = "சேலம்",
      serviceType = "தொழில் & பணிப்பாதை ஆலோசனை",
      preferredDate = "Today",
      preferredTimeSlot = "மாலை 06:30 - 07:30",
      consultationMode = ConsultationMode.PHONE_CALL,
      birthDetailsNotes = "பிறந்த தேதி: 05-03-1988, நேரம்: பிற்பகல் 02.40, இடம்: சேலம். புதிய தொழில் துவங்குவது பற்றி ஆலோசனை.",
      adminPrivateNotes = "மாலை 6:30க்கு அழைக்கவும்.",
      status = AppointmentStatus.PENDING,
      paymentStatus = PaymentStatus.PENDING,
      paymentMethod = "UPI",
      amount = 500.0,
      referenceNumber = "SVJ-20260828-03"
    ),
    AppointmentEntity(
      id = 4,
      clientName = "மகேந்திரன் பி.",
      phoneNumber = "+91 98841 33445",
      email = "mahen.p@outlook.com",
      dateOfBirth = "19-09-1985",
      timeOfBirth = "10:15 AM",
      placeOfBirth = "சென்னை",
      serviceType = "வாஸ்து & கிரக சாந்தி ஆலோசனை",
      preferredDate = "Tomorrow",
      preferredTimeSlot = "காலை 10:30 - 11:30",
      consultationMode = ConsultationMode.DIRECT_VISIT,
      birthDetailsNotes = "வீட்டின் வரைபடம் கொண்டு வருகிறார்.",
      adminPrivateNotes = "வாஸ்து வரைபட ஆய்வு.",
      status = AppointmentStatus.CONFIRMED,
      paymentStatus = PaymentStatus.PAID,
      paymentMethod = "Direct Cash",
      amount = 1500.0,
      referenceNumber = "SVJ-20260829-01"
    ),
    AppointmentEntity(
      id = 5,
      clientName = "பிரியா ராமச்சந்திரன்",
      phoneNumber = "+91 91761 44556",
      email = "priya.rc@gmail.com",
      dateOfBirth = "12-04-2001",
      timeOfBirth = "11:00 AM",
      placeOfBirth = "கோயம்புத்தூர்",
      serviceType = "கல்வி & குழந்தைகள் எதிர்கால ஆலோசனை",
      preferredDate = "Day after tomorrow",
      preferredTimeSlot = "மாலை 05:30 - 06:30",
      consultationMode = ConsultationMode.ONLINE_VIDEO,
      birthDetailsNotes = "உயர்கல்வி வெளிநாடு செல்ல வாய்ப்பு உள்ளதா என அறிய வேண்டும்.",
      adminPrivateNotes = "",
      status = AppointmentStatus.PENDING,
      paymentStatus = PaymentStatus.PENDING,
      paymentMethod = "GPay / UPI",
      amount = 500.0,
      referenceNumber = "SVJ-20260830-01"
    ),
    AppointmentEntity(
      id = 6,
      clientName = "ராஜேந்திரன் கே.",
      phoneNumber = "+91 99403 66778",
      email = "rajendran.k@yahoo.com",
      dateOfBirth = "03-01-1979",
      timeOfBirth = "04:15 PM",
      placeOfBirth = "தஞ்சாவூர்",
      serviceType = "வணிக & தொழில் வளர்ச்சி ஆலோசனை",
      preferredDate = "This Week",
      preferredTimeSlot = "மாலை 06:30 - 07:30",
      consultationMode = ConsultationMode.DIRECT_VISIT,
      birthDetailsNotes = "கூட்டுத் தொழில் துவங்குவது பற்றி.",
      adminPrivateNotes = "நிறைவுற்றது. நல்வாழ்த்துகள் கூறப்பட்டது.",
      status = AppointmentStatus.COMPLETED,
      paymentStatus = PaymentStatus.PAID,
      paymentMethod = "PhonePe",
      amount = 1000.0,
      referenceNumber = "SVJ-20260826-01"
    )
  )

  val defaultPayments = listOf(
    PaymentEntity(
      id = 1,
      paymentReference = "PAY-SVJ-90124",
      appointmentId = 1,
      clientName = "சுரேஷ் குமார்",
      phoneNumber = "+91 98401 98765",
      serviceName = "திருமண ஆலோசனை & பொருத்தம்",
      amount = 750.0,
      paymentMethod = "GPay / UPI",
      paymentDate = "Today",
      status = PaymentStatus.PAID,
      notes = "GPay ID: 9840198765@okhdfcbank"
    ),
    PaymentEntity(
      id = 2,
      paymentReference = "PAY-SVJ-90125",
      appointmentId = 2,
      clientName = "கார்த்திகா மகாலிங்கம்",
      phoneNumber = "+91 97910 22334",
      serviceName = "பொது ஜாதக ஆலோசனை",
      amount = 500.0,
      paymentMethod = "PhonePe",
      paymentDate = "Today",
      status = PaymentStatus.PAID,
      notes = "PhonePe Ref: 429188192"
    ),
    PaymentEntity(
      id = 3,
      paymentReference = "PAY-SVJ-90126",
      appointmentId = 4,
      clientName = "மகேந்திரன் பி.",
      phoneNumber = "+91 98841 33445",
      serviceName = "வாஸ்து & கிரக சாந்தி ஆலோசனை",
      amount = 1500.0,
      paymentMethod = "Direct Cash",
      paymentDate = "Yesterday",
      status = PaymentStatus.PAID,
      notes = "நேரடி முன்பணம் பெறப்பட்டது"
    ),
    PaymentEntity(
      id = 4,
      paymentReference = "PAY-SVJ-90127",
      appointmentId = 6,
      clientName = "ராஜேந்திரன் கே.",
      phoneNumber = "+91 99403 66778",
      serviceName = "வணிக & தொழில் வளர்ச்சி ஆலோசனை",
      amount = 1000.0,
      paymentMethod = "PhonePe",
      paymentDate = "26-08-2026",
      status = PaymentStatus.PAID,
      notes = "UPI Transaction Successful"
    )
  )

  val defaultNotifications = listOf(
    AdminNotificationEntity(
      id = 1,
      titleTamil = "புதிய முன்பதிவு கோரிக்கை",
      titleEnglish = "New Booking Request",
      messageTamil = "பிரியா ராமச்சந்திரன் அவர்களிடமிருந்து புதிய முன்பதிவு கோரிக்கை வந்துள்ளது (கல்வி ஆலோசனை).",
      messageEnglish = "New booking request received from Priya Ramachandran for Education Guidance.",
      type = "BOOKING",
      relatedId = 5,
      isRead = false,
      timestamp = System.currentTimeMillis() - 1000 * 60 * 15
    ),
    AdminNotificationEntity(
      id = 2,
      titleTamil = "கட்டணம் பெறப்பட்டது (₹750)",
      titleEnglish = "Payment Received (₹750)",
      messageTamil = "சுரேஷ் குமார் திருமண ஆலோசனை கட்டணம் ₹750 GPay மூலம் செலுத்தியுள்ளார்.",
      messageEnglish = "Suresh Kumar paid ₹750 via GPay for Marriage Consultation.",
      type = "PAYMENT",
      relatedId = 1,
      isRead = false,
      timestamp = System.currentTimeMillis() - 1000 * 60 * 45
    ),
    AdminNotificationEntity(
      id = 3,
      titleTamil = "இன்றைய சந்திப்பு நினைவூட்டல்",
      titleEnglish = "Today's Appointment Reminder",
      messageTamil = "காலை 11:30 மணிக்கு சுரேஷ் குமார் உடனான நேரடி சந்திப்பு திட்டமிடப்பட்டுள்ளது.",
      messageEnglish = "Direct visit appointment with Suresh Kumar scheduled at 11:30 AM.",
      type = "REMINDER",
      relatedId = 1,
      isRead = true,
      timestamp = System.currentTimeMillis() - 1000 * 60 * 120
    )
  )

  val defaultKnowledgeBase = listOf(
    KnowledgeBaseEntity(
      id = 1,
      questionTamil = "ஆலோசனைக்கான கட்டணம் எவ்வளவு?",
      answerTamil = "ஆலோசனைக்கான கட்டணம் ₹500.\n\nமுன்பதிவு செய்ய கீழே உள்ள பொத்தானைத் தேர்வு செய்யலாம் அல்லது நேரடியாக வாட்ஸ்அப் மூலம் தொடர்பு கொள்ளலாம்.",
      categoryTamil = "கட்டணம்",
      intentKey = "CONSULTATION_FEE",
      triggerKeywords = "fee, fees, consultation fee, cost, charge, price, evlo, evvalavu, fees evlo sir, consultation ku evlo, consultation charge enna, ஜாதகம் பார்க்க எவ்வளவு, கட்டணம், எவ்வளவு கட்டணம், பணம், ஆலோசனை கட்டணம்",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 2,
      questionTamil = "ஜோதிடரை எப்போது தொடர்பு கொள்ளலாம்? (நேரம்)",
      answerTamil = "ஜோதிடரை காலை 09.30 மணி முதல் மதியம் 01.00 மணி வரையும், மாலை 04.00 மணி முதல் இரவு 08.30 மணி வரையும் தொடர்புகொள்ளலாம்.\n\nதிங்கள் முதல் சனிக்கிழமை வரை ஆலோசனைகள் நடைபெறும். (ஞாயிற்றுக்கிழமை விடுமுறை).",
      categoryTamil = "நேரம்",
      intentKey = "CONTACT_TIMING",
      triggerKeywords = "time, timings, timing, eppo, eppo contact panlam, eppo call panradhu, when can i call, call timing, contact time, office time, opening time, நேரம், தொடர்பு நேரம், எப்போது பேசலாம், எந்த நேரம்",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 3,
      questionTamil = "ஆலோசனைக்கு முன்பதிவு செய்வது எப்படி?",
      answerTamil = "செயலியின் 'ஆலோசனை முன்பதிவு' பக்கத்தில் உங்கள் பெயர், தொலைபேசி எண், விரும்பும் தேதி மற்றும் நேரத்தைத் தேர்வு செய்து முன்பதிவு கோரிக்கையை அனுப்பலாம்.\n\nஅல்லது +91 97879 08717 என்ற WhatsApp எண்ணிற்கு உங்கள் விவரங்களை அனுப்பி நேரடியாக முன்பதிவு செய்யலாம்.",
      categoryTamil = "முன்பதிவு",
      intentKey = "APPOINTMENT_BOOKING",
      triggerKeywords = "appointment, book, booking, how to book, epdi book panradhu, slot, slot irukka, book panna enna pannanum, முன்பதிவு, அப்பாய்ண்ட்மென்ட், பதிவு செய்வது எப்படி",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 4,
      questionTamil = "ஆன்லைனில் (வீடியோ / போன்) ஆலோசனை பெற முடியுமா?",
      answerTamil = "ஆம், நிச்சயம் பெறலாம். நேரடி வருகை தர இயலாத வெளியூர் மற்றும் வெளிநாட்டு வாடிக்கையாளர்களுக்கு Google Meet, WhatsApp Video Call அல்லது தொலைபேசி அழைப்பு வழியாக விரிவான ஆலோசனைகள் வழங்கப்படுகின்றன.",
      categoryTamil = "ஆன்லைன் ஆலோசனை",
      intentKey = "ONLINE_CONSULTATION",
      triggerKeywords = "online, online consultation, video call, gmeet, zoom, phone call, remote, outstation, abroad, வெளிநாடு, ஆன்லைன், போன்ல பேசலாமா, வீடியோ கால்",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 5,
      questionTamil = "ஆலோசனைக்கு என்னென்ன தகவல்கள் தேவை?",
      answerTamil = "ஆலோசனைக்கு பின்வரும் குறிப்புகள் அவசியம்:\n1. துல்லியமான பிறந்த தேதி (Date of Birth)\n2. பிறந்த நேரம் (Exact Birth Time)\n3. பிறந்த ஊர் அல்லது மாவட்டம் (Place of Birth)\n4. ஏற்கனவே உள்ள ஜாதகக் குறிப்பு நகல் (இருப்பின்)\n5. ஆலோசிக்க வேண்டிய முக்கியமான கேள்விகள்.",
      categoryTamil = "தேவையான தகவல்கள்",
      intentKey = "REQUIRED_BIRTH_DETAILS",
      triggerKeywords = "details, documents, birth details, enna details theva, what details needed, dob, time of birth, place of birth, jathaga kurippu, தேவையான விவரங்கள், என்ன தகவல் வேண்டும், குறிப்புகள்",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 6,
      questionTamil = "அலுவலகத்தின் முகவரி எங்குள்ளது?",
      answerTamil = "அலுவலக முகவரி:\nஎண். 24, ஸ்ரீ விட்டல் ஜோதிடாலயம், கோவில் தெரு, மயிலாப்பூர், சென்னை - 600004.\n(அடையாளம்: மயிலாப்பூர் கபாலீஸ்வரர் கோவில் அருகில்).",
      categoryTamil = "தொடர்பு",
      intentKey = "OFFICE_LOCATION",
      triggerKeywords = "address, location, office, where, enga irukku, office address, location send pannunga, map, landmark, முகவரி, இடம், எங்கே உள்ளது, ஆபீஸ் அட்ரஸ், ஸ்ரீ விட்டல் ஜோதிடாலயம்",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 7,
      questionTamil = "கட்டணம் செலுத்தும் முறைகள் யாவை?",
      answerTamil = "கட்டணங்களை GPay, PhonePe, Paytm, BHIM UPI மற்றும் வங்கி கணக்கு பரிமாற்றம் (NEFT/IMPS) மூலமாக செலுத்தலாம். நேரடி ஆலோசனைக்கு வருபவர்கள் ரொக்கமாகவும் (Cash) செலுத்தலாம்.",
      categoryTamil = "கட்டணம்",
      intentKey = "PAYMENT_METHOD",
      triggerKeywords = "payment, pay, gpay, phonepe, upi, qr code, cash, online payment, transfer, epdi pay panradhu, payment method, கூகுள் பே, பணம் செலுத்துவது எப்படி, யுபிஐ",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 8,
      questionTamil = "முன்பதிவு செய்த நேரத்தை மாற்ற அல்லது ரத்து செய்ய முடியுமா?",
      answerTamil = "ஆம், மாற்றிக் கொள்ளலாம். உங்கள் முன்பதிவு நேரத்திற்கு குறைந்தபட்சம் 2 மணி நேரத்திற்கு முன்பாக +91 97879 08717 என்ற எண்ணிற்கு அழைத்து அல்லது WhatsApp மூலம் தெரிவித்து மாற்று நேரத்தைத் தேர்வு செய்து கொள்ளலாம்.",
      categoryTamil = "முன்பதிவு",
      intentKey = "APPOINTMENT_RESCHEDULE",
      triggerKeywords = "reschedule, change time, cancel, postpone, time mathalama, cancel panlama, change date, ரத்து, நேரம் மாற்றம், தேதி மாற்றம்",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 9,
      questionTamil = "ஒரு ஆலோசனையின் கால அளவு எவ்வளவு நேரம்?",
      answerTamil = "பொதுவாக ஒரு தனிநபர் ஆலோசனை 30 முதல் 45 நிமிடங்கள் வரை நடைபெறும். உங்கள் கேள்விகள் அனைத்திற்கும் பொறுமையாகவும் விரிவாகவும் ஜோதிடர் பதிலளிப்பார்.",
      categoryTamil = "ஆலோசனை தகவல்கள்",
      intentKey = "CONSULTATION_DURATION",
      triggerKeywords = "duration, how much time, evlo neram, minutes, consultation time, நேரம் எவ்வளவு எடுக்கும், கால அளவு",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 10,
      questionTamil = "ஜோதிடர் வழங்கும் முதன்மை சேவைகள் எவை?",
      answerTamil = "வழங்கப்படும் சேவைகள்:\n1. பொது ஜாதக பலன் ஆய்வு\n2. திருமணப் பொருத்தம் & திருமண கால கணிப்பு\n3. தொழில், வேலைவாய்ப்பு & உத்தியோக உயர்வு ஆலோசனை\n4. வணிகம் & தொழில் கூட்டாண்மை ஆலோசனை\n5. குழந்தைகள் கல்வி & உயர் படிப்பு வழிகாட்டல்\n6. வாஸ்து சாஸ்திர ஆலோசனை.",
      categoryTamil = "சேவைகள்",
      intentKey = "SERVICES",
      triggerKeywords = "services, what services, enna seivargal, list, sevai, jathagam, marriage, business, career, vastu, சேவைகள், என்னென்ன பார்ப்பீர்கள்",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    ),
    KnowledgeBaseEntity(
      id = 11,
      questionTamil = "பிறந்த நேரம் சரியாகத் தெரியவில்லை என்றால் என்ன செய்வது?",
      answerTamil = "பிறந்த நேரம் சரியாகத் தெரியவில்லை எனில், தோராயமான நேரத்தையும், நிகழ்ந்த முக்கிய வாழ்க்கைக் குறிப்புகளையும் முன்கூட்டியே ஜோதிடரிடம் தெரிவிக்கவும். பிரசன்ன ஜோதிட முறை அல்லது லக்ன பரிசோதனை மூலம் ஆய்வு செய்ய இயலும்.",
      categoryTamil = "ஆலோசனை தகவல்கள்",
      intentKey = "GENERAL_FAQ",
      triggerKeywords = "birth time therila, accurate time illa, no birth time, time thappu, நேரம் தெரியாது, பிறந்த நேரம் இல்லை",
      languageSource = "தமிழ் / Tanglish / English",
      status = KnowledgeStatus.ACTIVE
    )
  )

  val defaultWhatsAppConversations = listOf(
    WhatsAppConversationEntity(
      id = 1,
      senderPhone = "+91 98402 11223",
      rawMessage = "Sir fee evlo? GPay number send panreengala?",
      normalizedTamilQuery = "ஆலோசனைக்கான கட்டணம் எவ்வளவு மற்றும் GPay எண் என்ன?",
      detectedIntent = "CONSULTATION_FEE",
      proposedTamilAnswer = "ஆலோசனைக்கான கட்டணம் ₹500. GPay எண்: 9787908717. கட்டணம் செலுத்திய பின் ரசீதை அனுப்பவும்.",
      categoryTamil = "கட்டணம்",
      status = WhatsAppReviewStatus.APPROVED
    ),
    WhatsAppConversationEntity(
      id = 2,
      senderPhone = "+91 97908 44556",
      rawMessage = "Naalaikku evening 5pm slot free irukka sir? Online gmeet la pesalam.",
      normalizedTamilQuery = "நாளை மாலை 5.00 மணிக்கு ஆன்லைன் ஆலோசனைக் குறிப்பு உள்ளதா?",
      detectedIntent = "APPOINTMENT_BOOKING",
      proposedTamilAnswer = "நாளை மாலை 5.00 மணி slot கிடைக்கிறது. உங்கள் பிறந்த தேதி, நேரம், இடம் ஆகியவற்றை அனுப்பி முன்பதிவை உறுதிசெய்யவும்.",
      categoryTamil = "முன்பதிவு",
      status = WhatsAppReviewStatus.APPROVED
    ),
    WhatsAppConversationEntity(
      id = 3,
      senderPhone = "+91 94441 78901",
      rawMessage = "Enakku marriage eppo aagum? 1996 born.",
      normalizedTamilQuery = "திருமணம் எப்போது நிகழும் என்ற தனிப்பட்ட கேள்வி.",
      detectedIntent = "ASTROLOGY_PREDICTION",
      proposedTamilAnswer = "திருமணம் தொடர்பான தனிப்பட்ட கணிப்புகளுக்கு உங்கள் ஜாதகம் முழுமையாக ஆய்வு செய்யப்பட வேண்டும். ஜோதிடரை நேரடியாகத் தொடர்புகொண்டு நேரம் பெறவும்.",
      categoryTamil = "ஜோதிட கணிப்பு (Escalated)",
      status = WhatsAppReviewStatus.APPROVED
    ),
    WhatsAppConversationEntity(
      id = 4,
      senderPhone = "+91 91760 99881",
      rawMessage = "Foreign chance irukka sir? Horoscope pdf send panren.",
      normalizedTamilQuery = "வெளிநாடு வேலைவாய்ப்பு தொடர்பான தனிப்பட்ட ஜாதக ஆய்வு.",
      detectedIntent = "ASTROLOGY_PREDICTION",
      proposedTamilAnswer = "வெளிநாடு செல்லும் வாய்ப்பு அறிய ஜாதக ஆய்வு தேவைப்படுகிறது. ஜோதிடருடன் முன்பதிவு செய்து ஆலோசனை பெறலாம்.",
      categoryTamil = "ஜோதிட கணிப்பு (Escalated)",
      status = WhatsAppReviewStatus.PENDING_REVIEW
    ),
    WhatsAppConversationEntity(
      id = 5,
      senderPhone = "+91 98840 55667",
      rawMessage = "Office enga irukku sir? Sunday varalama?",
      normalizedTamilQuery = "அலுவலக முகவரி மற்றும் ஞாயிற்றுக்கிழமை வருகை பற்றிய கேள்வி.",
      detectedIntent = "OFFICE_LOCATION",
      proposedTamilAnswer = "அலுவலகம் மயிலாப்பூரில் உள்ளது. ஞாயிற்றுக்கிழமை விடுமுறை. திங்கள் முதல் சனி வரை வரலாம்.",
      categoryTamil = "தொடர்பு",
      status = WhatsAppReviewStatus.PENDING_REVIEW
    )
  )
}

