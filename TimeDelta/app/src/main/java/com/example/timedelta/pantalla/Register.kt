package com.example.timedelta.pantalla

import android.content.Context
import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.timedelta.R
import com.example.timedelta.dao.UsuarioDao
import com.example.timedelta.navegacion.AppScreams
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Returning
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun Register(navController: NavController, context: Context){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            BodyRegister(navController,context)
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BodyRegister(navController: NavController, context: Context){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val usuarionombre = remember { mutableStateOf("")}
        val email = remember{ mutableStateOf("") }
        val password = remember{ mutableStateOf("") }
        val confirmacionPassword = remember{ mutableStateOf("") }

        InputTextWhitValidation(variable = usuarionombre, titulo = "Nombre")

        InputTextWhitValidation(variable = email, titulo = "Email")

        InputTextWhitValidation(variable = password, titulo = "Contraseña")

        InputTextWhitValidation(variable = confirmacionPassword, titulo = "Contraseña")

        Button(onClick = { register(email.value,password.value,confirmacionPassword.value,usuarionombre.value,navController,context)}) {
            Text(text = "Registrate")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun register(email:String,
                     password : String,
                     confirmarPassword:String,
                     nombre : String,
                     navController: NavController,
                     context: Context){
    runBlocking {
        val inicioPrueba:LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val finalPrueba : LocalDateTime = LocalDateTime.parse(java.time.LocalDateTime.now().plusDays(5).toString())

        val usuario = Usuario(0,
            nombre,
            email,
            password,
            inicioPrueba,
            inicioPrueba,
            finalPrueba,
            false,
            null)
        val supabaseResponse=UsuarioDao().getCliente().postgrest["usuario"].select {
            eq("mailusuario",email)
        }

        if(supabaseResponse.body.toString() == "[]"){
            if(password==confirmarPassword){
                UsuarioDao().getCliente().postgrest["usuario"].insert(usuario, returning = Returning.REPRESENTATION)
                val jsonUsario = Json.encodeToString(usuario)
                navController.navigate(AppScreams.PantallaPrincipal.ruta+"/"+jsonUsario)
            }else{
                popUpAlert(context,"Las contraseñas no coinciden")
            }
        }else{
            popUpAlert(context,"Este Email ya esta registrado")
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun InputTextWhitValidation(variable: MutableState<String>,titulo:String){
    val clickCount = remember { mutableStateOf(0) }
    val verPassword = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(value = variable.value,
            onValueChange = {variable.value = it},
            label = { Text(text = titulo)},
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
                        Icon(painter = if(!verPassword.value) painterResource(R.drawable.view) else painterResource(id = R.drawable.hide),"icono que muestra la contraseña" ,Modifier.size(30.dp))
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
    if(variable.value!="" && clickCount.value!=0){
        if(titulo == "Email" || titulo == "Contraseña"){
            val esCorrecto = validar(variable.value,titulo)
            if(!esCorrecto&& titulo == "Email"){
                Text(text = "Email Invalido",
                    color = MaterialTheme.colors.error
                )
            }else if(!esCorrecto){
                Text(text = "Contraseña Invalida",
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}