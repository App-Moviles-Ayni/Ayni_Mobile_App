package com.curidev.ayni.feature_payment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.feature_order.domain.model.Order
import com.curidev.ayni.shared.ui.topappbar.PrevNextTopAppBar

@Composable
fun PaymentMethodScreen(
    id: Int,
    quantity: Int,
    navController: NavController,
    navigateToMastercardMethod: (Int, Int) -> Unit,
    navigateToVisaMethod: (Int, Int) -> Unit,
) {
    Scaffold(
        topBar = {
            PrevNextTopAppBar("Checkout", navController)
        },
        //bottomBar = { BottomProceedComponent() }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(25.dp, 0.dp)
        ) {
            CreditCardComponent(id, quantity, navigateToMastercardMethod, navigateToVisaMethod)
        }
    }
}

@Composable
fun CreditCardComponent(id: Int, quantity: Int, toMastercardMethod: (Int, Int) -> Unit, toVisaMethod: (Int, Int) -> Unit) {
    Text(
        text = "Payment Method",
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        modifier = Modifier.paddingFromBaseline(0.dp, 40.dp))
    Scaffold (
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Debit/Credit Card", fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(0.dp, 20.dp)
                .fillMaxWidth()
        ) {
            OutlinedButton(onClick = { toMastercardMethod(id, quantity) }) {
                Text(text = "MASTERCARD", textAlign = TextAlign.Center, modifier = Modifier.size(300.dp, 20.dp))
            }
            Divider(modifier = Modifier.padding(0.dp, 20.dp))
            OutlinedButton(onClick = { toVisaMethod(id, quantity) }) {
                Text(text = "VISA", textAlign = TextAlign.Center, modifier = Modifier.size(300.dp, 20.dp))
            }
        }
    }
}

@Composable
fun BottomProceedComponent() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp)) {
            Text(text = "Proceed to Payment")
        }
    }

}