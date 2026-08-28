package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GeometricBalanceLightColorScheme = lightColorScheme(
  primary = GeometricGoldPrimary,
  onPrimary = Color.White,
  primaryContainer = GeometricGoldContainer,
  onPrimaryContainer = GeometricGoldDark,

  secondary = GeometricForestGreen,
  onSecondary = Color.White,
  secondaryContainer = GeometricForestGreenContainer,
  onSecondaryContainer = GeometricForestGreenText,

  tertiary = GeometricMaroon,
  onTertiary = Color.White,
  tertiaryContainer = GeometricMaroonContainer,
  onTertiaryContainer = GeometricMaroonText,

  background = GeometricBackground,
  onBackground = GeometricTextPrimary,

  surface = GeometricSurface,
  onSurface = GeometricTextPrimary,
  surfaceVariant = GeometricSurfaceVariant,
  onSurfaceVariant = GeometricTextSecondary,
  surfaceContainer = GeometricSurfaceContainer,

  outline = GeometricGoldMetallic.copy(alpha = 0.35f),
  outlineVariant = GeometricDivider
)

private val GeometricBalanceDarkColorScheme = darkColorScheme(
  primary = DarkGeometricGoldPrimary,
  onPrimary = Color(0xFF261900),
  primaryContainer = Color(0xFF4A3403),
  onPrimaryContainer = Color(0xFFFFE7B8),

  secondary = DarkGeometricForestGreen,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFF1F3617),
  onSecondaryContainer = Color(0xFFC7EBC0),

  tertiary = Color(0xFFD4566A),
  onTertiary = Color.White,
  tertiaryContainer = Color(0xFF5A1420),
  onTertiaryContainer = Color(0xFFFFD9DF),

  background = DarkGeometricBackground,
  onBackground = DarkGeometricTextPrimary,

  surface = DarkGeometricSurface,
  onSurface = DarkGeometricTextPrimary,
  surfaceVariant = DarkGeometricSurfaceSub,
  onSurfaceVariant = DarkGeometricTextSecondary,
  surfaceContainer = DarkGeometricSurfaceSub,

  outline = Color(0xFF544A3C),
  outlineVariant = Color(0xFF38322B)
)

@Composable
fun TamilAstrologyTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) GeometricBalanceDarkColorScheme else GeometricBalanceLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
