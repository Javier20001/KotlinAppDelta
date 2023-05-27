package com.example.timedelta.pantalla

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.timedelta.Datos.Usuario
import com.example.timedelta.dao.UsuarioDao
import com.example.timedelta.navegacion.AppScreams
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun loggin(navController: NavController , context: Context , lifecycleScope: LifecycleCoroutineScope){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        bodyLoggin(navController = navController, context )
    }
}

@Composable
fun bodyLoggin(navController: NavController ,  context: Context){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val email = remember{ mutableStateOf("") }
        val contaseña = remember{ mutableStateOf("") }

        TextField(value = email.value,
            onValueChange = {email.value= it },
            label = { Text(text = "email")})

        TextField(value = contaseña.value,
            onValueChange = {contaseña.value = it},
            label = {Text(text = "contrase")},
            visualTransformation = PasswordVisualTransformation()
        )

        Button(onClick = { register( navController,context) }) {
            Text(text = "Registrate")
        }

        Button(onClick = { iniciarSeccion(email.value , contaseña.value , navController,context) }) {
            Text(text = "iniciar sesion")
        }
    }
}

private fun register(navController: NavController ,context: Context){
    navController.navigate(AppScreams.Register.ruta)
}


private fun iniciarSeccion(email : String , contraseña : String
                   , navController: NavController
                   ,context: Context){
    runBlocking {
        val supabaseResponse = withContext(Dispatchers.IO) {
            UsuarioDao().getCliente().postgrest["usuario"].select {
                eq("mailusuario", email)
                eq("contraseñausuario", contraseña)
            }
        }
        val usuario = supabaseResponse.decodeSingle<Usuario>()

        if (usuario != null) {
            val jsonUsuario = Json.encodeToString(usuario)
            navController.navigate(AppScreams.PantallaPrincipal.ruta + "/" + jsonUsuario)
        } else {
            withContext(Dispatchers.Main) {
                popUpAlert(context, "Uno de los datos es incorrecto")
            }
        }
    }
}

