package com.example.roboticapp.ui.screens

import com.example.roboticapp.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roboticapp.ui.theme.RoboticAppTheme

private val PageBackground = Color(0xFFF3F1EB)
private val TopGradientStart = Color(0xFFDCEFD9)
private val TopGradientEnd = Color(0xFFF3F1EB)
private val AccentGreen = Color(0xFF2E7D4F)
private val LightGreenChip = Color(0xFFE7F3E4)
private val ManualOrange = Color(0xFFB5772F)
private val CardBackground = Color(0xFFFFFFFF)

private const val CURRENT_BATTERY_LEVEL = 35

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobotHomeScreen() {
    var isConnected by remember { mutableStateOf(true) }
    var autonomousMode by remember { mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(TopGradientStart, TopGradientEnd),
                    endY = 900f
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 24.dp, bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "MY ROBOT",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "GrassBot Pro",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Surface(
                        shape = CircleShape,
                        color = CardBackground,
                        shadowElevation = 2.dp,
                        modifier = Modifier.size(44.dp)
                    ) {
                        IconButton(onClick = { /* TODO: no functionality yet */ }) {
                            Icon(
                                imageVector = Icons.Default.Bluetooth,
                                contentDescription = "Settings",
                                tint = Color.DarkGray
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = CardBackground,
                        shadowElevation = 2.dp,
                        modifier = Modifier.size(44.dp)
                    ) {
                        IconButton(onClick = { /* TODO: no functionality yet */ }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFBFD9C4))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.automoweraspirer4),
                        contentDescription = "Husqvarna Automower",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().padding(16.dp)
                    )

                    Surface(
                        onClick = { isConnected = !isConnected },
                        shape = RoundedCornerShape(50),
                        color = CardBackground,
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (isConnected) AccentGreen else Color(0xFFD32F2F))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isConnected) "Connected" else "Disconnected",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Husqvarna Automower Aspire R4",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BatteryIndicator(level = CURRENT_BATTERY_LEVEL)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$CURRENT_BATTERY_LEVEL%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "|", color = Color.LightGray)
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Task: ",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Mowing — Zone A",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                ActionCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.SportsEsports,
                    title = "Manual",
                    subtitle = "Drive the robot",
                    subtitleColor = ManualOrange,
                    onClick = { autonomousMode = false }
                )
                ActionCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Autorenew,
                    title = "Auto Mode",
                    subtitle = "Mow on its own",
                    subtitleColor = AccentGreen,
                    isActive = autonomousMode,
                    onClick = { autonomousMode = !autonomousMode }
                )
            }
        }
    }
}

@Composable
private fun ActionCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    subtitleColor: Color,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = if (isActive) AccentGreen else CardBackground,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (isActive) Color.White.copy(alpha = 0.25f) else LightGreenChip),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (isActive) Color.White else AccentGreen
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (isActive) Color.White else Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (isActive) "Running" else subtitle,
                fontSize = 12.sp,
                color = if (isActive) Color.White.copy(alpha = 0.85f) else subtitleColor
            )
        }
    }
}

/**
 * Small battery glyph that fills according to [level] (0-100) and changes
 * color depending on how charged the robot is. Drawn with Canvas so it
 * doesn't depend on any external icon set.
 */
@Composable
private fun BatteryIndicator(level: Int, modifier: Modifier = Modifier) {
    val clampedLevel = level.coerceIn(0, 100)
    val fillColor = when {
        clampedLevel > 50 -> AccentGreen
        clampedLevel > 20 -> Color(0xFFE6A400)
        else -> Color(0xFFD32F2F)
    }

    Canvas(modifier = modifier.size(width = 22.dp, height = 12.dp)) {
        val bodyWidth = size.width * 0.85f
        val nubWidth = size.width * 0.15f
        val strokeWidth = 1.5.dp.toPx()

        drawRoundRect(
            color = Color.DarkGray,
            topLeft = Offset(0f, 0f),
            size = Size(bodyWidth, size.height),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx()),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
        )

        drawRoundRect(
            color = Color.DarkGray,
            topLeft = Offset(bodyWidth + 1.dp.toPx(), size.height * 0.25f),
            size = Size(nubWidth, size.height * 0.5f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.dp.toPx())
        )

        val inset = strokeWidth
        val maxFillWidth = bodyWidth - inset * 2
        val fillWidth = maxFillWidth * (clampedLevel / 100f)
        drawRoundRect(
            color = fillColor,
            topLeft = Offset(inset, inset),
            size = Size(fillWidth, size.height - inset * 2),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RobotHomeScreenPreview() {
    RoboticAppTheme() {
        RobotHomeScreen()
    }
}

