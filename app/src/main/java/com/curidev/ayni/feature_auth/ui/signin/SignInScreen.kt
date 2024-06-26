package com.curidev.ayni.feature_auth.ui.signin

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
import com.curidev.ayni.feature_auth.data.repository.AuthRepository
import com.curidev.ayni.shared.ui.button.CustomButton1
import com.curidev.ayni.shared.ui.button.CustomButton2
import com.curidev.ayni.shared.ui.button.CustomTextButton1
import com.curidev.ayni.shared.ui.textfield.InputTextField
import com.curidev.ayni.shared.ui.textfield.PasswordTextField

@Composable
fun SignInScreen(navigateToWelcomePage: ()-> Unit, navigateToHome: ()-> Unit) {
    val username = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val authRepository = AuthRepository()

    Scaffold {paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            contentAlignment = Alignment.TopStart
        ){
            Column {
                CustomButton2(onclick = {navigateToWelcomePage()})
            }
        }

        Column(modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.padding(50.dp))
            Text(text = "Sign In", textAlign = TextAlign.Center,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp)
            Spacer(modifier = Modifier.padding(30.dp))
            InputTextField(input = username, placeholder = "Username")
            Spacer(modifier = Modifier.padding(10.dp))
            PasswordTextField(password = password, text = "Password")
            Spacer(modifier = Modifier.padding(25.dp))
            CustomButton1(text = "Log In", onclick = {
                authRepository.signIn(username.value, password.value){
                    navigateToHome()
                }
            })
            CustomTextButton1(text = "Forgot your password?") {}
        }
    }

}