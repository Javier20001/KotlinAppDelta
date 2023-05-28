package com.example.timedelta.navegacion


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.timedelta.pantalla.Register
import com.example.timedelta.pantalla.Loggin
import com.example.timedelta.pantalla.pantallaESpera

import com.example.timedelta.pantalla.pantallaPrincipal

@Composable
fun AppNavegacion(context: Context){
    val navControler = rememberNavController()

    NavHost(navController = navControler, startDestination = AppScreams.PantallaEspera.ruta){
        composable(route = AppScreams.PantallaEspera.ruta){
            pantallaESpera(navController = navControler )
        }
        composable(route = AppScreams.Loggin.ruta){
            Loggin(navController = navControler, context = context)
        }
        composable(route = AppScreams.PantallaPrincipal.ruta+"/{usuario}",
            arguments = listOf(navArgument(name = "usuario"){
                type = NavType.StringType
            })){
                pantallaPrincipal(navController = navControler,it.arguments?.getString("usuario"))
            }
        composable(route = AppScreams.Register.ruta){
            Register(navController = navControler, context = context)
        }
    }
}