object Dec19 {

    private val blueprints = mutableListOf<Blueprint>()
    private val robots = mutableListOf<Robot>()


    fun a(): Int {
        val input = readInput("dec19")

        input.forEach {
            blueprints.add(Blueprint(it))
        }

        blueprints.forEach { blueprint ->
            robots.clear()
            robots.add(Robot(output = MaterialType.ORE))
            val inventory = MutableList(4) { 0 }

            for (i in 1..24) {
                // 1. Check if can create robot
                val newRobots = blueprint.createRobotsIfPossible(inventory)

                // 2. Mine
                robots.forEach {
                    inventory[it.output.type]++
                }

                // 3. Robot is ready
                robots.addAll(newRobots)
            }

            blueprint.qualityLevel = blueprint.id * inventory[MaterialType.GEODE.type]
            println("ID: ${blueprint.id}, geodes: ${inventory[MaterialType.GEODE.type]}, ql: ${blueprint.qualityLevel}")
        }

        return blueprints.maxOf { it.qualityLevel }
    }

    fun b(): Int {
        return -1
    }

    class Blueprint(input: String) {
        var qualityLevel = 0

        fun createRobotsIfPossible(inventory: MutableList<Int>): List<Robot> {  // TODO KAST: Need to figure out which robots to construct
            val robots = mutableListOf<Robot>()

            robotBlueprints.forEach {
                if (it.canBeAfforded(inventory)) {
                    robots.add(it.construct(inventory))
                }
            }

            return robots
        }

        var id = 0
        val robotBlueprints = mutableListOf<Robot>()

        init {
            id = Regex("[0-9]+").find(input, 0)!!.value.toInt()

            val splittedInput = input.split(".")
            for (i in splittedInput.indices) {
                val robot = when (i) {
                    0 -> {
                        val ore = Regex("[0-9]+").find(splittedInput[i], 15)!!.value.toInt()
                        Robot(ore, 0, 0, MaterialType.ORE)
                    }
                    1 -> {
                        val ore = Regex("[0-9]+").find(splittedInput[i], 0)!!.value.toInt()
                        Robot(ore, 0, 0, MaterialType.CLAY)
                    }
                    2 -> {
                        val ore = Regex("[0-9]+").findAll(splittedInput[i], 0).first().value.toInt()
                        val clay = Regex("[0-9]+").findAll(splittedInput[i], 0).last().value.toInt()
                        Robot(ore, clay, 0, MaterialType.OBSIDIAN)
                    }
                    3 -> {
                        val ore = Regex("[0-9]+").findAll(splittedInput[i], 0).first().value.toInt()
                        val obsidian = Regex("[0-9]+").findAll(splittedInput[i], 0).last().value.toInt()
                        Robot(ore, 0, obsidian, MaterialType.GEODE)
                    }
                    else -> null
                }
                if (robot != null) {
                    robotBlueprints.add(robot)
                }
            }

        }
    }

    class Robot(
        val costOre: Int = 0,
        val costClay: Int = 0,
        val costObsidian: Int = 0,
        val output: MaterialType
    ) {
        fun canBeAfforded(inventory: List<Int>): Boolean {
            return inventory[MaterialType.ORE.type] >= costOre &&
                    inventory[MaterialType.CLAY.type] >= costClay &&
                    inventory[MaterialType.OBSIDIAN.type] >= costObsidian
        }

        fun construct(inventory: MutableList<Int>): Robot {
            inventory[MaterialType.ORE.type] -= costOre
            inventory[MaterialType.CLAY.type] -= costClay
            inventory[MaterialType.OBSIDIAN.type] -= costObsidian

            return Robot(output = this.output)
        }

    }

    enum class MaterialType(val type: Int) {
        ORE(0),
        CLAY(1),
        OBSIDIAN(2),
        GEODE(3);
    }

}