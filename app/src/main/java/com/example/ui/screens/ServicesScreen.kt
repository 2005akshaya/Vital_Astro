package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.AstrologyServicesData
import com.example.data.model.ServiceItem
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun ServicesScreen(
  viewModel: AstrologyViewModel,
  onNavigateToBooking: (String) -> Unit = {},
  modifier: Modifier = Modifier
) {
  val services = AstrologyServicesData.defaultServices
  val appLanguage by viewModel.appLanguage.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("services_screen"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Column {
        TraditionalMotifBadge(text = AppStrings.servicesBadge(appLanguage))
        Text(
          text = AppStrings.servicesTitle(appLanguage),
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = AppStrings.servicesSubtitle(appLanguage),
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 18.sp
          )
        )
      }
    }

    items(services) { service ->
      DetailedServiceCard(
        service = service,
        appLanguage = appLanguage,
        onBook = { onNavigateToBooking(if (appLanguage == AppLanguage.ENGLISH) service.titleEnglish else service.titleTamil) }
      )
    }

    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.HelpOutline,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = AppStrings.serviceHelpTitle(appLanguage),
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = AppStrings.serviceHelpSub(appLanguage),
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 18.sp
            )
          )
        }
      }
    }
  }
}

@Composable
fun DetailedServiceCard(
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
    modifier = modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = when (service.iconType) {
                "favorite" -> Icons.Default.Favorite
                "work" -> Icons.Default.Work
                "trending_up" -> Icons.Default.TrendingUp
                "school" -> Icons.Default.School
                "home" -> Icons.Default.Home
                else -> Icons.Default.AutoAwesome
              },
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = if (appLanguage == AppLanguage.ENGLISH) service.titleEnglish else service.titleTamil,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
            Text(
              text = "${AppStrings.durationLabel(appLanguage)}: ${if (appLanguage == AppLanguage.ENGLISH) service.durationEnglish else service.durationTamil}",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }

        Surface(
          color = GeometricForestGreenContainer,
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = if (appLanguage == AppLanguage.ENGLISH) service.feeEnglish else service.feeTamil,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricForestGreen,
              fontSize = 12.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = if (appLanguage == AppLanguage.ENGLISH) service.descriptionEnglish else service.descriptionTamil,
        style = MaterialTheme.typography.bodySmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 13.sp,
          lineHeight = 19.sp
        )
      )

      val highlights = if (appLanguage == AppLanguage.ENGLISH) service.keyHighlightsEnglish else service.keyHighlightsTamil
      if (highlights.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        highlights.forEach { highlight ->
          Row(
            modifier = Modifier.padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              tint = GeometricForestGreen,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = highlight,
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Button(
        onClick = onBook,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth().height(42.dp)
      ) {
        Icon(
          imageVector = Icons.Default.CalendarMonth,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = AppStrings.bookService(appLanguage),
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 12.5.sp
          )
        )
      }
    }
  }
}
