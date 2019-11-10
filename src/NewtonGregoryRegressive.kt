import kotlin.math.abs

class NewtonGregoryRegressive(points: List<OrderedPair>) : NewtonGregory(points) {

    override val polynomial: String
        get() {
            return if (pointsAreEquispaced)
                "P(X) = ${points.last().second}${deltas.foldIndexed("") { index, str, list -> "$str ${list.first().let { if (!it.equals(0F)) "${calculateEquispacedTerm(index, it).let { term -> "${term.signAsString()} ${abs(term)}" }}${stringXValues(index)}" else "" }}" }}"
            else
                "P(X) = ${points.last().second}${deltas.foldIndexed("") { index, str, list -> "$str ${list.first().let { if (!it.equals(0F)) "${it.signAsString()} ${abs(it)}${stringXValues(index)}" else "" }}" }}"
        }
    override fun differencesTableString() {
        println("|${pad("X")}|${pad("Y")}|${stringTitles()}")
        points.reversed().forEachIndexed { index, (x, y) -> println("|${pad(x.toString())}|${pad(y.toString())}|${stringDeltas(index)}") }
    }

    override fun printSteps() {
        printSeparator()

        println("1. Calculamos la tabla de diferencias finitas utilizando los puntos dados:")
        differencesTableString()

        if (pointsAreEquispaced) {
            println()
            println("2. Calculamos cada término del polinomio")
            println(polynomialSteps())
            println()
            println("Los puntos son equiespaciados")
        } else {
            println()
            println("Los puntos no son equiespaciados")
        }

        println()
        println("El grado es: ${findGrade()}")

        printSeparator()
    }

    override fun evaluate(k: Float): Float =
            points.last().second + (deltas.foldIndexed(0F) { index, acc, list -> acc + term(index, list.first()) * xValues(index, k) })

    override fun calculateDeltasNotEquispaced(values: List<Float>, accum: List<List<Float>>, nDelta: Int): List<List<Float>> {
        var indexA = 0
        return values.reversed().zipWithNext { a, b ->
            val divider = points[indexA + 1 + nDelta].first - points[indexA].first
            indexA++
            (a-b) / divider
        }
                .let {
                    if (it.isEmpty()) accum else calculateDeltasNotEquispaced(it.reversed(), accum.plusElement(it), nDelta + 1)
                }
    }

    override fun calculateDeltasEquispaced(values: List<Float>, accum: List<List<Float>>): List<List<Float>> =
            values.reversed().zipWithNext { a, b -> a - b }
                    .let {
                        if (it.isEmpty()) accum else calculateDeltasEquispaced(it.reversed(), accum.plusElement(it))
                    }


    override fun polynomialSteps(): String =
            "P(X) = ${points.reversed().first().second} ${deltas.foldIndexed(""){index, str, list -> "$str ${list.first().let { if (!it.equals(0)) "+ ${stringTerm(index, it)}${stringXValues(index)}" else "" }}"}}"

    override fun stringXValues(i: Int) =
            points.reversed().filterIndexed{ index, _ -> index <= i }.fold(""){str, value -> "$str(x " + value.first.let { if (it.equals(0F)) ")" else "- ${value.first})"} }

    override fun stringTitles(): String =
            deltas.foldIndexed("") { index, acc, _ -> "$acc${pad("∇${index.let { if (it > 0) "^${it+1}" else "" }}")}|" }
}