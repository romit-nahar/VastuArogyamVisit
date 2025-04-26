package com.example.vastuarogyamvisit.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Custom colors for Vastu Arogyam theme
private val VastuGreen = Color(0xFF2E7D32)
private val LightVastuGreen = Color(0xFFB0F8AF)
private val DarkVastuGreen = Color(0xFF005005)

private val VastuBrown = Color(0xFF795548)
private val LightVastuBrown = Color(0xFFA98274)
private val DarkVastuBrown = Color(0xFF4B2C20)

private val VastuOrange = Color(0xFFFF9800)  // Added for Vastu theme enhancements
private val LightVastuOrange = Color(0xFFFFE0B2)
private val DarkVastuOrange = Color(0xFFE65100)

private val LightColorScheme = lightColorScheme(
    primary = VastuGreen,
    onPrimary = Color.White,
    primaryContainer = LightVastuGreen,
    onPrimaryContainer = Color(0xFF002106),

    secondary = VastuBrown,
    onSecondary = Color.White,
    secondaryContainer = LightVastuBrown,
    onSecondaryContainer = Color(0xFF271610),

    tertiary = VastuOrange,
    onTertiary = Color.White,
    tertiaryContainer = LightVastuOrange,
    onTertiaryContainer = Color(0xFF3E2800)
)

private val DarkColorScheme = darkColorScheme(
    primary = LightVastuGreen,
    onPrimary = Color(0xFF002106),
    primaryContainer = DarkVastuGreen,
    onPrimaryContainer = LightVastuGreen,

    secondary = LightVastuBrown,
    onSecondary = Color(0xFF271610),
    secondaryContainer = DarkVastuBrown,
    onSecondaryContainer = LightVastuBrown,

    tertiary = LightVastuOrange,
    onTertiary = Color(0xFF3E2800),
    tertiaryContainer = DarkVastuOrange,
    onTertiaryContainer = LightVastuOrange
)

@Composable
fun VastuArogyamVisitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}