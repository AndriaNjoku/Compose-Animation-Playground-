package com.andrianjoku.composeanimationplayground.pdp

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.andrianjoku.composeanimationplayground.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddToBagBottomSheet(onAddToBagClicked: () -> Unit, onClose: () -> Unit) {

    var isComplete by remember { mutableStateOf(false) }

    var selectedSize by remember { mutableStateOf<String?>(null) } // Selected size state
    var startAnimation by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(false) }

    val productCardOffset = remember {
        androidx.compose.animation.core.Animatable(
            Offset.Zero,
            Offset.VectorConverter
        )
    }

    val productCardAlpha = remember { androidx.compose.animation.core.Animatable(1f) } // Alpha for fade-out
    val productCardWidth = remember { androidx.compose.animation.core.Animatable(1f) } // Width factor
    val modalHeight = remember { androidx.compose.animation.core.Animatable(380f) } // Modal height

    LaunchedEffect(startAnimation) {
        if (startAnimation) {
            // Launch animations concurrently
            launch {
                modalHeight.animateTo(
                    targetValue = 280f, // Lower the modal height
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }

            launch {
                productCardAlpha.animateTo(
                    targetValue = 0f, // Fade out
                    animationSpec = tween(durationMillis = 600)
                )
                productCardWidth.animateTo(
                    targetValue = 0.5f, // Shrink width to 50%
                    animationSpec = tween(durationMillis = 600)
                )
            }

            productCardOffset.animateTo(
                targetValue = Offset(x = 0f, y = 200f), // Move the product card down
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            // Show loading indicator and reset product card
            showLoading = true
            productCardAlpha.snapTo(1f) // Reset visibility
            productCardWidth.snapTo(1f) // Reset width
            productCardOffset.snapTo(Offset.Zero) // Reset position
            modalHeight.snapTo(500f) // Reset modal height

            // Allow loading indicator to display for a moment
            kotlinx.coroutines.delay(500L)
            showLoading = false // Hide loading after delay
            isComplete = true // Set to true after delay
        }
    }

    if (showLoading) {
        // Show loading indicator during the transition
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(modalHeight.value.dp) // Reflect the reduced height
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    } else {
        // Main bottom sheet content
        Column(
            modifier = Modifier
                .height(modalHeight.value.dp) // Adjust modal height dynamically
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Product card with fading and shrinking width animation
            ProductCard(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            productCardOffset.value.x.toInt(),
                            productCardOffset.value.y.toInt()
                        )
                    }
                    .alpha(productCardAlpha.value) // Fade out
                    .fillMaxWidth(productCardWidth.value), // Shrink width
                imagePainter = painterResource(id = R.drawable.profile),
                title = "Product Title",
                subtitle = "Product Subtitle",
                price = "$19.99"
            )

            // Animated visibility for "Select Size" label and size selector
            AnimatedVisibility(visible = !startAnimation) {
                Column {
                    // Header with "Select Size" and close button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Select Size", style = MaterialTheme.typography.titleLarge)
                        IconButton(onClick = onClose) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    // Size selection buttons
                    val sizes = listOf("S", "M", "L", "XL")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        sizes.forEach { size ->
                            Button(
                                onClick = { selectedSize = size },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedSize == size) Color.Black else Color.White,
                                    contentColor = if (selectedSize == size) Color.White else Color.Black,
                                    disabledContainerColor = Color.Gray
                                ),
                                shape = RectangleShape
                            ) {
                                Text(text = size)
                            }
                        }
                    }
                }
            }

            // Add to Bag button with animated visibility
            AnimatedVisibility(
                visible = selectedSize != null || startAnimation,
                enter = fadeIn(animationSpec = tween(300)) + slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                ),
                exit = fadeOut()
            ) {
                Row(Modifier.fillMaxWidth()) {
                    AddToBagButton(
                        Modifier.weight(1f),
                        isComplete
                    ) {
                        startAnimation = true
                    }
                }
            }
            if (isComplete){
                OthersAlsoBoughtSection()
            }

        }
    }
}

