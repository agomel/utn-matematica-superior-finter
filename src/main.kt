typealias OrderedPair = Pair<Double, Double>

fun main() {
    do {
        val orderedPairs = requestOrderedPairs()
        if (orderedPairs.isNotEmpty()) {
            val option = requestInterpolation()
            act(orderedPairs, option)
            printSeparator()
        }
    } while (orderedPairs.isNotEmpty())
    print("¡Chau! :)")
}

fun act(orderedPairs: List<OrderedPair>, option: InterpolateItem) {
    val interpolation = option.interpolation(orderedPairs)
    when (requestAction()) {

        ShowPolynomial -> println(interpolation.polynomial)

        ShowSteps -> interpolation.printSteps()

        SetValueToPolynomial -> {
            val x = requestDouble("x = ")
            val y = interpolation.evaluate(x)
            println()
            println("P($x) = $y")
            println()
        }

        AlterValues -> {
            act(alterValues(orderedPairs), option)
            return
        }

        Exit -> return
    }

    act(orderedPairs, option)
}

fun alterValues(orderedPairs: List<OrderedPair>): List<OrderedPair> {
    println()
    val mutablePairs = orderedPairs.toMutableList()
    do {
        println("Elija un par ordenado para modificar:")
        mutablePairs.forEachIndexed { i, it -> println("${i + 1}. $it") }
        println("0. Terminar")
        val index = request {
            val index = it?.toIntOrNull() ?: -1
            if (index >= 0 && index <= orderedPairs.size) {
                index
            } else {
                null
            }
        }

        println()
        println("Ingrese el par ordenado:")
        if (index > 0.0) {
            val x = requestDouble("x = ")
            val y = requestDouble("y = ")
            mutablePairs[index - 1] = x to y
        }
        println()
    } while (index > 0.0)
    return mutablePairs
}
