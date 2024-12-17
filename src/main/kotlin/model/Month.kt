package model

import androidx.compose.ui.graphics.Color

data class Month(
    var id: Int,
    var name: String,
    var color: Color,
    var imageURL: String
)
