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
import com.example.timedelta.Datos.Usuario
import com.example.timedelta.navegacion.AppScreams
import com.example.timedelta.navegacion.appNavegacion
import kotlinx.serialization.json.Json

@Composable
fun pantallaPrincipal(navController: NavController,usuario : String?){
    val usuarioObject = usuario?.let { Json.decodeFromString<Usuario>(it) }
    Column() {
        Text(text = "Bienvenido a la pantalla principal")
        Spacer(modifier = Modifier.height(8.dp))
        if (usuarioObject != null) {
            bodyPantallaPrincipal(navController,usuarioObject)
        }
    }
}

@Composable
fun bodyPantallaPrincipal(navController: NavController,usuario: Usuario){
    Column() {
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