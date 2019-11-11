import kotlin.math.pow

abstract class NewtonGregory(protected val points: List<OrderedPair>) : Interpolation {

    override fun printSteps() {
        printSeparator()

        println("1. Calculamos la tabla de diferencias finitas utilizando los puntos dados:")
        differencesTableString()

        if(pointsAreEquispaced) {
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

    protected abstract fun polynomialSteps(): String

    protected val deltas: List<List<Double>> by lazy {
        points.map { it.second }.let {
            if (pointsAreEquispaced)
                calculateDeltasEquispaced(it)
            else
                calculateDeltasNotEquispaced(it)
        }
    }

    protected val h: Double by lazy { points[1].first - points[0].first } //la distancia entre cada punto, si son equiespaciados

    protected abstract fun calculateDeltasNotEquispaced(values: List<Double>, accum: List<List<Double>> = emptyList(), nDelta: Int = 0): List<List<Double>>

    protected abstract fun calculateDeltasEquispaced(values: List<Double>, accum: List<List<Double>> = emptyList()): List<List<Double>>

    protected val pointsAreEquispaced: Boolean by lazy {
        if(points.size < 2) false
        else points.map { it.first }.zipWithNext { a, b -> b - a == h }.all { it } //chequeo que todos los puntos cumplan esa distancia
    }

    abstract fun stringXValues(i: Int) : String

    abstract fun xValues(i: Int, x: Double):Double

    abstract fun differencesTableString ()

    protected fun findGrade() =
        points.filterIndexed { index, _ -> index <= deltas.indexOf(biggestTerm()) }.size

    protected fun biggestTerm() =
        deltas.findLast { list -> !list.first().let { it.equals(0.0) } }

    protected fun term(index: Int, delta: Double) =
        if (!pointsAreEquispaced) delta else calculateEquispacedTerm(index, delta)

    protected fun calculateEquispacedTerm(index: Int, delta: Double): Double = //pow es elevar
        delta / (factorial(index + 1) * h.pow(index + 1))

    protected fun stringTerm(index: Int, delta: Double): String =
        "[$delta / ${index.let { if (it == 0) h.toString() else "(${it + 1}! * $h^${it + 1})"}}]"

    protected fun factorial(num: Int): Long {
        var factorial: Long = 1
        for (i in 1..num)
            factorial *= i
        return factorial
    }

    protected fun pad(title: String) =
        title.padEnd(13)

    abstract fun stringTitles() : String;

    protected fun stringDeltas(index: Int): String =
        deltas.fold("") { acc, list -> if (list.size > index) "$acc${pad(list[index].toString())}|" else acc }
}