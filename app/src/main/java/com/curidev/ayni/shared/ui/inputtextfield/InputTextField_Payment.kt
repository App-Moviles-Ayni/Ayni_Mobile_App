package com.curidev.ayni.shared.ui.inputtextfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField_Payment(input: MutableState<String>, placeholder: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(text = placeholder)
        },
        value = input.value, onValueChange = {
            input.value = it
        })
}