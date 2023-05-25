package com.example.timedelta

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.timedelta.dao.UsuarioDao
import com.example.timedelta.navegacion.appNavegacion
import com.example.timedelta.ui.theme.TimeDeltaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //dentro de la logica del login vamos a meter los select y desde ahi la redireccion a las otras paginas
        setContent {
            TimeDeltaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    appNavegacion(context = this, lifecycleScope)
                }
            }
        }
    }
}
