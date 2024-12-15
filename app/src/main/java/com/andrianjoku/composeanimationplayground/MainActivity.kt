package com.andrianjoku.composeanimationplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.andrianjoku.composeanimationplayground.examples.AddToCartAnimation
import com.andrianjoku.composeanimationplayground.interactiveNodeGraph.NodeRelationGraph
import com.andrianjoku.composeanimationplayground.pdp.ColorSelectionAnimation
import com.andrianjoku.composeanimationplayground.pdp.ProductPage
import com.andrianjoku.composeanimationplayground.ui.theme.ComposeAnimationPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeAnimationPlaygroundTheme {
                NodeRelationGraph()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowcasePDP() {
    ComposeAnimationPlaygroundTheme {
        ProductPage {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowcaseExample() {
    val cartOffset = Offset(400f, 1000f) // Replace with your cart icon position

    AddToCartAnimation(
        thumbnail = ImageBitmap.imageResource(id = R.drawable.profile),
        cartOffset = cartOffset,
        onAnimationEnd = {

        }
    )
}