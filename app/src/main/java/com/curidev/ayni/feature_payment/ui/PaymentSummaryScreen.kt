package com.curidev.ayni.feature_payment.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.feature_payment.domain.Order
import com.curidev.ayni.shared.ui.topappbar.PrevNextTopAppBar


@Composable
fun PaymentSummaryScreen(
    navController: NavController,
    navigateToPaymentMethod: () -> Unit) {
    val order = remember {
        mutableStateOf<Order>(
            Order(
                title = "The Crimson Beauty",
                description = "The Crimson Beauty is a stunning flowering plant known for its vibrant red flowers and glossy green leaves",
                quantity = "10000",
                unitPrice = "23"
            )
        )
    }
    Scaffold(
        topBar = {
            PrevNextTopAppBar("Checkout", navController)
        },
        bottomBar = { BottomProceedComponent(order, navigateToPaymentMethod) }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(25.dp, 0.dp)
        ) {

            OrderDetailsItem(order)
        }
    }
}

@Composable
fun OrderDetailsItem(order: MutableState<Order>) {
    Text(
        text = "Orders",
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
                Text(text = "Order Details", fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,)
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Edit")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(0.dp, 20.dp)
                .fillMaxWidth()
        ) {
            ProductDetails(item = order.value)
            DeliveryMethod()
        }
    }
}

@Composable
fun ProductDetails(item: Order) {
    Card (
        modifier = Modifier.fillMaxWidth()
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 15.dp)
        ) {
            Text(
                text = item.title,
            )
            Divider(modifier = Modifier.padding(0.dp, 20.dp))
            Text(
                text = "Description: ${ item.description }",
            )
            Divider(modifier = Modifier.padding(0.dp, 10.dp))
            Text(text = "Quantity: ${ item.quantity } kg")
            Text(text = "Unit Price(Kg): ${ item.unitPrice }$")
        }
    }
}
@Composable
fun DeliveryMethod() {
    val typesOfDelivery = listOf(
        "Door Delivery",
        "Pick Up"
    )
    val selected = remember {
        mutableStateListOf<String>()
    }
    Column (
        modifier = Modifier
            .padding(0.dp, 20.dp, 0.dp)
    ) {
        Text(text = "Delivery method",
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp)

        Card {
            typesOfDelivery.forEach() {it
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selected.contains(it),
                        onCheckedChange = {isChecked ->
                            if(isChecked) { selected.add(it) }
                            else { selected.remove(it) }
                        })
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun BottomProceedComponent(order: MutableState<Order>, navigateTo: () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            val item = order.value
            Text(text = "Total", fontSize = 20.sp)
            Text(text = "${ item.quantity.toDouble() * item.unitPrice.toDouble() }", fontSize = 20.sp)
        }
        Button(onClick = { navigateTo() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp)) {
            Text(text = "Proceed to Payment Method")
        }
    }

}