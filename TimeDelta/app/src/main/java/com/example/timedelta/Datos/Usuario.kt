package com.example.timedelta.Datos

import kotlinx.datetime.LocalDateTime

@kotlinx.serialization.Serializable
data class Usuario(
    val idusuario:Int?=0,
    val nombreusuario:String?="",
    val mailusuario:String?="",
    val contraseñausuario:String?="",
    val fecharegistro: LocalDateTime?,
    val fechainicioprueba: LocalDateTime?,
    val fechafinalizacionprueba: LocalDateTime?,
    val estadopago:Boolean?=false,
    val fechadepago: LocalDateTime?,
)