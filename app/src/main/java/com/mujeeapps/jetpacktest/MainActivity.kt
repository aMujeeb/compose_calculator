package com.mujeeapps.jetpacktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mujeeapps.jetpacktest.components.InputField
import com.mujeeapps.jetpacktest.ui.theme.JetPackTestTheme
import com.mujeeapps.jetpacktest.utils.calculateTotalPerPerson
import com.mujeeapps.jetpacktest.utils.calculateTotalTip
import com.mujeeapps.jetpacktest.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTipApp {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
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


@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun MainContent() {

    val splitByState = remember {
        mutableStateOf(1)
    }

    val range = IntRange(start = 1, endInclusive = 15)

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }

    BillForm(
        splitByState = splitByState,
        tipAmountState = tipAmountState,
        range = range,
        totalForPerson = totalPerPersonState
    )
}

@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(12.dp)
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalForPerson: MutableState<Double>,
    onValChange: (String) -> Unit = {}
) {
    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyBoardController = LocalSoftwareKeyboardController.current

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    val tipPercentage = (sliderPositionState.value * 100).toInt()

    TopHeader(totalPerPerson = totalForPerson.value)

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            corner = CornerSize(8.dp)
        ), border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column() {
            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                isEnabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) {
                        return@KeyboardActions
                    }
                    onValChange(totalBillState.value.trim())
                    keyBoardController?.hide()
                })
            if (validState) {
                //Split Row
                Row(
                    modifier = Modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Split",
                        modifier = Modifier
                            .align(
                                alignment = Alignment.CenterVertically
                            )
                            .padding(start = 8.dp),
                        style = TextStyle(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Row(
                        modifier = Modifier.padding(3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                            splitByState.value =
                                if (splitByState.value > 1) splitByState.value - 1
                                else 1
                            totalForPerson.value = calculateTotalPerPerson(
                                totalBillState.value,
                                splitByState.value,
                                tipPercentage
                            )
                        })

                        Text(
                            text = "${splitByState.value}",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                            style = TextStyle(color = Color.Yellow, fontWeight = FontWeight.Bold)
                        )

                        RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                            if (splitByState.value < range.last) {
                                splitByState.value = splitByState.value + 1
                            }
                            totalForPerson.value = calculateTotalPerPerson(
                                totalBillState.value,
                                splitByState.value,
                                tipPercentage
                            )
                        })
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                //Tip Row
                Row(
                    modifier = Modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Tip",
                        modifier = Modifier
                            .align(
                                alignment = Alignment.CenterVertically
                            )
                            .padding(start = 8.dp),
                        style = TextStyle(color = Color.Red)
                    )

                    Spacer(modifier = Modifier.width(200.dp))

                    Text(
                        text = "$ ${tipAmountState.value}",
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        ),
                        style = TextStyle(color = Color.Red)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$tipPercentage %",
                        style = TextStyle(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    //Slider
                    Slider(modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                        value = sliderPositionState.value, onValueChange = { newValue ->
                            sliderPositionState.value = newValue
                            tipAmountState.value = calculateTotalTip(
                                totalBill = totalBillState.value,
                                tipPercentage = tipPercentage
                            )
                            totalForPerson.value = calculateTotalPerPerson(
                                totalBillState.value,
                                splitByState.value,
                                tipPercentage
                            )

                        },
                        steps = 5,
                        onValueChangeFinished = {

                        })
                }
            } else {
                Box() {

                }
            }
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