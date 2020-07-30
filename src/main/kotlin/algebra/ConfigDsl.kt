package algebra

import arrow.core.left

/* gakshintala created on 4/10/20 */
fun <ParentT, ChildT, FailureT> liftToParentValidationType(
    childValidation: Validator<ChildT, FailureT>,
    toChildMapper: (ParentT) -> ChildT?,
    invalidParent: FailureT,
    invalidChild: FailureT,
): Validator<ParentT, FailureT> = { parent: ParentT ->
    when (parent) {
        null -> invalidParent.left()
        else -> when (val child = toChildMapper(parent)) {
            null -> invalidChild.left()
            else -> childValidation(child)
        }
    }
}

fun <ParentT, ChildT, FailureT> liftAllToParentValidationType(
    childValidations: List<Validator<ChildT, FailureT>>,
    toChildMapper: (ParentT) -> ChildT?,
    invalidParent: FailureT,
    invalidChild: FailureT,
): List<Validator<ParentT, FailureT>> = childValidations.map {
    liftToParentValidationType(
        it,
        toChildMapper,
        invalidParent,
        invalidChild
    )
}
