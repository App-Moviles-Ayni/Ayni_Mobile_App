package com.curidev.ayni.shared.ui.bottomnavigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.curidev.ayni.ui.mainmenu.myGreenColor

@Composable
fun BottomNavigationBar(
    navigateToHome: () -> Unit,
    navigateToProducts: () -> Unit,
    navigateToOrders: () -> Unit,
    navigateToReviews: () -> Unit,
    actualView: Int
    )
{
    var selected by remember {
        mutableStateOf(actualView)
    }
    Text(text = "Bottom Navigation Bar")
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        NavigationBarItem(
            selected = selected == 0,
            onClick = {
                selected = 0
                navigateToHome() },
            label = { Text("Home") },
            icon = {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = if (selected == 0) Color.Green else Color.Gray
                )
            }
        )
        NavigationBarItem(
            selected = selected == 1,
            onClick = {
                selected = 1
                navigateToProducts() },
            label = { Text("Products") },
            icon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Products",
                    tint = if (selected == 1) Color.Green else Color.Gray
                )
            }
        )
        NavigationBarItem(
            selected = selected == 2,
            onClick = {
                selected = 2
                navigateToOrders() },
            label = { Text("Shopping") },
            icon = {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "Shopping",
                    tint = if (selected == 2) Color.Green else Color.Gray
                )
            }
        )
        NavigationBarItem(
            selected = selected == 3,
            onClick = {
                selected = 3
                navigateToReviews() },
            label = { Text("Review") },
            icon = {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Review",
                    tint = if (selected == 3) Color.Green else Color.Gray
                )
            }
        )
    }
}