package com.curidev.ayni.ui.detailpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.feature_product.data.repository.ProductRepository
import com.curidev.ayni.feature_product.domain.model.Product
import com.curidev.ayni.shared.filtertopappbar.MarketTopAppBar
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailPage(navController: NavController, id: Int){
    val product = remember {
        mutableStateOf<Product?>(null)
    }

    ProductRepository().getProductById(id) { retrievedProduct ->
        product.value = retrievedProduct
    }

    product.value?.let {
            Scaffold(
                topBar = {
                    MarketTopAppBar("Market")
                }
            ) { paddingValues ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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

    Card(modifier = Modifier.padding(4.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ProductImage(product.imageUrl)
            Text(
                "Plant Details",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Distance: $recommendedCultivationDistance",
            )
            Text(
                text = "Depth: $recommendedCultivationDepth",
            )
            Text(
                text = "Weather: $recommendedGrowingClimate",
            )
            Text(
                text = "Soil: $recommendedSoilType",
            )
            Text(
                text = "Season: $recommendedGrowingSeason",
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
            )
        }
    }
}

@Composable
fun ProductImage(imageUrl: String) {
    GlideImage(
        imageModel = { imageUrl },
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    )
}