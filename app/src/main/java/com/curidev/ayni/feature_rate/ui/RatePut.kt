package com.curidev.ayni.ui.productpage

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.curidev.ayni.feature_order.domain.model.Sale
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProductItemWithRatingAndPrice(
    sale: Sale,
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
                imageModel = { sale.imageUrl }
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = sale.name, style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = sale.description)
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
fun ProductItemWithRating(sale: Sale, rating: Float) {
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
                imageModel = { sale.imageUrl }
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = sale.name, style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = sale.description)
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
