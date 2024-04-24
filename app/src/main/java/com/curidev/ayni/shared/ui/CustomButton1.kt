package com.curidev.ayni.shared.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val myGreenColor = Color(0xFF008000) // RGB value for a specific shade of green

@Composable
fun CustomButton1(
    text: String,
    onclick: () -> Unit,
) {
    Button(
        modifier = Modifier.width(200.dp), // Set a specific width here
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = myGreenColor
        ),
        onClick = { onclick() }
    ) {
        Text(text = text)
    }
}