/* gakshintala created on 4/10/20 */
package common

import arrow.core.Either
import declarative.*
import domain.ImmutableEgg
import domain.Yolk
import domain.validation.ValidationFailure
import domain.validation.ValidationFailures.NO_CHILD_TO_VALIDATE
import domain.validation.ValidationFailures.NO_PARENT_TO_VALIDATE_CHILD
import java.util.stream.Stream

const val MIN_DAYS_TO_HATCH = 15
const val MAX_DAYS_TO_HATCH = 21
const val MAX_DAYS_TO_SHIP = 5
const val MAX_SIZE_FOR_PARALLEL = 10000

typealias Validator<FailureT, ValidatableT> = (Either<FailureT, ValidatableT>) -> Either<FailureT, ValidatableT>

val PARENT_VALIDATION_CHAIN = listOf(validate1Simple, validate2Throwable, validateParent3)
val CHILD_VALIDATION_CHAIN: List<Validator<ValidationFailure, Yolk>> = listOf(validateChild31, validateChild32)

val EGG_VALIDATION_CHAIN: List<Validator<ValidationFailure, ImmutableEgg>> = (
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
