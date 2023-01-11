import kotlin.math.pow

object Dec25 {

    private const val BASE = 5.0

    fun a(): String {
        val input = readInput("dec25")
        return input.sumOf { fromSnafu(it) }
            .toSnafu()
    }

    fun b(): String {
        return ""
    }

    private fun fromSnafu(snafu: String): Long {
        var decimal = 0L
        for (i in snafu.indices.reversed()) {
            decimal += BASE.pow(snafu.length - 1 - i).toLong() *
                    when (snafu[i]) {
                        '0' -> 0
                        '1' -> 1
                        '2' -> 2
                        '-' -> -1
                        '=' -> -2
                        else -> 0
                    }
        }
        return decimal
    }

    private fun Long.toSnafu(): String {
        val resBuilder = StringBuilder()
        var rest = this
        var i = 0
        while (rest != 0L) {
            val divisor = BASE.pow(i).toLong()
            val value = (rest / divisor).mod(BASE.toLong())
            rest -= value * divisor

            val snafuValue = when (value) {
                0L -> '0'
                1L -> '1'
                2L -> '2'
                3L -> '='
                4L -> '-'
                else -> 0
            }
            resBuilder.append(snafuValue)

            if (value >= 3) {
                rest += divisor * 5
            }

            i++
        }
        return resBuilder.toString().reversed()
    }
}