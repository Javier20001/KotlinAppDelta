package com.example.timedelta.navegacion

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.timedelta.pantalla.Register
import com.example.timedelta.pantalla.loggin
import com.example.timedelta.pantalla.pantallaESpera

import com.example.timedelta.pantalla.pantallaPrincipal

@Composable
fun appNavegacion(context: Context,lifecycleScope: LifecycleCoroutineScope){
    val navControler = rememberNavController()
    NavHost(navController = navControler, startDestination = AppScreams.PantallaEspera.ruta){
        composable(route = AppScreams.PantallaEspera.ruta){
            pantallaESpera(navController = navControler )
        }
        composable(route = AppScreams.Loggin.ruta){
            loggin(navController = navControler, context = context,lifecycleScope)
        }
        composable(route = AppScreams.PantallaPrincipal.ruta){
            pantallaPrincipal(navController = navControler)
        }
        composable(route = AppScreams.Register.ruta){
            Register(navController = navControler, context = context,lifecycleScope)
        }
    }
}