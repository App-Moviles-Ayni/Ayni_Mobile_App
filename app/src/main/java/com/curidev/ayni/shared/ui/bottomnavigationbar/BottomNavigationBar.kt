package com.curidev.ayni.shared.ui.bottomnavigationbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.curidev.ayni.shared.ui.button.myGreen2Color

@Composable
fun BottomNavigationBar(
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit)
{
    Text(text = "Bottom Navigation Bar")
    NavigationBar() {
        NavigationBarItem(selected = true, onClick = { navigateToHome() },
            label = { Text("Home") },
            icon = {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
        )
        NavigationBarItem(selected = false, onClick = { navigateToProducts() },
            label = { Text("Products") },
            icon = {
                Icon(Icons.Filled.Search, contentDescription = "Products")
            }
        )
        NavigationBarItem(selected = false, onClick = { navigateToOrders() },
            label = { Text("Shopping") },
            icon = {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Shopping")
            }
        )
        NavigationBarItem(selected = false, onClick = { navigateToReviews() },
            label = { Text("Review") },
            icon = {
                Icon(Icons.Filled.Star, contentDescription = "Review")
            }
        )
    }
}