abstract class Interpolation() {
    abstract fun polynomial(): String
    abstract fun printSteps()
    abstract fun evaluate(k: Float): Float
}