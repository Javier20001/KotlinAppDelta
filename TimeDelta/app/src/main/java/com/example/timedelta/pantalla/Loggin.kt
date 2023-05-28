package com.example.timedelta.pantalla

import android.content.Context
import android.view.MotionEvent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.timedelta.R


@Composable
fun Loggin(navController: NavController , context: Context){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BodyLoggin(navController = navController, context )
    }
}

@Composable
fun BodyLoggin(navController: NavController ,  context: Context){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val email = remember{ mutableStateOf("") }
        val password = remember{ mutableStateOf("") }

        InputTextWhitValidation(variable = email, titulo = "Email")

        InputTextWhitValidation(variable = password, titulo = "Contraseña")

        Button(onClick = { register( navController) }) {
            Text(text = "Registrate")
        }

        Button(onClick = { iniciarSeccion(email.value , password.value , navController,context) }) {
            Text(text = "iniciar sesion")
        }
    }
}

private fun register(navController: NavController){
    navController.navigate(AppScreams.Register.ruta)
}


private fun iniciarSeccion(email : String , password : String
                   , navController: NavController
                   ,context: Context){
    runBlocking {
        val supabaseResponse = withContext(Dispatchers.IO) {
            UsuarioDao().getCliente().postgrest["usuario"].select {
                eq("mailusuario", email)
                eq("contraseñausuario", password)
            }
        }

        var jsonUsuario = Json.encodeToString(supabaseResponse.body)
        if (jsonUsuario != "[]") {
            val usuario = supabaseResponse.decodeSingle<Usuario>()
            jsonUsuario = Json.encodeToString(usuario)
            navController.navigate(AppScreams.PantallaPrincipal.ruta + "/" + jsonUsuario)
        } else {
            popUpAlert(context, "Uno de los datos es incorrecto")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun InputTextWhitValidation(variable: MutableState<String>, titulo:String){
    val clickCount = remember { mutableStateOf(0) }
    val verPassword = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    ) {
        OutlinedTextField(value = variable.value,
            onValueChange = {variable.value = it},
            label = { Text(text = titulo) },
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        clickCount.value++
                    })
                }
                .pointerInteropFilter { motionEvent ->
                    // Increment clickCount when a single tap occurs
                    if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                        clickCount.value++
                    }
                    false
                }
                .fillMaxWidth(),
            textStyle = TextStyle(fontSize = 16.sp),
            visualTransformation = if(titulo == "Contraseña" &&!verPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon =  {
                if (titulo == "Contraseña"){
                    IconButton(
                        onClick = {
                            // Acción a realizar cuando se hace clic en el botón
                            verPassword.value=!verPassword.value
                        }
                    ) {
                        Icon(painter = if(!verPassword.value) painterResource(R.drawable.view) else painterResource(id = R.drawable.hide),"icono que muestra la contraseña" ,
                            Modifier.size(30.dp))
                    }
                }
            }
        )
    }

    if(variable.value=="" && clickCount.value!=0){
        Text(text = "Campo necesario",
            color = MaterialTheme.colors.error
        )
    }
}