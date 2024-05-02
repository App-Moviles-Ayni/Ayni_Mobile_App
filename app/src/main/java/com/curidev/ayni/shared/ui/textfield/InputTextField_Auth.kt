package com.curidev.ayni.shared.ui.textfield
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(input: MutableState<String>, placeholder: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        modifier = modifier.width(550.dp).padding(horizontal = 20.dp), // Alinea al centro horizontalmente
        placeholder = {
            Text(text = placeholder)
        },
        value = input.value, onValueChange = {
            input.value = it
        })
}