object Dec2 {

    fun a(): Int {
        val input = readInput("dec2")
        var score = 0

        input.forEach {
            val opponent = when (it[0]) {
                'A' -> 1    // Rock
                'B' -> 2    // Paper
                'C' -> 3    // Scissors
                else -> 0
            }
            val self = when (it[2]) {
                'X' -> 1    // Rock
                'Y' -> 2    // Paper
                'Z' -> 3    // Scissors
                else -> 0
            }
            val roundScore = when (opponent - self) {
                0 -> 3  // Draw
                1, -2 -> 0 // Opponent won
                -1, 2 -> 6  // I won
                else -> 0
            }

            score += (self + roundScore)
        }

        return score
    }

    fun b(): Int {
        val input = readInput("dec2")
        var score = 0

        input.forEach {
            val opponent = when (it[0]) {
                'A' -> 1    // Rock
                'B' -> 2    // Paper
                'C' -> 3    // Scissors
                else -> 0
            }
            val self = when (it[2]) {
                'X' -> (opponent - 2).mod(3) + 1 // Lose
                'Y' -> opponent                  // Draw
                'Z' -> opponent.mod(3) + 1       // Win
                else -> 0
            }
            val roundScore = when (it[2]) {
                'X' -> 0    // Lose
                'Y' -> 3    // Draw
                'Z' -> 6    // Win
                else -> 0
            }

            score += (self + roundScore)
        }

        return score
    }
}