package com.andrianjoku.composeanimationplayground.pdp

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductPageWithBottomSheet() {
    // Create BottomSheetScaffold state
    val bottomSheetState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            AddToBagBottomSheet(
                onAddToBagClicked = {
                    coroutineScope.launch {
                        bottomSheetState.bottomSheetState.hide() // Close the bottom sheet
                    }
                },
                onClose = {
                    coroutineScope.launch {
                        bottomSheetState.bottomSheetState.hide() // Close when "X" is clicked
                    }
                }
            )
        },
        sheetPeekHeight = 0.dp // Hidden initially
    ) {
        // Main Product Page content
        ProductPage{
            coroutineScope.launch {
                bottomSheetState.bottomSheetState.expand() // Expand the bottom sheet
            }
        }
    }
}

@Preview
@Composable
fun ProductPageWithBottomSheetPreview() {
    ProductPageWithBottomSheet()
}