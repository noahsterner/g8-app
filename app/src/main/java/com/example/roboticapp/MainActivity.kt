package com.example.roboticapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.roboticapp.ui.theme.RoboticAppTheme
import com.example.roboticapp.Domains.BLE.BleCentralManager
import com.example.roboticapp.view.PermissionsGate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoboticAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    fun initiliazePermission(): List<String> {
       var permissions = listOf<String>(
           Manifest.permission.BLUETOOTH_SCAN,
           Manifest.permission.BLUETOOTH_CONNECT,
       )

       return permissions.filter { permission ->
            this.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED
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
    RoboticAppTheme {
        Greeting("Android")
    }
}