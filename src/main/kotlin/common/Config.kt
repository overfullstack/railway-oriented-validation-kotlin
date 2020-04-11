/* gakshintala created on 4/10/20 */
package common

import arrow.core.Either
import declarative.validate1
import declarative.validate2
import declarative.validate31
import domain.ImmutableEgg
import domain.validation.ValidationFailure
import domain.validation.ValidationFailures

const val MIN_DAYS_TO_HATCH = 15
const val MAX_DAYS_TO_HATCH = 21
const val MAX_DAYS_TO_SHIP = 5
const val MAX_SIZE_FOR_PARALLEL = 10000

typealias Validator<FailureT, ValidatableT> = (Either<FailureT, ValidatableT>) -> Either<FailureT, ValidatableT>

private val validationList: List<Validator<ValidationFailure, ImmutableEgg>> =
    listOf(validate1, validate2) + liftToParentValidationType(
        validate31,
        ImmutableEgg::yolk,
        ValidationFailures.NO_PARENT_TO_VALIDATE_CHILD
    )
