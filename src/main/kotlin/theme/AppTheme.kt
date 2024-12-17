package theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val colorScheme = AppColorScheme(
    background = Color.White,
    primary = Color.Gray,
    secondary = Color.LightGray,
    primaryText = Color.Gray,
    secondaryText = Color.LightGray,
    accent = Color.Cyan
)

private val typography = AppTypography(
    titleLarge = TextStyle(
      fontFamily = Bahiana,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    ),
    titleNormal = TextStyle(
        fontFamily = Bahiana,
        fontSize = 24.sp
    ),
    body = TextStyle(
        fontFamily = Bahiana,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Bahiana,
        fontSize = 12.sp
    ),
    labelNormal = TextStyle(
        fontFamily = Bahiana,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Bahiana,
        fontSize = 16.sp
    ),
)

private val shape = AppShape(
    container = RoundedCornerShape(5.dp),
    button = RoundedCornerShape(5.dp)
)

private val size = AppSize(
    large = 24.dp,
    medium = 18.dp,
    normal = 14.dp,
    small = 12.dp
)

private val padding = AppPadding(
    large = 20.dp,
    normal = 15.dp,
    small = 10.dp
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
){
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppSize provides size,
        LocalAppShape provides shape,
        LocalAppPadding provides padding,
        content = content
    )
}

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val size: AppSize
        @Composable get() = LocalAppSize.current

    val padding: AppPadding
        @Composable get() = LocalAppPadding.current
}