/* gakshintala created on 4/11/20 */
package algebra

import arrow.core.*
import arrow.fx.coroutines.parTraverse
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/* ---------------------------FAIL FAST--------------------------- */

fun <FailureT, ValidatableT> failFastStrategy(
    validations: List<Validator<ValidatableT, FailureT>>,
    throwableMapper: (Throwable) -> FailureT,
    invalidValidatable: FailureT,
): FailFastStrategy<ValidatableT, FailureT> = { validatable ->
    when (validatable) {
        null -> invalidValidatable.left()
        else -> {
            logger.info { "Processing $validatable on ${Thread.currentThread().name}" }
            // Validations are run sequential for fail-fast
            validations.fold(validatable.right() as Either<FailureT, Any?>) { previousValidationResult, currentValidation ->
                previousValidationResult.flatMap {
                    fireValidation(currentValidation, validatable, throwableMapper)
                }
            }.map { validatable } // To put back the original validatable in place of `Any?` in right state.
        }
    }
}

fun <FailureT, ValidatableT> failFastStrategy2(
    validations: List<Validator<ValidatableT, FailureT>>,
    throwableMapper: (Throwable) -> FailureT,
    invalidValidatable: FailureT,
): FailFastStrategy<ValidatableT, FailureT> = { validatable ->
    when (validatable) {
        null -> invalidValidatable.left()
        else -> {
            val validationResult = validations.asSequence()
                .map { fireValidation(it, validatable, throwableMapper) }
                .firstOrNull { it.isLeft() } ?: validatable.right()
            validationResult as Either<FailureT, ValidatableT?>
        }
    }
}

/* ---------------------------ACCUMULATION--------------------------- */

/**
 * Multiple validatables are validated in-parallel
 */
fun <FailureT, ValidatableT> accumulationStrategy(
    validations: List<Validator<ValidatableT, FailureT>>,
    throwableMapper: (Throwable) -> FailureT,
    invalidValidatable: FailureT,
): AccumulationStrategy<ValidatableT, FailureT> = { validatable ->
    when (validatable) {
        null -> listOf(invalidValidatable.left())
        else -> {
            validations.map { fireValidation(it, validatable, throwableMapper) }
                .map { it.map { validatable } }
        }
    }
}

/**
 * Multiple validations are run on each validatable in-parallel.
 */
fun <FailureT, ValidatableT> accumulationStrategyInParallel(
    validations: List<Validator<ValidatableT, FailureT>>,
    throwableMapper: (Throwable) -> FailureT,
    invalidValidatable: FailureT,
): AccumulationStrategy<ValidatableT, FailureT> = { validatable ->
    when (validatable) {
        null -> listOf(invalidValidatable.left())
        else -> {
            validations
                .parTraverse { validation -> fireValidation(validation, validatable, throwableMapper) }
                .map { it.map { validatable } }
        }
    }
}

private suspend fun <FailureT, ValidatableT> fireValidation(
    validation: Validator<ValidatableT, FailureT>,
    validatable: ValidatableT,
    throwableMapper: (Throwable) -> FailureT,
): Either<FailureT, Any?> = Either.catch {
    validation(validatable)
}.fold({ throwableMapper(it).left() }, ::identity)

/* ---------------------------IMPERATIVE FAIL FAST--------------------------- */

suspend fun <FailureT, ValidatableT> runAllValidationsFailFastStrategyImperative(
    validatables: List<ValidatableT?>,
    validations: List<Validator<ValidatableT, FailureT>>,
    throwableMapper: (Throwable) -> FailureT,
    invalidValidatable: FailureT,
): List<Either<FailureT, ValidatableT?>> {
    val validationResults = mutableListOf<Either<FailureT, ValidatableT?>>()
    for (validatable in validatables) {
        validationResults += when (validatable) { // 🚩 Mutation 
            null -> invalidValidatable.left()
            else -> {
                lateinit var validationResult: Either<FailureT, ValidatableT?>
                for (validation in validations) {
                    validationResult = try {
                        validation(validatable).map { validatable }
                    } catch (e: Throwable) {
                        throwableMapper(e).left()
                    }
                    if (validationResult.isLeft()) {
                        break
                    }
                }
                if (validationResult.isLeft()) validationResult else validatable.right()
            }
        }
    }
    return validationResults
}
