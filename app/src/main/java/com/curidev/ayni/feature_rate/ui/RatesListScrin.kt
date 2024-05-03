package com.curidev.ayni.feature_rate.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.curidev.ayni.feature_product.data.repository.ProductRepository
import com.curidev.ayni.feature_product.domain.model.Rate
import com.curidev.ayni.feature_rate.data.repository.RateRepository
import com.curidev.ayni.order.data.repository.SaleRepository
import com.curidev.ayni.order.domain.model.Sale
import com.curidev.ayni.order.ui.ordersscreen.SearchField
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RatesListScrin(
    navController: NavController,
    selectSale: (Int) -> Unit,
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit
) {
    Scaffold(
        topBar = {
            FilterTopAppBar("Reviews", navController)
        },
        bottomBar = {
            BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders,navigateToReviews)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)){
            SearchField()
            RatesList(selectSale = selectSale)
        }
    }
}

@Composable
fun RatesList(saleRepository: SaleRepository = SaleRepository(), rateRepository: RateRepository = RateRepository(), selectSale: (Int) -> Unit) {
    val sales = remember {
        mutableStateOf(emptyList<Sale>())
    }

    val rates = remember {
        mutableStateOf(emptyList<Rate>())
    }

    saleRepository.getAll {
        sales.value = it
    }

    rateRepository.getAll {
        rates.value = it
    }

    LazyColumn {
        items(sales.value) {sale ->
            val associatedRates = rates.value.filter { it.productId == sale.id }
            val averageRate = if (associatedRates.isNotEmpty()) {
                associatedRates.map { it.rate }.average().toInt()
            } else {
                0
            }
            RateItem(sale, averageRate, selectSale)
        }
    }
}

@Composable
fun RateItem(sale: Sale, averageRate: Int, selectSale: (Int) -> Unit) {
    ListItem(
        modifier = Modifier.clickable {
            selectSale(sale.id)
        },
        leadingContent = {
            GlideImage(
                imageModel = { sale.imageUrl },
                modifier = Modifier.size(80.dp))
        },
        headlineContent = { Text("${sale.name}") },
        trailingContent = {
            Column{
                Text(text = ""/*"${rate.date}"*/)
                Spacer(modifier = Modifier.padding(5.dp))
                Row {
                    for (i in 1..5) {
                        if (i <= averageRate) {
                            Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color(0xFF3EAF2C))
                        } else {
                            Icon(Icons.Outlined.Star, contentDescription = "Star", tint = Color(0xFF000000))
                        }
                    }
                }
            }
        },
    )
}
