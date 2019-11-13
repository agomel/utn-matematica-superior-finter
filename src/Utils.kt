import java.lang.Exception
import kotlin.math.pow
import kotlin.math.roundToInt

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

fun Double.round(decimals: Int = 6) = (this * 10.0.pow(decimals)).roundToInt() / 10.0.pow(decimals)