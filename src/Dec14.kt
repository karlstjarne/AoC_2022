object Dec14 {

    private val nodeMap = HashMap<String, Node>()
    private var yMax = 100

    fun a(): Int {
        setup()

        while (fallingGrainOfSandA()) {
        }

        return nodeMap.asIterable().filter { it.value.type == Type.SAND }.size
    }

    private fun setup() {
        nodeMap.clear()
        val input = readInput("dec14")
        input.forEach { line ->
            line.split(" -> ")
                .map { coords -> Coordinate(raw = coords) }
                .windowed(2, 1)
                .forEach {
                    for (i in it[0].x toward it[1].x) {
                        for (j in it[0].y toward it[1].y) {
                            val node = Node(Coordinate(i, j), Type.ROCK)
                            nodeMap[node.coordinate.toString()] = node
                        }
                    }
                }
        }
    }

    fun b(): Int {
        setup()
        yMax = 2 + nodeMap.asIterable()
            .filter { it.value.type == Type.ROCK }
            .maxOf { it.value.coordinate.y }

        while (nodeMap["500,0"] == null) {
            fallingGrainOfSandB()
        }

        return nodeMap.asIterable().filter { it.value.type == Type.SAND }.size
    }

    /**
     * @return true if grain of sand came to rest
     */
    private fun fallingGrainOfSandA(): Boolean {
        val sand = Coordinate(500, 0)

        while (true) {
            if (nodeMap[sand.oneDown()] != null) { // Hit something
                if (nodeMap[sand.oneDownLeft()] == null) { // Check down left
                    sand.setFromRaw(sand.oneLeft())
                } else if (nodeMap[sand.oneDownRight()] == null) { // Check down right
                    sand.setFromRaw(sand.oneRight())
                } else {
                    nodeMap[sand.toString()] = Node(sand, Type.SAND)
                    break
                }
            }
            if (sand.y >= 1000) {
                return false
            }
            sand.incY()
        }
        return true
    }

    /**
     * @return true if grain of sand came to rest
     */
    private fun fallingGrainOfSandB() {
        val sand = Coordinate(500, 0)

        while (true) {
            if ((sand.y + 1) == yMax) {
                nodeMap[sand.toString()] = Node(sand, Type.SAND)
                break
            } else if (nodeMap[sand.oneDown()] != null) { // Hit something
                if (nodeMap[sand.oneDownLeft()] == null) { // Check down left
                    sand.setFromRaw(sand.oneLeft())
                } else if (nodeMap[sand.oneDownRight()] == null) { // Check down right
                    sand.setFromRaw(sand.oneRight())
                } else {
                    nodeMap[sand.toString()] = Node(sand, Type.SAND)
                    break
                }
            }
            sand.incY()
        }
    }

    enum class Type {
        ROCK,
        SAND,
    }

    private class Node(val coordinate: Coordinate, val type: Type)

    private class Coordinate(
        var x: Int = 0,
        var y: Int = 0,
        raw: String = ""
    ) {
        init {
            setFromRaw(raw)
        }

        fun incY() = y++

        fun oneDown() = "$x,${y + 1}"
        fun oneLeft() = "${x - 1},$y"
        fun oneRight() = "${x + 1},$y"
        fun oneDownLeft() = "${x - 1},${y + 1}"
        fun oneDownRight() = "${x + 1},${y + 1}"

        fun setFromRaw(raw: String) {
            if (raw.isNotEmpty()) {
                x = raw.substring(0, raw.indexOf(",")).toInt()
                y = raw.substring(raw.indexOf(",") + 1, raw.length).toInt()
            }
        }

        override fun toString() = "$x,$y"
    }

    private infix fun Int.toward(to: Int): IntProgression {
        val step = if (this > to) -1 else 1
        return IntProgression.fromClosedRange(this, to, step)
    }
}