package com.andrianjoku.composeanimationplayground.examples

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun OffsetExample() {
    var moved by remember { mutableStateOf(false) }
    val offsetX by animateIntAsState(
        targetValue = if (moved) 200 else 0,
        animationSpec = tween(durationMillis = 600)
    )
    val offsetY by animateIntAsState(
        targetValue = if (moved) 400 else 0,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX, offsetY) }
            .size(100.dp)
            .background(Color(0xFFE91E63))
            .clickable { moved = !moved },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Move Me",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun OffsetExamplePreview() {
    OffsetExample()
}