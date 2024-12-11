package com.andrianjoku.composeanimationplayground.pdp

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.andrianjoku.composeanimationplayground.R

@Composable
fun WishlistHeartIcon() {
    var isSelected by remember { mutableStateOf(false) }

    // Using keyframes for a more complex animation sequence
    val heartScale by animateFloatAsState(
        targetValue = if (isSelected) 1.5f else 1f, // Controls the end size
        animationSpec = keyframes {
            durationMillis = 1000 // Total animation duration
            1f at 0 // Start at 0% size (optional, can start from 1f directly)
            1.2f at 100 // Quickly scale up
            0.9f at 200 // Bounce to a smaller size
            1.4f at 300 // Overshoot to emphasize effect
            1f at 500 // Settle back to normal or end size
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size((100.dp * heartScale)) // Scale based on heartScale
            .clickable { isSelected = !isSelected }
    ) {
        Image(
            painter = painterResource(id = R.drawable.heart), // Replace with your heart icon resource ID
            contentDescription = "Wishlist Heart Icon",
            modifier = Modifier.size((100.dp * heartScale)) // Apply animation to icon size
        )
    }
}