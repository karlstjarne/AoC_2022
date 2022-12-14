object Dec11 {


    fun a(): Int {
        val monkeys = setup()
        for (i in 0 until 20) {
            monkeys.forEach { monkey ->
                monkey.items.forEach {
                    val worryLevel = monkey.operation(it) / 3
                    val receivingMonkey = monkey.getReceiver(worryLevel)
                    monkeys[receivingMonkey].items.add(worryLevel)
                    monkey.inspections++
                }
                monkey.items = mutableListOf() // All items thrown
            }

        }
        return monkeys.map { it.inspections }.sortedDescending().subList(0, 2).product().toInt()
    }

    fun b(): Long {
        val monkeys = setup()
        val modDivisorProduct = monkeys.map { it.modDivisor }.product()

        for (i in 0 until 10000) {
            monkeys.forEach { monkey ->
                monkey.items.forEach {
                    val worryLevel = monkey.operation(it.mod(modDivisorProduct))
                    val receivingMonkey = monkey.getReceiver(worryLevel)
                    monkeys[receivingMonkey].items.add(worryLevel)
                    monkey.inspections++
                }
                monkey.items = mutableListOf() // All items thrown
            }

        }

        return monkeys.map { it.inspections }.sortedDescending().subList(0, 2).product()
    }

    private fun setup(): List<Monkey> {
        return listOf(
            Monkey(mutableListOf(64),
                { it * 7 },
                13,
                { 1 },
                { 3 }),
            Monkey(mutableListOf(60, 84, 84, 65),
                { it + 7 },
                19,
                { 2 },
                { 7 }),
            Monkey(mutableListOf(52, 67, 74, 88, 51, 61),
                { it * 3 },
                5,
                { 5 },
                { 7 }),
            Monkey(mutableListOf(67, 72),
                { it + 3 },
                2,
                { 1 },
                { 2 }),
            Monkey(mutableListOf(80, 79, 58, 77, 68, 74, 98, 64),
                { it * it },
                17,
                { 6 },
                { 0 }),
            Monkey(mutableListOf(62, 53, 61, 89, 86),
                { it + 8 },
                11,
                { 4 },
                { 6 }),
            Monkey(mutableListOf(86, 89, 82),
                { it + 2 },
                7,
                { 3 },
                { 0 }),
            Monkey(mutableListOf(92, 81, 70, 96, 69, 84, 83),
                { it + 4 },
                3,
                { 4 },
                { 5 })
        )
    }

    class Monkey(
        var items: MutableList<Long>,
        val operation: (Long) -> Long,
        val modDivisor: Long,
        val ifTrue: () -> Int,
        val ifFalse: () -> Int,
        var inspections: Long = 0
    ) {
        fun getReceiver(wl: Long) = if (wl.mod(modDivisor) == 0L) ifTrue() else ifFalse()
    }

    private fun Iterable<Long>.product(): Long {
        var prod = 1L
        for (element in this) {
            prod *= element
        }
        return prod
    }
}