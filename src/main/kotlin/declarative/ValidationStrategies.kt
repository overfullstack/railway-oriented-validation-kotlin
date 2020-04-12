/* gakshintala created on 4/11/20 */
package declarative

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import common.Validator

/* ---------------------------FAIL FAST--------------------------- */

fun <FailureT, ValidatableT> getFailFastStrategy(
    validations: List<Validator<FailureT, ValidatableT>>,
    invalidValidatable: FailureT
): (ValidatableT?) -> Either<FailureT, ValidatableT> = {
    when (it) {
        null -> invalidValidatable.left()
        else -> {
            val initial: Either<FailureT, ValidatableT> = it.right()
            validations.fold(initial) { validated, currentValidation ->
                if (validated.isRight()) currentValidation(initial) else validated
            }
        }
    }
}

fun <FailureT, ValidatableT> getFailFastStrategy2(
    validations: List<Validator<FailureT, ValidatableT>>,
    invalidValidatable: FailureT
): (ValidatableT?) -> Either<FailureT, ValidatableT> =
    { toBeValidated ->
        when (toBeValidated) {
            null -> invalidValidatable.left()
            else -> {
                val toBeValidatedRight: Either<FailureT, ValidatableT> = toBeValidated.right()
                validations
                    .map { validation -> validation(toBeValidatedRight) }
                    .firstOrNull { it.isLeft() } ?: toBeValidatedRight
            }
        }
    }

fun <FailureT, ValidatableT> getErrorAccumulationStrategy(
    validations: List<Validator<FailureT, ValidatableT>>,
    invalidValidatable: FailureT
): (ValidatableT?) -> List<Either<FailureT, ValidatableT>> =
    { toBeValidated ->
        when (toBeValidated) {
            null -> listOf(invalidValidatable.left())
            else -> {
                val toBeValidatedRight: Either<FailureT, ValidatableT> = toBeValidated.right()
                validations
                    .map { validation -> validation(toBeValidatedRight) }
                    .toList()
            }
        }
    }

fun <FailureT, ValidatableT> runAllValidationsFailFastImperative(
    validatables: List<ValidatableT?>,
    validations: List<Validator<FailureT, ValidatableT>>,
    invalidValidatable: FailureT
): List<Either<FailureT, ValidatableT>> {
    val validationResults = mutableListOf<Either<FailureT, ValidatableT>>()
    for (validatable in validatables) {
        validationResults += when (validatable) {
            null -> invalidValidatable.left()
            else -> {
                var toBeValidatedRight: Either<FailureT, ValidatableT> = validatable.right()
                for (validation in validations) {
                    toBeValidatedRight = validation(toBeValidatedRight)
                    if (toBeValidatedRight.isLeft()) {
                        break
                    }
                }
                toBeValidatedRight
            }
        }
    }
    return validationResults
}
