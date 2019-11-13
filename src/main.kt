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
            act(alterValues(orderedPairs, option), option)
            return;
        }

        Exit -> return
    }

    act(orderedPairs, option)
}

fun alterValues(orderedPairs: List<OrderedPair>, option: InterpolateItem): List<OrderedPair> {

    val actualInterpolation = option.interpolation(orderedPairs)
    val mutablePairs = orderedPairs.toMutableList()

    println()
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
        if (index > 0) {
            println("Ingrese el par ordenado (ingrese un guión medio para eliminar):")
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

    val newInterpolation = option.interpolation(mutablePairs)
    val hasAltered = (-50..50).any {
        if (actualInterpolation.evaluate(it.toDouble()) != newInterpolation.evaluate(it.toDouble())) {
            println("La caga: $it -> ${actualInterpolation.evaluate(it.toDouble())} != ${newInterpolation.evaluate(it.toDouble())}")
        }
        actualInterpolation.evaluate(it.toDouble()) != newInterpolation.evaluate(it.toDouble())
    }

    println("El cambio ${if (hasAltered) "" else "no "}altera al polinomio.")
    if (hasAltered) {
        println("El nuevo polinomio es:")
        println(newInterpolation.polynomial)
    }

    println()
    println("¿Desea continuar con el nuevo polinomio o con el original?")
    println("1. Usar el nuevo polinomio")
    println("0. Seguir con el original")
    val response = request { if (it?.toIntOrNull() == 1 || it?.toIntOrNull() == 0) it.toInt() == 1 else null }
    return if (response) mutablePairs else orderedPairs
}

