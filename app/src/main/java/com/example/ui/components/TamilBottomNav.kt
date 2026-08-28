package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.theme.*
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AppScreenTab

@Composable
fun TamilBottomNav(
  currentScreen: AppScreenTab,
  appLanguage: AppLanguage,
  onNavigateTo: (AppScreenTab) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .background(MaterialTheme.colorScheme.surface)
  ) {
    HorizontalDivider(
      thickness = 1.dp,
      color = MaterialTheme.colorScheme.outline
    )

    NavigationBar(
      modifier = Modifier.testTag("tamil_bottom_navigation"),
      containerColor = MaterialTheme.colorScheme.surface,
      tonalElevation = 0.dp
    ) {
      // 1. Home
      NavigationBarItem(
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
              fontSize = 11.sp,
              fontWeight = if (currentScreen == AppScreenTab.HOME) FontWeight.Bold else FontWeight.Medium
            )
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.testTag("nav_home")
      )

      // 2. AI Chat / Assistant
      NavigationBarItem(
        selected = currentScreen == AppScreenTab.CHAT,
        onClick = { onNavigateTo(AppScreenTab.CHAT) },
        icon = {
          BadgedBox(
            badge = {
              Badge(containerColor = MaterialTheme.colorScheme.primary) {
                Text("AI", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
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
              fontSize = 11.sp,
              fontWeight = if (currentScreen == AppScreenTab.CHAT) FontWeight.Bold else FontWeight.Medium
            )
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.testTag("nav_chat")
      )

      // 3. Services
      NavigationBarItem(
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
              fontSize = 11.sp,
              fontWeight = if (currentScreen == AppScreenTab.SERVICES) FontWeight.Bold else FontWeight.Medium
            )
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.testTag("nav_services")
      )

      // 4. Appointment
      NavigationBarItem(
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
              fontSize = 11.sp,
              fontWeight = if (currentScreen == AppScreenTab.APPOINTMENT) FontWeight.Bold else FontWeight.Medium
            )
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.testTag("nav_appointment")
      )

      // 5. FAQ
      NavigationBarItem(
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
              fontSize = 11.sp,
              fontWeight = if (currentScreen == AppScreenTab.FAQ) FontWeight.Bold else FontWeight.Medium
            )
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.testTag("nav_faq")
      )
    }
  }
}
