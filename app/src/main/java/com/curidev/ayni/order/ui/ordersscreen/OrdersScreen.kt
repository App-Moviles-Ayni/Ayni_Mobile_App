package com.curidev.ayni.order.ui.ordersscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.curidev.ayni.shared.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.shared.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun OrdersScreen(navigateToDetails: () -> Unit) {
    Scaffold(
        topBar = {
            FilterTopAppBar("Orders")
        },
        bottomBar = { BottomNavigationBar() }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)){
            SearchField()
            OrdersList(navigateToDetails)
        }
    }
}

@Composable
fun SearchField() {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        placeholder = { Text("Search") },
        value = "", onValueChange = {},
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        })
}

@Composable
fun OrdersList(navigateToDetails: () -> Unit) {
    LazyColumn {
        items(5) {
            OrderItem(navigateToDetails)
        }
    }
}

@Composable
fun OrderItem(navigateToDetails: () -> Unit) {
    val url = "https://cdn.donmai.us/original/cd/30/cd3038a1e4953a43c0e3620d953cdb2a.jpg"
    ListItem(
        modifier = Modifier.clickable(onClick = { navigateToDetails() }),
        headlineContent = { Text(text = "Nombre planta") },
        supportingContent = {
            Text(text = "Quantity: 1\nState: plap plap plap")},
        leadingContent = {
            GlideImage(
                imageModel = { url },
                modifier = Modifier.size(80.dp)) },
        trailingContent = { Text(text = "9 months ago")}
    )
}