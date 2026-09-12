package com.example.roboticapp.Domains.BLE

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.SparseArray
import androidx.annotation.RequiresPermission
import com.example.roboticapp.config.BLE
import com.example.roboticapp.data.BLEPeripheral

class BLECentralManager(
    context: Context
) {
    private val _bluetoothManager = context.getSystemService(BluetoothManager::class.java)
    private val _bluetoothAdapter = _bluetoothManager.adapter
    private val _handler = Handler(Looper.getMainLooper())
    private val _peripheralList = mutableListOf<BLEPeripheral>()
    val peripheralList: List<BLEPeripheral>
        get() = _peripheralList

    private val _scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.i("BLECentralManager", "Scan failed, errorCode: $errorCode")
        }

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)

            if (this@BLECentralManager._isCompatible(result)) {
                Log.i("BLECentralManager", result.scanRecord.toString())

                this@BLECentralManager._peripheralList.add(
                    BLEPeripheral(
                        device = result.device,
                        manufactureSpecificData = result.scanRecord?.manufacturerSpecificData,
                        serviceUUIDs = result.scanRecord?.serviceUuids,
                        serviceData = result.scanRecord?.serviceData
                    )
                )
            }
        }
    }

    /**
     * Scan devices over Bluetooth low energy.
     **/
    @RequiresPermission(android.Manifest.permission.BLUETOOTH_SCAN)
    fun scanDevices() {
        val scanner = this._createScanner()
        if (scanner != null) {
            scanner.startScan(this._scanCallback)

            this._handler.postDelayed({
                scanner.stopScan(this._scanCallback)
            }, 120_000)
        }
    }

    /**
     * Checks if a device is compatible by comparing manufacturerSpecificData
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun _isCompatible(result: ScanResult): Boolean {
        /*
        val manufactureSpecificData: SparseArray<ByteArray> =
            result.scanRecord?.manufacturerSpecificData ?: return false
        return manufactureSpecificData.get(BLE.COMPANY_IDENTIFIER) != null
         */

        return true //returns true because we do not have any manufacture data yet
    }

    /**
     * Create a BluetoothLeScanner if bluetooth is supported and enabled
     **/
    private fun _createScanner(): BluetoothLeScanner? {
        if (this._isBluetoothSupported() && this._isBluetoothEnabled()) {
            return this._bluetoothAdapter.bluetoothLeScanner
        }

        Log.w("BLECentralManager", "Bluetooth is not enabled or supported")
        return null
    }

    /**
     * Checks if bluetooth is supported
     **/
    private fun _isBluetoothSupported(): Boolean {
        return this._bluetoothAdapter != null
    }

    /**
     * Checks if bluetooth is enabled
     **/
    private fun _isBluetoothEnabled(): Boolean {
        return this._bluetoothAdapter.isEnabled
    }
}