package com.curidev.ayni.shared.ui.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.curidev.ayni.ui.mainmenu.myGreenColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTopAppBar(title: String, navController: NavController){
    CenterAlignedTopAppBar(modifier = Modifier.fillMaxWidth(), title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
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