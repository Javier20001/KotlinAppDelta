package com.example.timedelta.pantalla

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.timedelta.navegacion.AppScreams
import kotlinx.coroutines.delay

import com.example.timedelta.R

@Composable
fun pantallaESpera(navController: NavController){
    LaunchedEffect(key1 = 1){
        delay(1000)
        navController.popBackStack()//evita que regreses a esta pantalla
        navController.navigate(AppScreams.Loggin.ruta)
    }
    bodyPantallaEspera()//llamamos al body mientra s se ejecuta lo de arriba
}

@Composable
fun bodyPantallaEspera(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(painter = painterResource(id= R.drawable.timedeltalogo),
            contentDescription = "Time Delta Logo",
            Modifier.size(150.dp,150.dp)
        )
        Text(text = "Bienvenido a Nuestra Aplicacion",
            fontSize = 30.sp, //tamaño
            fontWeight = FontWeight.Light//caligrafia
        )
    }
}

