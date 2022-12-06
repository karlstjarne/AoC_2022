package ks.aoc

object Dec5 {

    private val REGEX = Regex("[0-9]+")
    private const val NUMBER_OF_STACKS = 8

    lateinit var stacks: MutableList<MutableList<Char>>
    lateinit var instructions: List<String>

    fun a(): String {
        setup()

        instructions.forEach {
            val line = REGEX.findAll(it).toList().map { match -> match.value }
            val nbrCrates = line[0].toInt()
            val from = line[1].toInt() - 1
            val to = line[2].toInt() - 1

            for (i in 1..nbrCrates) {
                val crate = stacks[from][stacks[from].lastIndex]
                stacks[to].add(crate)
                stacks[from].removeLast()
            }
        }

        val result = stacks.map { it.last() }.toString()
        return Regex("[^A-Z]").replace(result, "")
    }


    fun b(): String {
        setup()

        instructions.forEach {
            val line = REGEX.findAll(it).toList().map { match -> match.value }
            val nbrCrates = line[0].toInt()
            val from = line[1].toInt() - 1
            val to = line[2].toInt() - 1

            val stackSize = stacks[from].size

            val crates = stacks[from].subList(stackSize - nbrCrates, stackSize)
            stacks[to].addAll(crates)
            stacks[from].subList(stackSize - nbrCrates, stackSize).clear()
        }

        val result = stacks.map { it.last() }.toString()
        return Regex("[^A-Z]").replace(result, "")
    }

    private fun setup() {
        val input = FileUtil.loadResourceFile(javaClass.classLoader, "dec5")

        val stacksRaw = input.subList(0, input.indexOfFirst { !it.contains("[") })
        stacks = mutableListOf(mutableListOf())
        instructions = input.subList(input.indexOfFirst { it.contains("move") }, input.size)


        for (i in 0 until NUMBER_OF_STACKS) {
            stacks.add(mutableListOf())
        }
        stacksRaw.reversed().forEach {
            for (i in 0..NUMBER_OF_STACKS) {
                val j = i * 4 + 1
                if (j < it.length && it[j] != ' ') {
                    stacks[i].add(it[j])
                }
            }
        }
    }
}