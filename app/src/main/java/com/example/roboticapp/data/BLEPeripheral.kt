package com.example.roboticapp.data

import android.bluetooth.BluetoothDevice
import android.os.ParcelUuid
import android.util.SparseArray

data class BLEPeripheral(
    val device: BluetoothDevice,
    val manufactureSpecificData: SparseArray<ByteArray>?,
    val serviceUUIDs: List<ParcelUuid>?,
    val serviceData: Map<ParcelUuid, ByteArray>?
)
