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

private val LightColorScheme = lightColorScheme(
    primary = VastuGreen,
    onPrimary = Color.White,
    primaryContainer = LightVastuGreen,
    onPrimaryContainer = Color(0xFF002106),

    secondary = VastuBrown,
    onSecondary = Color.White,
    secondaryContainer = LightVastuBrown,
    onSecondaryContainer = Color(0xFF271610))