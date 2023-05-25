package com.example.timedelta.navegacion

sealed class AppScreams(val ruta : String) {
    object PantallaPrincipal : AppScreams("PantallaPrincipal")
    object PantallaEspera : AppScreams("PantallaEspera")
    object Loggin : AppScreams("Loggin")
    object Register : AppScreams("Register")
}