package common

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import java.util.function.UnaryOperator

/* gakshintala created on 4/10/20 */

fun <ParentT, ChildT, FailureT> liftToParentValidationType(
    childValidation: UnaryOperator<Either<FailureT, ChildT>>,
    toChildMapper: (ParentT) -> ChildT,
    invalidParent: FailureT
) = { validatedParent: Either<FailureT, ParentT?> ->
    validatedParent
        .flatMap { parent ->
            when (parent) {
                null -> invalidParent.left()
                else -> childValidation.apply(toChildMapper(parent).right())
                    .map { parent }
            }
        }
}

fun <ParentT, ChildT, FailureT> liftAllToParentValidationType(
    childValidations: List<UnaryOperator<Either<FailureT, ChildT>>>,
    toChildMapper: (ParentT) -> ChildT,
    invalidParent: FailureT
) = childValidations.map {
    liftToParentValidationType(
        it,
        toChildMapper,
        invalidParent
    )
}
