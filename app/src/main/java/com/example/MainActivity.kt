package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.components.TamilBottomNav
import com.example.ui.components.TamilNavRail
import com.example.ui.components.TamilTopBar
import com.example.ui.components.WhatsAppFloatingButton
import com.example.ui.screens.*
import com.example.ui.theme.TamilAstrologyTheme
import com.example.ui.viewmodel.AppScreenTab
import com.example.ui.viewmodel.AstrologyViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: AstrologyViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val isDarkMode by viewModel.isDarkMode.collectAsState()
      TamilAstrologyTheme(darkTheme = isDarkMode) {
        TamilAstrologyApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun TamilAstrologyApp(viewModel: AstrologyViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val config by viewModel.practiceConfig.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()
  val isDarkMode by viewModel.isDarkMode.collectAsState()

  BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
    val isWideScreen = maxWidth >= 720.dp

    Row(modifier = Modifier.fillMaxSize()) {
      if (isWideScreen && currentScreen != AppScreenTab.ADMIN) {
        TamilNavRail(
          currentScreen = currentScreen,
          appLanguage = appLanguage,
          onNavigateTo = { tab -> viewModel.navigateTo(tab) }
        )
      }

      Scaffold(
        modifier = Modifier.weight(1f),
        topBar = {
          TamilTopBar(
            currentScreen = currentScreen,
            appLanguage = appLanguage,
            isDarkMode = isDarkMode,
            onToggleLanguage = { viewModel.toggleLanguage() },
            onToggleTheme = { viewModel.toggleDarkMode() },
            onNavigateTo = { tab -> viewModel.navigateTo(tab) }
          )
        },
        bottomBar = {
          if (!isWideScreen && currentScreen != AppScreenTab.ADMIN) {
            TamilBottomNav(
              currentScreen = currentScreen,
              appLanguage = appLanguage,
              onNavigateTo = { tab -> viewModel.navigateTo(tab) }
            )
          }
        },
        floatingActionButton = {
          if (currentScreen == AppScreenTab.HOME || currentScreen == AppScreenTab.SERVICES || currentScreen == AppScreenTab.FAQ) {
            WhatsAppFloatingButton(
              phoneNumber = config.whatsappContact,
              appLanguage = appLanguage
            )
          }
        }
      ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
          Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
            when (screen) {
              AppScreenTab.HOME -> LandingScreen(
                viewModel = viewModel,
                onNavigateTo = { tab -> viewModel.navigateTo(tab) }
              )
              AppScreenTab.CHAT -> ChatScreen(
                viewModel = viewModel,
                onNavigateToBooking = { viewModel.navigateTo(AppScreenTab.APPOINTMENT) }
              )
              AppScreenTab.SERVICES -> ServicesScreen(
                viewModel = viewModel,
                onNavigateToBooking = { _ -> viewModel.navigateTo(AppScreenTab.APPOINTMENT) }
              )
              AppScreenTab.APPOINTMENT -> AppointmentScreen(
                viewModel = viewModel,
                onBookingSuccess = { viewModel.navigateTo(AppScreenTab.HOME) }
              )
              AppScreenTab.FAQ -> FaqScreen(
                viewModel = viewModel,
                onAskChatQuestion = { question ->
                  viewModel.sendChatMessage(question)
                  viewModel.navigateTo(AppScreenTab.CHAT)
                }
              )
              AppScreenTab.PREPARATION -> ConsultationPrepScreen(
                viewModel = viewModel,
                onNavigateToBooking = { viewModel.navigateTo(AppScreenTab.APPOINTMENT) }
              )
              AppScreenTab.ADMIN -> AdminDashboardScreen(
                viewModel = viewModel
              )
            }
          }
        }
      }
    }
  }
}
