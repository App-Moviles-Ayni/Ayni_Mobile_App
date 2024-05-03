package com.curidev.ayni.feature_rate.ui.ratedetailsfloatingcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.curidev.ayni.feature_product.domain.model.Rate
import com.curidev.ayni.feature_rate.data.repository.RateRepository
import com.curidev.ayni.order.data.repository.SaleRepository
import com.curidev.ayni.order.domain.model.Sale
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RateDetailsFloatingCard(
    navController: NavController,
    id: Int,
    saleRepository: SaleRepository = SaleRepository(),
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit
    ) {
    val sale = remember {
        mutableStateOf<Sale?>(null)
    }

    saleRepository.getSaleById(id) {
        sale.value = it
    }

    sale.value?.let { sale ->
        Scaffold(
            topBar = {
                FilterTopAppBar("Review", navController)
            },
            bottomBar = {
                BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders, navigateToReviews)
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 30.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SaleImage(sale.imageUrl)
                        RatesOfSale(id, sale)
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
        modifier = Modifier.size(180.dp)
    )
}

@Composable
fun RatesOfSale(id: Int, sale: Sale, rateRepository: RateRepository = RateRepository()) {
    val rates = remember {
        mutableStateOf(emptyList<Rate>())
    }

    rateRepository.getAll {
        rates.value = it
    }

    LazyColumn {
        val filteredRates = rates.value.filter { it.productId == sale.id }
        items(filteredRates) { rate ->
            RateItem(rate, sale)
        }
    }
}

@Composable
fun RateItem(rate: Rate, sale: Sale) {
    ListItem(
        headlineContent = { Text(text = "User") },
        supportingContent = {
            val stars = buildString {
                Row {
                    for (i in 1..5) {
                        if (i <= rate.rate) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = "Star",
                                tint = Color(0xFF3EAF2C)
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Star,
                                contentDescription = "Star",
                                tint = Color(0xFF000000)
                            )
                        }
                    }
                }
            }
            Text(text = "description\n$stars")
                            },
        /*leadingContent = {
            GlideImage(
                imageModel = { sale.imageUrl },
                modifier = Modifier.size(50.dp)
            )
        }*/
    )
}