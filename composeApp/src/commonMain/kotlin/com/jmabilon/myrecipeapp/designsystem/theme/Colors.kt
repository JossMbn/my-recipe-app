package com.jmabilon.myrecipeapp.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Light theme colors
val BackgroundLight = Color(0xFFF4FBF8)
val OnBackgroundLight = Color(0xFF161D1C)
val PrimaryLight = Color(0xFF377B74)
val OnPrimaryLight = Color.White
val SurfaceLight = Color(0xFFF4FBF8)
val OnSurfaceLight = Color(0xFF161D1C)
val SurfaceVariantLight = Color(0xFFDAE5E2)
val OnSurfaceVariantLight = Color(0xFF3F4947)
val OutlineLight = Color(0xFF6F7977)
val OutlineVariantLight = Color(0xFFBEC9C6)
val SurfaceDimLight = Color(0xFFD5DBD9)
val SurfaceBrightLight = Color(0xFFF4FBF8)
val SurfaceContainerLowestLight = Color(0xFFFFFFFF)
val SurfaceContainerLowLight = Color(0xFFEFF5F3)
val SurfaceContainerLight = Color(0xFFE9EFED)
val SurfaceContainerHighLight = Color(0xFFE3EAE7)
val SurfaceContainerHighestLight = Color(0xFFDDE4E2)
val ErrorLight = Color(0xFFB3261E)
val OnErrorLight = Color.White

// Dark theme colors
val BackgroundDark = Color(0xFF0E1514)
val OnBackgroundDark = Color(0xFFDDE4E2)
val PrimaryDark = Color(0xFF81D5CB)
val OnPrimaryDark = Color(0xFF003733)
val SurfaceDark = Color(0xFF0E1514)
val OnSurfaceDark = Color(0xFFDDE4E2)
val SurfaceVariantDark = Color(0xFF3F4947)
val OnSurfaceVariantDark = Color(0xFFBEC9C6)
val OutlineDark = Color(0xFF899391)
val OutlineVariantDark = Color(0xFF3F4947)
val SurfaceDimDark = Color(0xFF0E1514)
val SurfaceBrightDark = Color(0xFF343A39)
val SurfaceContainerLowestDark = Color(0xFF090F0E)
val SurfaceContainerLowDark = Color(0xFF161D1C)
val SurfaceContainerDark = Color(0xFF1A2120)
val SurfaceContainerHighDark = Color(0xFF252B2A)
val SurfaceContainerHighestDark = Color(0xFF303635)
val ErrorDark = Color(0xFFF2B8B5)
val OnErrorDark = Color(0xFF601410)

// Extended brushes
val ColorScheme.backgroundBrush: Brush
    @Composable get() = extendedBrush(
        light = Brush.linearGradient(
            colors = listOf(
                Color(0xFFC0DBD5),
                Color(0xFFFFF8F0),
                Color(0xFFFFF8F0)
            ),
            start = Offset(0f, 0f),
            end = Offset.Infinite
        ),
        dark = Brush.linearGradient(
            colors = listOf(
                Color(0xFF2E3832),
                Color(0xFF121212)
            ),
            start = Offset(0f, 0f),
            end = Offset.Infinite
        )
    )

// Utils
/*@Composable
fun extendedColor(light: Color, dark: Color): Color {
    return if (isSystemInDarkTheme()) dark else light
}*/

@Composable
fun extendedBrush(light: Brush, dark: Brush): Brush {
    return if (isSystemInDarkTheme()) dark else light
}
