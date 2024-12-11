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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
fun KarmaCircle() {
    // Sample nodes representing the graph
    val nodes = remember {
        mutableStateOf(
            listOf(
                Node(
                    "1",
                    "Center",
                    Offset.Zero,
                    listOf("2", "3", "4", "5", "6", "7", "8"),
                    0f,
                    profileImage = R.drawable.p1,
                    positiveKarma = 85
                ),
                Node(
                    "2",
                    "Node 2",
                    Offset.Zero,
                    listOf("1", "9", "10"),
                    600f,
                    profileImage = R.drawable.p2,
                    positiveKarma = 72
                ),
                Node(
                    "3",
                    "Node 3",
                    Offset.Zero,
                    listOf("1"),
                    500f,
                    profileImage = R.drawable.p3,
                    positiveKarma = 50
                ),
                Node(
                    "4",
                    "Node 4",
                    Offset.Zero,
                    listOf("1"),
                    400f,
                    profileImage = R.drawable.p2,
                    positiveKarma = 60
                ),
                Node(
                    "5",
                    "Center",
                    Offset.Zero,
                    listOf("1"),
                    500f,
                    profileImage = R.drawable.p5,
                    positiveKarma = 90
                ),
                Node(
                    "6",
                    "Node 2",
                    Offset.Zero,
                    listOf("1"),
                    700f,
                    profileImage = R.drawable.p6,
                    positiveKarma = 45
                ),
                Node(
                    "7",
                    "Node 3",
                    Offset.Zero,
                    listOf("1"),
                    300f,
                    profileImage = R.drawable.p7,
                    positiveKarma = 68
                ),
                Node("8", "Node 4", Offset.Zero, listOf("1"), 400f, positiveKarma = 55),
                Node("11", "Node 4", Offset.Zero, listOf("1"), 400f, positiveKarma = 30),
                Node("12", "Node 4", Offset.Zero, listOf("1"), 400f, positiveKarma = 35),
                Node("13", "Node 4", Offset.Zero, listOf("1"), 400f, positiveKarma = 40),
                Node("14", "Node 4", Offset.Zero, listOf("1"), 400f, positiveKarma = 25),
                Node("15", "Node 4", Offset.Zero, listOf("2"), 500f, positiveKarma = 65),
                Node("16", "Node 4", Offset.Zero, listOf("2"), 500f, positiveKarma = 42),
                Node("17", "Node 4", Offset.Zero, listOf("2"), 500f, positiveKarma = 75),
                Node("18", "Node 4", Offset.Zero, listOf("2"), 500f, positiveKarma = 80),
                Node("19", "Node 4", Offset.Zero, listOf("2"), 500f, positiveKarma = 50),
                Node("20", "Node 4", Offset.Zero, listOf("2"), 600f, positiveKarma = 70),
                Node("21", "Node 4", Offset.Zero, listOf("2"), 500f, positiveKarma = 88),
                Node("22", "Node 4", Offset.Zero, listOf("2"), 500f, positiveKarma = 55),
                Node("23", "Node 4", Offset.Zero, listOf("3"), 900f, positiveKarma = 38),
                Node("24", "Node 4", Offset.Zero, listOf("3"), 600f, positiveKarma = 45),
                Node("25", "Node 4", Offset.Zero, listOf("3"), 400f, positiveKarma = 62),
                Node("26", "Node 4", Offset.Zero, listOf("3"), 500f, positiveKarma = 72),
                Node("27", "Node 4", Offset.Zero, listOf("3"), 500f, positiveKarma = 85),
                Node("28", "Node 4", Offset.Zero, listOf("3"), 800f, positiveKarma = 93),
                Node("29", "Node 4", Offset.Zero, listOf("3"), 700f, positiveKarma = 77),
                Node("30", "Node 4", Offset.Zero, listOf("3"), 400f, positiveKarma = 35),
                Node("31", "Node 4", Offset.Zero, listOf("3"), 500f, positiveKarma = 48),
                Node("32", "Node 4", Offset.Zero, listOf("3"), 800f, positiveKarma = 63),
                Node("33", "Node 4", Offset.Zero, listOf("3"), 700f, positiveKarma = 52),
                Node("34", "Node 4", Offset.Zero, listOf("3"), 400f, positiveKarma = 40)
            )
        )
    }

    var selectedNode by remember { mutableStateOf(nodes.value.first()) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenCenter = Offset(maxWidth.toPx() / 2, maxHeight.toPx() / 2)
        val totalNodes = nodes.value.size
        val nonCenterNodesCount = totalNodes - 1
        val angleStep = if (nonCenterNodesCount > 0) 360f / nonCenterNodesCount else 0f

        // Position the nodes
        nodes.value.forEachIndexed { index, node ->
            if (node.id == "1") {
                node.position = Offset.Zero
            } else {
                val angle = index * angleStep
                val radians = Math.toRadians(angle.toDouble())
                val isFirstGen = node.connections.contains("1")
                val origin = if (isFirstGen) {
                    Offset.Zero
                } else {
                    val parentId = node.connections.find { it != "1" } ?: "1"
                    nodes.value.find { it.id == parentId }?.position ?: Offset.Zero
                }

                val x = origin.x + (node.distanceFromCenter * cos(radians)).toFloat()
                val y = origin.y + (node.distanceFromCenter * sin(radians)).toFloat()
                node.position = Offset(x, y)
            }
        }

        val centerNode = nodes.value.find { it.id == "1" }

        // Position second-generation nodes
        nodes.value.forEach { parentNode ->
            val secondGenNodes = nodes.value.filter {
                it.connections.contains(parentNode.id) && it.id != "1" && it.id != parentNode.id
            }

            if (secondGenNodes.isNotEmpty() && centerNode != null) {
                val secondGenCount = secondGenNodes.size
                secondGenNodes.forEachIndexed { i, childNode ->
                    val angle = i * (360f / secondGenCount)
                    val radians = Math.toRadians(angle.toDouble())
                    val px =
                        parentNode.position.x + (childNode.distanceFromCenter * cos(radians)).toFloat()
                    val py =
                        parentNode.position.y + (childNode.distanceFromCenter * sin(radians)).toFloat()
                    childNode.position = Offset(px, py)
                }
            }
        }

        LaunchedEffect(selectedNode) {
            nodes.value.forEach { node ->
                node.visibility =
                    if (node.connections.contains(selectedNode.id) || node == selectedNode) {
                        NodeVisibility.NORMAL
                    } else {
                        NodeVisibility.HIDDEN
                    }
            }
        }

        val centerOffset by animateOffsetAsState(
            targetValue = screenCenter - selectedNode.position,
            animationSpec = tween(
                durationMillis = 600,
                easing = androidx.compose.animation.core.EaseInOut
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { tapOffset ->
                        val tappedNode = nodes.value.minByOrNull {
                            (it.position + centerOffset - tapOffset).getDistance()
                        }
                        if (tappedNode != null) {
                            selectedNode = tappedNode
                        }
                    })
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Draw connections
                nodes.value.forEach { node ->
                    node.connections.forEach { linkId ->
                        val targetNode = nodes.value.find { it.id == linkId }
                        if (targetNode != null) {
                            val start = node.position + centerOffset
                            val end = targetNode.position + centerOffset
                            val strokeWidth = if (node == selectedNode) 3.dp.toPx() else 1.dp.toPx()

                            when (node.visibility) {
                                NodeVisibility.NORMAL -> {
                                    drawLine(
                                        color = Color.Gray,
                                        start = start,
                                        end = end,
                                        strokeWidth = strokeWidth
                                    )
                                }

                                NodeVisibility.DIMMED -> {
                                    // If there's logic for DIMMED in the future, place it here.
                                }

                                NodeVisibility.HIDDEN -> {
                                    drawLine(
                                        color = Color.Gray,
                                        start = start,
                                        end = end,
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                            }
                        }
                    }
                }

                // Draw nodes
                nodes.value.forEach { node ->
                    val nodeCenter = node.position + centerOffset
                    when (node.visibility) {
                        NodeVisibility.NORMAL -> {
                            val radius = if (node == selectedNode) {
                                200f
                            } else {
                                (60f / 100) + 1 * node.positiveKarma.toFloat()
                            }
                            drawCircle(
                                color = if (node == selectedNode) Color.Red else Color.Blue,
                                radius = radius,
                                center = nodeCenter
                            )
                        }

                        NodeVisibility.DIMMED -> {
                            // If there's logic for DIMMED in the future, place it here.
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

            Box(
                modifier = Modifier
                    .offset(155f.dp, 365f.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = selectedNode.profileImage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }
                Text(text = "Blah")
            }
        }
    }
}

@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current
    return with(density) { this@toPx.toPx() }
}

private fun Offset.getDistance(): Float {
    return sqrt(x * x + y * y)
}

@Preview
@Composable
fun preview() {
    KarmaCircle()
}