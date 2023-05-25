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
import androidx.compose.material.Button
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.timedelta.Datos.Usuario
import com.example.timedelta.dao.UsuarioDao
import com.example.timedelta.navegacion.AppScreams
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Returning
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun Register(navController: NavController, context: Context, lifecycleScope: LifecycleCoroutineScope){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bodyRegister(navController,context,lifecycleScope)
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun bodyRegister(navController: NavController, context: Context, lifecycleScope: LifecycleCoroutineScope){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val usuarionombre = remember { mutableStateOf("")}
        val email = remember{ mutableStateOf("") }
        val contaseña = remember{ mutableStateOf("") }
        val confirmacionContaseña = remember{ mutableStateOf("") }

        inputText(variable = usuarionombre, titulo = "Nombre")

        inputText(variable = email, titulo = "Email")

        inputText(variable = contaseña, titulo = "Contraseña")

        inputText(variable = confirmacionContaseña, titulo = "Contraseña",)

        Button(onClick = { register(email.value,contaseña.value,usuarionombre.value,navController,context,lifecycleScope)}) {
            Text(text = "Registrate")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun register(email:String,
                     contraseña : String,
                     nombre : String,
                     navController: NavController,
                     context: Context,
                     lifecycleScope: LifecycleCoroutineScope){
    lifecycleScope.launch {
        //probla en el agragar, no altera los id
        var inicioPrueba:LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        var finalPrueba : LocalDateTime = LocalDateTime.parse(java.time.LocalDateTime.now().plusDays(5).toString())

        val usuario = Usuario(0,
            nombre,
            email,
            contraseña,
            inicioPrueba,
            inicioPrueba,
            finalPrueba,
            false,
            null)
        val supabaseResponse=UsuarioDao().getCliente().postgrest["usuario"].select {
            eq("mailusuario",email)
        }
        val usuarioSelect = supabaseResponse.decodeSingle<Usuario>()
        if(usuarioSelect==null){
            val respuesta = UsuarioDao().getCliente().postgrest["usuario"].insert(usuario, returning = Returning.REPRESENTATION)
            if(respuesta!=null){
                navController.navigate(AppScreams.PantallaPrincipal.ruta)
            }else{
                popUpAlert(context,"uno de los datos es incorrecto")
            }
        }else{
            popUpAlert(context,"Este mail ya esta registrado")
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun inputText(variable: MutableState<String>,titulo:String){
    val clickCount = remember { mutableStateOf(0) }
    //var passwordVisible =  remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(value = variable.value,
            onValueChange = {variable.value = it},
            label = { Text(text = titulo)},
            modifier = Modifier
                .pointerInput(Unit){
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
                }.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 16.sp),
            visualTransformation = if(titulo.equals("Contraseña")) PasswordVisualTransformation() else VisualTransformation.None
            //esta linea permite que se muestre la contraseña en base al valor de passwordVisible
            //visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
            )
        }
        /*este boton permite que se altere el estado para que se muestre la contraseña
        Button(
            onClick = { passwordVisible.value = !passwordVisible.value},
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(if (passwordVisible.value) "Hide" else "Show")
        }*/

    if(variable.value=="" && clickCount.value!=0){
        Text(text = "Campo necesario",
            color = MaterialTheme.colors.error
        )
    }
}