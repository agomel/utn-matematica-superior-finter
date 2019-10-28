class Lagrange(var puntos: Map<Float, Float>): Interpolation() {

    override fun printSteps() {
        //Pintamos cada Li(xi)
        puntos.keys.toList().forEachIndexed { index, xi ->
            println("L$index(xi) = ${liString(xi)}")
            println("L$index($xi) = ${liEvaluate(xi, xi)}")
        }

        println(polynomial())

        //TODO pintar el grado del polinomio
        //TODO pintar si los puntos son o no equiespaciados
    }

    //Polinomio entero en string
    override fun polynomial() =
        puntos.keys.fold("P(x)= ") {polinomioString, xi -> polinomioString.plus("(${puntos.getValue(xi) / liEvaluate(xi, xi)} ${liString(xi)}) +")}.dropLast(1)//drop last es para remover el ultimo +

//    val polynomialCoefficient = puntos.map { (x, y) -> y / liEvaluate(x, x) }.sum() //Si da 0 no es de grado maximo

    //    val evaluate = { k: Float -> puntos.map { (x, y) -> (y / liEvaluate(x, x)) * liEvaluate(x, k) }.sum() }
    override fun evaluate(k: Float): Float {
        return puntos.map { (x, y) -> (y / liEvaluate(x, x)) * liEvaluate(x, k) }.sum()
    }

    //Dado un valor de Xi obtego el resto de valores de y para sacar la funcion Li
    val liValues = {xiValue:Float ->  puntos.filter { (x, y) -> !x.equals(xiValue) }.keys }

    //Dado un valor de Xi obtengo su funcion Li y la evaluo en un valor k
    val liEvaluate = { xiValue:Float, k:Float -> liValues(xiValue).fold(1.toFloat()) { polinomio, value -> polinomio * (k-value) } }

    //Para un valor de xi convierto a string el valor de su funcion Li
    val liString = { xiValue:Float -> liValues(xiValue).fold("") { polinomio, value -> polinomio.plus("(x-$value)")} }
}

