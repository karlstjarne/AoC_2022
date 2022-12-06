package ks.aoc

import java.io.File

object Dec6 {

    private val REGEX = Regex("(\\w)(?=.*\\1)")

    @JvmStatic
    fun a(): Int {
        val input = File(javaClass.classLoader?.getResource("dec6")?.path).useLines { it.first() }
        var nbrOfChars = 0

        for (i in 4 until input.length) {
            val substring = input.substring(i-4, i)
            if (!REGEX.containsMatchIn(substring)) {
                nbrOfChars = i
                break
            }
        }
        return nbrOfChars
    }

    fun b(): Int {
        val input = File(javaClass.classLoader?.getResource("dec6")?.path).useLines { it.first() }
        var nbrOfChars = 0

        for (i in 14 until input.length) {
            val substring = input.substring(i-14, i)
            if (!REGEX.containsMatchIn(substring)) {
                nbrOfChars = i
                break
            }
        }
        return nbrOfChars
    }
}