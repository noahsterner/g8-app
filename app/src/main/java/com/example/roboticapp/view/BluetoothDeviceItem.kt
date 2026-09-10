package com.example.roboticapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable

@Composable
fun BluetoothDeviceItem(
    deviceName: String,
    deviceAddress: String?,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable{
                onClick()
            }
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                text = deviceName,
                style = MaterialTheme.typography.titleMedium
            )

            if (deviceAddress != null) {
                Text(
                    text = deviceAddress,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}