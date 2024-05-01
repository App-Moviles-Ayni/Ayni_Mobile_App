package com.curidev.ayni.shared.ui
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun CustomButton2(
    onclick: () -> Unit){

    IconButton(

        onClick = { onclick() },
    )
    { Icon(imageVector = Icons.Filled.ArrowBack,
        contentDescription = "Localized description")


    }

}