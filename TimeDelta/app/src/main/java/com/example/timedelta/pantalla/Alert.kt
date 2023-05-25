package com.example.timedelta.pantalla

import android.app.AlertDialog
import android.content.Context

fun popUpAlert(context : Context, mensage : String){
    val builder = AlertDialog.Builder(context)
    builder.setTitle("ERROR")
    builder.setMessage(mensage)
    builder.setPositiveButton("accept",null)
    val dialog : AlertDialog = builder.create()
    dialog.show()
}