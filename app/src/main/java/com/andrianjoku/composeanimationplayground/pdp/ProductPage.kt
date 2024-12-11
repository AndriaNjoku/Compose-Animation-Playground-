package com.andrianjoku.composeanimationplayground.pdp
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ProductVariant(val color: Color, val price: String)


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProductPage(onAddToBagClicked: () -> Unit) {
    // List of color variants with associated prices
    val variants = listOf(
        ColorVariant(Color.Black, "£8"),
        ColorVariant(Color.Blue, "£10"),
        ColorVariant(Color.Gray, "£12"),
        ColorVariant(Color.White, "£14"),
        ColorVariant(Color.DarkGray, "£16"),
        ColorVariant(Color.Green, "£18")
    )

    var selectedVariant by remember { mutableStateOf(variants.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Action Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Back action */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            IconButton(onClick = { /* Share action */ }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share"
                )
            }
        }

        // Product Image Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Product Image", color = Color.Gray, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Color Selection with Animation
        ColorSelectionAnimation(
            variants = variants,
            selectedVariant = selectedVariant,
            onVariantSelected = { variant ->
                selectedVariant = variant
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Product Information
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            // Product Price with Animation
            AnimatedContent(
                targetState = selectedVariant.price,
                transitionSpec = {
                    (
                            slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()).using(
                        SizeTransform(clip = false)
                    )
                }
            ) { animatedPrice ->
                Text(
                    text = animatedPrice,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "M&S COLLECTION",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Regular Fit Pure Cotton Crew Neck T-Shirt",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add to Bag Button and Wishlist Icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AddToBagButton(
                Modifier.weight(1f),
                false
            ){
                onAddToBagClicked()
            }
            Spacer(modifier = Modifier.width(8.dp))
            WishlistHeartIcon()
        }
    }
}


@Preview
@Composable
fun ProductPagePreview() {
    ProductPage {
        // Handle addToBagClicked event
    }
}