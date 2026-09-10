package com.example.roboticapp.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.security.Permission

class PermissionViewModel(): ViewModel() {
    val pendingDialogQueue = mutableStateListOf<String>()

    fun onPermissionResult(permission: String, isGranted: Boolean) {
        if(!isGranted && !this.pendingDialogQueue.contains(permission)) {
            this.pendingDialogQueue.add(permission)
        }
    }

    fun declinePermissionDialog() {
        this.pendingDialogQueue.removeAt(0)
    }
}

