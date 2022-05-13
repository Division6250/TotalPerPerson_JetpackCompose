package com.example.totalperperson

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.totalperperson.ui.theme.TotalPerPersonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TotalPerPersonTheme {
                MainApp()
            }
        }
    }
}

@Preview
@Composable
fun MainApp() {
    val bill = remember{
        mutableStateOf("0")
    }
    val split = remember{
        mutableStateOf(1)
    }
    val tax = remember{
        mutableStateOf(0F)
    }
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = MaterialTheme.colors.background
    ) {
        Column() {
            BillPerPerson(
                value = bill.value.toDouble().div(100).times(100.plus(tax.value.toInt())).div(split.value)
            )
            Surface(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                border = BorderStroke(0.5.dp, color = Color.Magenta),
                shape = RoundedCornerShape(5),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EnterBill(value = bill.value) {
                            newValue->bill.value = newValue
                    }
                    if (bill.value.toDouble().div(100).times(100.plus(tax.value.toInt())).div(split.value).toDouble() > 0) {
                        EnterSplit(value = split.value) {
                                newValue->split.value = newValue
                        }
                        EnterTax(bill = bill.value, value = tax.value) {
                                newValue->tax.value = newValue
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BillPerPerson(value: Double = 0.0) {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(150.dp),
        border = BorderStroke(0.5.dp, color = Color.Magenta),
        shape = RoundedCornerShape(10),
        color = Color.Magenta
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total per person",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "$value",
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Composable
fun EnterBill(value: String = "0", getValue: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        value = value,
        onValueChange = {
            val txt = it.ifEmpty {
                "0"
            }
            getValue(txt)
        },
        label = { Text("Enter bill") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_attach_money_24),
                contentDescription = "",
                modifier = Modifier
                    .size(23.dp)
                    .padding(bottom = 3.dp)
            )
        }
    )
}

@Composable
fun EnterSplit(value: Int = 1, getValue: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Split",
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(
            modifier = Modifier
                .size(25.dp, 15.dp)
        )
        Button(
            shape = CircleShape,
            onClick = {
                val max = if (value > 1) {
                    value - 1
                } else {
                    1
                }
                getValue(max)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_minus_24),
                contentDescription = "",
                modifier = Modifier
                    .size(23.dp)
            )
        }
        Spacer(
            modifier = Modifier
                .size(5.dp, 15.dp)
        )
        Text(
            text = "$value",
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(
            modifier = Modifier
                .size(5.dp, 15.dp)
        )
        Button(
            shape = CircleShape,
            onClick = {
                getValue(value + 1)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_add_24),
                contentDescription = "",
                modifier = Modifier
                    .size(23.dp)
            )
        }
    }
}

@Composable
fun EnterTax(bill: String = "0", value: Float = 0F, getValue: (Float) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tip:",
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(
                modifier = Modifier
                    .size(105.dp, 15.dp)
            )
            Text(
                text = bill.toDouble().div(100.0).times(value.toInt()).toString(),
                style = MaterialTheme.typography.subtitle1
            )
        }
        Text(
            text = "${value.toInt()}%",
            style = MaterialTheme.typography.subtitle1
        )
        Slider(
            modifier = Modifier
                .padding(15.dp, 0.dp),
            value = value,
            valueRange = 0F..100F,
            onValueChange = {
                getValue(it)
            },
            steps = 101
        )
    }
}