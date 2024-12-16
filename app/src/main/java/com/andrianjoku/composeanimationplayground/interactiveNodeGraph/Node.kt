package com.andrianjoku.composeanimationplayground.interactiveNodeGraph

import androidx.compose.ui.geometry.Offset
import com.andrianjoku.composeanimationplayground.R

/**
 * Represents a node in the graph.
 *
 * @param id Unique identifier for the node.
 * @param name Name of the node, used for display or identification purposes.
 * @param position The position of the node on the screen, represented as an [Offset]. Defaults to [Offset.Zero].
 * @param connections List of IDs representing other nodes this node is connected to.
 * @param distanceFromCenter The distance from the center node, used to calculate positioning within the graph.
 * @param visibility The visibility status of the node, defined by the [NodeVisibility] enum. Defaults to [NodeVisibility.NORMAL].
 * @param axiom A property representing a numerical value on a scale that can affect a UI property. Defaults to `0`.
 * @param profileImage Resource ID of the profile image associated with the node. Defaults to `R.drawable.p1`.
 */
data class Node(
    val id: String,
    val name: String,
    val description: String = "",
    var position: Offset = Offset.Zero,
    var connections: List<String> = listOf(), // Node IDs this node is connected to
    val distanceFromCenter: Float,
    var visibility: NodeVisibility = NodeVisibility.NORMAL,
    val axiom: Int = 0,
    val profileImage: Int = R.drawable.p1,
    var generation: Int = 1,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    /**
     * Directly connected to the selected node.
     */
    fun isFirsGen(selectedNode: Node): Boolean {
        return connections.contains(selectedNode.id) || id == "1"
    }
}

enum class NodeVisibility {
    NORMAL, // Fully visible
    DIMMED  // Not rendered
}

