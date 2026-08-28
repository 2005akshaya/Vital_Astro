package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun ConsultationPrepScreen(
  viewModel: AstrologyViewModel,
  onNavigateToBooking: () -> Unit,
  modifier: Modifier = Modifier
) {
  val appLanguage by viewModel.appLanguage.collectAsState()

  val requiredItems = if (appLanguage == AppLanguage.ENGLISH) {
    listOf(
      Pair("Date of Birth (Accurate)", "English calendar date or Tamil date (Year, Month, Date)"),
      Pair("Exact Time of Birth", "AM / PM / Night, Hour and Minute is essential for Lagna chart"),
      Pair("Place of Birth", "City / Town, District and State"),
      Pair("Parents Details", "Parents' names and birth order (if needed)"),
      Pair("Existing Horoscope Chart Copy", "Thirukanitha or Vakya Panchangam notes if available")
    )
  } else {
    listOf(
      Pair("பிறந்த தேதி (Date of Birth)", "ஆங்கில தேதி அல்லது தமிழ் தேதி (வருடம், மாதம், தேதி)"),
      Pair("துல்லியமான பிறந்த நேரம் (Time of Birth)", "காலை / மாலை / இரவு, மணி மற்றும் நிமிடம் மிக முக்கியம்"),
      Pair("பிறந்த இடம் (Place of Birth)", "ஊர் / நகரம், மாவட்டம் மற்றும் மாநிலம்"),
      Pair("பெற்றோர் விவரங்கள்", "பெற்றோர் பெயர் மற்றும் பிறந்த வரிசை (தேவைப்படின்)"),
      Pair("ஏற்கனவே கணிக்கப்பட்ட ஜாதக நகல்", "திருக்கணிதம் அல்லது வாக்கிய பஞ்சாங்கக் குறிப்பு இருப்பின்")
    )
  }

  val topicChecklist = if (appLanguage == AppLanguage.ENGLISH) {
    listOf(
      "Marriage compatibility and auspicious wedding muhurtham",
      "Career growth, job promotions, and overseas opportunities",
      "Financial stability, business growth, and debt relief",
      "Progeny blessings and children's higher education",
      "Health, well-being, and family peace",
      "Dasha-Bhukti planetary periods and authentic remedies"
    )
  } else {
    listOf(
      "திருமணப் பொருத்தம் மற்றும் சுப முகூர்த்தம்",
      "தொழில், வேலை வாய்ப்பு மற்றும் பதவி உயர்வு",
      "நிதி நிலைமை, கடன் தீர்வு மற்றும் வியாபார விருத்தி",
      "குழந்தைப் பாக்கியம் மற்றும் குழந்தைகளின் கல்வி",
      "ஆரோக்கியம் மற்றும் குடும்ப அமைதி",
      "திசா புக்தி பலன்கள் மற்றும் கிரகப் பரிகாரங்கள்"
    )
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("consultation_prep_screen"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Column {
        TraditionalMotifBadge(text = AppStrings.prepBadge(appLanguage))
        Text(
          text = AppStrings.prepTitle(appLanguage),
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = AppStrings.prepSubtitle(appLanguage),
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 18.sp
          )
        )
      }
    }

    // 1. Mandatory Birth Info Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Assignment,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = AppStrings.prepSection1Title(appLanguage),
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          requiredItems.forEach { (title, subtitle) ->
            Row(
              modifier = Modifier.padding(vertical = 5.dp),
              verticalAlignment = Alignment.Top
            ) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = GeometricForestGreen,
                modifier = Modifier.size(18.dp).padding(top = 2.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = title,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp
                  )
                )
                Text(
                  text = subtitle,
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.5.sp
                  )
                )
              }
            }
          }
        }
      }
    }

    // 2. Question Preparation Tips
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(GeometricForestGreenContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                tint = GeometricForestGreen,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = AppStrings.prepSection2Title(appLanguage),
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          topicChecklist.forEach { topic ->
            Row(
              modifier = Modifier.padding(vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = topic,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontSize = 12.5.sp
                )
              )
            }
          }
        }
      }
    }

    // CTA
    item {
      Button(
        onClick = onNavigateToBooking,
        colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth().height(48.dp)
      ) {
        Icon(
          imageVector = Icons.Default.CalendarMonth,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = AppStrings.heroBookingCta(appLanguage),
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        )
      }
    }
  }
}
