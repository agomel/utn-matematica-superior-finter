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

    protected val deltas: List<List<Float>> by lazy {
        points.map { it.second }.let {
            if (pointsAreEquispaced)
                calculateDeltasEquispaced(it)
            else
                calculateDeltasNotEquispaced(it)
        }
    }

    protected val h: Float by lazy { points[1].first - points[0].first } //la distancia entre cada punto, si son equiespaciados

    protected abstract fun calculateDeltasNotEquispaced(values: List<Float>, accum: List<List<Float>> = emptyList(), nDelta: Int = 0): List<List<Float>>

    protected abstract fun calculateDeltasEquispaced(values: List<Float>, accum: List<List<Float>> = emptyList()): List<List<Float>>

    protected val pointsAreEquispaced: Boolean by lazy {
        if(points.size < 2) false
        else points.map { it.first }.zipWithNext { a, b -> b - a == h }.all { it } //chequeo que todos los puntos cumplan esa distancia
    }

    abstract fun stringXValues(i: Int) : String

    protected fun xValues(i: Int, x: Float) =
        points.filterIndexed{ index, _ -> index <= i }.fold(1F){accum, value -> accum * (x - value.first)}

    abstract fun differencesTableString ()

    protected fun findGrade() =
        points.filterIndexed { index, _ -> index <= deltas.indexOf(biggestTerm()) }.size

    protected fun biggestTerm() =
        deltas.findLast { list -> !list.first().let { it.equals(0F) } }

    protected fun term(index: Int, delta: Float) =
        if (!pointsAreEquispaced) delta else calculateEquispacedTerm(index, delta)

    protected fun calculateEquispacedTerm(index: Int, delta: Float): Float = //pow es elevar
        delta / (factorial(index + 1) * h.pow(index + 1))

    protected fun stringTerm(index: Int, delta: Float): String =
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