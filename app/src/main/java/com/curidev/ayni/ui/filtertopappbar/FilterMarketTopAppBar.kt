package com.curidev.ayni.shared.filtertopappbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketTopAppBar(title: String){
    CenterAlignedTopAppBar(modifier = Modifier.fillMaxWidth(), title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Back")
            }
        },
        actions = {
            TextButton(onClick = { /*TODO*/ }) {
                Text("Filter", color = Color.Black)
            }
        }
    )
}