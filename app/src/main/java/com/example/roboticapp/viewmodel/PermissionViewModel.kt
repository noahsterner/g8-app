package com.example.roboticapp.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class PermissionViewModel(): ViewModel() {
    val pendingPermissionQueue: ArrayDeque<String> = ArrayDeque<String>()

    fun initializePermissions(context: Context) {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            listOf<String>(
                Manifest.permission.BLUETOOTH_SCAN,
            )
        } else {
            listOf<String>(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        permissions
            .filter { permission: String ->
                ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
            }
            .forEach { permission: String -> this.pendingPermissionQueue.add(permission) }
    }
}

