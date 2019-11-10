interface Interpolation {

    val polynomial: String

    fun printSteps()

    fun evaluate(k: Float): Float
}

object LagrangeItem : InterpolateItem("Lagrange") {

    override fun interpolation(points: List<OrderedPair>) = Lagrange(points)
}

object NewtonProgressive : InterpolateItem("Newton Gregory - Progresivo") {

    override fun interpolation(points: List<OrderedPair>) = NewtonGregoryProgressive(points)

}

object NewtonRegressive : InterpolateItem("Newton Gregory - Regresivo") {
    override fun interpolation(points: List<OrderedPair>) = NewtonGregoryRegressive(points)
}
