interface OptionItem {
    val title: String
}

abstract class InterpolateItem(override val title: String) : OptionItem {

    abstract fun interpolation(points: List<OrderedPair>): Interpolation

    companion object {
        val values = listOf(
            LagrangeItem,
            NewtonProgressive,
            NewtonRegressive
        )
    }
}

abstract class ActionItem(override val title: String) : OptionItem {

    companion object {
        val values = listOf(
            ShowPolynomial,
            ShowSteps,
            SetValueToPolynomial,
            AlterValues,
            Exit)
    }
}