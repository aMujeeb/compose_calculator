package com.mujeeapps.jetpacktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mujeeapps.jetpacktest.ui.theme.JetPackTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTipApp {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    TopHeader()
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MyTipApp(content: @Composable () -> Unit) {
    JetPackTestTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), color = Color.White
        ) {
            content()
        }
    }
}

@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            //.clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))
            .clip(
                shape = CircleShape.copy(all = CornerSize(12.dp))
            ),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total Per Person",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "$$total",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainContent() {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(
            corner = CornerSize(12.dp)
        ), border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column() {
            Text(text = "Test 1")
            Text(text = "Test 2")
            Text(text = "Test 3")
        }
    }
}


@Composable
fun DefaultPreview() {
    JetPackTestTheme {
        MyTipApp {
            TopHeader()
        }
    }
}