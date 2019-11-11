import java.lang.Exception

fun Number.signAsString(): String {
    fun isPositive() = when (this) {
        is Double -> this >= 0.0
        is Int -> this >= 0
        else -> throw Exception("Only with Double and Integers")
    }

    return if (isPositive()) "+" else "-"
}

fun printSeparator() {
    println()
    println("/****************************************************/")
    println()
}