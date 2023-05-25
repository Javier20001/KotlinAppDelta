package com.example.timedelta.dao

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.timedelta.Datos.Usuario
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Returning
import kotlinx.coroutines.launch

class UsuarioDao(){


    /*
    suspend fun agregarUsuario(usuario: Usuario){
        cliente.postgrest["usuario"].insert(usuario, returning = Returning.MINIMAL)
    }*/

    fun getCliente():SupabaseClient{
        return createSupabaseClient(
            supabaseUrl = "https://inzoeujqlipdrzuommhi.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imluem9ldWpxbGlwZHJ6dW9tbWhpIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODQ3MDcwNTYsImV4cCI6MjAwMDI4MzA1Nn0.lk2iLl9hTYt32bYTZ0Gs3AWnAWVCOdcICTuEJLGlIZQ"
        ){
            install(Postgrest)
        }
    }
}