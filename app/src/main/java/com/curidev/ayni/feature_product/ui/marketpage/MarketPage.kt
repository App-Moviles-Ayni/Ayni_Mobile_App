package com.curidev.ayni.feature_product.ui.marketpage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.R
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage
import java.util.Timer
import java.util.TimerTask

@Composable
fun MarketPage(
    navController: NavController,
    selectProduct: (Int) -> Unit,
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit,
    saleRepository: SaleRepository = SaleRepository()
) {
    val sales = remember {
        mutableStateOf(emptyList<Sale>())
    }
    val hotDeals = remember {
        mutableStateOf(emptyList<Sale>())
    }

    fun load() {
        saleRepository.getAll {
            sales.value = it
            hotDeals.value = it
        }
    }

    fun searchSales(query: String) {
        saleRepository.getSaleByName(query) {
            sales.value = it
        }
    }

    fun shuffleSales() {
        if (hotDeals.value.size > 3) {
            hotDeals.value = hotDeals.value.shuffled().take(4)
        }
    }

    LaunchedEffect(Unit) {
        load()
    }

    Scaffold(
        topBar = {
            FilterTopAppBar("Market", navController)
        },
        bottomBar = {
            BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders,navigateToReviews, 1)
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
                Spacer(modifier = Modifier.height(16.dp))
                Search(onSubmit = {
                    searchSales(it)
                })
                Text(
                    text = "Hot Deals",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 25.sp,
                    modifier = Modifier.padding(16.dp)
                )
                ProductDeals(
                    { shuffleSales() },
                    hotDeals,
                    selectProduct = selectProduct
                )
                Text(
                    text = "Products",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 25.sp,
                    modifier = Modifier.padding(16.dp)
                )
                ProductsTotal(sales, selectProduct = selectProduct)
            }
        }

    }
}

@Composable
fun Search(onSubmit: (String) -> Unit) {
    var searchtext by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        placeholder = { Text("Search") },
        value = searchtext,
        onValueChange = { searchtext = it },
        trailingIcon = {
            IconButton(onClick = {
                onSubmit(searchtext)
            }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSubmit(searchtext)
            }
        )
    )
}

@Composable
fun ProductDeals(
    shuffleProducts: () -> Unit,
    products: State<List<Sale>>,
    selectProduct: (Int) -> Unit){


    LaunchedEffect(true) {
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                try {
                    shuffleProducts()
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
fun ProductHot(sale: Sale, selectProduct: (Int) -> Unit){
    val name = sale.name

    Column(
        modifier = Modifier
            .clickable(onClick = { selectProduct(sale.id) })
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = { sale.imageUrl },
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
            text = "S/ ${sale.unitPrice}",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Stock: ${sale.quantity}",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProductsTotal(products: State<List<Sale>>, selectProduct: (Int) -> Unit){

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
    if (products.value.isEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nothing found ☹️",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ProductOne(sale: Sale, selectProduct: (Int) -> Unit) {

    val name = sale.name

    Column(
        modifier = Modifier
            .clickable(onClick = { selectProduct(sale.id) })
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = { sale.imageUrl },
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
            text = "S/ ${sale.unitPrice}",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Stock: ${sale.quantity}",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}