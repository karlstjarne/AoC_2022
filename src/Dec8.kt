object Dec8 {

    fun a(): Int {
        val trees: List<List<Tree>> = getTreeMatrix()

        trees.forEach {
            updateTreeVisibility(it)
            updateTreeVisibility(it.reversed())
        }

        transpose(trees).forEach {
            updateTreeVisibility(it)
            updateTreeVisibility(it.reversed())
        }

        return trees.sumOf { it.filter { tree -> tree.isSeen }.size }
    }

    fun b(): Int {
        val trees: List<List<Tree>> = getTreeMatrix()

        for (i in trees.indices) {
            for (j in trees[i].indices) {
                val tree = trees[i][j]

                // Left
                var k = j - 1
                var scoreLeft = 0
                while (trees[i].indices.contains(k)) {
                    scoreLeft++
                    if (tree.height <= trees[i][k].height) {
                        break
                    }
                    k--
                }

                //Right
                k = j + 1
                var scoreRight = 0
                while (trees[i].indices.contains(k)) {
                    scoreRight++
                    if (tree.height <= trees[i][k].height) {
                        break
                    }
                    k++
                }

                // Up
                k = i - 1
                var scoreUp = 0
                while (trees.indices.contains(k)) {
                    scoreUp++
                    if (tree.height <= trees[k][j].height) {
                        break
                    }
                    k--
                }

                // Down
                k = i + 1
                var scoreDown = 0
                while (trees.indices.contains(k)) {
                    scoreDown++
                    if (tree.height <= trees[k][j].height) {
                        break
                    }
                    k++
                }

                tree.scenicScore = scoreLeft * scoreRight * scoreUp * scoreDown
            }
        }

        return trees.maxOf { it.maxOf { tree -> tree.scenicScore } }
    }

    private fun getTreeMatrix(): List<List<Tree>> {
        val input = readInput("dec8")

        val trees: List<List<Tree>> = List(input.size) { List(input.first().length) { Tree() } }
        for (i in input.indices) {
            for (j in input[i].indices) {
                trees[i][j].height = input[i][j].digitToInt()
            }
        }
        return trees
    }

    private fun updateTreeVisibility(trees: List<Tree>) {
        var currentLargest = -1
        for (i in trees.indices) {
            val tree = trees[i]
            if (tree.height > currentLargest) {
                currentLargest = tree.height
                tree.isSeen = true
            }
        }
    }

    private fun transpose(input: List<List<Tree>>): List<List<Tree>> {
        val transpose: MutableList<MutableList<Tree>> = MutableList(input.size) { MutableList(input.first().size) { Tree() } }
        for (i in input.indices) {
            for (j in input[i].indices) {
                transpose[j][i] = input[i][j]
            }
        }
        return transpose
    }

    class Tree(
        var isSeen: Boolean = false,
        var height: Int = 0,
        var scenicScore: Int = 0
    )
}