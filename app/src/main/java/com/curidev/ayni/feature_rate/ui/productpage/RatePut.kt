package com.curidev.ayni.ui.productpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.curidev.ayni.feature_product.domain.model.Product
import com.curidev.ayni.feature_product.domain.model.Rate
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProductItemWithRatingAndPrice(
    product: Product,
    rating: Float,
    price: Double,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                //contentDescription = "Imagen del producto ${product.name}",
                imageModel = { product.imageUrl }
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = product.name, style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = product.description)
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (i in 0 until rating.toInt()) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating star",
                            tint = Color.Yellow
                        )
                    }
                    for (i in rating.toInt() until 5) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "Rating star",
                            tint = Color.Gray
                        )
                    }
                }
                //Text(text = "S/ ${price.toString("%.2f")}", style = MaterialTheme.typography.body2)
                /*if (onClick.isNotEmpty()) {
                    Button(onClick = onClick) {
                        Text("Ver detalles")
                    }
                }*/
            }
        }
    }
}

@Composable
fun ProductItemWithRating(product: Product, rating: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                //contentDescription = "Imagen del producto ${product.name}",
                imageModel = { product.imageUrl }
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = product.name, style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = product.description)
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (i in 0 until rating.toInt()) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating star",
                            tint = Color.Yellow
                        )
                    }
                    for (i in rating.toInt() until 5) {
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = "Rating star",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}


val products = listOf(
    Product(
        id = 1,
        name = "Papa",
        description = "Tubérculo comestible con almidón, originario de los Andes peruanos. Es un alimento básico en muchas culturas del mundo.",
        recommendedCultivationDistance = "30 cm",
        recommendedCultivationDepth = "10 cm",
        recommendedGrowingClimate = "Templado",
        recommendedSoilType = "Franco arenoso",
        recommendedGrowingSeason = "Verano",
        imageUrl = "https://www.example.com/papa.jpg",
        userId = 1
    ),
    Product(
        id = 2,
        name = "Zanahoria",
        description = "Hortaliza de raíz alargada y anaranjada, rica en vitaminas y minerales. Se consume cruda, cocida o en zumos.",
        recommendedCultivationDistance = "15 cm",
        recommendedCultivationDepth = "2 cm",
        recommendedGrowingClimate = "Fresco",
        recommendedSoilType = "Franco arenoso",
        recommendedGrowingSeason = "Primavera",
        imageUrl = "https://www.example.com/zanahoria.jpg",
        userId = 1
    ),

)

val ratings = listOf(
    Rate(
        id = 1,
        rate = 4,
        date = "2024-05-02",
        productId = 1,
        userId = 1
    ),
    Rate(
        id = 2,
        rate = 4,
        date = "2024-05-01",
        productId = 2,
        userId = 1
    ),
)
