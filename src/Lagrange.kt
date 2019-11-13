import kotlin.math.abs

class Lagrange(private val points: List<OrderedPair>) : Interpolation {

    override fun printSteps() {

        printSeparator()

        println("1. Obtenemos todos los li(x) = Π (x - xj) / (xi - xj)")
        points.forEachIndexed { index, it ->
            println("L$index(x) = ${liString(it.first)} / ${liEvaluate(it.first, it.first)}")
        }

        println()
        println("2. Obtenemos  p(x) = Σ yk lk(x)")
        println(points.foldIndexed("P(x) =") { index, accumulated, it ->
            val maybePlus = if (index != 0) "+" else ""
            "$accumulated $maybePlus (${it.second} / ${liEvaluate(it.first, it.first)}) ${liString(it.first)}"
        })

        println()
        println("3. El polinomio final es:")
        println(polynomial)

        println()
        println("El grado del polinomio es ${NewtonGregoryProgressive(points).findGrade()}")

        println()
        println("Los puntos ${if(pointsAreEquispaced) "no" else ""} son equiespaciados")
        println()

        printSeparator()
    }

    override val polynomial: String
        get() = points.foldIndexed("P(x) =") { index, accumulated, it ->
            val maybePlus = if (index != 0) "+" else ""
            "$accumulated $maybePlus (${it.second / liEvaluate(it.first, it.first)}) ${liString(it.first)}"
        }


    override fun evaluate(k: Double) = points.map { (x, y) -> (y / liEvaluate(x, x)) * liEvaluate(x, k) }.sum().round()

    //Dado un valor de Xi obtengo el resto de valores de y para sacar la funcion Li
    private fun liValues(xiValue: Double) =
        points.filter { (x, _) -> !x.equals(xiValue) }.map { it.first }

    //Dado un valor de Xi obtengo su funcion Li y la evaluo en un valor k
    private fun liEvaluate(xiValue: Double, k: Double) = liValues(xiValue).fold(1.0) { accumulated, value ->
        accumulated * (k - value)
    }


    //Obtengo la suma de todos los yi / Li(xi)
    private fun polinomialWithoutLi():Double = points.map { (x, y) -> (y / liEvaluate(x, x)) }.sum()

    //Para un valor de xi convierto a string el valor de su funcion Li
    private fun liString(xiValue: Double) = liValues(xiValue).fold("") { accumulated, value ->
        "$accumulated(x ${(-value).signAsString()} ${abs(value)})"
    }

    private val pointsAreEquispaced: Boolean by lazy{
        if(points.size < 2) false
        else{
            val h = points[1].first - points[0].first
            points.map { it.first }.zipWithNext { a, b -> b - a == h }.all { it } //chequeo que todos los puntos cumplan esa distancia
        }
    }

}
