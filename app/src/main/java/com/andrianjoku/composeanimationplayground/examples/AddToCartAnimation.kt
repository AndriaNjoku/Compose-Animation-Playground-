package com.andrianjoku.composeanimationplayground.examples

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.andrianjoku.composeanimationplayground.R
import kotlin.math.roundToInt

@Composable
fun AddToCartAnimation(
    modifier: Modifier = Modifier,
    thumbnail: ImageBitmap,
    cartOffset: Offset, // Position of the cart icon
    onAnimationEnd: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val thumbnailPosition = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

    LaunchedEffect(startAnimation) {
        if (startAnimation) {
            thumbnailPosition.animateTo(
                targetValue = cartOffset,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            onAnimationEnd()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = thumbnail,
            contentDescription = null,
            modifier = Modifier
                .offset {
                    IntOffset(
                        thumbnailPosition.value.x.roundToInt(),
                        thumbnailPosition.value.y.roundToInt()
                    )
                }
                .size(64.dp) // Thumbnail size
        )

        Button(
            onClick = { startAnimation = true },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Add to Cart")
        }
    }
}

@Preview
@Composable
fun preiew(){
    AddToCartAnimation(
        thumbnail = ImageBitmap.imageResource(id = R.drawable.profile),
        cartOffset = Offset(400f, 1000f), // Replace with your cart icon position
        onAnimationEnd = {
            // Handle animation end
        }
    )
}

