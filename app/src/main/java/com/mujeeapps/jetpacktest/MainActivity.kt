package com.mujeeapps.jetpacktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mujeeapps.jetpacktest.ui.theme.JetPackTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val painter = painterResource(id = R.drawable.screen3)
            val description = "Coconut Girl"
            val title = "Coconut Tree"
            Box(modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                    .padding(16.dp)) {
                CardDetailsView(painter = painter, description = description, title = title)
            }
        }
    }
}

@Composable
fun CardDetailsView(
        painter: Painter, //Painter for UI property access
        description: String,
        title: String,
        modifier: Modifier = Modifier
) {
    Card(
            modifier = modifier.fillMaxWidth(),
            elevation = 4.dp,
            shape = RoundedCornerShape(16.dp)
    ) {
        //Box modifier stacks controllers one top of other
        Box(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(fraction = 1f)) {
            Image(painter = painter,
                    contentDescription = description,
                    contentScale = ContentScale.FillBounds
            )

            Box(modifier = Modifier.fillMaxSize()
                    .background(brush =
                        Brush.verticalGradient(
                            colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                            ), startY = 400f
                    ))
            ) {

            }

            Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
            ) {
                Text(title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}