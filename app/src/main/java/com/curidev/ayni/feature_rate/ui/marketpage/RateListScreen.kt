package com.curidev.ayni.ui.marketpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.ui.graphics.Color
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.ui.productpage.ProductItemWithRatingAndPrice
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RateListScreen(
    sale: Sale,
    rating: Float,
    price: Double,
    onRateChange: (Float) -> Unit
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
                imageModel = { sale.imageUrl },
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

                var currentRating by remember { mutableStateOf(rating) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1 until 6) {
                        IconButton(onClick = { currentRating = i.toFloat() }) {
                            Icon(
                                imageVector = if (currentRating >= i) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Rating star",
                                tint = Color.Yellow
                            )
                        }
                    }
                    Button(onClick = { onRateChange(currentRating) }) {
                        Text("Calificar")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItemWithAddToCartButton(
    sale: Sale,
    rating: Float,
    price: Double,
    onAddToCart: (Sale) -> Unit
) {
    // ... (Similar to ProductItemWithRatingAndPrice)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { onAddToCart(sale) }) {
            Text("Añadir al carrito")
        }
    }
}


@Composable
fun ProductSearchResults(
    searchQuery: String,
    sales: List<Sale>,
    onProductClick: (Sale) -> Unit
) {
    var currentSearchQuery by remember { mutableStateOf(searchQuery) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = currentSearchQuery,
            onValueChange = { currentSearchQuery = it },
            label = { Text("Buscar producto") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val filteredProducts = sales.filter {
            it.name.contains(currentSearchQuery, true) ||
                    it.description.contains(currentSearchQuery, true)
        }

        if (filteredProducts.isEmpty()) {
            Text("No se encontraron productos")
        } else {
            LazyColumn {
                items(filteredProducts) { product ->
                    ProductItemWithRatingAndPrice(
                        sale = product,
                        rating = 4.0f, // Replace with actual rating
                        price = 10.0, // Replace with actual price
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}