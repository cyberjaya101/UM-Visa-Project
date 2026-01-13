package com.example.umvisamate.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// 小组主色调配置
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF163269), // Biscay
    secondary = Color(0xFFDEB406), // RebelsGold
    tertiary = Color(0xFF4A7FBA) // SteelBlue
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF163269), // Biscay
    secondary = Color(0xFFDEB406), // RebelsGold
    tertiary = Color(0xFF4A7FBA) // SteelBlue
)

@Composable
fun UMVisaMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    typography: androidx.compose.material3.Typography = androidx.compose.material3.Typography(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}