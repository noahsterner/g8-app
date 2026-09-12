package com.example.roboticapp.view


import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PermissionsGate(
    permissions: List<String>,
    content: @Composable () -> Unit
) {
    val permissionQueue = remember { mutableStateListOf(*permissions.toTypedArray()) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { results ->
            permissions.forEach { permission ->
                if (results[permission] == true) {
                    permissionQueue.remove(permission)
                }
            }
        }
    )

    /* Launch Permissions */
    LaunchedEffect(Unit) {
        launcher.launch(permissions.toTypedArray())
    }

    if (permissionQueue.isNotEmpty()) {
        Log.i("PermissionGate", "${permissionQueue.size}")

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center,

        ) {
            Text(
                "You have to enable all permissions to using this application",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

        }
    } else {
        Log.i("PermissionGate", "${permissionQueue.size}")
        content()
    }
}