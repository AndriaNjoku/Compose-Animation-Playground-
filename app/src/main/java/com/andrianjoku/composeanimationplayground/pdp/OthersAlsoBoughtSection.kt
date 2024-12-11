package com.andrianjoku.composeanimationplayground.pdp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrianjoku.composeanimationplayground.R

@Composable
fun OthersAlsoBoughtSection() {
    val items = listOf(
        Pair(R.drawable.profile, "£39.50"),
        Pair(R.drawable.profile, "£27.50"),
        Pair(R.drawable.profile, "£49.99"),
        Pair(R.drawable.profile, "£24.99")
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        // Title
        Text(
            text = "Others Also Bought",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Lazy Horizontal Grid
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items) { (imageRes, price) ->
                Column(
                    modifier = Modifier
                        .width(150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Image Placeholder
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .size(150.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Price
                    Text(
                        text = price,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OthersAlsoBoughtSectionPreview() {
    OthersAlsoBoughtSection()
}