package com.example.timedelta.pantalla

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timedelta.navegacion.AppScreams
import com.example.timedelta.navegacion.appNavegacion

@Composable
fun pantallaPrincipal(navController: NavController){
    Column() {
        Text(text = "Bienvenido a la pantalla principal")
        Spacer(modifier = Modifier.height(8.dp))
        bodyPantallaPrincipal(navController)
    }
}

@Composable
fun bodyPantallaPrincipal(navController: NavController){
    Column() {
        Button(onClick = { signOut(navController) }) {
            Text(text = "Cerrar Session")
        }
    }
}

fun signOut(navController: NavController){
    navController.navigate(AppScreams.Loggin.ruta)
}