// CORE
fun <T> request(requestText: String = "", condition: (String?) -> T?): T {
    while (true) {
        print("> $requestText")
        condition(readLine())?.let { return it }
    }
}

fun <T : OptionItem> requestOption(requestText: String = "", list: List<T>): T {
    println()
    println(requestText)
    list.forEachIndexed { i, it -> println("${i + 1}. ${it.title}") }
    println()

    return request { it?.trim()?.toIntOrNull()?.let { index -> list.getOrNull(index - 1) } }
}

// PRIMITIVES
fun requestInt(requestText: String = "") = request(requestText) { it?.toIntOrNull() }
fun requestDouble(requestText: String = "") = request(requestText) { it?.toDoubleOrNull() }

// DOMAIN
fun requestAction() = requestOption("Seleccione la acción a realizar:", ActionItem.values)
fun requestInterpolation() = requestOption("Seleccione la interpolación deseada:", InterpolateItem.values)
fun requestOrderedPairs(): List<OrderedPair> {

    println("Primero ingrese la cantidad de puntos")
    println()
    val count = requestInt()

    return if (count > 0) {
        println()
        println("Ingrese la serie de puntos")
        println()
        val xPoints = (1..count).map { requestDouble("x$it = ") }

        println()
        println("Ingrese sus imagenes")
        println()

        xPoints.mapIndexed { i, it -> it to requestDouble("x${i + 1} = $it => f(x${i + 1}) = f($it) = ") }
    } else {
        emptyList()
    }
}
