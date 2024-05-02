package com.curidev.ayni
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.curidev.ayni.ui.detailpage.DetailPage
import com.curidev.ayni.ui.home.Home
import com.curidev.ayni.ui.marketpage.MarketPage
import com.curidev.ayni.ui.productpage.ProductPage
import androidx.compose.ui.tooling.preview.Preview
import com.curidev.ayni.feature_payment.HomeScreen
import com.curidev.ayni.order.ui.home.Home
import com.curidev.ayni.ui.theme.AyniPlatformTheme
import com.curidev.ayni.ui.welcomepage.Welcome

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AyniPlatformTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    //HomeScreen()
                    Home()
                    Welcome()
                }
            }
        }
    }
}
