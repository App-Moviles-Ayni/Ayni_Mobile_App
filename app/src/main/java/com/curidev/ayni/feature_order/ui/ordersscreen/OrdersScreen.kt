package com.curidev.ayni.feature_order.ui.ordersscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.curidev.ayni.R
import com.curidev.ayni.feature_auth.data.repository.AuthRepository
import com.curidev.ayni.feature_auth.data.repository.UserRepository
import com.curidev.ayni.feature_auth.domain.model.User
import com.curidev.ayni.feature_order.domain.model.Order
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.feature_order.data.repository.OrderRepository
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun OrdersScreen(navController: NavController,
                 selectOrder: (Int) -> Unit,
                 navigateToHome: () -> Unit,
                 navigateToProducts: () -> Unit,
                 navigateToOrders: () -> Unit,
                 navigateToReviews: () -> Unit)
{
    val authRepository = AuthRepository()

    val userId = authRepository.getUserId()

    val user = remember {
        mutableStateOf<User?>(null)
    }

    if (userId != null) {
        UserRepository().getUserById(userId.toInt()) { retrievedUser ->
            user.value = retrievedUser
        }
    }

    user.value?.let {
        Scaffold(
            topBar = {
                FilterTopAppBar("Orders", navController)
            },
            bottomBar = {
                BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders, navigateToReviews, 2)
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                        .graphicsLayer(alpha = 0.4f)
                )
                Column(modifier = Modifier.padding(paddingValues)){
                    OrdersList(it.id.toInt(), selectOrder = selectOrder)
                }
            }

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
fun OrdersList(userId: Int, orderRepository: OrderRepository = OrderRepository(), selectOrder: (Int) -> Unit) {
    val orders = remember {
        mutableStateOf(emptyList<Order>())
    }

    orderRepository.getAll {
        orders.value = it
    }

    LazyColumn {
        items(orders.value) {order ->
            if(order.orderedBy == userId) {
                OrderItem(order, selectOrder)
            }
        }
    }
}

@Composable
fun OrderItem(order: Order, selectOrder: (Int) -> Unit) {
    val quantity = order.quantity
    val status = order.status

    val sale = remember {
        mutableStateOf<Sale?>(null)
    }

    SaleRepository().getSaleById(order.saleId) { retrievedSale ->
        sale.value = retrievedSale
    }

    sale.value?.let {
        ListItem(
            modifier = Modifier.clickable(onClick = {
                selectOrder(order.id)
            }),
            headlineContent = { Text(text = "${it.name}") },
            supportingContent = {
                Text(text = "Quantity: $quantity\nStatus: $status")},
            leadingContent = {
                GlideImage(
                    imageModel = { it.imageUrl },
                    modifier = Modifier.size(80.dp)) },
            trailingContent = { Text(text = "${order.orderedDate}")}
        )
    }
}