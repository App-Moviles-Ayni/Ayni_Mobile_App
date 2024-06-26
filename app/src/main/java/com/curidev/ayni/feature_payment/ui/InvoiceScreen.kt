package com.curidev.ayni.feature_payment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.feature_order.data.repository.OrderRepository
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.feature_order.domain.model.Order
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.shared.ui.topappbar.PrevNextTopAppBar

@Composable
fun InvoiceScreen(
    id: Int,
    navController: NavController,
    navigateToProducts: () -> Unit)
{
    val order = remember {
        mutableStateOf<Order?>(null)
    }

    OrderRepository().getOrderById(id){
        order.value = it
    }

    order.value?.let {
        Scaffold(
            topBar = {
                PrevNextTopAppBar("Checkout", navController = navController)
            },
            bottomBar = { ExitComponent(navigateToProducts) }
        ) { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .padding(25.dp, 0.dp)
            ) {
                InvoiceComponent(order.value!!)
            }
        }
    }
}

@Composable
fun InvoiceComponent(order: Order) {
    val sale = remember {
        mutableStateOf<Sale?>(null)
    }

    SaleRepository().getSaleById(order.saleId) {
        sale.value = it
    }

    sale.value?.let {
        Text(
            text = "Invoice",
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            modifier = Modifier.paddingFromBaseline(0.dp, 40.dp))
        Scaffold(
            topBar = {
                Text(text = order.orderedDate)
            }
        ) { paddingValues ->
            Card(modifier = Modifier
                .padding(paddingValues)
                .padding()) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 15.dp, 0.dp, 0.dp)
                ) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = "favorite")
                    Text(text = "Transaction succeed")
                }
                Divider(modifier = Modifier.padding(15.dp))
                Text(text = "Product", modifier = Modifier.padding(10.dp, 5.dp))
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 60.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = sale.value!!.name)
                    Text(text = order.totalPrice.toString())
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Invoice id")
                    Text(text = order.id.toString())
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Payment Method")
                    Text(text = order.paymentMethod)
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total", fontSize = 20.sp)
                    Text(text = order.totalPrice.toString(), fontSize = 20.sp)
                }
            }
        }
    }

}

@Composable
fun ExitComponent(navigateToProducts: () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = { navigateToProducts() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp)) {
            Text(text = "See more products")
        }
    }

}