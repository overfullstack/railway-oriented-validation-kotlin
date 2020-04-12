package common

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right

/* gakshintala created on 4/10/20 */

fun <ParentT, ChildT, FailureT> liftToParentValidationType(
    childValidation: Validator<FailureT, ChildT>,
    toChildMapper: (ParentT) -> ChildT?,
    invalidParent: FailureT,
    invalidChild: FailureT
): Validator<FailureT, ParentT> = { validatedParent: Either<FailureT, ParentT?> ->
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
    childValidations: List<Validator<FailureT, ChildT>>,
    toChildMapper: (ParentT) -> ChildT?,
    invalidParent: FailureT,
    invalidChild: FailureT
): List<Validator<FailureT, ParentT>> = childValidations.map {
    liftToParentValidationType(
        it,
        toChildMapper,
        invalidParent,
        invalidChild
    )
}
