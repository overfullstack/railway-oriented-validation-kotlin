/* gakshintala created on 4/11/20 */
package declarative

import arrow.core.Either
import arrow.core.right
import common.Validator

/* ---------------------------FAIL FAST--------------------------- */

fun <FailureT, ValidatableT> getFailFastStrategy(
    validations: List<Validator<FailureT, ValidatableT>>,
): (ValidatableT) -> Either<FailureT, ValidatableT> = {
    val initial: Either<FailureT, ValidatableT> = it.right()
    validations.fold(initial) { validated, currentValidation -> if (validated.isRight()) currentValidation(initial) else validated
    }
}

fun <FailureT, ValidatableT> getFailFastStrategy2(
    validations: List<Validator<FailureT, ValidatableT>>,
): (ValidatableT) -> Either<FailureT, ValidatableT> =
    { toBeValidated: ValidatableT ->
        val toBeValidatedRight: Either<FailureT, ValidatableT> = toBeValidated.right()
        validations
            .map { validation -> validation(toBeValidatedRight) }
            .firstOrNull { it.isLeft() } ?: toBeValidatedRight
    }

