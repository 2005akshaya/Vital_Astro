package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
fun TamilNavRail(
  currentScreen: AppScreenTab,
  appLanguage: AppLanguage,
  onNavigateTo: (AppScreenTab) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationRail(
    modifier = modifier.testTag("tamil_navigation_rail"),
    containerColor = MaterialTheme.colorScheme.surface,
    header = {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 12.dp)
      ) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
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
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = AppStrings.appName(appLanguage),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 10.sp
          )
        )
      }
    }
  ) {
    // 1. Home
    NavigationRailItem(
      selected = currentScreen == AppScreenTab.HOME,
      onClick = { onNavigateTo(AppScreenTab.HOME) },
      icon = {
        Icon(
          imageVector = if (currentScreen == AppScreenTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
          contentDescription = AppStrings.navHome(appLanguage)
        )
      },
      label = {
        Text(
          text = AppStrings.navHome(appLanguage),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (currentScreen == AppScreenTab.HOME) FontWeight.Bold else FontWeight.Normal
          )
        )
      },
      colors = NavigationRailItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
      )
    )

    // 2. Chat / Assistant
    NavigationRailItem(
      selected = currentScreen == AppScreenTab.CHAT,
      onClick = { onNavigateTo(AppScreenTab.CHAT) },
      icon = {
        BadgedBox(
          badge = {
            Badge(containerColor = MaterialTheme.colorScheme.primary) {
              Text("AI", fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        ) {
          Icon(
            imageVector = if (currentScreen == AppScreenTab.CHAT) Icons.Filled.ChatBubble else Icons.Outlined.ChatBubbleOutline,
            contentDescription = AppStrings.navChat(appLanguage)
          )
        }
      },
      label = {
        Text(
          text = AppStrings.navChat(appLanguage),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (currentScreen == AppScreenTab.CHAT) FontWeight.Bold else FontWeight.Normal
          )
        )
      },
      colors = NavigationRailItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
      )
    )

    // 3. Services
    NavigationRailItem(
      selected = currentScreen == AppScreenTab.SERVICES,
      onClick = { onNavigateTo(AppScreenTab.SERVICES) },
      icon = {
        Icon(
          imageVector = if (currentScreen == AppScreenTab.SERVICES) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome,
          contentDescription = AppStrings.navServices(appLanguage)
        )
      },
      label = {
        Text(
          text = AppStrings.navServices(appLanguage),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (currentScreen == AppScreenTab.SERVICES) FontWeight.Bold else FontWeight.Normal
          )
        )
      },
      colors = NavigationRailItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
      )
    )

    // 4. Appointment
    NavigationRailItem(
      selected = currentScreen == AppScreenTab.APPOINTMENT,
      onClick = { onNavigateTo(AppScreenTab.APPOINTMENT) },
      icon = {
        Icon(
          imageVector = if (currentScreen == AppScreenTab.APPOINTMENT) Icons.Filled.CalendarMonth else Icons.Outlined.CalendarMonth,
          contentDescription = AppStrings.navAppointment(appLanguage)
        )
      },
      label = {
        Text(
          text = AppStrings.navAppointment(appLanguage),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (currentScreen == AppScreenTab.APPOINTMENT) FontWeight.Bold else FontWeight.Normal
          )
        )
      },
      colors = NavigationRailItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
      )
    )

    // 5. FAQ
    NavigationRailItem(
      selected = currentScreen == AppScreenTab.FAQ,
      onClick = { onNavigateTo(AppScreenTab.FAQ) },
      icon = {
        Icon(
          imageVector = if (currentScreen == AppScreenTab.FAQ) Icons.Filled.Help else Icons.Outlined.HelpOutline,
          contentDescription = AppStrings.navFaq(appLanguage)
        )
      },
      label = {
        Text(
          text = AppStrings.navFaq(appLanguage),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (currentScreen == AppScreenTab.FAQ) FontWeight.Bold else FontWeight.Normal
          )
        )
      },
      colors = NavigationRailItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
      )
    )

    // 6. Prep Guide
    NavigationRailItem(
      selected = currentScreen == AppScreenTab.PREPARATION,
      onClick = { onNavigateTo(AppScreenTab.PREPARATION) },
      icon = {
        Icon(
          imageVector = if (currentScreen == AppScreenTab.PREPARATION) Icons.Filled.AssignmentTurnedIn else Icons.Outlined.AssignmentTurnedIn,
          contentDescription = AppStrings.navPrep(appLanguage)
        )
      },
      label = {
        Text(
          text = AppStrings.navPrep(appLanguage),
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = if (currentScreen == AppScreenTab.PREPARATION) FontWeight.Bold else FontWeight.Normal
          )
        )
      },
      colors = NavigationRailItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
      )
    )
  }
}
