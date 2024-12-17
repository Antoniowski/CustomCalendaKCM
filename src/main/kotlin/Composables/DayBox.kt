package Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import theme.AppTheme

@Composable
fun DayBox(viewModel: AppViewModel, dayString: String, today: Boolean, modifier: Modifier)
{
    Box(contentAlignment = Alignment.TopStart,
        modifier = modifier
            .border(1.dp, viewModel.currentMonthColor.value)
            .background(if(dayString == "") Color(
                viewModel.currentMonthColor.value.red,
                viewModel.currentMonthColor.value.green,
                viewModel.currentMonthColor.value.blue,
                alpha = .2f) else Color.Transparent)
            .padding(3.dp)
    ){
        Text(dayString,
            fontFamily = AppTheme.typography.labelLarge.fontFamily,
            fontSize = AppTheme.typography.labelLarge.fontSize)
        if(today)
            Box(modifier = Modifier
            .clip(CircleShape)
            .background(viewModel.currentMonthColor.value)
            .fillMaxWidth(.25f)
            .aspectRatio(1f)
            .align(Alignment.BottomStart))
    }
}