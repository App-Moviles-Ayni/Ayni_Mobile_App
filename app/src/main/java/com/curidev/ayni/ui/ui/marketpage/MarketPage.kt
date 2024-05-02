package com.curidev.ayni.ui.marketpage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
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
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.feature_product.data.repository.ProductRepository
import com.curidev.ayni.feature_product.domain.model.Product
import com.curidev.ayni.shared.filtertopappbar.MarketTopAppBar
import com.curidev.ayni.ui.home.Routes
import com.skydoves.landscapist.glide.GlideImage
import java.util.Timer
import java.util.TimerTask




import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductItemWithRatingAndRateButton(
    product: Product,
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
            Image(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                contentDescription = "Imagen del producto ${product.name}",
                imageUrl = product.imageUrl
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
                Text(text = "S/ ${price.toString("%.2f")}", style = MaterialTheme.typography.body2)

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
    product: Product,
    rating: Float,
    price: Double,
    onAddToCart: (Product) -> Unit
) {
    // ... (Similar to ProductItemWithRatingAndPrice)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { onAddToCart(product) }) {
            Text("Añadir al carrito")
        }
    }
}


@Composable
fun ProductSearchResults(
    searchQuery: String,
    products: List<Product>,
    onProductClick: (Product) -> Unit
) {
    var currentSearchQuery by remember { mutableStateOf(searchQuery) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = currentSearchQuery,
            onValueChange = { currentSearchQuery = it },
            label = { Text("Buscar producto") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val filteredProducts = products.filter {
            it.name.contains(currentSearchQuery, true) ||
                    it.description.contains(currentSearchQuery, true)
        }

        if (filteredProducts.isEmpty()) {
            Text("No se encontraron productos")
        } else {
            LazyColumn {
                items(filteredProducts) { product ->
                    ProductItemWithRatingAndPrice(
                        product = product,
                        rating = 4.0f, // Replace with actual rating
                        price = 10.0, // Replace with actual price
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}