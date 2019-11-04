import kotlin.math.pow

class NewtonGregoryProgressive(private val points: List<OrderedPair>) : Interpolation {

    override val polynomial: String
        get() {
            return if (pointsAreEquispaced())
                ""
            else
                "P(X) = ${points.first().second} ${deltas.foldIndexed(""){index, str, list -> "$str ${list.first().let { if (it > 0) "+ $it${stringXValues(index)}" else "" }}"}}"
        }

    override fun printSteps() {
        println("1. Calculamos la tabla de diferencias finitas utilizando los puntos dados:")
        differencesTableString()
    }

    override fun evaluate(k: Float): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val deltas: List<List<Float>> by lazy {
        points.map { it.second }.let {
            if (pointsAreEquispaced())
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

    private fun pointsAreEquispaced(): Boolean {
        if(points.size > 1) return false
        return points.map { it.first }.zipWithNext { a, b -> b - a == h }.all { it } //chequeo que todos los puntos cumplan esa distancia
    }

    private fun stringXValues(i: Int) =
        points.filterIndexed{ index, _ -> index <= i }.fold(""){str, value -> "$str(x - ${value.first})"}

    private fun differencesTableString() {
        println("|  X  |  Y  | ${stringTitles(deltas)}")
        points.forEachIndexed { index, (x, y) -> println("| $x | $y |${stringDeltas(deltas, index)}") }

    }

    private fun calculateTerm(index: Int, delta: Float): Float = //pow es elevar
        delta / (factorial(index) * h.pow(index))

    private fun factorial(num: Int): Long {
        var factorial: Long = 1
        for (i in 1..num)
            factorial *= i
        return factorial
    }

    private fun stringTitles(deltas: List<List<Float>>) =
            deltas.foldIndexed("") { index, acc, _ -> "$acc Δ${index.let { if (it > 0) "^${it+1}" else "" }}  |" }

    private fun stringDeltas(deltas: List<List<Float>>, index: Int): String =
        deltas.fold("") { acc, list -> if (list.size > index) acc + " ${list[index]} |" else acc }
}