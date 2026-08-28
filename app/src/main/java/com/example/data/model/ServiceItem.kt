package com.example.data.model

data class ServiceItem(
  val id: String,
  val titleTamil: String,
  val titleEnglish: String,
  val descriptionTamil: String,
  val descriptionEnglish: String,
  val feeTamil: String,
  val feeEnglish: String,
  val durationTamil: String,
  val durationEnglish: String,
  val keyHighlightsTamil: List<String>,
  val keyHighlightsEnglish: List<String>,
  val iconType: String = "horoscope"
)

object AstrologyServicesData {
  val defaultServices = listOf(
    ServiceItem(
      id = "general_horoscope",
      titleTamil = "பொது ஜாதக ஆலோசனை",
      titleEnglish = "General Horoscope Consultation",
      descriptionTamil = "உங்கள் ஜாதகம் மற்றும் கிரக நிலைகள் தொடர்பான விரிவான தனிப்பட்ட ஆலோசனை.",
      descriptionEnglish = "Comprehensive personal reading of your birth chart, planetary alignments, and upcoming phases.",
      feeTamil = "₹500",
      feeEnglish = "₹500",
      durationTamil = "30-40 நிமிடங்கள்",
      durationEnglish = "30-40 Minutes",
      keyHighlightsTamil = listOf(
        "லக்னம், ராசி மற்றும் நவாம்ச நிலை ஆய்வு",
        "தசா புத்தி பலன்கள் மற்றும் நடப்பு கால கட்டம்",
        "ஆரோக்கியம், குடும்ப நலம் மற்றும் பரிகார வழிகாட்டுதல்"
      ),
      keyHighlightsEnglish = listOf(
        "Lagna, Rasi and Navamsa chart analysis",
        "Dasa Bukthi predictions and current transit period",
        "Health, family welfare, and remedy suggestions"
      ),
      iconType = "star"
    ),
    ServiceItem(
      id = "marriage_consultation",
      titleTamil = "திருமண ஆலோசனை & பொருத்தம்",
      titleEnglish = "Marriage & Kundali Matching",
      descriptionTamil = "திருமணம் தொடர்பான கேள்விகளுக்கான தனிப்பட்ட ஜோதிட ஆலோசனை மற்றும் பொருத்த ஆய்வு.",
      descriptionEnglish = "Detailed astrological matching, 10 poruthams check, dosha analysis, and auspicious marriage timing.",
      feeTamil = "₹750",
      feeEnglish = "₹750",
      durationTamil = "45 நிமிடங்கள்",
      durationEnglish = "45 Minutes",
      keyHighlightsTamil = listOf(
        "பத்து பொருத்தங்கள் & தசா சந்தி ஆய்வு",
        "செவ்வாய், ராகு-கேது தோஷ பரிசீலனை",
        "திருமண கால கணிப்பு & நல்ல முகூர்த்த நேரம்"
      ),
      keyHighlightsEnglish = listOf(
        "10 Poruthams & Dasa Sandhi examination",
        "Chevvai (Manglik) & Rahu-Ketu dosha review",
        "Marriage timing prediction & auspicious muhurtham"
      ),
      iconType = "favorite"
    ),
    ServiceItem(
      id = "career_consultation",
      titleTamil = "தொழில் & பணிப்பாதை ஆலோசனை",
      titleEnglish = "Career & Job Consultation",
      descriptionTamil = "தொழில் மற்றும் உத்தியோக வளர்ச்சி தொடர்பான துல்லியமான ஜோதிட வழிகாட்டுதல்.",
      descriptionEnglish = "Precise astrological guidance for job stability, promotions, career switches, and foreign opportunities.",
      feeTamil = "₹500",
      feeEnglish = "₹500",
      durationTamil = "30-40 நிமிடங்கள்",
      durationEnglish = "30-40 Minutes",
      keyHighlightsTamil = listOf(
        "பத்தாம் பாவகம் மற்றும் ஜீவன ஸ்தான பலன்கள்",
        "அரசு வேலை அல்லது தனியார் வேலை வாய்ப்பு",
        "பணி மாற்றம், வெளிநாடு பயணம் மற்றும் பதவி உயர்வு காலம்"
      ),
      keyHighlightsEnglish = listOf(
        "10th house & livelihood status evaluation",
        "Government vs private sector prospects",
        "Job change, abroad relocation & promotion timing"
      ),
      iconType = "work"
    ),
    ServiceItem(
      id = "business_consultation",
      titleTamil = "வணிக & தொழில் வளர்ச்சி ஆலோசனை",
      titleEnglish = "Business & Trade Growth Consultation",
      descriptionTamil = "புதிய வணிகம் தொடங்குதல் மற்றும் வர்த்தக மேம்பாடு தொடர்பான தனிப்பட்ட ஆலோசனை.",
      descriptionEnglish = "Specialized astrological advice for new ventures, partnership compatibility, and financial growth.",
      feeTamil = "₹1,000",
      feeEnglish = "₹1,000",
      durationTamil = "45-60 நிமிடங்கள்",
      durationEnglish = "45-60 Minutes",
      keyHighlightsTamil = listOf(
        "கூட்டுத் தொழில் (Partnership) பொருத்தம்",
        "புதிய நிறுவனம் திறப்பதற்கான சுப முகூர்த்தம்",
        "லாப ஸ்தான ஆய்வு மற்றும் முதலீட்டு எச்சரிக்கைகள்"
      ),
      keyHighlightsEnglish = listOf(
        "Business partnership compatibility check",
        "Auspicious muhurtham for opening new ventures",
        "11th house profit analysis & investment guidance"
      ),
      iconType = "trending_up"
    ),
    ServiceItem(
      id = "education_consultation",
      titleTamil = "கல்வி & குழந்தைகள் எதிர்கால ஆலோசனை",
      titleEnglish = "Education & Children's Future Guidance",
      descriptionTamil = "குழந்தைகளின் கல்வித் துறை, உயர் படிப்பு மற்றும் எதிர்கால நல்வாழ்விற்கான வழிகாட்டல்.",
      descriptionEnglish = "Guidance on suitable study streams, higher education, memory enhancement, and children's prosperity.",
      feeTamil = "₹500",
      feeEnglish = "₹500",
      durationTamil = "30 நிமிடங்கள்",
      durationEnglish = "30 Minutes",
      keyHighlightsTamil = listOf(
        "ஐந்தாம் பாவகம் மற்றும் புதன் கிரக பலம் ஆய்வு",
        "பொருத்தமான படிப்பு துறை மற்றும் உயர்கல்வி வழிமுறை",
        "கவனம் மற்றும் ஞாபக சக்தி மேம்பாட்டு வழிகள்"
      ),
      keyHighlightsEnglish = listOf(
        "5th house intelligence & Mercury strength analysis",
        "Ideal subject streams & higher education paths",
        "Remedies for focus and memory improvement"
      ),
      iconType = "school"
    ),
    ServiceItem(
      id = "vastu_consultation",
      titleTamil = "வாஸ்து & கிரக சாந்தி ஆலோசனை",
      titleEnglish = "Vastu Shastra & Planetary Remedies",
      descriptionTamil = "வீடு, மனை மற்றும் வணிக கட்டிடங்களுக்கான பாரம்பரிய வாஸ்து சாஸ்திர வழிகாட்டுதல்.",
      descriptionEnglish = "Traditional Vastu guidance for home, plot, and commercial buildings without demolition.",
      feeTamil = "₹1,500",
      feeEnglish = "₹1,500",
      durationTamil = "நேரில் அல்லது வரைபட ஆய்வு",
      durationEnglish = "In-Person or Blueprint Review",
      keyHighlightsTamil = listOf(
        "மனை மற்றும் கட்டிட திசைகள் ஆய்வு",
        "பூஜை அறை, சமையலறை மற்றும் பிரதான கதவு அமைவிடங்கள்",
        "இடிக்காமல் செய்யும் எளிய வாஸ்து பரிகாரங்கள்"
      ),
      keyHighlightsEnglish = listOf(
        "Plot orientation and directional energy analysis",
        "Pooja room, kitchen, and entrance positioning",
        "Simple corrective remedies without architectural demolition"
      ),
      iconType = "home"
    )
  )
}
