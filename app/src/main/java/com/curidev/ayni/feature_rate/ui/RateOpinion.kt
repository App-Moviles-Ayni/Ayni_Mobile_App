package com.curidev.ayni.ui.detailpage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.curidev.ayni.feature_order.data.repository.SaleRepository
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.shared.ui.topappbar.FilterTopAppBar
import com.skydoves.landscapist.glide.GlideImage

val myGreenColor = Color(0xFF3EAF2C)
@Composable
fun RateOpinion(navController: NavController, id: Int){
    val sale = remember {
        mutableStateOf<Sale?>(null)
    }

    SaleRepository().getSaleById(id) { retrievedProduct ->
        sale.value = retrievedProduct
    }

    sale.value?.let {
        Scaffold(
            topBar = {
                FilterTopAppBar("Market", navController)
            }
        ) { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchF()
                ProductImage(sale.value!!.imageUrl)
                Spacer(modifier = Modifier.height(4.dp))
                PlantDescription(sale.value!!)
                Spacer(modifier = Modifier.height(1.dp))
                OrderButton()
            }
        }
    }
}

@Composable
fun PlantDescription(sale: Sale){

    val description = sale.description

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            "Plant Details",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Plant Description",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Description: $description",
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun SearchF() {

    var searchtext by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        placeholder = { Text("Search") },
        value = searchtext,
        onValueChange = { newValue -> searchtext = newValue },
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}

@Composable
fun ProductImage(imageUrl: String) {
    GlideImage(
        imageModel = { imageUrl },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(240.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}

@Composable
fun OrderButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(36.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp)),
            colors = ButtonDefaults.buttonColors(Color(0xFF3EAF2C)),
        ) {
            Text(text = "Order",
                style = TextStyle(color = Color.White, fontWeight = FontWeight.Light))
        }
    }
}
