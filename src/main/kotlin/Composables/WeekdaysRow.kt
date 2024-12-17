package Composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import theme.AppTheme

@Composable
fun WeekdayRow(viewModel: AppViewModel, modifier: Modifier)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        for (days in viewModel.weekdayFirstLetterList){
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxHeight().fillMaxWidth().weight(1f).aspectRatio(1f))
            {
                Text(days, textAlign = TextAlign.Center,
                fontFamily = AppTheme.typography.labelLarge.fontFamily,
                fontSize = AppTheme.typography.labelLarge.fontSize,
                fontWeight = AppTheme.typography.labelLarge.fontWeight,
                color = viewModel.currentMonthColor.value)
            }
        }
    }
}