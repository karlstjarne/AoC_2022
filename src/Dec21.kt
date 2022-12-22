object Dec21 {
    private val monkeyMap = HashMap<String, Monkey>()

    fun a(): Long {
        setup()
        return getNumberOf("root")
    }

    fun b() {
        setup()
        simplifyMonkeyMap()

        monkeyMap["humn"]!!.number = 9999L
        monkeyMap["root"]!!.jobRaw = '-'
        monkeyMap["root"]!!.number = 0L
        monkeyMap["root"]!!.job = { l1: Long, l2: Long -> l1 - l2 }

        val chain = getChainFromHumnToRoot()
        inverseChainAndPrintAsKotlinCode(chain)
    }

    private fun inverseChainAndPrintAsKotlinCode(chain: List<Pair<String, Monkey>>) {
        chain.reversed().forEach {
            if (it.first != "humn") {
                val s1 = if (hasHumnAsChild(it.second.source1Id)) it.second.source1Id else monkeyMap[it.second.source1Id]!!.number
                val s2 = if (hasHumnAsChild(it.second.source2Id)) it.second.source2Id else monkeyMap[it.second.source2Id]!!.number
                println()
                println("val ${it.first} = $s1 ${it.second.jobRaw} $s2")

                val invertedJob = invertJob(it.second.jobRaw)
                if (hasHumnAsChild(it.second.source1Id)) {
                    println("val ${it.second.source1Id} = ${it.first} $invertedJob $s2")
                } else {
                    if (isMinusOrDivision(it.second.jobRaw)) {
                        println("val ${it.second.source2Id} = $s1 ${it.second.jobRaw} ${it.first}")
                    } else {
                        println("val ${it.second.source2Id} = ${it.first} $invertedJob $s1 ")
                    }
                }
            }
        }
    }

    private fun isMinusOrDivision(operation: Char): Boolean {
        return operation == '-' || operation == '/'

    }

    private fun invertJob(job: Char): Char {
        return when (job) {
            '+' -> '-'
            '-' -> '+'
            '*' -> '/'
            '/' -> '*'
            else -> TODO("Should not happen")
        }
    }

    private fun getChainFromHumnToRoot(): List<Pair<String, Monkey>> {
        val list = mutableListOf<Pair<String, Monkey>>()
        var node = "humn"
        list.add(Pair(node, monkeyMap[node]!!))
        while (node != "root") {
            node = getParent(node)
            list.add(Pair(node, monkeyMap[node]!!))
        }
        return list
    }

    private fun getParent(id: String): String {
        return monkeyMap.asIterable().firstOrNull { it.value.source1Id == id || it.value.source2Id == id }!!.key
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
            if (monkey.source1Id == "humn" || monkey.source2Id == "humn" || id == "humn") {
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
                val jobRaw = restOfString[5]
                val job = when (jobRaw) {
                    '+' -> { l1: Long, l2: Long -> l1 + l2 }
                    '-' -> { l1: Long, l2: Long -> l1 - l2 }
                    '*' -> { l1: Long, l2: Long -> l1 * l2 }
                    '/' -> { l1: Long, l2: Long -> l1 / l2 }
                    else -> TODO("Should not happen")
                }
                Monkey(job, jobRaw, 0L, source1Id, source2Id)
            }
            monkeyMap[id] = monkey
        }
    }

    private class Monkey(
        var job: (Long, Long) -> Long = { _, _ -> 0L },
        var jobRaw: Char = ' ',
        var number: Long = 0L,
        val source1Id: String = "",
        val source2Id: String = "",
    )
}