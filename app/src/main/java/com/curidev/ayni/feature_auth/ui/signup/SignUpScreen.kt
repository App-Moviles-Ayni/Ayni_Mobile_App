package com.curidev.ayni.feature_auth.ui.signup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curidev.ayni.feature_auth.data.remote.UserRequest
import com.curidev.ayni.feature_auth.data.repository.AuthRepository
import com.curidev.ayni.shared.ui.CustomButton1
import com.curidev.ayni.shared.ui.CustomButton2
import com.curidev.ayni.shared.ui.CustomTextButton1
import com.curidev.ayni.shared.ui.InputTextField
import com.curidev.ayni.shared.ui.PasswordTextField

@Composable
fun SignUpScreen(navigateToWelcomePage: () -> Unit, navigateToSignIn: () -> Unit) {
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val role = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(50.dp))
            Text(
                text = "Create an account",
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(text = "Increase your production", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.padding(50.dp))
            InputTextField(input = username, placeholder = "Username")
            Spacer(modifier = Modifier.padding(10.dp))
            InputTextField(input = email, placeholder = "Email address")
            Spacer(modifier = Modifier.padding(10.dp))
            InputTextField(input = role, placeholder = "Enter a role")
            Spacer(modifier = Modifier.padding(10.dp))
            PasswordTextField(password = password, text = "Password")
            Spacer(modifier = Modifier.padding(25.dp))
            // Agregamos el evento onclick a CustomButton1
            CustomButton1(text = "Create account", onclick = {
                AuthRepository().signUp(
                    UserRequest(
                        username = username.value,
                        email = email.value,
                        role = role.value,
                        password = password.value
                    )
                ) { user ->
                    // Aquí puedes manejar la respuesta del registro si es necesario
                }
            })
            CustomTextButton1(text = "Already have an account?", navigateToSignIn)
        }
    }
}