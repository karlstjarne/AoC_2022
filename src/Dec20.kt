import java.util.Collections.swap
import kotlin.math.abs

object Dec20 {

    fun a(): Long {
        val input = readInput("dec20")
        val nodes = mutableListOf<Node>()
        for (i in input.indices) {
            nodes.add(Node(i, input[i].toLong()))
        }

        val output = mixList(nodes, 1)
        return getTheSum(output, nodes)
    }

    fun b(): Long {
        val input = readInput("dec20")
        val decryptionKey = 811589153L

        val nodes = mutableListOf<Node>()
        for (i in input.indices) {
            nodes.add(Node(i, input[i].toLong() * decryptionKey))
        }

        val output = mixList(nodes, 10)
        return getTheSum(output, nodes)
    }

    private fun mixList(nodes: MutableList<Node>, mixes: Int): MutableList<Node> {
        val output = nodes.toMutableList()
        println("initial: " + output.map { it.number })
        for (j in 1..mixes) {
            for (i in nodes.indices) {
                // Get the node to move
                val node = output.find { it.id == i }!!

                // move it
                val currentIndex = output.indexOf(node)
                var newIndex = (currentIndex + node.number.mod(nodes.size - 1)).mod(nodes.size)

                output.remove(node)
                output.add(newIndex, node)

//                println("After  (${node.number}): " + output.map { it.number })
            }
//            println("After $j mixes: " + output.map { it.encryptedNumber })
        }
        return output
    }

    private fun getTheSum(output: MutableList<Node>, nodes: MutableList<Node>): Long {
        val indexOfZero = output.indexOf(output.find { it.number == 0L })
        var sum = 0L
        for (i in 1..3) {
            sum += output[(indexOfZero + i * 1000).mod(nodes.size)].number
        }
        return sum
    }

    private class Node(val id: Int = 0, val number: Long = 0)
}
