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
import com.curidev.ayni.feature_product.data.repository.ProductRepository
import com.curidev.ayni.feature_product.domain.model.Product
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.ui.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage

val myGreenColor = Color(0xFF3EAF2C)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    navigateToPayment: () -> Unit,
    id: Int,
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit
){
    val product = remember {
        mutableStateOf<Product?>(null)
    }

    ProductRepository().getProductById(id) { retrievedProduct ->
        product.value = retrievedProduct
    }

    product.value?.let {
        Scaffold(
            topBar = {
                FilterTopAppBar("Market", navController)
            },
            bottomBar = {
                BottomNavigationBar(navigateToHome,navigateToProducts,navigateToOrders,navigateToReviews)
            },
            floatingActionButton = {
                OrderButton(navigateToPayment)
            }
        ) { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchF()
                ProductImage(product.value!!.imageUrl)
                PlantDescription(product.value!!)
            }
        }
    }
}

@Composable
fun PlantDescription(product: Product){

    val recommendedCultivationDistance = product.recommendedCultivationDistance
    val recommendedCultivationDepth = product.recommendedCultivationDepth
    val recommendedGrowingClimate = product.recommendedGrowingClimate
    val recommendedSoilType = product.recommendedSoilType
    val recommendedGrowingSeason = product.recommendedGrowingSeason
    val description = product.description

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            "Plant Details",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Distance: $recommendedCultivationDistance",
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "Depth: $recommendedCultivationDepth",
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Plant Description",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Description: $description",
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
fun OrderButton(navigateToPayment: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { navigateToPayment() },
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp)),
        containerColor = Color(0xFF3EAF2C),
        text = {
            Text(
                text = "Order",
                style = TextStyle(color = Color.White, fontWeight = FontWeight.Light)
            ) },
        icon = { Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White) },
    )
}
