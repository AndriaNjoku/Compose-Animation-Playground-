package com.andrianjoku.composeanimationplayground.interactiveNodeGraph

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun NodeRelationGraph() {
    // 1. Data Setup & State
    val nodesState = remember { mutableStateOf(Helper.createSampleNodes()) }
    var selectedNode by remember { mutableStateOf(nodesState.value.first()) }
    var nodeParentState: List<Node> by remember { mutableStateOf(emptyList()) }
    var isExpanded by remember { mutableStateOf(false) }


    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        val screenCenter = Offset(maxWidth.toPx() / 2, maxHeight.toPx() / 2)

        computeNodesAroundParents(nodesState.value)

        LaunchedEffect(selectedNode) {
            updateNodeVisibility(
                nodes = nodesState.value,
                selectedNode = selectedNode
            )

            println(" Selected Node is: $selectedNode")

            // Add a delay before expanding the node
            isExpanded = false // Collapse first
            delay(1000) // Adjust delay as needed
            isExpanded = true

            nodesState.value
                .find { it.connections.contains(selectedNode.id) } // Look for children
                ?.let {
                    if (!nodeParentState.contains(selectedNode)) {
                        nodeParentState = nodeParentState + selectedNode // Add to list
                    }
                }
        }

        // Animate camera offset towards the selected node
        val centerOffset by animateOffsetAsState(
            targetValue = screenCenter - selectedNode.position,
            animationSpec = tween(
                durationMillis = 300,
                easing = androidx.compose.animation.core.EaseInOut
            )
        )

        // 3. UI Structure
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { tapOffset ->
                            val tappedNode =
                                findClosestNode(nodesState.value, tapOffset, centerOffset)
                            if (tappedNode != null && tappedNode.isFirsGen(selectedNode) || nodeParentState.contains(
                                    tappedNode
                                )
                            ) {
                                selectedNode = tappedNode!!
                            }
                        }
                    )
                }
        ) {

            // 4. Drawing
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawConnections(nodesState.value, centerOffset, selectedNode)
                drawNodes(nodesState.value, selectedNode, centerOffset)
            }

            // 5. Overlays
            DrawNodeImage(
                nodeParentState,
                nodesState.value.filter { it.isFirsGen(selectedNode) }
                    .sortedByDescending { it.axiom }
                    .take(5),
                centerOffset
            )
            CenteredAnimatedVisibilityOverlay(selectedNode, isExpanded)
        }
    }
}

@Composable
fun CenteredAnimatedVisibilityOverlay(
    selectedNode: Node,
    isExpanded: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center) // Centers everything in the parent Box
    ) {
        // Profile Image (collapsed state)
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier.zIndex(1f) // Ensure the profile image is always above during its visibility
        ) {
            ProfileDetailsCard(selectedNode)
        }
    }
}

/**
 * Card displaying detailed profile information.
 */
@Composable
private fun ProfileDetailsCard(selectedNode: Node) {
    Box(
        modifier = Modifier
            .size(200.dp) // Set the size of the circular container
            .clip(CircleShape) // Make the container circular
            .background(color = Color.LightGray) // Background color for the circle
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Padding to ensure text isn't too close to the edges
            verticalArrangement = Arrangement.Center, // Center the content vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally
        ) {
            Text(
                text = selectedNode.name,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Karma: ${selectedNode.axiom}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Connections: ${selectedNode.connections.size}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = selectedNode.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/**
 * Positions second-generation nodes around their parent node.
 */
private fun computeNodesAroundParents(nodes: List<Node>) {
    nodes.forEach { parentNode ->
        val children = nodes.filter {
            it.connections.contains(parentNode.id) && it.id != "1" && it.id != parentNode.id // excludes root node
        }

        if (children.isNotEmpty()) {
            val count = children.size
            children.forEachIndexed { i, childNode ->
                val angle = i * (360f / count)
                val radians = Math.toRadians(angle.toDouble())
                val px = parentNode.position.x + (childNode.distanceFromCenter * cos(radians)).toFloat()
                val py = parentNode.position.y + (childNode.distanceFromCenter * sin(radians)).toFloat()
                childNode.position = Offset(px, py)
            }
        }
    }
}

/**
 * Updates the visibility of nodes based on the currently selected node.
 */
private fun updateNodeVisibility(nodes: List<Node>, selectedNode: Node) {
    nodes.forEach { node ->
        node.visibility =
            if (node.connections.contains(selectedNode.id) || node == selectedNode || node.id == "1") {
                NodeVisibility.NORMAL
            } else {
                NodeVisibility.DIMMED
            }
    }
}

/**
 * Finds the closest node to a given tap position.
 */
private fun findClosestNode(nodes: List<Node>, tapOffset: Offset, centerOffset: Offset): Node? {
    return nodes.minByOrNull { (it.position + centerOffset - tapOffset).getDistance() }
}

/**
 * Draws connections (lines) between connected nodes on the canvas.
 */
private fun DrawScope.drawConnections(
    nodes: List<Node>,
    centerOffset: Offset,
    selectedNode: Node
) {
    nodes.forEach { node ->
        node.connections.forEach { linkId ->
            val targetNode = nodes.find { it.id == linkId }
            if (targetNode != null) {
                val start = node.position + centerOffset
                val end = targetNode.position + centerOffset
                val strokeWidth = if (node == selectedNode) 3.dp.toPx() else 1.dp.toPx()

                when (node.visibility) {
                    NodeVisibility.NORMAL -> {
                        drawLine(
                            color = if (node.axiom >= 50.0) {
                                Color.Green
                            } else {
                                Color.Red
                            },
                            start = start,
                            end = end,
                            strokeWidth = strokeWidth
                        )
                    }

                    NodeVisibility.DIMMED -> {
                        drawLine(
                            color = Color.Gray,
                            start = start,
                            end = end,
                            strokeWidth = 1.dp.toPx(),
                            alpha = 0.2f
                        )
                    }
                }
            }
        }
    }
}

/**
 * Draws the nodes (circles) on the canvas.
 */
private fun DrawScope.drawNodes(
    nodes: List<Node>,
    selectedNode: Node,
    centerOffset: Offset,
    pulseAnimationProgress: Float = 0f
) {
    nodes.forEach { node ->
        val nodeCenter = node.position + centerOffset
        when (node.visibility) {
            NodeVisibility.NORMAL -> {

                if (node.connections.contains(selectedNode.id)) { // Check if connected
                    val pulseScale = 1f + pulseAnimationProgress * 0.2f // Adjust scale factor
                    val radius = (60f / 100) + node.axiom.toFloat() * pulseScale // Apply scale to radius

                    drawCircle(
                        color = if (node.axiom <= 50) Color.Red else Color.Green,
                        radius = radius,
                        center = nodeCenter
                    )
                } else {
                    drawCircle(
                        color = if (node.axiom <= 50) Color.Red else Color.Green,
                        radius = if (node == selectedNode) {
                            (60f / 100) + node.axiom.toFloat()
                        } else {
                            150f
                        },
                        center = nodeCenter
                    )
                }
            }

            NodeVisibility.DIMMED -> {
                drawCircle(
                    color = Color.Gray,
                    radius = 30f,
                    alpha = 0.5f,
                    center = nodeCenter
                )
            }
        }
    }
}

@Composable
private fun DrawNodeImage(
    parentNodes: List<Node>,
    nodes: List<Node>,
    centerOffset: Offset
) {
    val density = LocalDensity.current

    // Position the parent nodes
    (nodes + parentNodes).forEach { node ->
        val parentImageOffset = node.position + centerOffset
        val parentImageOffsetX =
            with(density) { parentImageOffset.x.toDp() - (node.axiom / 2).dp }
        val parentImageOffsetY =
            with(density) { parentImageOffset.y.toDp() - (node.axiom / 2).dp }

        Box(
            modifier = Modifier
                .offset(parentImageOffsetX, parentImageOffsetY),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = node.profileImage),
                contentDescription = null,
                modifier = Modifier
                    .size((node.axiom).dp) // Ensure size reflects karma
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current
    return with(density) { this@toPx.toPx() }
}

@Preview
@Composable
fun preview() {
    NodeRelationGraph()
}
