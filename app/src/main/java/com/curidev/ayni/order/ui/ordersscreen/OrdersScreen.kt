package com.curidev.ayni.order.ui.ordersscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.curidev.ayni.order.domain.model.Order
import com.curidev.ayni.order.repository.OrderRepository
import com.curidev.ayni.shared.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun OrdersScreen(selectOrder: (Int) -> Unit) {
    Scaffold(
        topBar = {
            FilterTopAppBar("Orders")
        },
        bottomBar = { BottomNavigationBar() }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)){
            SearchField()
            OrdersList(selectOrder = selectOrder)
        }
    }
}

@Composable
fun SearchField() {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        placeholder = { Text("Search") },
        value = "", onValueChange = {},
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        })
}

@Composable
fun OrdersList(orderRepository: OrderRepository = OrderRepository(), selectOrder: (Int) -> Unit) {
    val orders = remember {
        mutableStateOf(emptyList<Order>())
    }

    orderRepository.getAll {
        orders.value = it
    }

    LazyColumn {
        items(orders.value) {order ->
            OrderItem(order, selectOrder)
        }
    }
}

@Composable
fun OrderItem(order: Order, selectOrder: (Int) -> Unit) {
    val quantity = order.quantity
    val status = order.status
    val id = order.id
    val url = "https://cdn.donmai.us/original/cd/30/cd3038a1e4953a43c0e3620d953cdb2a.jpg"
    ListItem(
        modifier = Modifier.clickable(onClick = {
            selectOrder(order.id)
        }),
        headlineContent = { Text(text = "Nombre planta") },
        supportingContent = {
            Text(text = "Quantity: $quantity\nStatus: $status")},
        leadingContent = {
            GlideImage(
                imageModel = { url },
                modifier = Modifier.size(80.dp)) },
        trailingContent = { Text(text = "9 months ago")}
    )
}