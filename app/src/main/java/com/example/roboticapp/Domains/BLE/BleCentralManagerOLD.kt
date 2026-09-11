package com.example.roboticapp.Domains.BLE

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.roboticapp.data.BleDeviceOLD

class BleCentralManagerOLD(
    private val bluetoothAdapter: BluetoothAdapter,
) {

    private val bluetoothLeScanner: BluetoothLeScanner?
        get() = bluetoothAdapter.bluetoothLeScanner

    private var scanning = false

    private val handler = Handler(Looper.getMainLooper())

    private val SCAN_PERIOD: Long = 60000

    val devices = mutableStateListOf<BleDeviceOLD>()

    private val leScanCallback = object : ScanCallback() {

        @SuppressLint("MissingPermission")
        override fun onScanResult(
            callbackType: Int,
            result: ScanResult
        ) {
            super.onScanResult(callbackType, result)

            val device = result.device

            if (!devices.any { it.address == device.address }) {

                val deviceName = try {
                    device.name ?: "Unknown device"
                } catch (e: SecurityException) {
                    "Unknown device"
                }

                devices.add(
                    BleDeviceOLD(
                        device = device,
                        name = deviceName,
                        address = device.address
                    )
                )

                Log.i(
                    "BleCentralManager",
                    "Found device: $deviceName - ${device.address}"
                )
            }
        }
    }

    fun connectToDevice(device: BleDeviceOLD?) {
        //TODO
        Log.i(
            "BleCentralManager",
            "Trying to connect to device: ${device?.name}"
        )
    }

    @SuppressLint("MissingPermission")
    fun scanLeDevice() {

        val scanner = bluetoothLeScanner

        if (scanner == null) {
            Log.w(
                "BleCentralManager",
                "Bluetooth is turned off or not supported"
            )
            return
        }

        if (!scanning) {

            Log.i("BleCentralManager", "Scanning for BT devices")
            devices.clear()

            scanning = true

            scanner.startScan(leScanCallback)

            handler.postDelayed({

                scanning = false

                scanner.stopScan(leScanCallback)

            }, SCAN_PERIOD)

        } else {

            scanning = false

            scanner.stopScan(leScanCallback)
        }
    }
}