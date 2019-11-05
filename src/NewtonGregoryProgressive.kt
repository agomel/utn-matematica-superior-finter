import kotlin.math.pow

class NewtonGregoryProgressive(private val points: List<OrderedPair>) : Interpolation {

    override val polynomial: String
        get() {
            return if (pointsAreEquispaced)
                "P(X) = ${points.first().second}${deltas.foldIndexed(""){index, str, list -> "$str ${list.first().let { if (!it.equals(0F)) "+ ${calculateEquispacedTerm(index, it)}${stringXValues(index)}" else "" }}"}}"
            else
                "P(X) = ${points.first().second}${deltas.foldIndexed(""){index, str, list -> "$str ${list.first().let { if (!it.equals(0F)) "+ $it${stringXValues(index)}" else "" }}"}}"
        }

    override fun printSteps() {
        printSeparator()

        println("1. Calculamos la tabla de diferencias finitas utilizando los puntos dados:")
        differencesTableString()

        if(pointsAreEquispaced) {
            println()
            println("2. Calculamos cada término del polinomio")
            println("P(X) = ${points.first().second} ${deltas.foldIndexed(""){index, str, list -> "$str ${list.first().let { if (!it.equals(0)) "+ ${stringTerm(index, it)}${stringXValues(index)}" else "" }}"}}")
            println()
            println("Los puntos son equiespaciados")
        } else {
            println()
            println("Los puntos no son equiespaciados")
        }

        printSeparator()
    }

    override fun evaluate(k: Float): Float =
        points.first().second + (deltas.foldIndexed(0F){index, acc, list -> acc + term(index, list.first()) * xValues(index, k) })

    private val deltas: List<List<Float>> by lazy {
        points.map { it.second }.let {
            if (pointsAreEquispaced)
                calculateDeltasEquispaced(it)
            else
                calculateDeltasNotEquispaced(it)
        }
    }

    private val h: Float by lazy { points[1].first - points[0].first } //la distancia entre cada punto, si son equiespaciados

    private fun calculateDeltasNotEquispaced(values: List<Float>, accum: List<List<Float>> = emptyList(), nDelta: Int = 0): List<List<Float>> {
        var indexA = 0
        return  values.zipWithNext { a, b ->
            val divider = points[indexA + 1 + nDelta].first - points[indexA].first
            indexA++
            (b - a) / divider }
                .let {
                    if (it.isEmpty()) accum else calculateDeltasNotEquispaced(it, accum.plusElement(it), nDelta + 1)
                }
    }

    private fun calculateDeltasEquispaced(values: List<Float>, accum: List<List<Float>> = emptyList()): List<List<Float>> =
        values.zipWithNext { a, b -> b - a}
            .let {
                if (it.isEmpty()) accum else calculateDeltasEquispaced(it, accum.plusElement(it))
            }

    private val pointsAreEquispaced: Boolean by lazy{
        if(points.size < 2) false
        else points.map { it.first }.zipWithNext { a, b -> b - a == h }.all { it } //chequeo que todos los puntos cumplan esa distancia
    }

    private fun stringXValues(i: Int) =
        points.filterIndexed{ index, _ -> index <= i }.fold(""){str, value -> "$str(x - ${value.first})"}

    private fun xValues(i: Int, x: Float) =
        points.filterIndexed{ index, _ -> index <= i }.fold(1F){accum, value -> accum * (x - value.first)}

    private fun differencesTableString() {
        println("|   X   |   Y   | ${stringTitles(deltas)}")
        points.forEachIndexed { index, (x, y) -> println("|  $x  |  $y  |${stringDeltas(deltas, index)}") }

    }

    private fun term(index: Int, delta: Float) =
        if (!pointsAreEquispaced) delta else calculateEquispacedTerm(index, delta)

    private fun calculateEquispacedTerm(index: Int, delta: Float): Float = //pow es elevar
        delta / (factorial(index + 1) * h.pow(index + 1))

    private fun stringTerm(index: Int, delta: Float): String =
        "[$delta / ${index.let { if (it == 0) h.toString() else "(${it + 1}! * $h^${it + 1})"}}]"

    private fun factorial(num: Int): Long {
        var factorial: Long = 1
        for (i in 1..num)
            factorial *= i
        return factorial
    }

    private fun stringTitles(deltas: List<List<Float>>) =
        deltas.foldIndexed("") { index, acc, _ -> "$acc  Δ${index.let { if (it > 0) "^${it+1}" else "" }}   |" }

    private fun stringDeltas(deltas: List<List<Float>>, index: Int): String =
        deltas.fold("") { acc, list -> if (list.size > index) "$acc ${list[index]}  |" else acc }
}