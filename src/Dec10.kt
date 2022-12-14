object Dec10 {

    private val REGEX = Regex("[a-z ]")
    private val RANGE = 20..220 step 40

    fun a(): Int {
        val input = readInput("dec10")
        var x = 1
        var cp = 0
        var signalStrength = 0

        for (i in input.indices) {
            val row = input[i]
            val cpInc = if (row == "noop") 1 else 2

            for (j in 1..cpInc) {
                cp++
                if (RANGE.contains(cp)) {
                    signalStrength += cp * x
                }
            }

            val incs = try {
                REGEX.replace(row, "").toInt()
            } catch (e: Exception) {
                0
            }
            x += incs
        }

        return signalStrength
    }

    fun b(): String {
        val input = readInput("dec10")
        var x = 1
        var cp = 0
        var matrix = List(6) { MutableList(40) { "." } }

        for (i in input.indices) {
            val row = input[i]
            val cpInc = if (row == "noop") 1 else 2

            for (j in 1..cpInc) {
                val line = cp / 40
                val idx = cp.mod(40)
                cp++

                if (((x - 1)..(x + 1)).contains(idx)) {
                    matrix[line][idx] = "#"
                }

            }

            val incs = try {
                REGEX.replace(row, "").toInt()
            } catch (e: Exception) {
                0
            }
            x += incs
        }

        return matrix.map {
            it.toString().replace(",", "")
        }
            .toString()
            .replace("], [", "\n")
            .replace("[[", "")
            .replace("]]", "")
    }
}