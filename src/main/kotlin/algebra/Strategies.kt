/* gakshintala created on 4/11/20 */
package algebra

import arrow.core.Either
import arrow.core.left
import arrow.core.right

/* ---------------------------FAIL FAST--------------------------- */

fun <FailureT, ValidatableT> getFailFastStrategyStrategy(
    validations: List<Validator<ValidatableT, FailureT>>,
    invalidValidatable: FailureT
): FailFastStrategy<ValidatableT, FailureT> = {
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

fun <FailureT, ValidatableT> getFailFastStrategyStrategy2(
    validations: List<Validator<ValidatableT, FailureT>>,
    invalidValidatable: FailureT
): FailFastStrategy<ValidatableT, FailureT> =
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
    validations: List<Validator<ValidatableT, FailureT>>,
    invalidValidatable: FailureT
): AccumulationStrategy<ValidatableT, FailureT> =
    {
        when (it) {
            null -> listOf(invalidValidatable.left())
            else -> {
                val toBeValidatedRight: Either<FailureT, ValidatableT> = it.right()
                validations.fold(emptyList()) { failureResults, currentValidation ->
                    val result = currentValidation(toBeValidatedRight)
                    if (result.isLeft()) (failureResults + result) else failureResults
                }
            }
        }
    }

fun <FailureT, ValidatableT> getErrorAccumulationStrategy2(
    validations: List<Validator<ValidatableT, FailureT>>,
    invalidValidatable: FailureT
): AccumulationStrategy<ValidatableT, FailureT> =
    { toBeValidated ->
        when (toBeValidated) {
            null -> listOf(invalidValidatable.left())
            else -> {
                val toBeValidatedRight: Either<FailureT, ValidatableT> = toBeValidated.right()
                validations
                    .map { validation -> validation(toBeValidatedRight) }
                    .filter { it.isLeft() }
            }
        }
    }

fun <FailureT, ValidatableT> runAllValidationsFailFastStrategyImperative(
    validatables: List<ValidatableT?>,
    validations: List<Validator<ValidatableT, FailureT>>,
    invalidValidatable: FailureT
): List<Either<FailureT, ValidatableT>> {
    val validationResults = mutableListOf<Either<FailureT, ValidatableT>>()
    for (validatable in validatables) {
        validationResults += when (validatable) { // 🚩 Mutation 
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
