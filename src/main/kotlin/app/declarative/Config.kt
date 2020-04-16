/* gakshintala created on 4/10/20 */
package app.declarative

import algebra.Validator
import algebra.liftAllToParentValidationType
import algebra.liftToParentValidationType
import app.common.MAX_SIZE_FOR_PARALLEL
import app.domain.ImmutableEgg
import app.domain.Yolk
import app.domain.validation.ValidationFailure
import app.domain.validation.ValidationFailures.NO_CHILD_TO_VALIDATE
import app.domain.validation.ValidationFailures.NO_PARENT_TO_VALIDATE_CHILD
import java.util.stream.Stream

/**
 * The Validation Chains.<br>
 * If these parent-child dependencies are complex, we can make use of some graph algorithm to create a linear dependency graph of all validations.
 */
val PARENT_VALIDATION_CHAIN = listOf(validate1Simple, validate2Throwable, validateParent3)

val CHILD_VALIDATION_CHAIN: List<Validator<Yolk, ValidationFailure>> = listOf(validateChild31, validateChild32)

val EGG_VALIDATION_CHAIN: List<Validator<ImmutableEgg, ValidationFailure>> = (
        PARENT_VALIDATION_CHAIN + liftAllToParentValidationType(
            CHILD_VALIDATION_CHAIN,
            ImmutableEgg::yolk,
            NO_PARENT_TO_VALIDATE_CHILD,
            NO_CHILD_TO_VALIDATE
        ) + listOf(
            validateParent41,
            validateParent42,
            liftToParentValidationType(
                validateChild4,
                ImmutableEgg::yolk,
                NO_PARENT_TO_VALIDATE_CHILD,
                NO_CHILD_TO_VALIDATE
            )
        ))

fun <E> getStreamBySize(list: List<E>): Stream<E> {
    return if (list.size >= MAX_SIZE_FOR_PARALLEL) list.parallelStream() else list.stream()
}
