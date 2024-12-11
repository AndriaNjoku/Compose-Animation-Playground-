package com.andrianjoku.composeanimationplayground.examples

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GraphicsLayerExample() {
    var isTransformed by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isTransformed, label = "Transformation")

    val scale by transition.animateFloat(
        label = "Scale",
        transitionSpec = { tween(durationMillis = 500) }
    ) { state -> if (state) 1.2f else 1f }

    val rotation by transition.animateFloat(
        label = "Rotation",
        transitionSpec = { tween(durationMillis = 500) }
    ) { state -> if (state) 360f else 0f }

    val alpha by transition.animateFloat(
        label = "Alpha",
        transitionSpec = { tween(durationMillis = 500) }
    ) { state -> if (state) 0.5f else 1f }

    Box(
        modifier = Modifier
            .size(100.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                alpha = alpha
            )
            .background(Color(0xFF2196F3))
            .clickable { isTransformed = !isTransformed },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tap Me",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun GraphicsLayerExamplePreview() {
    GraphicsLayerExample()
}