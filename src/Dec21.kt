object Dec21 {

    private val monkeyMap = HashMap<String, Monkey>()

    fun a(): Long {
        setup()
        return getNumberOf("root")
    }

    fun b(): Long {
        setup()
        simplifyMonkeyMap()
        val rootMonkey = monkeyMap["root"]!!
        monkeyMap["root"] = Monkey({ l1: Long, l2: Long -> l1 - l2 }, 0L, rootMonkey.source1Id, rootMonkey.source2Id)
        var nbr = 0L
        while (getNumberOf("root") != 0L) {
            nbr++
            monkeyMap["humn"] = Monkey(number = nbr)
            if (nbr.mod(1000) == 0) {
                println(nbr)
            }
        }
        return nbr
    }

    private fun getNumberOf(id: String): Long {
        val monkey = monkeyMap[id]!!
        return if (monkey.number != 0L) {
            monkey.number
        } else {
            monkey.job(getNumberOf(monkey.source1Id), getNumberOf(monkey.source2Id))
        }
    }

    private fun simplifyMonkeyMap() {
        monkeyMap.forEach {
            if (!hasHumnAsChild(it.key)) {
                it.value.number = getNumberOf(it.key)
            }
        }
    }

    private fun hasHumnAsChild(id: String): Boolean {
        val monkey = monkeyMap[id]!!
        return if (monkey.source1Id.isNotEmpty()) {
            if (monkey.source1Id == "humn" || monkey.source2Id == "humn") {
                true
            } else {
                hasHumnAsChild(monkey.source1Id) || hasHumnAsChild(monkey.source2Id)
            }
        } else {
            false
        }
    }

    private fun setup() {
        val input = readInput("dec21")
        input.forEach {
            val id = it.substring(0, it.indexOf(":"))
            val restOfString = it.substring(it.indexOf(":") + 2, it.length)

            val monkey = try {
                Monkey(number = restOfString.toLong())
            } catch (e: java.lang.NumberFormatException) {
                val source1Id = restOfString.substring(0, 4)
                val source2Id = restOfString.substring(7, restOfString.length)
                val job = when (restOfString[5]) {
                    '+' -> { l1: Long, l2: Long -> l1 + l2 }
                    '-' -> { l1: Long, l2: Long -> l1 - l2 }
                    '*' -> { l1: Long, l2: Long -> l1 * l2 }
                    '/' -> { l1: Long, l2: Long -> l1 / l2 }
                    else -> TODO("Should not happen")
                }
                Monkey(job, 0L, source1Id, source2Id)
            }
            monkeyMap[id] = monkey
        }
    }

    private class Monkey(
        val job: (Long, Long) -> Long = { _, _ -> 0L },
        var number: Long = 0L,
        val source1Id: String = "",
        val source2Id: String = "",
    )
}