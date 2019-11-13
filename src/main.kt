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
            val x = requestDouble("k = ")
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
        println("0. Terminar")
        mutablePairs.forEachIndexed { i, it -> println("${i + 1}. $it") }
        println("${mutablePairs.size + 1}. Agregar")
        val index = request {
            val index = it?.toIntOrNull() ?: -1
            if (index >= 0 && index <= mutablePairs.size + 1) {
                index
            } else {
                null
            }
        }

        println()
        println("Ingrese el par ordenado (ingrese un guión medio para eliminar):")
        if (index > 0) {
            val x = request("x = ") { if (it?.trim() == "-") "-" else it?.toDoubleOrNull() }
            if (x == "-" && index <= mutablePairs.size) {
                mutablePairs.removeAt(index - 1)
            } else if (x is Double) {
                val y = requestDouble("y = ")

                if (index <= mutablePairs.size) {
                    mutablePairs[index - 1] = x to y
                } else {
                    mutablePairs.add(x to y)
                }
            }
        }
        println()
    } while (index > 0)
    return mutablePairs
}
