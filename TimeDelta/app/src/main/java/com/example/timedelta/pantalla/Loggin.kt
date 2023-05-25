package com.example.timedelta.pantalla

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.timedelta.navegacion.AppScreams

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.timedelta.Datos.Usuario
import com.example.timedelta.dao.UsuarioDao
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch


@Composable
fun loggin(navController: NavController , context: Context , lifecycleScope: LifecycleCoroutineScope){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        bodyLoggin(navController = navController, context , lifecycleScope)
    }
}

@Composable
fun bodyLoggin(navController: NavController ,  context: Context , lifecycleScope: LifecycleCoroutineScope){
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

        Button(onClick = { register( navController,context, lifecycleScope) }) {
            Text(text = "Registrate")
        }

        Button(onClick = { iniciarSeccion(email.value , contaseña.value , navController,context,lifecycleScope ) }) {
            Text(text = "iniciar sesion")
        }
    }
}

private fun register(navController: NavController ,context: Context , lifecycleScope: LifecycleCoroutineScope){
    navController.navigate(AppScreams.Register.ruta)
}

private fun iniciarSeccion(email : String , contraseña : String
                   , navController: NavController
                   ,context: Context
                   ,lifecycleScope: LifecycleCoroutineScope){
    lifecycleScope.launch {
        val supabaseResponse=UsuarioDao().getCliente().postgrest["usuario"].select {
            eq("mailusuario",email)
            eq("contraseñausuario",contraseña)
        }
        val usuario = supabaseResponse.decodeSingle<Usuario>()
        if(usuario!=null){
            navController.navigate(AppScreams.PantallaPrincipal.ruta)
        }else{
            popUpAlert(context,"uno de los datos es incorrecto")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun testLoggin(){
    val navControler = rememberNavController()
    val context = LocalContext.current
    //loggin(navController = navControler, context = context)
}

