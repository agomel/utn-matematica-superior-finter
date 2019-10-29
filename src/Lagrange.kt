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
        println("//TODO pintar el grado del polinomio")

        println()
        println("//TODO pintar si los puntos son o no equiespaciados")

        printSeparator()
    }

    override val polynomial: String
        get() = points.foldIndexed("P(x) =") { index, accumulated, it ->
            val maybePlus = if (index != 0) "+" else ""
            "$accumulated $maybePlus (${it.second / liEvaluate(it.first, it.first)}) ${liString(it.first)}"
        }


    override fun evaluate(k: Float) = points.map { (x, y) -> (y / liEvaluate(x, x)) * liEvaluate(x, k) }.sum()

    //Dado un valor de Xi obtengo el resto de valores de y para sacar la funcion Li
    private fun liValues(xiValue: Float) =
        points.filter { (x, _) -> !x.equals(xiValue) }.map { it.first }

    //Dado un valor de Xi obtengo su funcion Li y la evaluo en un valor k
    private fun liEvaluate(xiValue: Float, k: Float) = liValues(xiValue).fold(1f) { accumulated, value ->
        accumulated * (k - value)
    }

    //Para un valor de xi convierto a string el valor de su funcion Li
    private fun liString(xiValue: Float) = liValues(xiValue).fold("") { accumulated, value ->
        "$accumulated(x ${(-value).signAsString()} ${abs(value)})"
    }

    //    val polynomialCoefficient = puntos.map { (x, y) -> y / liEvaluate(x, x) }.sum() //Si da 0 no es de grado maximo
    //    val evaluate = { k: Float -> puntos.map { (x, y) -> (y / liEvaluate(x, x)) * liEvaluate(x, k) }.sum() }
}
