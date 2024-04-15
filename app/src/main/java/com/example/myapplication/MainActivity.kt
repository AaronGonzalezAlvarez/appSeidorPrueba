package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.example.myapplication.data.RetrofitServiceFactory
import com.example.myapplication.pruebas.makeApiRequest
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val service = RetrofitServiceFactory.makeRetrofitService(applicationContext)

        lifecycleScope.launch {
            try {
                //val data = Login("PEPITO_ES","Usuario1234*","manager")
                //val listPopuralMovie = service.listPopuralMovie(data)
                //println(listPopuralMovie)
                makeApiRequest()
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}