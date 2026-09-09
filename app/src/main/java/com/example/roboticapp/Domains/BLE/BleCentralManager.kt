package com.example.roboticapp.Domains.BLE

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat

class BleCentralManager(
    private val bluetoothAdapter: BluetoothAdapter,
    private val context: Context
) {
    private val bluetoothLeScanner: BluetoothLeScanner?
        get() = bluetoothAdapter.bluetoothLeScanner

    private var scanning = false
    private val handler = Handler(Looper.getMainLooper())

    private val SCAN_PERIOD: Long = 60000

    private val leDeviceListAdapter = LeDeviceListAdapter(context)

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            leDeviceListAdapter.addDevice(result.device)
            leDeviceListAdapter.notifyDataSetChanged()
        }
    }

    private fun hasScanPermission(): Boolean {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Manifest.permission.BLUETOOTH_SCAN
        } else {
            Manifest.permission.ACCESS_FINE_LOCATION
        }
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun scanLeDevice() {
        if (!hasScanPermission()) {
            Log.w("BleCentralManager", "Saknar Bluetooth-scan-behörighet")
            return
        }

        val scanner = bluetoothLeScanner
        if (scanner == null) {
            Log.w("BleCentralManager", "Bluetooth är avstängt eller stöds inte")
            return
        }

        if (!scanning) {
            handler.postDelayed({
                if (hasScanPermission()) {
                    scanning = false
                    scanner.stopScan(leScanCallback)
                }
            }, SCAN_PERIOD)
            scanning = true
            scanner.startScan(leScanCallback)
        } else {
            scanning = false
            scanner.stopScan(leScanCallback)
        }
    }
}