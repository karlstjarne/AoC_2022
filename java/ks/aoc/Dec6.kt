package ks.aoc

import java.io.File

object Dec6 {

    private val REGEX = Regex("(\\w)(?=.*\\1)")

    fun a(): Int {
        return getIndexOfNotRepeat(4)
    }

    fun b(): Int {
        return getIndexOfNotRepeat(14)
    }

    private fun getIndexOfNotRepeat(window: Int): Int {
        val input = File(javaClass.classLoader?.getResource("dec6")?.path).useLines { it.first() }
        var nbrOfChars = 0

        for (i in window until input.length) {
            val substring = input.substring(i - window, i)
            if (!REGEX.containsMatchIn(substring)) {
                nbrOfChars = i
                break
            }
        }
        return nbrOfChars
    }
}