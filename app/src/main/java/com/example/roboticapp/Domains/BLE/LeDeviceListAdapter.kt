package com.example.roboticapp.Domains.BLE

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class LeDeviceListAdapter(context: Context) : ArrayAdapter<BluetoothDevice>(context, 0) {

    private val devices = mutableListOf<BluetoothDevice>()

    fun addDevice(device: BluetoothDevice) {
        if (!devices.contains(device)) {
            devices.add(device)
        }
    }

    override fun getCount(): Int = devices.size

    override fun getItem(position: Int): BluetoothDevice = devices[position]
    @SuppressLint("MissingPermission")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)

        val device = getItem(position)
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        text1.text = device.name ?: "Unkown entity"
        text2.text = device.address

        return view
    }
}