package Composables
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import kotlin.system.exitProcess


@Composable
fun WindowScope.CustomCloseButton(){
    TextButton(
        onClick = { exitProcess(0)},
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ),
        shape = CircleShape,
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .padding(2.dp)
    ){
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
            Icon(useResource("icons/x-solid.svg"){ loadSvgPainter(it, Density(10f))}, "",
                tint = Color.Black,
                modifier = Modifier.matchParentSize())
        }
    }
}
@Composable
fun CustomMinimizeButton(windowState: WindowState){
    TextButton(
        onClick = {if(windowState.isMinimized.not()) windowState.isMinimized = windowState.isMinimized.not()},
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ),
        shape = CircleShape,
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .padding(2.dp)
    ){
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
            Icon(useResource("icons/window-minimize-regular.svg"){ loadSvgPainter(it, Density(10f))}, "",
                tint = Color.Black,
                modifier = Modifier.matchParentSize())
        }
    }
}

@Composable
fun WindowScope.CustomTopBar(
    windowState: WindowState,
    modifier: Modifier
){
    Row(
        modifier = modifier
    ){
        WindowDraggableArea{
            Box(modifier = Modifier
                .fillMaxWidth(.85f)
                .fillMaxHeight()
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp, alignment = Alignment.End),
            modifier = Modifier.fillMaxSize()
        ){
            CustomMinimizeButton(windowState)
            CustomCloseButton()
        }
    }
}