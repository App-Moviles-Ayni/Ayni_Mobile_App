package com.curidev.ayni.feature_payment.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curidev.ayni.shared.ui.inputtextfield.InputTextField_Payment
import com.curidev.ayni.shared.ui.topappbar.PrevNextTopAppBar

@Composable
fun PaymentMastercardMethodScreen(navigateToInvoice: () -> Unit) {
    Scaffold(
        topBar = {
            PrevNextTopAppBar("Checkout")
        },
        bottomBar = { BottomConfirmComponent(navigateToInvoice) }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(25.dp, 0.dp)
        ) {
            MastercardFormComponent()
        }
    }
}


@Composable
fun MastercardFormComponent() {
    var cardNumber = remember { mutableStateOf("") }
    var expirationDate = remember { mutableStateOf("") }
    var cvv = remember { mutableStateOf("") }
    var name = remember { mutableStateOf("") }
    var address = remember { mutableStateOf("") }
    var country = remember { mutableStateOf("") }
    var city = remember { mutableStateOf("") }
    var postalCode = remember { mutableStateOf("") }

    Text(
        text = "Payment Method Visa",
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        modifier = Modifier.paddingFromBaseline(0.dp, 40.dp))
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Mastercard", textAlign = TextAlign.Center)
        InputTextField_Payment(input = cardNumber, placeholder = "Card Number")
        Divider(modifier = Modifier.padding(5.dp))
        Row {
            InputTextField_Payment(input = expirationDate, placeholder = "Expiration Date", modifier = Modifier.width(200.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            InputTextField_Payment(input = cvv, placeholder = "CVV", modifier = Modifier.width(150.dp))
        }
        Divider(modifier = Modifier.padding(5.dp))
        InputTextField_Payment(input = name, placeholder = "Name")
        Divider(modifier = Modifier.padding(5.dp))
        InputTextField_Payment(input = address, placeholder = "Address")
        Divider(modifier = Modifier.padding(5.dp))
        InputTextField_Payment(input = country, placeholder = "Country")
        Divider(modifier = Modifier.padding(5.dp))
        InputTextField_Payment(input = city, placeholder = "City")
        Divider(modifier = Modifier.padding(5.dp))
        InputTextField_Payment(input = postalCode, placeholder = "Postal Code")
    }
}
