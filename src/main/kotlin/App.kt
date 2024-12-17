import Composables.CustomTopBar
import Composables.DayBox
import Composables.WeekdayRow
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import theme.AppTheme
import java.io.File
import java.util.Calendar

@Composable
@Preview
fun WindowScope.App(windowState: WindowState, viewModel: AppViewModel) {
    AppTheme{
        Column(modifier = Modifier.fillMaxSize()){
            CustomTopBar(windowState, Modifier.fillMaxWidth().height(25.dp).background(Color.White))
            //Main Box
            Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)){
                Row(horizontalArrangement = Arrangement.spacedBy(15.dp), modifier = Modifier.padding(15.dp)){
                    /**
                     * Left side of the calendar. It contains the month title, the lunar phase and the week grid of
                     * the calendar.
                     */
                    Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(.45f)) {
                        //CURRENT MONTH TITLE
                        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                viewModel.currentMonthName.value + " " + viewModel.currentYear.value,
                                fontFamily = AppTheme.typography.titleLarge.fontFamily,
                                fontWeight = AppTheme.typography.titleLarge.fontWeight,
                                fontSize = AppTheme.typography.titleLarge.fontSize,
                                color = viewModel.currentMonthColor.value
                            )
                        }

//                        //LUNAR PHASES
//                        Row(modifier = Modifier.fillMaxWidth().height(20.dp)) {
//
//                        }

                        //SEPARATOR
                        Box(
                            modifier = Modifier.fillMaxWidth().height(1.dp)
                                .background(viewModel.currentMonthColor.value)
                        )

                        //WEEKDAY ROW
                        WeekdayRow(viewModel = viewModel, modifier = Modifier.fillMaxWidth().height(30.dp))

                        //WEEK GRID
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            //Six fixed rows
                            for (x in viewModel.weekList.indices) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier
                                        .fillMaxHeight().weight(1f)
                                ) {
                                    for (day in viewModel.weekList[x].third) {
                                        DayBox(
                                            viewModel = viewModel,
                                            dayString = if (day.toString() != "0") day.toString() else "",
                                            today = day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) &&
                                                    viewModel.weekList[x].first == Calendar.getInstance().get(Calendar.YEAR) &&
                                                    viewModel.weekList[x].second == Calendar.getInstance().get(Calendar.MONTH),
                                            modifier = Modifier.fillMaxSize().weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * RIGHT SIDE
                     * It contains the image related to the current month of the calendar.
                     * To use custom images follow the instruction in the README.TXT file.
                     */
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Image(
                            loadImageBitmap(
                                File(
                                    viewModel.monthList[viewModel.currentMonth.value].imageURL
                                ).inputStream()
                            ),
                            contentDescription = "",
                            modifier = Modifier.matchParentSize()
                        )
                    }
                }

                //Button Box
                Box(modifier = Modifier.fillMaxSize().background(Color.Transparent))
                {
                    //Previous Month
                    Row(modifier = Modifier.fillMaxHeight().fillMaxWidth(.5f).align(Alignment.TopStart)){
                        TextButton(viewModel::previousMonth,
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(50, 50 , 50 , 0),
                                contentColor = viewModel.currentMonthColor.value,
                            ),
                            modifier = Modifier.fillMaxWidth(.25f).fillMaxHeight()
                        ){
                            Text("")
                        }
                    }

                        //Next Month
                    Row(modifier = Modifier.fillMaxHeight().fillMaxWidth(.5f).align(Alignment.TopEnd),
                        horizontalArrangement = Arrangement.End){
                        TextButton( viewModel::nextMonth,
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(50, 50 , 50 , 0),
                                contentColor = viewModel.currentMonthColor.value,
                            ),
                            modifier = Modifier.fillMaxWidth(.25f).fillMaxHeight()
                        ){
                            Text("")
                        }
                    }
                }
            }
        }
    }
}