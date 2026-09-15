package com.example.roboticapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.JdkConstants

/*****************
 * UI to control the robot
 *****************/
@Composable
fun ControllerPage(
    modifier: Modifier
) {
    Column(modifier.padding(start=16.dp,top=16.dp, end=16.dp, bottom = 70.dp).fillMaxHeight() ) {
        /***showing data to the user***/
        //TODO data is hardcoded
        Row() {

            Text(text = "67%")//
            Spacer(Modifier.weight(1f))
            Text(text = "bluetooth connection")// we should have a function to check if we are connected to bluetooth
        }
        Row(modifier.height(60.dp).fillMaxWidth()) {
            /***let the users know if we have errors***/
            //TODO get errors from robot
            Column() {
                Text(text = "ERROR BOX: ")
                Text(text="ERROR: LOST CONTACT WITH THE ROBOT")
            }
        }
        Row(modifier.background(Color.Black).fillMaxWidth().height(475.dp)){
            /***Draw the robots paths***/
            //TODO hardcoded line. should get data from the robot
            Canvas(modifier = Modifier
                .fillMaxSize()){
                drawLine(            color = Color.Red,
                    start = Offset(50f, 400f),
                    end = Offset(400f, 400f),
                    strokeWidth = 10f)
            }
            //This is for like path
            // to draw we could use canvas maybe?
            //black square is just a placeholder to see size of the canvas
        }
        Spacer(modifier.weight(1f))
        /***all this below is for moving the robot***/
        //TODO onClick()={} should be sent to the robot
        Column( modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier.padding(bottom = 20.dp)) {
                Button(modifier=modifier.width(130.dp).height(50.dp),onClick = {}) {
                    Icon(imageVector =  Icons.Filled.ArrowUpward,
                        contentDescription = "up")
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(modifier=modifier.width(85.dp).height(50.dp),onClick = {},) {
                    Icon(imageVector =  Icons.AutoMirrored.Filled.ArrowLeft,
                        contentDescription = "Left")
                }
                Spacer(modifier.weight(0.5f))
                Button(modifier=modifier.width(130.dp).height(50.dp),onClick = {},) {
                    Icon(imageVector =  Icons.Filled.ArrowDownward,
                        contentDescription = "down")
                }
                Spacer(modifier.weight(0.5f))
                Button(modifier=modifier.width(85.dp).height(50.dp),onClick = {},) {
                    Icon(imageVector =  Icons.AutoMirrored.Filled.ArrowRight,
                    contentDescription = "Right")

                }

            }
        }

    }



}