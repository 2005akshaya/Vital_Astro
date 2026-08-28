package com.example.ui.util

import com.example.data.model.AppLanguage

object AppStrings {

  fun appName(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஸ்ரீ விட்டல் ஜோதிடம்"
    AppLanguage.ENGLISH -> "Sri Vittal Astrology"
  }

  fun astrologerName(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஜோதிட ஸ்ரீ ராஜகோபால்"
    AppLanguage.ENGLISH -> "Astrologer Sri Rajagopal"
  }

  fun centerName(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஸ்ரீ விட்டல் ஜோதிடாலயம்"
    AppLanguage.ENGLISH -> "Sri Vittal Astrological Center"
  }

  fun appSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "பாரம்பரிய ஜோதிட ஆலோசனை மையம்"
    AppLanguage.ENGLISH -> "Traditional Astrological Consultation Center"
  }

  fun liveStatus(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "AI உதவியாளர் நேரலையில்"
    AppLanguage.ENGLISH -> "AI Assistant Live"
  }

  fun digitalAid(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "டிஜிட்டல் உதவி"
    AppLanguage.ENGLISH -> "Digital Assistant"
  }

  fun adminMode(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "நிர்வாகம்"
    AppLanguage.ENGLISH -> "Admin"
  }

  fun clientMode(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "பயனர்"
    AppLanguage.ENGLISH -> "Client"
  }

  // Navigation
  fun navHome(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "முகப்பு"
    AppLanguage.ENGLISH -> "Home"
  }

  fun navChat(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "உதவி"
    AppLanguage.ENGLISH -> "Assistant"
  }

  fun navServices(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "சேவைகள்"
    AppLanguage.ENGLISH -> "Services"
  }

  fun navAppointment(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "முன்பதிவு"
    AppLanguage.ENGLISH -> "Booking"
  }

  fun navFaq(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "கேள்விகள்"
    AppLanguage.ENGLISH -> "FAQ"
  }

  fun navPrep(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தயாரிப்பு"
    AppLanguage.ENGLISH -> "Prep Guide"
  }

  // Home Screen
  fun heroBadge(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தமிழ் & ஆங்கில AI டிஜிட்டல் உதவியாளர்"
    AppLanguage.ENGLISH -> "Tamil & English AI Digital Assistant"
  }

  fun heroTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "உங்கள் கேள்விகளுக்கு உடனடி பதில்கள்"
    AppLanguage.ENGLISH -> "Instant Answers to Your Astrological Inquiries"
  }

  fun heroSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஆலோசனை, கட்டணம், நேரம், முன்பதிவு மற்றும் தொடர்பு குறித்த தகவல்களை எளிதாக அறிந்து கொள்ளுங்கள்."
    AppLanguage.ENGLISH -> "Easily find details on consultation fees, timings, appointment booking, services, and contact information."
  }

  fun heroChatCta(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "AI உதவியாளரிடம் கேளுங்கள்"
    AppLanguage.ENGLISH -> "Ask AI Assistant"
  }

  fun heroBookingCta(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "முன்பதிவு"
    AppLanguage.ENGLISH -> "Book Appointment"
  }

  fun trustStatement(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தனிப்பட்ட கிரக நிலை ஆய்வு மற்றும் ஜோதிட ஆலோசனைகள் ஜோதிடரால் நேரடியாக வழங்கப்படுகின்றன."
    AppLanguage.ENGLISH -> "Personal planetary readings and horoscope consultations are exclusively provided directly by the astrologer."
  }

  fun disclaimer(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "இந்த AI உதவியாளர் ஆலோசனை மற்றும் சேவைகள் தொடர்பான பொதுவான தகவல்களை மட்டுமே வழங்குகிறது. தனிப்பட்ட ஜாதக ஆய்வு மற்றும் ஜோதிட கணிப்புகளுக்கு ஜோதிடரை நேரடியாகத் தொடர்புகொள்ளவும்."
    AppLanguage.ENGLISH -> "This AI assistant provides informational guidance regarding consultation services, fees, and procedures. For personal birth chart predictions, please consult the astrologer directly."
  }

  fun feeLabel(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஆலோசனை கட்டணம்"
    AppLanguage.ENGLISH -> "Consultation Fee"
  }

  fun timingLabel(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தொடர்பு நேரம்"
    AppLanguage.ENGLISH -> "Contact Hours"
  }

  fun timingValue(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "காலை 10 - மதியம் 1 | மாலை 4 - இரவு 8"
    AppLanguage.ENGLISH -> "10 AM - 1 PM | 4 PM - 8 PM"
  }

  fun featuredServices(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஆலோசனை சேவைகள்"
    AppLanguage.ENGLISH -> "Consultation Services"
  }

  fun viewAll(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "அனைத்தும் காண்க"
    AppLanguage.ENGLISH -> "View All"
  }

  fun getConsultation(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஆலோசனை பெறுங்கள்"
    AppLanguage.ENGLISH -> "Book Consultation"
  }

  fun prepGuideTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஆலோசனைக்கு முன் தயாராகுங்கள்"
    AppLanguage.ENGLISH -> "Prepare for Your Consultation"
  }

  fun prepGuideNote(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "குறிப்பு: பிறந்த நேரம் சரியாகத் தெரியவில்லை என்றால், இதை முன்கூட்டியே ஜோதிடரிடம் தெரிவிக்கவும்."
    AppLanguage.ENGLISH -> "Note: If the exact birth time is uncertain, please inform the astrologer in advance."
  }

  // Chat Screen
  fun chatHeaderTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஸ்ரீ விட்டல் AI உதவியாளர்"
    AppLanguage.ENGLISH -> "Sri Vittal AI Assistant"
  }

  fun chatPlaceholder(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "உங்கள் கேள்வியை தட்டச்சு செய்க... (Tamil / Tanglish / English)"
    AppLanguage.ENGLISH -> "Type your question... (English / Tamil / Tanglish)"
  }

  fun thinkingText(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தகவலைத் தேடுகிறது..."
    AppLanguage.ENGLISH -> "Finding information..."
  }

  fun escalationTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "நேரடி ஜோதிடர் தொடர்பு தேவை"
    AppLanguage.ENGLISH -> "Direct Astrologer Consultation Required"
  }

  fun escalationDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தனிப்பட்ட கிரக நிலைகள் மற்றும் ஜாதக பலன்களை ஜோதிடர் மட்டுமே துல்லியமாக கணிப்பார். கீழேயுள்ள வழிகளில் தொடர்பு கொள்ளலாம்:"
    AppLanguage.ENGLISH -> "Personal planetary calculations and birth chart predictions are analyzed directly by the astrologer. You can reach out using the options below:"
  }

  fun contactAstrologerBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஜோதிடரைத் தொடர்புகொள்ளுங்கள்"
    AppLanguage.ENGLISH -> "Contact Astrologer"
  }

  fun callBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "அழைக்க"
    AppLanguage.ENGLISH -> "Call"
  }

  fun clearChat(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "உரையாடலை அழிக்க"
    AppLanguage.ENGLISH -> "Clear Chat"
  }

  // Appointment Screen
  fun bookingBadge(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "முன்பதிவு மையம்"
    AppLanguage.ENGLISH -> "Booking Center"
  }

  fun bookingTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஜோதிட ஆலோசனை முன்பதிவு"
    AppLanguage.ENGLISH -> "Book Astrological Consultation"
  }

  fun bookingSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "கீழேயுள்ள படிவத்தை பூர்த்தி செய்து உங்கள் ஆலோசனை நேரத்தை உறுதி செய்யுங்கள்."
    AppLanguage.ENGLISH -> "Please fill in the form below to confirm your consultation appointment."
  }

  fun bookingSuccessTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "முன்பதிவு வெற்றிகரமாக பெறப்பட்டது!"
    AppLanguage.ENGLISH -> "Appointment Booked Successfully!"
  }

  fun bookingRefLabel(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "முன்பதிவு எண்"
    AppLanguage.ENGLISH -> "Reference Number"
  }

  fun bookingSuccessDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "உங்கள் முன்பதிவு விவரங்கள் ஜோதிடரிடம் சமர்ப்பிக்கப்பட்டுள்ளது. குறித்த நேரத்தில் உங்களை தொலைபேசி அல்லது WhatsApp வழியாக தொடர்பு கொள்வோம்."
    AppLanguage.ENGLISH -> "Your booking details have been sent to Astrologer Sri Rajagopal. We will connect with you via phone or WhatsApp at the scheduled time."
  }

  fun goHomeBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "முகப்பிற்குச் செல்"
    AppLanguage.ENGLISH -> "Go to Home"
  }

  fun labelName(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "உங்கள் பெயர் (Client Name)"
    AppLanguage.ENGLISH -> "Your Full Name"
  }

  fun labelPhone(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தொலைபேசி எண் (Phone Number)"
    AppLanguage.ENGLISH -> "Phone Number"
  }

  fun labelServiceType(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஆலோசனை சேவை வகை"
    AppLanguage.ENGLISH -> "Consultation Service"
  }

  fun labelMode(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஆலோசனை முறை (Mode)"
    AppLanguage.ENGLISH -> "Consultation Mode"
  }

  fun labelDate(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "விருப்பமான நாள் (Preferred Date)"
    AppLanguage.ENGLISH -> "Preferred Date"
  }

  fun labelTimeSlot(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "விருப்பமான நேரம் (Preferred Time Slot)"
    AppLanguage.ENGLISH -> "Preferred Time Slot"
  }

  fun labelBirthNotes(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "பிறப்பு விவரங்கள் / குறிப்புகள் (விருப்பத்தேர்வு)"
    AppLanguage.ENGLISH -> "Birth Details / Notes (Optional)"
  }

  fun placeholderBirthNotes(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "பிறந்த தேதி, நேரம், ஊர் மற்றும் கேட்க வேண்டிய கேள்விகள்"
    AppLanguage.ENGLISH -> "Date of birth, birth time, place of birth, and questions"
  }

  fun btnConfirmBooking(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "முன்பதிவை உறுதி செய்"
    AppLanguage.ENGLISH -> "Confirm Appointment"
  }

  // Prep Screen
  fun prepBadge(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஆலோசனை வழிகாட்டி"
    AppLanguage.ENGLISH -> "Consultation Guide"
  }

  fun prepTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஆலோசனைக்கு முன் செய்ய வேண்டியவை"
    AppLanguage.ENGLISH -> "Before Your Consultation"
  }

  fun prepSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "துல்லியமான பலன்களைப் பெற கீழே கொடுக்கப்பட்டுள்ள குறிப்புகளை தயார் செய்து வைத்துக் கொள்ளுங்கள்."
    AppLanguage.ENGLISH -> "Please prepare the following details in advance to get an accurate reading."
  }

  fun prepSection1Title(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தேவையான அடிப்படை ஜாதக விவரங்கள்"
    AppLanguage.ENGLISH -> "Required Basic Horoscope Details"
  }

  fun prepSection2Title(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "முக்கியமான கேள்விகளை பட்டியலிடுங்கள்"
    AppLanguage.ENGLISH -> "List Your Key Questions"
  }

  fun errorName(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தயவுசெய்து உங்கள் பெயரை உள்ளிடவும்"
    AppLanguage.ENGLISH -> "Please enter your name"
  }

  fun errorPhone(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "சரியான 10 இலக்க தொலைபேசி எண்ணை உள்ளிடவும்"
    AppLanguage.ENGLISH -> "Please enter a valid phone number"
  }

  fun errorDate(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "தயவுசெய்து விருப்பமான நாளைத் தேர்ந்தெடுக்கவும்"
    AppLanguage.ENGLISH -> "Please select a preferred date"
  }

  // FAQ Screen
  fun faqBadge(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "அடிக்கடி கேட்கப்படும் கேள்விகள்"
    AppLanguage.ENGLISH -> "Frequently Asked Questions"
  }

  fun faqTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "பொதுவான வினா-விடைகள்"
    AppLanguage.ENGLISH -> "Frequently Asked Questions"
  }

  fun faqSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "கட்டணம், நேரம், முன்பதிவு மற்றும் ஜாதக ஆலோசனை தொடர்பான உடனடி விளக்கங்கள்."
    AppLanguage.ENGLISH -> "Instant answers regarding consultation fees, timings, bookings, and services."
  }

  fun allCategories(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "அனைத்தும்"
    AppLanguage.ENGLISH -> "All"
  }

  fun searchPlaceholder(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "கேள்விகளைத் தேடுக..."
    AppLanguage.ENGLISH -> "Search questions..."
  }

  fun noFaqFound(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "பொருத்தமான கேள்விகள் காணப்படவில்லை."
    AppLanguage.ENGLISH -> "No matching questions found."
  }

  fun askAiMore(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "இது குறித்து AI உதவியாளரிடம் மேலும் கேட்க"
    AppLanguage.ENGLISH -> "Ask AI Assistant more about this"
  }

  // Services Screen
  fun servicesBadge(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "ஜோதிட சேவைகள்"
    AppLanguage.ENGLISH -> "Astrological Services"
  }

  fun servicesTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "அனைத்து ஆலோசனை சேவைகள்"
    AppLanguage.ENGLISH -> "All Consultation Services"
  }

  fun servicesSubtitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "உங்கள் வாழ்க்கையின் முக்கியமான முடிவுகளுக்கு துல்லியமான தமிழ் ஜோதிட வழிகாட்டுதல்."
    AppLanguage.ENGLISH -> "Accurate and insightful astrological guidance for your life's critical milestones."
  }

  fun durationLabel(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "கால அளவு"
    AppLanguage.ENGLISH -> "Duration"
  }

  fun bookService(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "இந்த சேவைக்கு முன்பதிவு செய்க"
    AppLanguage.ENGLISH -> "Book this Service"
  }

  fun serviceHelpTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "எந்த சேவையைத் தேர்வு செய்வது என்று தெரியவில்லையா?"
    AppLanguage.ENGLISH -> "Need help choosing the right service?"
  }

  fun serviceHelpSub(lang: AppLanguage): String = when (lang) {
    AppLanguage.TAMIL -> "எங்கள் AI உதவியாளரிடம் உங்கள் தேவைகளைக் கூறி எந்த ஆலோசனை உங்களுக்கு சிறந்தது என்பதைத் தெரிந்து கொள்ளலாம்."
    AppLanguage.ENGLISH -> "Consult our AI Assistant to understand which consultation suits your current astrological needs best."
  }
}
