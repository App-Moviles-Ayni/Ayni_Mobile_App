package com.curidev.ayni.shared.ui.button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val myGreen2Color = Color(0xFF008000) // RGB value for a specific shade of green
@Composable
fun CustomTextButton1(text: String, onClick: () -> Unit) {

    TextButton(
        onClick = { onClick() },

    ) {
        Text(text = text, color = myGreen2Color)
    }
}