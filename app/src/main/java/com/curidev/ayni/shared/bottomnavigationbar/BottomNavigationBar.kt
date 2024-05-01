package com.curidev.ayni.shared.bottomnavigationbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar() {
    Text(text = "Bottom Navigation Bar")
    NavigationBar() {
        NavigationBarItem(selected = true, onClick = { /*TODO*/ },
            label = { Text("Home") },
            icon = {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
        )
        NavigationBarItem(selected = false, onClick = { /*TODO*/ },
            label = { Text("Products") },
            icon = {
                Icon(Icons.Filled.Search, contentDescription = "Products")
            }
        )
        NavigationBarItem(selected = false, onClick = { /*TODO*/ },
            label = { Text("Shopping") },
            icon = {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Shopping")
            }
        )
        NavigationBarItem(selected = false, onClick = { /*TODO*/ },
            label = { Text("Review") },
            icon = {
                Icon(Icons.Filled.Star, contentDescription = "Review")
            }
        )
    }
}