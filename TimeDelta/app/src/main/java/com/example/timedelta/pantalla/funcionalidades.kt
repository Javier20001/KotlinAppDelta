package com.example.timedelta.pantalla

fun validar(valor:String,nombre:String):Boolean{
    var regex = Regex("")
    if(nombre=="Email"){
        regex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
    }else if(nombre=="Contraseña"){
        regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}\$")
    }
    return regex.matches(valor)
}