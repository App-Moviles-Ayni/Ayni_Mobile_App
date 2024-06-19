package com.curidev.ayni.ui.mainmenu

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curidev.ayni.feature_auth.data.repository.AuthRepository
import com.curidev.ayni.feature_auth.data.repository.UserRepository
import com.curidev.ayni.feature_auth.domain.model.User
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.ProductTopAppBar
import com.skydoves.landscapist.glide.GlideImage
import java.util.Timer
import java.util.TimerTask

val myGreenColor = Color(0xFF3EAF2C)

@Composable
fun MainMenuScreen(
    selectProduct: (Int) -> Unit,
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
                ProductTopAppBar("" )
            },
            bottomBar = {
                BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders,navigateToReviews)
            }
        ) {  paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)){
                Spacer(modifier = Modifier.width(8.dp))
                UserProduct(userName = it.username)
                Spacer(modifier = Modifier.width(8.dp))
                SearchField()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Latest Purchases",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "See All ->",
                        color = Color.Red,
                        modifier = Modifier.clickable { /*TODO*/ }
                    )
                }
                ProductsLatest(selectProduct = selectProduct)
                Text(
                    text = "Products In Stock",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )
                ProductsList(selectProduct = selectProduct)
            }
        }
    }
}

@Composable
fun ProductsLatest(saleRepository: SaleRepository = SaleRepository(), selectProduct: (Int) -> Unit) {
    val products = remember {
        mutableStateOf(emptyList<Sale>())
    }

    saleRepository.getAll {
        products.value = it
    }

    LaunchedEffect(true) {
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                try {
                    saleRepository.getAll { productDeals ->
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
            ProductPurchase(product, selectProduct)
        }
    }
}

@Composable
fun ProductPurchase(sale: Sale, selectProduct: (Int) -> Unit){
    Card(
        modifier = Modifier
            .padding(end = 16.dp)
            .clickable(onClick = { selectProduct(sale.id) })
            .width(110.dp)
            .height(140.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box {
            GlideImage(
                imageModel = { sale.imageUrl },
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    //.background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black), 0f, 100f))
                    .padding(16.dp)
            ) {
                Text(
                    text = sale.name,
                    fontStyle = FontStyle.Normal,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun UserProduct(userName: String){
    Text(
        text = "Welcome, $userName",
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        fontSize = 30.sp,
        modifier = Modifier.padding(bottom = 16.dp),
        color = Color.Black
    )
}


@Composable
fun SearchField(){

    var searchtext by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxWidth()
            .background(myGreenColor, RoundedCornerShape(10))
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            text = "Find your Product",
            style = LocalTextStyle.current.copy(color = Color.White),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(9.dp))
        TextField(
            value = searchtext,
            onValueChange = { newValue -> searchtext = newValue },
            placeholder = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20),
            textStyle = LocalTextStyle.current.copy(color = Color.Black)
        )
    }
}

@Composable
fun ProductsList(saleRepository: SaleRepository = SaleRepository(), selectProduct: (Int) -> Unit) {
    val products = remember {
        mutableStateOf(emptyList<Sale>())
    }

    saleRepository.getAll {
        products.value = it
    }

    try {
        saleRepository.getAll { productList ->
            products.value = productList
        }
    } catch (e: Exception) {
        Log.e("ProductsList", "Error al obtener la lista de productos: ${e.message}")
    }

    LazyColumn {
        items(products.value) { product ->
            ProductItem(product, selectProduct)
        }
    }
}

@Composable
fun ProductItem(sale: Sale, selectProduct: (Int) -> Unit) {
    val name = sale.name
    val description = if (sale.description.length > 50) {
        "${sale.description.substring(0, 50)}..."
    } else {
        sale.description
    }

    Row(
        modifier = Modifier
            .clickable(onClick = { selectProduct(sale.id) })
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "$name", fontWeight = FontWeight.Bold)
            Text(text = "Description: $description")
        }

        GlideImage(
            imageModel = { sale.imageUrl },
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
    }
}