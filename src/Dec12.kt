object Dec12 {

    fun a(): Int {
        val matrix = getMatrix(true)
        return dijkstrasDistance(matrix)
    }

    fun b(): Int {
        val matrix = getMatrix(false)

        return matrix.flatten()
            .filter { it.height == 'a'.toInt() }
            .map {
                it.dist = 0
                val dist = dijkstrasDistance(matrix)
                matrix.flatten().forEach { node ->
                    node.dist = Int.MAX_VALUE
                    node.evaluated = false
                }
                if (dist < 0) Int.MAX_VALUE else dist   // ugh, ugly hack... again...
            }
            .minOf { it }
    }

    private fun getMatrix(includeStart: Boolean): List<MutableList<Node>> {
        val input = readInput("dec12")
        val matrix = List(input.size) { MutableList(input[0].length) { Node(x = 0, y = 0) } }

        for (i in input.indices) {
            for (j in input[i].indices) {
                val startDist = if (includeStart) 0 else Int.MAX_VALUE
                val node = when (input[i][j]) {
                    'S' -> Node(startDist, 'a'.toInt(), Type.START, x = j, y = i)
                    'E' -> Node(height = 'z'.toInt(), type = Type.END, x = j, y = i)
                    else -> Node(height = input[i][j].toInt(), x = j, y = i)
                }

                matrix[i][j] = node
            }
        }
        return matrix
    }

    private fun dijkstrasDistance(matrix: List<MutableList<Node>>): Int {
        while (matrix.any { it.any { node -> !node.evaluated } }) {
            // 1. pick node with lowest dist and eval = false
            val smallestNode = matrix.flatten().filter { !it.evaluated }.minBy { it.dist }

            // 2. update adjacent nodes (and check if reachable)
            if (evaluateNeighbours(smallestNode, matrix)) {
                break
            }

            // 3. set eval = true
            smallestNode.evaluated = true
        }

        return matrix.flatten().first { it.type == Type.END }.dist
    }

    private fun evaluateNeighbours(smallestNode: Node, matrix: List<MutableList<Node>>): Boolean {
        for (k in (smallestNode.x - 1)..(smallestNode.x + 1) step 2) {
            if (matrix[smallestNode.y].indices.contains(k)) {
                val neighbour = matrix[smallestNode.y][k]
                if (!neighbour.evaluated && (neighbour.height - smallestNode.height) <= 1) {
                    neighbour.dist = smallestNode.dist + 1
                    if (neighbour.type == Type.END) {
                        return true
                    }
                }
            }
        }

        for (k in (smallestNode.y - 1)..(smallestNode.y + 1) step 2) {
            if (matrix.indices.contains(k)) {
                val neighbour = matrix[k][smallestNode.x]
                if (!neighbour.evaluated && (neighbour.height - smallestNode.height) <= 1) {
                    neighbour.dist = smallestNode.dist + 1
                    if (neighbour.type == Type.END) {
                        return true
                    }
                }
            }
        }
        return false
    }

    class Node(
        var dist: Int = Int.MAX_VALUE,
        val height: Int = 0,
        val type: Type = Type.NODE,
        var evaluated: Boolean = false,
        val x: Int,
        val y: Int
    )

    enum class Type {
        NODE,
        START,
        END
    }
}