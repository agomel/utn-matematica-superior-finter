interface Interpolation {

    val polynomial: String

    fun printSteps()

    fun evaluate(k: Float): Float
}

object LagrangeItem : InterpolateItem("Lagrange") {

    override fun interpolation(points: List<OrderedPair>) = Lagrange(points)
}

object NewtonProgressive : InterpolateItem("Newton Gregory - Progresivo") {

    override fun interpolation(points: List<OrderedPair>): Interpolation {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    // override val interpolation = object : Interpolation { ... }
}

object NewtonRegressive : InterpolateItem("Newton Gregory - Regresivo") {

    override fun interpolation(points: List<OrderedPair>): Interpolation {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    // override val interpolation = object : Interpolation { ... }
}
