package com.example.ui.screens.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun AdminAuthScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val pinInput by viewModel.adminPinInput.collectAsState()
  val pinError by viewModel.adminPinError.collectAsState()
  val config by viewModel.practiceConfig.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(GeometricBackground)
      .padding(20.dp),
    contentAlignment = Alignment.Center
  ) {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = GeometricSurface),
      border = CardDefaults.outlinedCardBorder().copy(
        width = 1.5.dp,
        brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.40f))
      ),
      elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
      modifier = Modifier
        .fillMaxWidth()
        .widthIn(max = 440.dp)
        .testTag("admin_auth_card")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // App / Temple Emblem
        Box(
          modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(GeometricGoldPrimary)
            .border(2.dp, GeometricGoldMetallic, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Image(
            painter = painterResource(id = R.drawable.img_vittal_logo_1787913597109),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          TraditionalMotifBadge(
            text = if (appLanguage == AppLanguage.TAMIL) "நிர்வாகக் கட்டுப்பாட்டு மையம்" else "Owner Admin Portal"
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = config.astrologerNameTamil.ifEmpty { "ஜோதிடர் ஸ்ரீ ராஜகோபால்" },
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary
            ),
            textAlign = TextAlign.Center
          )
          Text(
            text = config.titleTamil.ifEmpty { "ஸ்ரீ விட்டல் ஜோதிடாலயம்" },
            style = MaterialTheme.typography.bodySmall.copy(
              color = GeometricGoldDark,
              fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center
          )
        }

        Text(
          text = if (appLanguage == AppLanguage.TAMIL)
            "அப்பா, உங்கள் 4 இலக்க நிர்வாக கடவுச்சொல்லை உள்ளிடவும்:"
          else
            "Please enter your 4-digit Admin PIN:",
          style = MaterialTheme.typography.bodyMedium.copy(
            color = GeometricTextSecondary,
            textAlign = TextAlign.Center
          )
        )

        // PIN Indicators (Dots)
        Row(
          horizontalArrangement = Arrangement.spacedBy(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          repeat(4) { index ->
            val isFilled = index < pinInput.length
            Box(
              modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(
                  if (isFilled) GeometricGoldPrimary else GeometricSurfaceSub
                )
                .border(
                  width = 1.5.dp,
                  color = if (isFilled) GeometricGoldMetallic else GeometricGoldBorderSubtle,
                  shape = CircleShape
                )
            )
          }
        }

        // Error message if any
        AnimatedVisibility(visible = pinError != null) {
          Surface(
            color = GeometricMaroonContainer,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = pinError ?: "",
              style = MaterialTheme.typography.bodySmall.copy(
                color = GeometricMaroon,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
              ),
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
          }
        }

        // Default PIN helper for easy access
        Surface(
          color = GeometricGoldContainer.copy(alpha = 0.5f),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = if (appLanguage == AppLanguage.TAMIL) "இயல்புநிலை கடவுச்சொல் (Default PIN): 1234" else "Default PIN: 1234",
            style = MaterialTheme.typography.labelSmall.copy(
              color = GeometricGoldDark,
              textAlign = TextAlign.Center,
              fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(vertical = 4.dp)
          )
        }

        // Numeric Keypad
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          val keypadRows = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("CLEAR", "0", "ENTER")
          )

          keypadRows.forEach { row ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              row.forEach { key ->
                when (key) {
                  "CLEAR" -> {
                    IconButton(
                      onClick = {
                        if (pinInput.isNotEmpty()) {
                          viewModel.updateAdminPinInput(pinInput.dropLast(1))
                        }
                      },
                      modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GeometricSurfaceSub)
                    ) {
                      Icon(
                        imageVector = Icons.Default.Backspace,
                        contentDescription = "Backspace",
                        tint = GeometricTextSecondary
                      )
                    }
                  }
                  "ENTER" -> {
                    Button(
                      onClick = { viewModel.submitAdminPin() },
                      colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
                      shape = RoundedCornerShape(12.dp),
                      modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                        .testTag("admin_pin_submit")
                    ) {
                      Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Submit",
                        tint = Color.White
                      )
                    }
                  }
                  else -> {
                    Button(
                      onClick = {
                        if (pinInput.length < 4) {
                          viewModel.updateAdminPinInput(pinInput + key)
                          if (pinInput.length + 1 == 4) {
                            // Automatically submit on 4 digits
                            viewModel.submitAdminPin()
                          }
                        }
                      },
                      colors = ButtonDefaults.buttonColors(
                        containerColor = GeometricSurfaceSub,
                        contentColor = GeometricTextPrimary
                      ),
                      shape = RoundedCornerShape(12.dp),
                      border = CardDefaults.outlinedCardBorder().copy(
                        width = 1.dp,
                        brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldBorderSubtle)
                      ),
                      modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                    ) {
                      Text(
                        text = key,
                        style = MaterialTheme.typography.titleMedium.copy(
                          fontWeight = FontWeight.Bold,
                          fontSize = 20.sp
                        )
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
