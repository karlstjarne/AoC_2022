package ks.aoc

object Dec3 {

    fun a(): Int {
        val input = FileUtil.loadResourceFile(javaClass.classLoader, "dec3")
        var prioSum = 0

        input.forEach {
            val comp1 = it.substring(0, it.length / 2).toList()
            val comp2 = it.substring(it.length / 2, it.length).toList()

            val item = comp1.filter { char ->  comp2.contains(char) }
            prioSum += toPriority(item.first())
        }

        return prioSum
    }

    fun b(): Int {
        val input = FileUtil.loadResourceFile(javaClass.classLoader, "dec3")
        var prioSum = 0

        input.windowed(3,3)
            .forEach {
            val elve1 = it[0].toList()
            val elve2 = it[1].toList()
            val elve3 = it[2].toList()

            val item = elve1.filter { char ->  elve2.contains(char) }
                .filter { char ->  elve3.contains(char) }
            prioSum += toPriority(item.first())
        }

        return prioSum


    }

    private fun toPriority(char: Char): Int {
        return if (char.isUpperCase()) {
            char.toInt() - 38
        } else {
            char.toInt() - 96
        }
    }
}