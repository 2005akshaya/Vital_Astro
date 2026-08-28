package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.ui.theme.*
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AppScreenTab

@Composable
fun TamilTopBar(
  currentScreen: AppScreenTab,
  appLanguage: AppLanguage,
  isDarkMode: Boolean,
  onToggleLanguage: () -> Unit,
  onToggleTheme: () -> Unit,
  onNavigateTo: (AppScreenTab) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(MaterialTheme.colorScheme.surface)
  ) {
    // 1. Primary Header Bar (Geometric Balance)
    Surface(
      modifier = Modifier.fillMaxWidth(),
      color = MaterialTheme.colorScheme.surface,
      shadowElevation = 1.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .statusBarsPadding()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Logo & Title
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clickable { onNavigateTo(AppScreenTab.HOME) }
              .testTag("app_branding_header")
          ) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(GeometricGoldPrimary)
                .border(1.dp, GeometricGoldMetallic, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Image(
                painter = painterResource(id = R.drawable.img_vittal_logo_1787913597109),
                contentDescription = AppStrings.appName(appLanguage),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
              Text(
                text = AppStrings.appName(appLanguage),
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface,
                  letterSpacing = (-0.2).sp
                )
              )
              Text(
                text = "${AppStrings.astrologerName(appLanguage)} • ${AppStrings.centerName(appLanguage)}",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 10.5.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }
          }

          // Header Controls (Language Switcher + Theme Toggle + Admin Toggle)
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // Visible Language Switcher (தமிழ் | English)
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              border = CardDefaults.outlinedCardBorder().copy(
                width = 1.dp,
                brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
              ),
              modifier = Modifier
                .clickable { onToggleLanguage() }
                .testTag("language_toggle_button")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Translate,
                  contentDescription = "Language Switcher",
                  modifier = Modifier.size(13.dp),
                  tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = if (appLanguage == AppLanguage.TAMIL) "English" else "தமிழ்",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary
                  )
                )
              }
            }

            // Theme Switcher (☀️ / 🌙)
            IconButton(
              onClick = onToggleTheme,
              modifier = Modifier
                .size(34.dp)
                .testTag("theme_toggle_button")
            ) {
              Icon(
                imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode",
                tint = if (isDarkMode) DarkGeometricGoldPrimary else GeometricGoldDark,
                modifier = Modifier.size(18.dp)
              )
            }

            // Admin / Client Toggle Button
            AssistChip(
              onClick = {
                if (currentScreen == AppScreenTab.ADMIN) {
                  onNavigateTo(AppScreenTab.HOME)
                } else {
                  onNavigateTo(AppScreenTab.ADMIN)
                }
              },
              label = {
                Text(
                  text = if (currentScreen == AppScreenTab.ADMIN) AppStrings.clientMode(appLanguage) else AppStrings.adminMode(appLanguage),
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (currentScreen == AppScreenTab.ADMIN) GeometricForestGreen else GeometricGoldDark,
                    fontSize = 11.sp
                  )
                )
              },
              leadingIcon = {
                Icon(
                  imageVector = if (currentScreen == AppScreenTab.ADMIN) Icons.Default.Person else Icons.Default.AdminPanelSettings,
                  contentDescription = "Admin / User Mode",
                  modifier = Modifier.size(14.dp),
                  tint = if (currentScreen == AppScreenTab.ADMIN) GeometricForestGreen else GeometricGoldDark
                )
              },
              colors = AssistChipDefaults.assistChipColors(
                containerColor = if (currentScreen == AppScreenTab.ADMIN) GeometricForestGreenContainer else GeometricGoldContainer
              ),
              border = AssistChipDefaults.assistChipBorder(
                enabled = true,
                borderColor = if (currentScreen == AppScreenTab.ADMIN) GeometricForestGreen.copy(alpha = 0.3f) else GeometricGoldBorder
              ),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.testTag("admin_toggle_button")
            )
          }
        }

        // Geometric divider border
        HorizontalDivider(
          thickness = 1.dp,
          color = MaterialTheme.colorScheme.outline
        )
      }
    }

    // 2. Geometric Sub-bar (Status & Digital Assistance indicator)
    Surface(
      color = MaterialTheme.colorScheme.surfaceVariant,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(7.dp)
              .clip(CircleShape)
              .background(GeometricStatusLiveGreen)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = AppStrings.liveStatus(appLanguage),
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }

        Text(
          text = AppStrings.digitalAid(appLanguage),
          style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 0.8.sp
          )
        )
      }
    }

    HorizontalDivider(
      thickness = 1.dp,
      color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    )
  }
}
