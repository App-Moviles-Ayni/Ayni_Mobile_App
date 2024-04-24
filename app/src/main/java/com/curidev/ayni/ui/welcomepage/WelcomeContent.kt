package com.curidev.ayni.ui.welcomepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curidev.ayni.R
import com.curidev.ayni.shared.ui.CustomButton1
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun WelcomeContent(navigateToSignUp: () -> Unit, navigateToSignIn: () -> Unit){

    BoxWithConstraints {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ){
                Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AyniImage()
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "Stay on the top with us", fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold
                    , fontSize = 30.sp)
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "We are your new platform to recommend the best products for you", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.padding(10.dp))
                CustomButton1(text = "Create account", navigateToSignUp)
                Spacer(modifier = Modifier.padding(8.dp))
                CustomButton1(text = "Sign In", navigateToSignIn)

            }
            }


        }
    }


}
@Composable
fun AyniImage(){
    GlideImage(imageModel = { R.drawable.ayni}, modifier = Modifier.size(280.dp))
}