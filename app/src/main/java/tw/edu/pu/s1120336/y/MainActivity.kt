package tw.edu.pu.s1120336.y

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tw.edu.pu.s1120336.y.ui.theme.YTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.unit.IntOffset

import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.ui.graphics.ImageShader


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //PointerEvents()
                    Tap()
                    Drag_Horizontal()
                    Drag_Vertical()
                    Ghost()

                }
            }
        }
    }
}

@Composable
fun PointerEvents() {
    var msg by remember { mutableStateOf("作者：楊靖惠") }
    Column {
        Text("\n" + msg)
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Yellow)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            msg = "${event.type}, ${event.changes.first().position}"
                        }
                    }
                }
        )
    }
}

@Composable
fun Tap() {
    var msg by remember { mutableStateOf("TAP相關手勢實例") }
    var count by remember { mutableStateOf(0) }
    var offset1 by remember { mutableStateOf(Offset.Zero) }
    var offset2 by remember { mutableStateOf(Offset.Zero) }
    var PU = arrayListOf(R.drawable.pu0, R.drawable.pu1,
        R.drawable.pu2, R.drawable.pu3,
        R.drawable.pu4, R.drawable.pu5)
    var Number by remember { mutableStateOf(0) }




    Column {
        Text("\n" + msg +"\n計數:" +count.toString(),
          modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onPress = { count = 0 }
            )
        }
        )

        Box(modifier = Modifier
            .background(Color.Cyan)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, dragAmount ->offset2+=dragAmount},
                    onDragStart = {offset1 = it
                        offset2 = it },
                    onDragEnd = {
                        //msg="從" + offset1.toString() + "拖曳到" + offset2.toString()
                        if (offset2.x >= offset1.x){
                            msg = "長按後向右拖曳"
                            Number ++
                            if (Number>5){Number=0}
                        }
                        else{
                            msg = "長按後向左拖曳"
                            Number --
                            if (Number<0){Number=5}
                        }
                    },
                )
            }

        ) {
            Text("")
        }

        Image(
            painter = painterResource(id = PU[Number]),
            contentDescription = "靜宜之美",
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            msg = "後觸發onTap(短按)"
                            count++
                        },
                        onDoubleTap = {
                            msg = "雙擊"
                            count += 3
                        },
                        onLongPress = {
                            msg = "長按"
                            count += 2
                        },
                        onPress = { msg = "先觸發onPress(按下)" }
                    )
                }

        )
    }
}
@Composable
fun Drag_Horizontal() {
    var offsetX by remember { mutableStateOf(0f) }
    Text(
        text = "水平拖曳",
        modifier = Modifier
            .offset { IntOffset(offsetX.toInt(), 215) }
            .draggable(
                orientation= Orientation.Horizontal,
                state = rememberDraggableState{ delta ->
                    offsetX += delta
                }
            )
    )
}
@Composable
fun Drag_Vertical() {
    var offsetY by remember { mutableStateOf(0f) }
    Text(
        text = "垂直拖曳",
        modifier = Modifier
            .offset { IntOffset(600, offsetY.toInt() + 100) }
            .draggable(
                orientation= Orientation.Vertical,
                state = rememberDraggableState{ delta ->
                    offsetY += delta
                }
            )
    )
}
@Composable
fun Ghost() {
    Image(
        painter = painterResource(id=R.drawable.ghost1),
        contentDescription = "精靈1",
        modifier = Modifier
            .offset { IntOffset(800,200) }
    )
    Image(
        painter = painterResource(id=R.drawable.ghost2),
        contentDescription = "精靈2",
        modifier = Modifier
            .offset { IntOffset(300,800) }
    )
    Image(
        painter = painterResource(id=R.drawable.ghost3),
        contentDescription = "精靈3",
        modifier = Modifier
            .offset { IntOffset(500,500) }
    )
}
