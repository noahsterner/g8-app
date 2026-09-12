package com.example.roboticapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roboticapp.Domains.BLE.BleCentralManagerOLD
import com.example.roboticapp.data.BleDeviceOLD

//Mock data to see how it looks with devices
val mockDevices = listOf(
    BleDeviceOLD(
        device = null,
        name = "Robot 1",
        address = "AA:BB:CC:DD:EE:01"
    ),
    BleDeviceOLD(
        device = null,
        name = "Robot 2",
        address = "AA:BB:CC:DD:EE:02"
    ),
    BleDeviceOLD(
        device = null,
        name = "Robot 3",
        address = "AA:BB:CC:DD:EE:03"
    )

)

@Composable
fun BluetoothScreen(
    bleCentralManager: BleCentralManagerOLD,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Bluetooth Devices",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = {
                bleCentralManager.scanLeDevice()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Scan for devices")
        }

        Text(
            text = "Found devices: ${bleCentralManager.devices.size}",
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(
                //switch if you want to search for real
                // items = bleCentralManager.devices
                items = (mockDevices)
            ) { device ->

                BluetoothDeviceItem(
                    deviceName = device.name ?: "Unknown device",
                    deviceAddress = device.address,
                    onClick = {
                        bleCentralManager.connectToDevice(device)
                    }
                )
            }
        }
    }
}
