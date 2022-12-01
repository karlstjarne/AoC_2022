package ks.aoc

object Dec1 {

    @JvmStatic
    fun a(): Int {
        return getCaloriesPerElf().max()
    }

    @JvmStatic
    fun b(): Int {
        val elves = getCaloriesPerElf()
        return elves.sortedDescending()
            .subList(0, 3)
            .sum()
    }


    private fun getCaloriesPerElf(): List<Int> {
        val input = FileUtil.loadResourceFile(javaClass.classLoader, "dec1")

        val elves = mutableListOf<Int>()
        var caloriesSum = 0

        input.forEach {
            if (it.isNotEmpty()) {
                val value = it.toInt()
                caloriesSum += value
            } else {
                elves.add(caloriesSum)
                caloriesSum = 0
            }
        }
        elves.add(caloriesSum) // Last one

        return elves
    }
}