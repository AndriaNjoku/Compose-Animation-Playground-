package com.andrianjoku.composeanimationplayground.interactiveNodeGraph

import androidx.compose.ui.geometry.Offset
import com.andrianjoku.composeanimationplayground.R
import java.util.LinkedList
import java.util.Queue

object Helper {
    /**
     * Creates a sample list of nodes representing the graph.
     */
    fun createSampleNodes(): List<Node> {
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
                id = "11",
                name = "Noah White",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile9,
                positiveKarma = 30
            ),
            Node(
                id = "12",
                name = "Isabella Harris",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile10,
                positiveKarma = 35
            ),
            Node(
                id = "13",
                name = "Ethan Clark",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile1,
                positiveKarma = 40
            ),
            Node(
                id = "14",
                name = "Charlotte Lewis",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile2,
                positiveKarma = 25
            ),
            Node(
                id = "15",
                name = "Amelia Hall",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile3,
                positiveKarma = 65
            ),
            Node(
                id = "16",
                name = "Alexander Young",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile4,
                positiveKarma = 42
            ),
            Node(
                id = "17",
                name = "Harper King",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile5,
                positiveKarma = 75
            ),
            Node(
                id = "18",
                name = "Lucas Wright",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile6,
                positiveKarma = 80
            ),
            Node(
                id = "19",
                name = "Ella Scott",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile7,
                positiveKarma = 50
            ),
            Node(
                id = "20",
                name = "Mason Green",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile8,
                positiveKarma = 70
            ),
            Node(
                id = "21",
                name = "Avery Adams",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile9,
                positiveKarma = 88
            ),
            Node(
                id = "22",
                name = "Logan Perez",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile10,
                positiveKarma = 55
            ),
            Node(
                id = "23",
                name = "Evelyn Roberts",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 900f,
                profileImage = R.drawable.profile1,
                positiveKarma = 38
            ),
            Node(
                id = "24",
                name = "Jackson Turner",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile2,
                positiveKarma = 45
            ),
            Node(
                id = "25",
                name = "Luna Phillips",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile3,
                positiveKarma = 62
            ),
            Node(
                id = "26",
                name = "Sebastian Campbell",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile4,
                positiveKarma = 72
            ),
            Node(
                id = "27",
                name = "Grace Parker",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile5,
                positiveKarma = 85
            ),
            Node(
                id = "28",
                name = "Ella Mitchell",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 800f,
                profileImage = R.drawable.profile6,
                positiveKarma = 93
            ),
            Node(
                id = "29",
                name = "Henry Bailey",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 700f,
                profileImage = R.drawable.profile7,
                positiveKarma = 77
            ),
            Node(
                id = "30",
                name = "Scarlett Rivera",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile8,
                positiveKarma = 35
            ),
            Node(
                id = "31",
                name = "Daniel Cooper",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile9,
                positiveKarma = 48
            ),
            Node(
                id = "32",
                name = "Aria Watson",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 800f,
                profileImage = R.drawable.profile10,
                positiveKarma = 63
            ),
            Node(
                id = "33",
                name = "Levi Brooks",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 700f,
                profileImage = R.drawable.profile1,
                positiveKarma = 52
            ),
            Node(
                id = "34",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile2,
                positiveKarma = 40
            ),
            Node(
                id = "35",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("17"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile2,
                positiveKarma = 40
            ),
            Node(
                id = "36",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("17"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile2,
                positiveKarma = 40
            ),
            Node(
                id = "37",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("17"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile2,
                positiveKarma = 60
            )

        )
    }

    /**
     * Assigns generations to nodes based on their connections.
     */
    fun assignGenerations(
        nodes: List<Node>,
        rootId: String = "1"
    ): List<Node> {
        if (nodes.isEmpty()) return emptyList() // Handle empty input

        val nodeMap = nodes.associateBy { it.id }.toMutableMap()
        val queue: Queue<Pair<Node, Int>> = LinkedList()
        val visited = mutableSetOf<String>()

        // Initialize the root node
        val rootNode = nodeMap[rootId] ?: throw IllegalArgumentException("Root node with ID $rootId not found")
        queue.add(Pair(rootNode, 1))
        visited.add(rootId)

        // BFS to assign generations
        while (queue.isNotEmpty()) {
            val (currentNode, currentGeneration) = queue.remove()
            currentNode.generation = currentGeneration

            // Process all connections
            currentNode.connections.forEach { connectionId ->
                if (!visited.contains(connectionId)) {
                    nodeMap[connectionId]?.let { childNode ->
                        queue.add(Pair(childNode, currentGeneration + 1))
                        visited.add(connectionId)
                    }
                }
            }
        }

        // Ensure all nodes are returned even if disconnected
        return nodeMap.values.toList()
    }
}