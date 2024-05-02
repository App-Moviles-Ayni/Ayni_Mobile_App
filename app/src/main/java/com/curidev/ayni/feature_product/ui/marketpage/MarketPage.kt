package com.curidev.ayni.feature_product.ui.marketpage

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curidev.ayni.feature_product.data.repository.ProductRepository
import com.curidev.ayni.feature_product.domain.model.Product
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.MarketTopAppBar
import com.skydoves.landscapist.glide.GlideImage
import java.util.Timer
import java.util.TimerTask

@Composable
fun MarketPage(
    selectProduct: (Int) -> Unit,
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit
) {
    Scaffold(
        topBar = {
            MarketTopAppBar("Market")
        },
        bottomBar = {
            BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)){
            Spacer(modifier = Modifier.height(16.dp))
            Search()
            Text(
                text = "Hot Deals",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 25.sp,
                modifier = Modifier.padding(16.dp)
            )
            ProductDeals(selectProduct = selectProduct)
            Text(
                text = "Products",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 25.sp,
                modifier = Modifier.padding(16.dp)
            )
            ProductsTotal(selectProduct = selectProduct)
        }
    }
}

@Composable
fun Search() {

    var searchtext by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        placeholder = { Text("Search") },
        value = searchtext,
        onValueChange = { newValue -> searchtext = newValue },
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}

@Composable
fun ProductDeals(productRepository: ProductRepository = ProductRepository(), selectProduct: (Int) -> Unit){
    val products = remember {
        mutableStateOf(emptyList<Product>())
    }

    LaunchedEffect(true) {
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                try {
                    productRepository.getAll { productDeals ->
                        products.value = productDeals.shuffled()
                    }
                } catch (e: Exception) {
                    Log.e("ProductsDeals", "Error al obtener la lista de productos: ${e.message}")
                }
            }
        }
        timer.scheduleAtFixedRate(timerTask, 0, 5000)
    }

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        products.value.take(3).forEach { product ->
            ProductHot(product, selectProduct)
        }
    }
}

@Composable
fun ProductHot(product: Product, selectProduct: (Int) -> Unit){
    val name = product.name
    val recommendedCultivationDistance = product.recommendedCultivationDistance
    val recommendedCultivationDepth = product.recommendedCultivationDepth

    Column(
        modifier = Modifier
            .clickable(onClick = { /*TODO*/ })
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = { product.imageUrl },
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$name",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "$recommendedCultivationDistance",
            textAlign = TextAlign.Center
        )
        Text(
            text = "$recommendedCultivationDepth",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProductsTotal(productRepository: ProductRepository = ProductRepository(), selectProduct: (Int) -> Unit){
    val products = remember {
        mutableStateOf(emptyList<Product>())
    }

    productRepository.getAll {
        products.value = it
    }

    try {
        productRepository.getAll { productTotal ->
            products.value = productTotal
        }
    } catch (e: Exception) {
        Log.e("ProductsList", "Error al obtener la lista de productos: ${e.message}")
    }

    LazyColumn {
        itemsIndexed(products.value.chunked(3)) { index, chunkedProducts ->
            if (index > 0) {
                Spacer(modifier = Modifier.height(16.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                chunkedProducts.forEach { product ->
                    ProductOne(product, selectProduct)
                }
            }
        }
    }
}

@Composable
fun ProductOne(product: Product, selectProduct: (Int) -> Unit) {

    val name = product.name
    val recommendedCultivationDistance = product.recommendedCultivationDistance
    val recommendedCultivationDepth = product.recommendedCultivationDepth



    Column(
        modifier = Modifier
            .clickable(onClick = { selectProduct(product.id) })
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = { product.imageUrl },
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$name",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "$recommendedCultivationDistance",
            textAlign = TextAlign.Center
        )
        Text(
            text = "$recommendedCultivationDepth",
            textAlign = TextAlign.Center
        )
    }
}