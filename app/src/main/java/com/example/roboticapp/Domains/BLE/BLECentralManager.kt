package com.example.roboticapp.Domains.BLE

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattConnectionSettings
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.ParcelUuid
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.core.util.containsKey
import com.example.roboticapp.config.BLE
import com.example.roboticapp.data.BLEPeripheral


val COMMAND_SERVICE_UUID = ""

@SuppressLint("MissingPermission")
class BLECentralManager(
    context: Context
) {
    private val _context = context
    private var _isScanning = false
    private var _scanner: BluetoothLeScanner? = null
    private val _bluetoothManager = context.getSystemService(BluetoothManager::class.java)
    private val _bluetoothAdapter = _bluetoothManager.adapter
    private var _gatt: BluetoothGatt? = null
    private val _handler = Handler(Looper.getMainLooper())
    private val _peripheralList = mutableListOf<BLEPeripheral>()
    val peripheralList: List<BLEPeripheral>
        get() = _peripheralList

    private val _scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()
    
    private val _scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.i("BLECentralManager", "Scan failed, errorCode: $errorCode")
        }

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)

            if (this@BLECentralManager._isCompatible(result)) {
                Log.i("BLECentralManager", result.scanRecord.toString())

                val newPeripheral = BLEPeripheral(
                    device = result.device,
                    manufactureSpecificData = result.scanRecord?.manufacturerSpecificData,
                    serviceUUIDs = result.scanRecord?.serviceUuids,
                    serviceData = result.scanRecord?.serviceData
                )

                val index = this@BLECentralManager._peripheralList.indexOfFirst { it -> it.device == result.device }
                if(index == -1) {
                    this@BLECentralManager._peripheralList.add(newPeripheral)
                } else {
                    this@BLECentralManager._peripheralList[index] = newPeripheral
                }

                if(this@BLECentralManager._isScanning) {
                    this@BLECentralManager.connect()
                    this@BLECentralManager.stopScan()
                }
            }
        }
    }

    private var _connectionAttempts = 1
    private val _gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if(status == BluetoothGatt.GATT_SUCCESS) {
                Log.i("BLECentralManager", "GATT Success")

                if(newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.i("BLECentralManager", "device connected")
                    gatt.discoverServices()
                    this@BLECentralManager._gatt = gatt
                } else if(newState == BluetoothProfile.STATE_DISCONNECTING) {
                    Log.i("BLECentralManager", "device disconnected")
                    gatt.close()
                }
            } else {
                gatt.close()
                this@BLECentralManager._connectionAttempts++
                if(this@BLECentralManager._connectionAttempts <= 10) {
                    this@BLECentralManager.connect()
                } else {
                    Log.i("BLECenteralManager", "Could not conncet to BLE device")
                }
            }
        }
    }
    
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun connect() {
        if(this._isScanning) {
            this._bluetoothAdapter.let { adapter ->
                    val device = adapter.getRemoteDevice(this._peripheralList[0].device.address)
                    when {
                        Build.VERSION.SDK_INT >= 37 -> {
                            val gattSettings = BluetoothGattConnectionSettings.Builder()
                                .setTransport(BluetoothDevice.TRANSPORT_LE)
                                .setAutoConnectEnabled(false)
                                .build()

                            val executor = ContextCompat.getMainExecutor(this._context)
                            device.connectGatt(
                                gattSettings,
                                executor,
                                this._gattCallback
                            )
                        }

                        else -> {
                            device.connectGatt(this._context, false, this._gattCallback)
                        }
                    }
            }
        }
    }

    /**
     * Stop scanning after devices over Bluetooth low energy.
     **/
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopScan() {
        if(this._isScanning) {
            this._scanner?.stopScan(this._scanCallback)
            this._isScanning = false
        }
    }
    /**
     * Scan devices over Bluetooth low energy.
     **/
    @RequiresPermission(android.Manifest.permission.BLUETOOTH_SCAN)
    fun scanDevices() {
        this._scanner = this._createScanner()
        if (this._scanner != null) {
            this._isScanning = true
            this._scanner!!.startScan(this._scanCallback)

            this._handler.postDelayed({
                this._scanner!!.stopScan(this._scanCallback)
            }, 120_000)
        }
    }

    /**
     * Checks if a device is compatible by comparing manufacturerSpecificData
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun _isCompatible(result: ScanResult): Boolean {
        val manufacturer = result.scanRecord?.manufacturerSpecificData ?: return false
        return manufacturer.containsKey(BLE.COMPANY_IDENTIFIER)
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