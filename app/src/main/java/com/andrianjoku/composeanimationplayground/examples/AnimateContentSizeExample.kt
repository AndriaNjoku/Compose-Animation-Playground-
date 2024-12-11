package com.andrianjoku.composeanimationplayground.examples

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimateContentSizeExample() {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0xFFE0E0E0))
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = "Tap to ${if (expanded) "Collapse" else "Expand"}",
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )
        if (expanded) {
            Text(
                text = "This is the expanded content. It will smoothly animate the size change when this text appears or disappears.",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun AnimateContentSizeExamplePreview() {
    AnimateContentSizeExample()
}