package com.andrianjoku.composeanimationplayground.pdp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.andrianjoku.composeanimationplayground.R
import kotlinx.coroutines.launch

@Composable
fun AddToBagButton(
    modifier: Modifier = Modifier,
    isComplete: Boolean,
    onClick: () -> Unit,
) {
    var isClicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isClicked) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    var showCheckmark by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Modifier
    Box(
        modifier = modifier
            .padding(16.dp)
            .scale(scale)
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .clickable {
                onClick.invoke()
                isClicked = true
                showCheckmark = true
                // Reset the state after a short delay
                coroutineScope.launch {
                    delay(300) // Duration of bounce effect
                    isClicked = false
                    delay(700) // Duration to keep checkmark visible
                    showCheckmark = false
                }
            }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (isComplete) "Checkout" else "Add to bag",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            // Animated visibility of the checkmark icon
            AnimatedVisibility(
                visible = showCheckmark
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bag), // Replace with your checkmark or bag icon
                    contentDescription = "Added to bag",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp)
                        .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
                )
            }
        }
    }
}