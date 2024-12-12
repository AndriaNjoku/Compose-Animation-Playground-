package com.andrianjoku.composeanimationplayground.interactiveNodeGraph

import androidx.compose.ui.geometry.Offset
import com.andrianjoku.composeanimationplayground.R

data class Node(
    val id: String,
    val name: String,
    var position: Offset = Offset.Zero,
    var connections: List<String> = listOf(), // Node IDs this node is connected to
    val distanceFromCenter: Float,
    var visibility: NodeVisibility = NodeVisibility.NORMAL,
    val positiveKarma: Int = 0,
    val profileImage: Int = R.drawable.p1,
    var generation: Int = 1,
) {

    /**
     * Directly connected to the selected node.
     */
    fun isFirsGen(selectedNode: Node): Boolean {
        return connections.contains(selectedNode.id) || id == "1"
    }
}

enum class NodeVisibility {
    NORMAL, // Fully visible
    DIMMED, // Grayed out
    HIDDEN  // Not rendered
}

