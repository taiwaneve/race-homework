package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {
    val score = gameViewModel.score

    Box(modifier = Modifier.fillMaxSize()) {
        // 紅色圓形與邊界偵測邏輯（範例）
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val circleX = gameViewModel.circleX
            val radius = 50f

            drawCircle(Color.Red, radius, Offset(circleX, size.height / 2))

            if (circleX + radius >= canvasWidth) {
                gameViewModel.increaseScore()
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume() // 告訴系統已經處理了這個事件
                            gameViewModel.MoveCircle(dragAmount.x, dragAmount.y)
                        }
                    }


            ) {
                // 繪製圓形
                drawCircle(
                    color = Color.Red,
                    radius = 100f,
                    center = Offset(gameViewModel.circleX, gameViewModel.circleY)
                )
            }


            Text(
                text = message + gameViewModel.screenWidthPx.toString() + "*"
                        + gameViewModel.screenHeightPx.toString()
            )
            // 顯示姓名與分數（置中）
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            ) {
                androidx.compose.foundation.layout.Row {
                    Text(
                        text = "姓名：楊博薰",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        text = "分數：$score",
                        fontSize = 20.sp
                    )
                }
            }

// 遊戲開始按鈕（置中但稍微往下）
            Button(
                onClick = {
                    gameViewModel.gameRunning = true
                    gameViewModel.StartGame()
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp) // 避免與上方文字重疊
            ) {
                Text("遊戲開始")
            }
        }
    }
}
