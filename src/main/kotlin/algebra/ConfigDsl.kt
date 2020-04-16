package algebra

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right

/* gakshintala created on 4/10/20 */

fun <ParentT, ChildT, FailureT> liftToParentValidationType(
    childValidation: Validator<ChildT, FailureT>,
    toChildMapper: (ParentT) -> ChildT?,
    invalidParent: FailureT,
    invalidChild: FailureT
): Validator<ParentT, FailureT> = { validatedParent: Either<FailureT, ParentT?> ->
    validatedParent
        .flatMap { parent ->
            when (parent) {
                null -> invalidParent.left()
                else -> when (val child = toChildMapper(parent)) {
                    null -> invalidChild.left()
                    else -> childValidation(child.right()).map { parent }
                }
            }
        }
}

fun <ParentT, ChildT, FailureT> liftAllToParentValidationType(
    childValidations: List<Validator<ChildT, FailureT>>,
    toChildMapper: (ParentT) -> ChildT?,
    invalidParent: FailureT,
    invalidChild: FailureT
): List<Validator<ParentT, FailureT>> = childValidations.map {
    liftToParentValidationType(
        it,
        toChildMapper,
        invalidParent,
        invalidChild
    )
}
