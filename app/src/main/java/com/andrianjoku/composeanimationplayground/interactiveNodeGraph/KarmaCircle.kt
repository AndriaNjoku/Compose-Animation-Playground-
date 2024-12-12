package com.andrianjoku.composeanimationplayground.interactiveNodeGraph

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
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
import com.andrianjoku.composeanimationplayground.R
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun NodeRelationGraph() {

    // 1. Data Setup & State
    val nodesState = remember { mutableStateOf(createSampleNodes()) }
    var selectedNode by remember { mutableStateOf(nodesState.value.first()) }

    // When we click through generations 1st -> 2nd etc then the parent who makes this jump remains
    // selected as a route back up through the generations.

    var nodeParentState by remember { mutableStateOf(emptyList<Node>()) }


    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        // 2. Layout Computations
        val screenCenter = Offset(maxWidth.toPx() / 2, maxHeight.toPx() / 2)
        computeNodePositions(nodesState.value)
        computeSecondGenPositions(nodesState.value)

        // Update visibility based on selected node
        LaunchedEffect(selectedNode) {
            updateNodeVisibility(
                nodes = nodesState.value,
                selectedNode = selectedNode
            )

            //if the node has children we are ascending through the generations
            // we therefore want to keep these nodes selected
            nodesState.value.filter { it.id == "1" }
                .find { it.connections.contains(selectedNode.id) }?.let {
                nodeParentState = nodeParentState + selectedNode

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
                drawNodes(nodesState.value, centerOffset, selectedNode)
            }

            // 5. Overlays: User Node Image & Selected Node UI
            DrawNodeImage(
                nodeParentState,
                nodesState.value.filter { it.isFirsGen(selectedNode) } // Filter for first-generation nodes
                    .sortedByDescending { it.positiveKarma } // Sort by positiveKarma in descending order
                    .take(5),
                centerOffset
            )
            DrawSelectedNodeOverlay(selectedNode)
        }
    }
}

/**
 * Creates a sample list of nodes representing the graph.
 */
private fun createSampleNodes(): List<Node> {
    return listOf(
        Node(
            id = "1",
            name = "Michael Johnson",
            position = Offset.Zero,
            connections = listOf("2", "3", "4", "5", "6", "7", "8"),
            distanceFromCenter = 0f,
            profileImage = R.drawable.profile1,
            positiveKarma = 85
        ),
        Node(
            id = "2",
            name = "Emily Davis",
            position = Offset.Zero,
            connections = listOf("1", "9", "10"),
            distanceFromCenter = 600f,
            profileImage = R.drawable.profile2,
            positiveKarma = 72
        ),
        Node(
            id = "3",
            name = "James Wilson",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile3,
            positiveKarma = 50
        ),
        Node(
            id = "4",
            name = "Sophia Brown",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 400f,
            profileImage = R.drawable.profile4,
            positiveKarma = 60
        ),
        Node(
            id = "5",
            name = "Oliver Martinez",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile5,
            positiveKarma = 90
        ),
        Node(
            id = "6",
            name = "Ava Garcia",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 700f,
            profileImage = R.drawable.profile6,
            positiveKarma = 45
        ),
        Node(
            id = "7",
            name = "Liam Anderson",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 300f,
            profileImage = R.drawable.profile7,
            positiveKarma = 68
        ),
        Node(
            id = "8",
            name = "Mia Thompson",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 400f,
            profileImage = R.drawable.profile8,
            positiveKarma = 55
        ),
        Node(
            id = "9",
            name = "Noah White",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 400f,
            profileImage = R.drawable.profile9,
            positiveKarma = 30
        ),
        Node(
            id = "10",
            name = "Isabella Harris",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 400f,
            profileImage = R.drawable.profile10,
            positiveKarma = 35
        ),
        Node(
            id = "11",
            name = "Ethan Clark",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 400f,
            profileImage = R.drawable.profile1,
            positiveKarma = 40
        ),
        Node(
            id = "12",
            name = "Charlotte Lewis",
            position = Offset.Zero,
            connections = listOf("1"),
            distanceFromCenter = 400f,
            profileImage = R.drawable.profile2,
            positiveKarma = 25
        ),
        Node(
            id = "13",
            name = "Amelia Hall",
            position = Offset.Zero,
            connections = listOf("2"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile3,
            positiveKarma = 65
        ),
        Node(
            id = "14",
            name = "Alexander Young",
            position = Offset.Zero,
            connections = listOf("2"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile4,
            positiveKarma = 42
        ),
        Node(
            id = "15",
            name = "Harper King",
            position = Offset.Zero,
            connections = listOf("2"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile5,
            positiveKarma = 75
        ),
        Node(
            id = "16",
            name = "Lucas Wright",
            position = Offset.Zero,
            connections = listOf("2"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile6,
            positiveKarma = 80
        ),
        Node(
            id = "17",
            name = "Ella Scott",
            position = Offset.Zero,
            connections = listOf("2"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile7,
            positiveKarma = 50
        ),
        Node(
            id = "18",
            name = "Mason Green",
            position = Offset.Zero,
            connections = listOf("2"),
            distanceFromCenter = 600f,
            profileImage = R.drawable.profile8,
            positiveKarma = 70
        ),
        Node(
            id = "19",
            name = "Avery Adams",
            position = Offset.Zero,
            connections = listOf("2"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile9,
            positiveKarma = 88
        ),
        Node(
            id = "20",
            name = "Logan Perez",
            position = Offset.Zero,
            connections = listOf("2"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile10,
            positiveKarma = 55
        ),
        Node(
            id = "21",
            name = "Evelyn Roberts",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 900f,
            profileImage = R.drawable.profile1,
            positiveKarma = 38
        ),
        Node(
            id = "22",
            name = "Jackson Turner",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 600f,
            profileImage = R.drawable.profile2,
            positiveKarma = 45
        ),
        Node(
            id = "23",
            name = "Luna Phillips",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 400f,
            profileImage = R.drawable.profile3,
            positiveKarma = 62
        ),
        Node(
            id = "24",
            name = "Sebastian Campbell",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile4,
            positiveKarma = 72
        ),
        Node(
            id = "25",
            name = "Grace Parker",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile5,
            positiveKarma = 85
        ),
        Node(
            id = "26",
            name = "Ella Mitchell",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 800f,
            profileImage = R.drawable.profile6,
            positiveKarma = 93
        ),
        Node(
            id = "27",
            name = "Henry Bailey",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 700f,
            profileImage = R.drawable.profile7,
            positiveKarma = 77
        ),
        Node(
            id = "28",
            name = "Scarlett Rivera",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 400f,
            profileImage = R.drawable.profile8,
            positiveKarma = 35
        ),
        Node(
            id = "29",
            name = "Daniel Cooper",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 500f,
            profileImage = R.drawable.profile9,
            positiveKarma = 48
        ),
        Node(
            id = "30",
            name = "Aria Watson",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 800f,
            profileImage = R.drawable.profile10,
            positiveKarma = 63
        ),
        Node(
            id = "31",
            name = "Levi Brooks",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 700f,
            profileImage = R.drawable.profile1,
            positiveKarma = 52
        ),
        Node(
            id = "32",
            name = "Victoria Foster",
            position = Offset.Zero,
            connections = listOf("3"),
            distanceFromCenter = 400f,
            profileImage = R.drawable.profile2,
            positiveKarma = 40
        )
    )
}

/**
 * Positions first-generation nodes around the center and others around their parent.
 */
private fun computeNodePositions(nodes: List<Node>) {
    val totalNodes = nodes.size
    val nonCenterNodesCount = totalNodes - 1
    val angleStep = if (nonCenterNodesCount > 0) 360f / nonCenterNodesCount else 0f

    nodes.forEachIndexed { index, node ->
        if (node.id == "1") {
            node.position = Offset.Zero
        } else {
            val angle = index * angleStep
            val radians = Math.toRadians(angle.toDouble())
            val isFirstGen = node.connections.contains("1")
            val origin = if (isFirstGen) Offset.Zero else findParentNodePosition(nodes, node)

            val x = origin.x + (node.distanceFromCenter * cos(radians)).toFloat()
            val y = origin.y + (node.distanceFromCenter * sin(radians)).toFloat()
            node.position = Offset(x, y)
        }
    }
}

/**
 * Positions second-generation nodes around their parent node.
 */
private fun computeSecondGenPositions(nodes: List<Node>) {
    nodes.forEach { parentNode ->
        val secondGenNodes = nodes.filter {
            it.connections.contains(parentNode.id) && it.id != "1" && it.id != parentNode.id
        }

        if (secondGenNodes.isNotEmpty()) {
            val count = secondGenNodes.size
            secondGenNodes.forEachIndexed { i, childNode ->
                val angle = i * (360f / count)
                val radians = Math.toRadians(angle.toDouble())
                val px =
                    parentNode.position.x + (childNode.distanceFromCenter * cos(radians)).toFloat()
                val py =
                    parentNode.position.y + (childNode.distanceFromCenter * sin(radians)).toFloat()
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
                NodeVisibility.HIDDEN
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
 * Finds a parent node position for non-first-gen nodes.
 */
private fun findParentNodePosition(nodes: List<Node>, node: Node): Offset {
    val parentId = node.connections.find { it != "1" } ?: "1"
    return nodes.find { it.id == parentId }?.position ?: Offset.Zero
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
                            color = if (node.positiveKarma >= 50.0) {
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
                        // Future logic if needed
                    }

                    NodeVisibility.HIDDEN -> {
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
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawNodes(
    nodes: List<Node>,
    centerOffset: Offset,
    selectedNode: Node
) {
    nodes.forEach { node ->
        val nodeCenter = node.position + centerOffset
        when (node.visibility) {
            NodeVisibility.NORMAL -> {
                val radius = if (node == selectedNode) {
                    200f
                } else {
                    (60f / 100) + node.positiveKarma.toFloat()
                }
                drawCircle(
                    color = if (node.positiveKarma <= 50) Color.Red else Color.Green,
                    radius = radius,
                    center = nodeCenter
                )
            }

            NodeVisibility.DIMMED -> {
                // Future logic if needed
            }

            NodeVisibility.HIDDEN -> {
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
    (parentNodes + nodes).forEach { node ->
        val parentImageOffset = node.position + centerOffset
        val parentImageOffsetX = with(density) { parentImageOffset.x.toDp() - (node.positiveKarma / 2).dp }
        val parentImageOffsetY = with(density) { parentImageOffset.y.toDp() - (node.positiveKarma / 2).dp }

        Box(
            modifier = Modifier
                .offset(parentImageOffsetX, parentImageOffsetY),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = node.profileImage),
                contentDescription = null,
                modifier = Modifier
                    .size((node.positiveKarma).dp) // Ensure size reflects karma
                    .clip(CircleShape)
            )
        }
    }
}

/**
 * Draws the overlay that shows the currently selected node’s image and a label ("Blah").
 * This remains fixed at a certain offset on the screen.
 */
@Composable
private fun DrawSelectedNodeOverlay(selectedNode: Node) {
    Box(
        modifier = Modifier
            .offset(145.dp, 350.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = selectedNode.profileImage),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
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

private fun Offset.getDistance(): Float = sqrt(x * x + y * y)

@Preview
@Composable
fun preview() {
    NodeRelationGraph()
}
