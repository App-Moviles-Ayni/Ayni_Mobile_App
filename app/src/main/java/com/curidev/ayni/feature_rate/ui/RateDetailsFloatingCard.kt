package com.curidev.ayni.feature_rate.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.curidev.ayni.feature_order.data.repository.OrderRepository
import com.curidev.ayni.feature_product.domain.model.Rate
import com.curidev.ayni.feature_rate.data.repository.RateRepository
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.feature_order.domain.model.Order
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun RateDetailsFloatingCard(
    navController: NavController,
    id: Int,
    orderRepository: OrderRepository = OrderRepository(),
    saleRepository: SaleRepository = SaleRepository(),
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit
    ) {
    val order = remember {
        mutableStateOf<Order?>(null)
    }

    val sale = remember {
        mutableStateOf<Sale?>(null)
    }

    orderRepository.getOrderById(id) {retrievedOrder->
        order.value = retrievedOrder
        saleRepository.getSaleById(retrievedOrder.saleId) {
            sale.value = it
        }
    }

    order.value?.let { order ->
        Scaffold(
            topBar = {
                FilterTopAppBar("Review", navController)
            },
            bottomBar = {
                BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders, navigateToReviews, 3)
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        sale.value?.let {
                            SaleImage(sale.value?.imageUrl)
                            RatesOfSale(id, sale.value!!, order, navController, navigateToHome)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SaleImage(imageUrl: String?) {
    GlideImage(
        imageModel = { imageUrl },
        modifier = Modifier.size(140.dp)
    )
}

@Composable
fun RatesOfSale(
    id: Int,
    sale: Sale,
    order: Order,
    navController: NavController,
    navigateToHome: () -> Unit,
    rateRepository: RateRepository = RateRepository(),
) {
    val rates = remember {
        mutableStateOf(emptyList<Rate>())
    }

    rateRepository.getAll {
        rates.value = it
    }

    LazyColumn {
        val filteredRates = rates.value.filter { it.productId == sale.id }
    }
    RateItem(sale, order, rateRepository, navController, navigateToHome)
}

@Composable
fun RateItem(
    sale: Sale,
    order: Order,
    rateRepository: RateRepository,
    navController: NavController,
    navigateToHome: () -> Unit) {
    var showMessage = remember {
        mutableStateOf(false)
    }

    val currentDateTime = getCurrentDateIso()
    var rating = remember {
        mutableStateOf(0)
    }
    val userId = order.orderedBy;
    val productId = sale.id;

    ListItem(
        headlineContent = { Text(text = "Post your rate") },
        supportingContent = {
            val stars = buildString {
                Column {
                    RatingBar(rating = rating.value) { newRating ->
                        rating.value = newRating
                    }
                    Button(
                        onClick = {
                            val newRate = createRate(
                                currentDateTime,
                                rating.value,
                                userId,
                                productId,
                            )
                            RateRepository().createRate(newRate) { rate ->
                                OrderRepository().qualifyOrder(order.id) { order ->
                                }
                                showMessage.value = true
                                navigateToHome()
                            }
                        }
                    ) {
                        Text(text = "Send")
                    }
                    if (showMessage.value){
                        LaunchedEffect(Unit) {
                            delay(1000)
                            showMessage.value = false
                            navController.popBackStack()
                        }
                        Column {
                            Text("Rate created successfully!")
                        }
                    }
                }
            }
            Text(text = "description\n$stars") },
    )
}

@Composable
fun RatingBar(rating: Int, onRatingChanged: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier.wrapContentWidth()
    ) {
        for (i in 1..5){
            Icon(
                modifier = Modifier
                    .size(28.dp)  // Adjust size as needed
                    .padding(0.dp)
                    .clickable { onRatingChanged(i) },
                imageVector = if (i <= rating) Icons.Filled.Star
                else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (i <= rating) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

fun getCurrentDateIso(): String {
    val date = Date()
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}

fun createRate(currentDateTime: String, rating: Int, userId: Int, productId: Int): Rate {
    return Rate(
        id = 0,
        rate = rating,
        date = currentDateTime,
        productId = productId,
        userId = userId
    )
}