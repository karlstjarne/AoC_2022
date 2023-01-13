import kotlin.math.absoluteValue

object Dec9 {

    fun a(): Int {
        val input = readInput("dec9")
        val head = Node()
        val tail = Node()
        val visitedCoords = mutableListOf<Node>()

        input.forEach {
            val direction = it[0]
            val steps = it.substring(2, it.length).toInt()

            for (i in 0 until steps) {
                val prevHeadX = head.x
                val prevHeadY = head.y

                when (direction) {
                    'U' -> head.moveBy(0, 1)
                    'D' -> head.moveBy(0, -1)
                    'R' -> head.moveBy(1, 0)
                    'L' -> head.moveBy(-1, 0)
                }

                if (!tail.isAdjacent(head)) {
                    tail.moveTo(prevHeadX, prevHeadY)
                    visitedCoords.add(Node(tail))
                }
            }
        }

        return visitedCoords.distinctBy { it.toString() }.size  // This is stupid, distinct() and equals() should be sufficient...
    }

    fun b(): Int {
        return -1
    }

    private class Node(var x: Int = 0, var y: Int = 0) {
        constructor(other: Node) : this(other.x, other.y)

        fun moveTo(x: Int, y: Int) {
            this.x = x
            this.y = y
        }

        fun moveBy(x: Int, y: Int) {
            this.x += x
            this.y += y
        }

        fun isAdjacent(that: Node): Boolean {
            val deltaX = this.x - that.x
            val deltaY = this.y - that.y
            return deltaX.absoluteValue <= 1 && deltaY.absoluteValue <= 1
        }

        override fun toString(): String {
            return "x: $x, y: $y"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Node) return false
            return x == other.x && y == other.y
        }
    }

}