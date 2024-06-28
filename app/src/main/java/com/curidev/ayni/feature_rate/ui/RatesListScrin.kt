package com.curidev.ayni.feature_rate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.curidev.ayni.R
import com.curidev.ayni.feature_auth.data.repository.AuthRepository
import com.curidev.ayni.feature_auth.data.repository.UserRepository
import com.curidev.ayni.feature_auth.domain.model.User
import com.curidev.ayni.feature_order.data.repository.OrderRepository
import com.curidev.ayni.feature_product.domain.model.Rate
import com.curidev.ayni.feature_rate.data.repository.RateRepository
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.feature_order.domain.model.Order
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RatesListScrin(
    navController: NavController,
    selectOrder: (Int) -> Unit,
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit
) {
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
                FilterTopAppBar("Reviews", navController)
            },
            bottomBar = {
                BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders,navigateToReviews, 3)
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
                    RatesList(it.id, selectOrder = selectOrder)
                }
            }

        }
    }
}

@Composable
fun RatesList(userId: String, orderRepository: OrderRepository = OrderRepository(), rateRepository: RateRepository = RateRepository(), selectOrder: (Int) -> Unit) {
    val orders = remember {
        mutableStateOf(emptyList<Order>())
    }

    val rates = remember {
        mutableStateOf(emptyList<Rate>())
    }

    orderRepository.getAll {
        orders.value = it
    }

    rateRepository.getAll {
        rates.value = it
    }

    LazyColumn {
        items(orders.value) {order ->
            if(order.orderedBy == userId.toInt() && order.status == "finalized") {
                val associatedRates = rates.value.filter { it.productId == order.id }
                val averageRate = if (associatedRates.isNotEmpty()) {
                    associatedRates.map { it.rate }.average().toInt()
                } else {
                    0
                }
                RateItem(order, averageRate, selectOrder)
            }
        }
    }
}

@Composable
fun RateItem(order: Order, averageRate: Int, selectOrder: (Int) -> Unit) {

    val sale = remember {
        mutableStateOf<Sale?>(null)
    }

    val saleRepository = SaleRepository()

    saleRepository.getSaleById(order.saleId) { retrievedSale ->
        sale.value = retrievedSale
    }

    sale.value?.let {
        ListItem(
            modifier = Modifier.clickable {
                selectOrder(order.id)
            },
            leadingContent = {
                GlideImage(
                    imageModel = { it.imageUrl },
                    modifier = Modifier.size(80.dp))
            },
            headlineContent = { Text("${it.name}") },
            trailingContent = {
                Column{
                    Text(text = "Qualify your order")
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row {
                        for (i in 1..5) {
                            if (i <= averageRate) {
                                Icon(Icons.Outlined.Star, contentDescription = "Star", tint = Color(0xFF3EAF2C))
                            } else {
                                Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color(0xFF000000))
                            }
                        }
                    }
                }
            },
        )
    }
}
