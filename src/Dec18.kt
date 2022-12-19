import kotlin.math.absoluteValue

object Dec18 {

    fun a(): Int {
        val droplets = getDroplets()
        return droplets.sumOf { it.emptySides }
    }

    fun b(): Int {
        val droplets = getDroplets()
        
        return -1
    }

    private fun getDroplets(): MutableList<Droplet> {
        val input = readInput("dec18")
        val droplets = mutableListOf<Droplet>()

        input.forEach {
            val droplet = Droplet(it)

            droplets.forEach { drop ->
                if (droplet.isNeighbour(drop)) {
                    droplet.emptySides--
                    drop.emptySides--
                }
            }
            droplets.add(droplet)
        }
        return droplets
    }

    class Droplet(input: String) {
        var x = 0
        var y = 0
        var z = 0
        var emptySides = 6

        init {
            val splittedInput = input.split(",")
            x = splittedInput[0].toInt()
            y = splittedInput[1].toInt()
            z = splittedInput[2].toInt()
        }

        fun isNeighbour(other: Droplet): Boolean {
            return ((x - other.x).absoluteValue +
                    (y - other.y).absoluteValue +
                    (z - other.z).absoluteValue) <= 1

        }
    }
}