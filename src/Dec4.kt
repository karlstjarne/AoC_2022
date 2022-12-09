object Dec4 {
    private val REGEX = Regex("(-|,)")

    fun a(): Int {
        val input = readInput("dec4")
        var overlaps = 0

        input.forEach {
            val numbers = REGEX.split(it)
            val e1Start = numbers[0].toInt()
            val e1End = numbers[1].toInt()
            val e2Start = numbers[2].toInt()
            val e2End = numbers[3].toInt()
            if ((e1Start in e2Start..e2End &&
                        e1End in e2Start..e2End) ||
                (e2Start in e1Start..e1End &&
                        e2End in e1Start..e1End)
            ) {
                overlaps++
            }
        }

        return overlaps
    }


    fun b(): Int {
        val input = readInput("dec4")
        var overlaps = 0

        input.forEach {
            val numbers = REGEX.split(it)
            val e1Start = numbers[0].toInt()
            val e1End = numbers[1].toInt()
            val e2Start = numbers[2].toInt()
            val e2End = numbers[3].toInt()
            if (e1Start in e2Start..e2End ||
                e1End in e2Start..e2End ||
                e2Start in e1Start..e1End ||
                e2End in e1Start..e1End
            ) {
                overlaps++
            }
        }

        return overlaps
    }
}