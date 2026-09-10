package com.example.roboticapp.data
import android.bluetooth.BluetoothDevice
data class BleDevice (
    val device: BluetoothDevice?,
    val name: String,
    val address: String?
)

