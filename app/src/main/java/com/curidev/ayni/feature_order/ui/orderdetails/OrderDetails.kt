package com.curidev.ayni.feature_order.ui.orderdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.feature_order.domain.model.Order
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.feature_order.data.repository.OrderRepository
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.PrevTopAppBar
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun OrderDetails(
    navController: NavController,
    id: Int,
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit)
{
    val order = remember {
        mutableStateOf<Order?>(null)
    }

    val sale = remember {
        mutableStateOf<Sale?>(null)
    }

    OrderRepository().getOrderById(id) { retrievedOrder ->
        order.value = retrievedOrder
        SaleRepository().getSaleById(retrievedOrder.saleId) { retrievedSale ->
            sale.value = retrievedSale
        }
    }

    order.value?.let {
        sale.value?.let {
            Scaffold(
                topBar = {
                    PrevTopAppBar("Order Details", navController)
                },
                bottomBar = {
                    BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders,navigateToReviews)
                }
            ) { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    OrderCard(order.value!!, sale.value!!)
                    OrderDescription(order.value!!)
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Order, sale: Sale) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Column(modifier = Modifier.padding(10.dp), ) {
            OrderImage(sale.imageUrl)
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "Order details of ${sale.name} ",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold))
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                Text(modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Normal),
                    text = "Quantity")
                Text(text = "${order.quantity} kg")
            }
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = Color(0xFF000000))
            Spacer(modifier = Modifier.padding(3.dp))
            Row {
                Text(modifier = Modifier.weight(1f), text = "Status",
                    style = TextStyle(fontWeight = FontWeight.Normal))
                Text(text = "${order.status} ", style = TextStyle(fontWeight = FontWeight.Normal))
            }
        }
    }
}

@Composable
fun OrderDescription(order: Order) {
    var isOpen by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.padding(25.dp)) {
            Text(text = "Description",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold))
            Spacer(modifier = Modifier.padding(5.dp))
            if(order.description.isEmpty())
                Text(text = "No description available",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal))
            else {
                Text(
                    text = order.description,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                Text(modifier = Modifier.weight(1f), text = "Date",
                    style = TextStyle(fontWeight = FontWeight.Normal))
                Text(modifier = Modifier.padding(5.dp), text = "25 Nov 2024",
                    style = TextStyle(fontWeight = FontWeight.Normal))
            }
            Button(
                modifier = Modifier
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                onClick = { isOpen = true }) {
                Text(text = "Cancel Order", style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold))
            }
        }
    }

    if (isOpen) {
        CancelBottomSheet(onDismissRequest = { isOpen = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelBottomSheet(onDismissRequest: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(36.dp),
        ) {
            Column {
                Text(text = "Are you sure you want to cancel the order?",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "When you press the button, the operation will be cancelled",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
                    textAlign = TextAlign.Center)
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF3EAF2C)),
                    onClick = { onDismissRequest() }) {
                    Text(text = "No",
                        style = TextStyle(color = Color.White, fontWeight = FontWeight.Light))
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    onClick = { onDismissRequest() }) {
                    Text(text = "Yes",
                        style = TextStyle(color = Color.White, fontWeight = FontWeight.Light))
                }
            }

        }
    }
}

@Composable
fun OrderImage(imageUrl: String) {
    //val imageUrl = "https://cdn.donmai.us/original/cd/30/cd3038a1e4953a43c0e3620d953cdb2a.jpg"
    GlideImage(
        imageModel = { imageUrl },
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    )
}
