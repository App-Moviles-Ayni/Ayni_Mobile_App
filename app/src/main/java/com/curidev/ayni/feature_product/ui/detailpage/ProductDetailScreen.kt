package com.curidev.ayni.feature_product.ui.detailpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage

val myGreenColor = Color(0xFF3EAF2C)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    navigateToPayment: (Int) -> Unit,
    id: Int,
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit
){
    val sale = remember { mutableStateOf<Sale?>(null) }

    LaunchedEffect(id) {
        SaleRepository().getSaleById(id) { retrievedProduct ->
            sale.value = retrievedProduct
        }
    }

    sale.value?.let {
        Scaffold(
            topBar = {
                FilterTopAppBar("Market", navController)
            },
            bottomBar = {
                BottomNavigationBar(navigateToHome, navigateToProducts, navigateToOrders, navigateToReviews, 1)
            },
            floatingActionButton = {
                OrderButton(sale.value!!, navigateToPayment)
            }
        ) { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchF()
                ProductImage(sale.value!!.imageUrl)
                PlantDescription(sale.value!!)
            }
        }
    }
}

@Composable
fun PlantDescription(sale: Sale){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Sale Description",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = sale.description,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            "Price",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "S/ ${sale.unitPrice} per unit",
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            "Stock",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "${sale.quantity} Kg",
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun SearchF() {
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
fun ProductImage(imageUrl: String) {
    GlideImage(
        imageModel = { imageUrl },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(190.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}

@Composable
fun OrderButton(sale: Sale, navigateToPayment: (Int) -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { navigateToPayment(sale.id) },
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp)),
        containerColor = Color(0xFF3EAF2C),
        text = {
            Text(
                text = "Order Now",
                style = TextStyle(color = Color.White, fontWeight = FontWeight.Light)
            ) },
        icon = { Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White) },
    )
}
