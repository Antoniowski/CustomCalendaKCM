package model

import androidx.compose.ui.graphics.Color

object MonthColors{
    var colors: MutableMap<Int, Color> = mutableMapOf(
        0 to Color(10, 35, 255),
        1 to Color(60, 100, 255),
        2 to Color(40, 140, 140),
        3 to Color(50, 75, 50),
        4 to Color(0, 60, 15),
        5 to Color(114, 63, 47),
        6 to Color(120, 50, 90),
        7 to Color(120, 20, 30),
        8 to Color(76, 80, 76),
        9 to Color(130, 63, 47),
        10 to Color(90, 80, 76),
        11 to Color(0, 0, 90)
    )
    var images: MutableMap<Int, Color> = mutableMapOf()
}
