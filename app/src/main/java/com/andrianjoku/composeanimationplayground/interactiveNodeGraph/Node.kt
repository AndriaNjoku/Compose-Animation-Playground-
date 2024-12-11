package com.andrianjoku.composeanimationplayground.interactiveNodeGraph

import androidx.compose.ui.geometry.Offset
import com.andrianjoku.composeanimationplayground.R

data class Node(
    val id: String,
    val label: String,
    var position: Offset = Offset.Zero,
    var connections: List<String> = listOf(), // Node IDs this node is connected to
    val distanceFromCenter: Float,
    var visibility: NodeVisibility = NodeVisibility.NORMAL,
    val positiveKarma: Int = 0,
    val profileImage: Int = R.drawable.p1,
    ) {

    fun isFirsGen(): Boolean {
        return connections.contains("1")
    }
}

enum class NodeVisibility {
    NORMAL, // Fully visible
    DIMMED, // Grayed out
    HIDDEN  // Not rendered
}

