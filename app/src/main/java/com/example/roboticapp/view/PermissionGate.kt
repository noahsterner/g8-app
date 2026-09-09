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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roboticapp.viewmodel.PermissionViewModel

@Composable
fun PermissionsGate(content: @Composable () -> Unit ) {
    val viewModel = PermissionViewModel()
    viewModel.initializePermissions(LocalContext.current)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if(isGranted) {
                viewModel.pendingPermissionQueue.removeFirst()
            }

            Log.i("TEST", isGranted.toString())
        }
    )

    LaunchedEffect(viewModel.pendingPermissionQueue) {
        viewModel.pendingPermissionQueue.forEach { permission: String -> launcher.launch(permission) }
    }

    if(viewModel.pendingPermissionQueue.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "You have to enable all permissions for this app before using it",
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        }
    } else {
        content()
    }
}
