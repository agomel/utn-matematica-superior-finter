import java.lang.Exception

fun Number.signAsString(): String {
    fun isPositive() = when (this) {
        is Float -> this >= 0f
        is Int -> this >= 0
        else -> throw Exception("Only with Float and Integers")
    }

    return if (isPositive()) "+" else "-"
}

fun printSeparator() {
    println()
    println("/****************************************************/")
    println()
}