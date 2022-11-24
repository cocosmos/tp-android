package com.crea.dev4.mipam.tpandroid.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(

    primaryVariant = Purple900,
    secondary = Teal200,
    surface = Navy,
    onSurface = Chartreuse,
    primary = Purple700,
    onPrimary = Chartreuse
)

private val LightColorPalette = lightColors(

    primaryVariant = Purple700,
    secondary = Teal200,
    surface = LightBlue,
    onSurface = Purple900,
    onPrimary = Color.White,
    primary = Navy,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TPAndroidTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}