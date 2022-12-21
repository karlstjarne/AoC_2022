object Dec20 {

    fun a(): Long {
        val input = readInput("dec20")
        val nodes = mutableListOf<Node>()
        for (i in input.indices) {
            nodes.add(Node(i, input[i].toLong()))
        }

        val output = mixList(nodes, 1)

        val indexOfZero = output.indexOf(output.find { it.number == 0L })
        var sum = 0L
        for (i in 1..3) {
            sum += output[(indexOfZero + i * 1000).mod(nodes.size)].number
        }

        return sum
    }

    private fun mixList(nodes: MutableList<Node>, mixes: Int, decryptionKey: Long = 1): MutableList<Node> {
        val output = nodes.map { Node(it.id, it.number * decryptionKey) }.toMutableList()

        for (j in 1..mixes) {
            for (i in nodes.indices) {
                // Get the node to move
                val node = output.find { it.id == i }!!

                // move it
                val currentIndex = output.indexOf(node)
                var newIndex = (currentIndex + node.number.toInt())
                if (newIndex < 0) newIndex += newIndex / nodes.size - 1  // Handle (multiple) negative wraps
                if (newIndex >= nodes.size) newIndex += newIndex / nodes.size  // Handle (multiple) positive wraps
                newIndex = newIndex.mod(nodes.size)     // Wrap
                if (newIndex == 0) newIndex = nodes.size - 1 else if (newIndex == (nodes.size - 1)) newIndex = 0    // Handle the "luriga"

                //            println("Before (${node.number}): " + output.map { it.number })

                output.remove(node)
                output.add(newIndex, node)

                //            println("After  (${node.number}): " + output.map { it.number })
            }
        }
        return output
    }


    fun b(): Long {
        val decryptionKey = 811589153L
        val input = readInput("dec20")
        val nodes = mutableListOf<Node>()
        for (i in input.indices) {
            nodes.add(Node(i, input[i].toLong()))
        }

        val output = mixList(nodes, 10, decryptionKey)

        val indexOfZero = output.indexOf(output.find { it.number == 0L })
        var sum = 0L
        for (i in 1..3) {
            sum += output[(indexOfZero + i * 1000).mod(nodes.size)].number
        }

        return sum
    }

    private class Node(val id: Int = 0, val number: Long = 0)
}