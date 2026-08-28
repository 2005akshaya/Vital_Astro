package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.AstrologyServicesData
import com.example.data.model.ServiceItem
import com.example.ui.components.TamilSacredDivider
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AppScreenTab
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun LandingScreen(
  viewModel: AstrologyViewModel,
  onNavigateTo: (AppScreenTab) -> Unit,
  modifier: Modifier = Modifier
) {
  val config by viewModel.practiceConfig.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("landing_screen"),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // 1. HERO BANNER & MAIN CTA
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(230.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.img_vittal_logo_1787913597109),
          contentDescription = AppStrings.appName(appLanguage),
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay for contrast
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  Color.Black.copy(alpha = 0.35f),
                  Color.Black.copy(alpha = 0.82f)
                )
              )
            )
        )

        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
          verticalArrangement = Arrangement.Bottom
        ) {
          Surface(
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.95f),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(bottom = 6.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = AppStrings.heroBadge(appLanguage),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary,
                  fontSize = 11.sp
                )
              )
            }
          }

          Text(
            text = AppStrings.heroTitle(appLanguage),
            style = MaterialTheme.typography.displayMedium.copy(
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp,
              lineHeight = 26.sp
            )
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = AppStrings.heroSubtitle(appLanguage),
            style = MaterialTheme.typography.bodySmall.copy(
              color = Color.White.copy(alpha = 0.9f),
              fontSize = 12.sp,
              lineHeight = 16.sp
            )
          )
        }
      }
    }

    // 2. PRIMARY & SECONDARY ACTION BUTTONS
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Primary CTA: AI Assistant (Forest Green)
          Button(
            onClick = { onNavigateTo(AppScreenTab.CHAT) },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
            modifier = Modifier
              .weight(1.1f)
              .height(50.dp)
              .testTag("hero_chat_cta_button")
          ) {
            Icon(
              imageVector = Icons.Default.ChatBubble,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(17.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = AppStrings.heroChatCta(appLanguage),
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 11.5.sp
              )
            )
          }

          // Secondary CTA: Booking
          OutlinedButton(
            onClick = { onNavigateTo(AppScreenTab.APPOINTMENT) },
            shape = RoundedCornerShape(12.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
              brush = Brush.linearGradient(
                listOf(
                  GeometricGoldMetallic.copy(alpha = 0.6f),
                  MaterialTheme.colorScheme.primary
                )
              )
            ),
            modifier = Modifier
              .weight(0.9f)
              .height(50.dp)
              .testTag("hero_appointment_cta_button")
          ) {
            Icon(
              imageVector = Icons.Default.CalendarMonth,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(17.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = AppStrings.heroBookingCta(appLanguage),
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp
              )
            )
          }
        }
      }
    }

    // 3. TRUST STATEMENT & DISCLAIMER CARDS
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp)
      ) {
        // Trust Statement
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = GeometricForestGreenContainer.copy(alpha = 0.5f)),
          border = CardDefaults.outlinedCardBorder().copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(GeometricForestGreen.copy(alpha = 0.20f))
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(GeometricForestGreen.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null,
                tint = GeometricForestGreen,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = AppStrings.trustStatement(appLanguage),
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                color = GeometricForestGreenText,
                lineHeight = 18.sp,
                fontSize = 12.sp
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Disclaimer
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder().copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(18.dp).padding(top = 1.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = AppStrings.disclaimer(appLanguage),
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.5.sp,
                lineHeight = 17.sp
              )
            )
          }
        }
      }
    }

    // 4. ASTROLOGER PROFILE & TIMINGS
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "🕉️",
                color = Color.White,
                fontSize = 20.sp
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = if (appLanguage == AppLanguage.ENGLISH) config.astrologerNameEnglish else config.astrologerNameTamil,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
              Text(
                text = "${if (appLanguage == AppLanguage.ENGLISH) config.titleEnglish else config.titleTamil} • ${if (appLanguage == AppLanguage.ENGLISH) config.experienceYearsEnglish else config.experienceYears}",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontSize = 12.sp
                )
              )
            }
          }

          TamilSacredDivider(modifier = Modifier.padding(vertical = 8.dp))

          // Timing Info Grid
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = AppStrings.feeLabel(appLanguage),
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
              Text(
                text = config.consultationFeeAmount,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = GeometricForestGreen
                )
              )
            }
            Column(modifier = Modifier.weight(1.5f)) {
              Text(
                text = AppStrings.timingLabel(appLanguage),
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
              Text(
                text = AppStrings.timingValue(appLanguage),
                style = MaterialTheme.typography.bodySmall.copy(
                  fontWeight = FontWeight.Medium,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
            }
          }
        }
      }
    }

    // 5. FEATURED SERVICES
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            TraditionalMotifBadge(text = AppStrings.navServices(appLanguage))
            Text(
              text = AppStrings.featuredServices(appLanguage),
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
          }
          TextButton(onClick = { onNavigateTo(AppScreenTab.SERVICES) }) {
            Text(
              text = AppStrings.viewAll(appLanguage),
              style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
              )
            )
            Icon(
              imageVector = Icons.Default.ChevronRight,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(16.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyRow(
          contentPadding = PaddingValues(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(AstrologyServicesData.defaultServices.take(4)) { service ->
            ServicePreviewCard(
              service = service,
              appLanguage = appLanguage,
              onBook = { onNavigateTo(AppScreenTab.APPOINTMENT) }
            )
          }
        }
      }
    }

    // 6. CONSULTATION PREPARATION CHECKLIST PREVIEW
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 14.dp)
          .clickable { onNavigateTo(AppScreenTab.PREPARATION) }
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(30.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(16.dp)
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = AppStrings.prepGuideTitle(appLanguage),
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
            }
            Icon(
              imageVector = Icons.Default.ChevronRight,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          val checklistItems = if (appLanguage == AppLanguage.ENGLISH) {
            listOf(
              "Date of Birth (Accurate)",
              "Exact Time of Birth (AM/PM)",
              "Place of Birth (City / District)",
              "Existing horoscope chart copy (if available)",
              "Specific questions to consult"
            )
          } else {
            listOf(
              "பிறந்த தேதி (Date of Birth)",
              "துல்லியமான பிறந்த நேரம் (Time of Birth)",
              "பிறந்த இடம் (Place of Birth)",
              "ஏற்கனவே உள்ள ஜாதகம் இருப்பின் அதன் நகல்",
              "ஆலோசிக்க வேண்டிய முக்கியமான கேள்விகள்"
            )
          }

          checklistItems.forEach { itemText ->
            Row(
              modifier = Modifier.padding(vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = GeometricForestGreen,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = itemText,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontSize = 12.5.sp
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = AppStrings.prepGuideNote(appLanguage),
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.primary,
              fontSize = 11.sp,
              fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
          )
        }
      }
    }
  }
}

@Composable
fun ServicePreviewCard(
  service: ServiceItem,
  appLanguage: AppLanguage,
  onBook: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = CardDefaults.outlinedCardBorder().copy(
      width = 1.dp,
      brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
      .width(260.dp)
      .padding(vertical = 4.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = when (service.iconType) {
              "favorite" -> Icons.Default.Favorite
              "work" -> Icons.Default.Work
              "trending_up" -> Icons.Default.TrendingUp
              else -> Icons.Default.Star
            },
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp)
          )
        }
        Surface(
          color = GeometricForestGreenContainer,
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = if (appLanguage == AppLanguage.ENGLISH) service.feeEnglish else service.feeTamil,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricForestGreen
            ),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = if (appLanguage == AppLanguage.ENGLISH) service.titleEnglish else service.titleTamil,
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = if (appLanguage == AppLanguage.ENGLISH) service.descriptionEnglish else service.descriptionTamil,
        style = MaterialTheme.typography.bodySmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 12.sp,
          lineHeight = 16.sp
        ),
        maxLines = 2
      )

      Spacer(modifier = Modifier.height(10.dp))

      Button(
        onClick = onBook,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = AppStrings.getConsultation(appLanguage),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        )
      }
    }
  }
}
