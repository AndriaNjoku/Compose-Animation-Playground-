package com.andrianjoku.composeanimationplayground.examples

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp

@Composable
fun AnimateCardExpansionExample() {
    var expanded by remember { mutableStateOf(false) }

    // Animate padding
    val padding by animateDpAsState(
        targetValue = if (expanded) 20.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "Padding Animation"
    )

    // Animate card size
    val height by animateDpAsState(
        targetValue = if (expanded) 200.dp else 100.dp,
        animationSpec = tween(durationMillis = 300),
        label = "Height Animation"
    )

    // Animate color change (bonus!)
    val backgroundColor = if (expanded) Color(0xFF81C784) else Color(0xFF4CAF50)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = padding)
            .clickable { expanded = !expanded }
            .background(backgroundColor, shape = RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(height)
                .background(Color.White, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (expanded) "Expanded Content" else "Tap to Expand",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        if (expanded) {
            Text(
                text = "Additional details can go here!",
                modifier = Modifier.padding(top = 8.dp),
                color = Color.White
            )
        }
    }
}
@Preview
@Composable
fun AnimateDpAsStateExamplePreview() {
    AnimateCardExpansionExample()
}