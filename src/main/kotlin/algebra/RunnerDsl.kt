package algebra

import arrow.core.Either
import arrow.fx.coroutines.parTraverse

suspend fun <FailureT, ValidatableT> validateAndFailFast(
    validatables: List<ValidatableT?>,
    validators: List<Validator<ValidatableT, FailureT>>,
    throwableMapper: (Throwable) -> FailureT,
    invalidValidatable: FailureT,
): List<Either<FailureT, ValidatableT?>> =
    validatables.parTraverse(failFastStrategy(validators, throwableMapper, invalidValidatable))

suspend fun <FailureT, ValidatableT> validateAndAccumulateErrors(
    validatables: List<ValidatableT?>,
    validators: List<Validator<ValidatableT, FailureT>>,
    throwableMapper: (Throwable) -> FailureT,
    invalidValidatable: FailureT,
): List<List<Either<FailureT, ValidatableT?>>> =
    validatables.parTraverse(accumulationStrategy(validators, throwableMapper, invalidValidatable))

