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
                axiom = 85
            ),
            Node(
                id = "2",
                name = "Emily Davis",
                position = Offset.Zero,
                connections = listOf("1",),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile2,
                axiom = 72
            ),
            Node(
                id = "3",
                name = "James Wilson",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile3,
                axiom = 100
            ),
            Node(
                id = "4",
                name = "Sophia Brown",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile4,
                axiom = 60
            ),
            Node(
                id = "5",
                name = "Oliver Martinez",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile5,
                axiom = 90
            ),
            Node(
                id = "6",
                name = "Ava Garcia",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 700f,
                profileImage = R.drawable.profile6,
                axiom = 55
            ),
            Node(
                id = "7",
                name = "Liam Anderson",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 300f,
                profileImage = R.drawable.profile7,
                axiom = 68
            ),
            Node(
                id = "8",
                name = "Mia Thompson",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile8,
                axiom = 55
            ),
            Node(
                id = "9",
                name = "Noah White",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile9,
                axiom = 30
            ),
            Node(
                id = "10",
                name = "Isabella Harris",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile10,
                axiom = 35
            ),
            Node(
                id = "11",
                name = "Ethan Clark",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile1,
                axiom = 40
            ),
            Node(
                id = "12",
                name = "Charlotte Lewis",
                position = Offset.Zero,
                connections = listOf("1"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile2,
                axiom = 25
            ),
            Node(
                id = "13",
                name = "Amelia Hall",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile3,
                axiom = 65
            ),
            Node(
                id = "14",
                name = "Alexander Young",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile4,
                axiom = 42
            ),
            Node(
                id = "15",
                name = "Harper King",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 700f,
                profileImage = R.drawable.profile5,
                axiom = 75
            ),
            Node(
                id = "16",
                name = "Lucas Wright",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile6,
                axiom = 80
            ),
            Node(
                id = "17",
                name = "Ella Scott",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile7,
                axiom = 50
            ),
            Node(
                id = "18",
                name = "Mason Green",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile8,
                axiom = 70
            ),
            Node(
                id = "19",
                name = "Avery Adams",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile9,
                axiom = 88
            ),
            Node(
                id = "20",
                name = "Logan Perez",
                position = Offset.Zero,
                connections = listOf("2"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile10,
                axiom = 55
            ),
            Node(
                id = "23",
                name = "Evelyn Roberts",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 900f,
                profileImage = R.drawable.profile1,
                axiom = 38
            ),
            Node(
                id = "24",
                name = "Jackson Turner",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile2,
                axiom = 45
            ),
            Node(
                id = "25",
                name = "Luna Phillips",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile3,
                axiom = 62
            ),
            Node(
                id = "26",
                name = "Sebastian Campbell",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile4,
                axiom = 72
            ),
            Node(
                id = "27",
                name = "Grace Parker",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile5,
                axiom = 85
            ),
            Node(
                id = "28",
                name = "Ella Mitchell",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 800f,
                profileImage = R.drawable.profile6,
                axiom = 93
            ),
            Node(
                id = "29",
                name = "Henry Bailey",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 700f,
                profileImage = R.drawable.profile7,
                axiom = 77
            ),
            Node(
                id = "30",
                name = "Scarlett Rivera",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile8,
                axiom = 35
            ),
            Node(
                id = "31",
                name = "Daniel Cooper",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile9,
                axiom = 48
            ),
            Node(
                id = "32",
                name = "Aria Watson",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 800f,
                profileImage = R.drawable.profile10,
                axiom = 63
            ),
            Node(
                id = "33",
                name = "Levi Brooks",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 700f,
                profileImage = R.drawable.profile1,
                axiom = 52
            ),
            Node(
                id = "34",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("3"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile2,
                axiom = 40
            ),
            Node(
                id = "35",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("17"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile3,
                axiom = 40
            ),
            Node(
                id = "36",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("17"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile4,
                axiom = 40
            ),
            Node(
                id = "37",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("17"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile5,
                axiom = 60
            ),
            Node(
                id = "38",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("17"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile6,
                axiom = 60
            ),
            Node(
                id = "39",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("17"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile7,
                axiom = 60
            ),
            Node(
                id = "40",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("17"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile8,
                axiom = 60
            ),
            Node(
                id = "41",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("38"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile9,
                axiom = 60
            ),
            Node(
                id = "42",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("39"),
                distanceFromCenter = 600f,
                profileImage = R.drawable.profile10,
                axiom = 60
            ),
            Node(
                id = "43",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("38"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile1,
                axiom = 60
            ),
            Node(
                id = "44",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("38"),
                distanceFromCenter = 300f,
                profileImage = R.drawable.profile2,
                axiom = 60
            ),
            Node(
                id = "45",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("28"),
                distanceFromCenter = 500f,
                profileImage = R.drawable.profile3,
                axiom = 60
            ),
            Node(
                id = "46",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("28"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile4,
                axiom = 60
            ),
            Node(
                id = "47",
                name = "Victoria Foster",
                position = Offset.Zero,
                connections = listOf("45"),
                distanceFromCenter = 400f,
                profileImage = R.drawable.profile5,
                axiom = 60
            )

        )
    }
}