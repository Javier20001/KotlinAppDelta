package com.example.timedelta.pantalla

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timedelta.Datos.Usuario
import com.example.timedelta.navegacion.AppScreams
import kotlinx.serialization.json.Json

@Composable
fun pantallaPrincipal(navController: NavController,usuario : String?){
    val usuarioObject = usuario?.let { Json.decodeFromString<Usuario>(it) }
    BackHandler() {
        // Cierra la actividad actual (la aplicación)
        //activity.finish()
    }
    if (usuarioObject != null) {
        bodyPantallaPrincipal(navController,usuarioObject)
    }
}

@Composable
fun bodyPantallaPrincipal(navController: NavController,usuario: Usuario){

    Column() {
        Text(text = "Bienvenido a la pantalla principal")
        Spacer(modifier = Modifier.height(8.dp))
        usuario?.let {
            it.nombreusuario?.let { it1 -> Text(it1) }
        }
        Button(onClick = { signOut(navController) }) {
            Text(text = "Cerrar Session")
        }
    }
}

fun signOut(navController: NavController){
    navController.navigate(AppScreams.Loggin.ruta)
}