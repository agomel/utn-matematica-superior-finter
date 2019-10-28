typealias OrderedPair = Pair<Float, Float>

fun main() {
    val orderedPairs = requestOrderedPairs()
    val option = requestInterpolation()
    act(orderedPairs, option)
}

fun act(orderedPairs: List<OrderedPair>, option: InterpolateItem) {
    do {
        val interpolation = option.interpolation(orderedPairs)
        val action = requestAction()
        when (action) {

            ShowPolynomial -> println(interpolation.polynomial)

            ShowSteps -> interpolation.printSteps()

            SetValueToPolynomial -> {
                val x = requestFloat("x = ")
                val y = interpolation.evaluate(x)
                println()
                println("P($x) = $y")
                println()
            }

            AlterValues -> {
                // TODO: Pedir valores
                val newOrderedPair = emptyList<OrderedPair>()
                act(newOrderedPair, option)
            }
        }
    } while (action != Exit)
}