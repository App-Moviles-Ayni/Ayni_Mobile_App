package com.curidev.ayni.feature_payment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.shared.ui.topappbar.PrevNextTopAppBar

@Composable
fun PaymentSummaryScreen(
    id: Int,
    navController: NavController,
    navigateToPaymentMethod: (Int, Int) -> Unit
) {
    val sale = remember { mutableStateOf<Sale?>(null) }
    val quantity = remember { mutableStateOf("") }
    val quantityError = remember { mutableStateOf(false) }

    SaleRepository().getSaleById(id) { retrievedProduct ->
        sale.value = retrievedProduct
    }

    sale.value?.let {
        Scaffold(
            topBar = { PrevNextTopAppBar("Checkout", navController) },
            bottomBar = {
                BottomProceedComponent(
                    sale = sale.value!!,
                    quantity = quantity.value,
                    quantityError = quantityError.value,
                    navigateTo = navigateToPaymentMethod
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(25.dp, 0.dp)
            ) {
                OrderDetailsItem(
                    sale = sale.value!!,
                    quantity = quantity,
                    quantityError = quantityError
                )
            }
        }
    }
}

@Composable
fun OrderDetailsItem(
    sale: Sale,
    quantity: MutableState<String>,
    quantityError: MutableState<Boolean>
) {
    Text(
        text = "Orders",
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        modifier = Modifier.paddingFromBaseline(0.dp, 40.dp)
    )

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order Details",
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(0.dp, 20.dp)
                .fillMaxWidth()
        ) {
            ProductDetails(
                item = sale,
                quantity = quantity,
                quantityError = quantityError
            )
            DeliveryMethod()
        }
    }
}

@Composable
fun ProductDetails(
    item: Sale,
    quantity: MutableState<String>,
    quantityError: MutableState<Boolean>
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 15.dp)
        ) {
            Text(text = item.name)
            Divider(modifier = Modifier.padding(0.dp, 20.dp))
            Text(text = "Description: ${item.description}")
            Divider(modifier = Modifier.padding(0.dp, 10.dp))
            Text(text = "Available Quantity: ${item.quantity} kg")
            Text(text = "Unit Price(Kg): S/ ${item.unitPrice}")

            OutlinedTextField(
                value = quantity.value,
                onValueChange = {
                    quantity.value = it
                    val qty = it.toDoubleOrNull()
                    quantityError.value = qty == null || qty > item.quantity || qty <= 0
                },
                label = { Text("Quantity") },
                isError = quantityError.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            if (quantityError.value) {
                Text(
                    text = "Invalid quantity",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun DeliveryMethod() {
    val typesOfDelivery = listOf("Door Delivery", "Pick Up")
    val selected = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.padding(0.dp, 20.dp, 0.dp)) {
        Text(
            text = "Delivery method",
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )

        Card {
            typesOfDelivery.forEach { deliveryMethod ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selected.contains(deliveryMethod),
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selected.add(deliveryMethod)
                            } else {
                                selected.remove(deliveryMethod)
                            }
                        }
                    )
                    Text(text = deliveryMethod)
                }
            }
        }
    }
}

@Composable
fun BottomProceedComponent(
    sale: Sale,
    quantity: String,
    quantityError: Boolean,
    navigateTo: (Int, Int) -> Unit
) {
    val total = quantity.toDoubleOrNull()?.let { it * sale.unitPrice } ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Total", fontSize = 20.sp)
            Text(text = "S/ ${"%.2f".format(total)}", fontSize = 20.sp)
        }
        Button(
            onClick = {
                if (!quantityError) {
                    navigateTo(sale.id, quantity.toInt())
                }
            },
            enabled = !quantityError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp)
        ) {
            Text(text = "Proceed to Payment Method")
        }
    }
}

