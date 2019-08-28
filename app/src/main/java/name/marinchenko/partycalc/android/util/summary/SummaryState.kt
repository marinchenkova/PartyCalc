package name.marinchenko.partycalc.android.util.summary

class SummaryState(
        someListIsEmpty: Boolean,
        sumsAreEqual: Boolean
) {

    val state = if (someListIsEmpty) State.NothingToCalculate
    else if (!sumsAreEqual) State.SumsAreNotEqual
    else State.Ok


    enum class State {
        NothingToCalculate,
        SumsAreNotEqual,
        Ok
    }
}