import java.util.*

object Dec7 {

    private const val DISK_SPACE = 70_000_000
    private const val REQUIRED_SPACE = 30_000_000
    private val fileStructure = hashMapOf<String, Node>()

    fun a(): Int {
        setup()
        // Go through each node, find <= 100000
        var totalSum = 0
        fileStructure.forEach {
            val node = it.value
            if (node.type == Type.DIRECTORY && node.size <= 100_000) {
                totalSum += node.size
            }
        }

        return totalSum
    }

    fun b(): Int {
        setup()
        val usedSpace = fileStructure.asIterable().maxBy { it.value.size }.value.size
        val missingSpace = REQUIRED_SPACE - (DISK_SPACE - usedSpace)
        return fileStructure.asIterable()
            .filter { it.value.type == Type.DIRECTORY }
            .sortedBy { it.value.size }
            .first { it.value.size >= missingSpace }
            .value
            .size
    }

    private fun setup() {
        val input = readInput("dec7")

        var currentNode = Node(Type.DIRECTORY, "/", 0, "root-root", "")

        // Build file structure
        input.forEach {
            if (it.startsWith("\$ cd ..")) {
                currentNode = fileStructure[currentNode.parentId]!!
            } else if (it.startsWith("\$ cd ")) {
                val name = it.substring(5, it.length)
                val id = UUID.randomUUID().toString()
                currentNode = Node(
                    Type.DIRECTORY,
                    name,
                    0,
                    id,
                    currentNode.id
                )
                fileStructure[id] = currentNode
            } else if (!it.startsWith("\$") && !it.startsWith("dir")) {
                val size = it.split(" ")[0].toInt()
                val name = it.split(" ")[1]
                val id = UUID.randomUUID().toString()
                fileStructure[id] = Node(Type.FILE, name, size, id, currentNode.id)
                currentNode.incSize(size)
                incrementParentRecursive(currentNode.parentId, size)
            }
        }
    }

    private fun incrementParentRecursive(parentId: String, size: Int) {
        if (parentId != "root-root") {
            val parent: Node = fileStructure[parentId]!!
            parent.incSize(size)
            incrementParentRecursive(parent.parentId, size)
        }
    }

    class Node(val type: Type, var name: String, var size: Int, val id: String, val parentId: String) {
        fun incSize(sizeToAdd: Int) {
            size += sizeToAdd
        }
    }

    enum class Type {
        DIRECTORY,
        FILE
    }
}