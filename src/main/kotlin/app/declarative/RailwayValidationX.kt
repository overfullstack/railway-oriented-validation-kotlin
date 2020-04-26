/* gakshintala created on 4/10/20 */
package app.declarative

import algebra.Validator
import app.domain.ImmutableEgg
import app.domain.Yolk
import app.domain.validation.ValidationFailure
import app.domain.validation.ValidationFailures.*
import arrow.core.Either
import arrow.core.extensions.fx
import arrow.core.filterOrElse

/**
 * <pre>
 * This class contains validations as functions.
 *
 * Requirements
 * ∙ Partial Failures
 *
 * Problems solved:
 * ∙ Octopus Orchestrator - 😵 dead
 * ∙ Mutation to Transformation
 * ∙ Unit-Testability - 👍
 * ∙ Complexity - Minimum
 * ∙ Chaos to Order
 * </pre>
 */

val validate1SimpleX: Validator<ImmutableEgg, ValidationFailure> = {
    it.filterOrElse(::simpleOperation1) { NO_EGG_TO_VALIDATE_1 }
}

val validate2ThrowableX: Validator<ImmutableEgg, ValidationFailure> = { validatedEgg ->
    Either.fx<ValidationFailure, Boolean> {
        val immutableEgg = !validatedEgg
        val operationResult = !throwableOperation2(immutableEgg).unsafeRunSyncEither()
        !operationResult.mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
    }.filterOrElse({ it }, { TOO_LATE_TO_HATCH_2 })
}

val validateParent3X: Validator<ImmutableEgg, ValidationFailure> = { validatedEgg ->
    Either.fx<ValidationFailure, Boolean> {
            val immutableEgg = !validatedEgg
            val operationResult = !throwableOperation3(immutableEgg).unsafeRunSyncEither()
            !operationResult.mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }.filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
}

val validateChild31X: Validator<Yolk, ValidationFailure> = { validatedYolk ->
    Either.fx<ValidationFailure, Boolean> {
        val yolk = !validatedYolk
        val operationResult = !throwableNestedOperation(yolk).unsafeRunSyncEither()
        !operationResult.mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
    }.filterOrElse({ it }, { YOLK_IS_IN_WRONG_COLOR_C_3 })
}

val validateChild32X: Validator<Yolk, ValidationFailure> = { validatedYolk ->
    Either.fx<ValidationFailure, Boolean> {
        val yolk = !validatedYolk
        val operationResult = !throwableNestedOperation(yolk).unsafeRunSyncEither()
        !operationResult.mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
    }.filterOrElse({ it }, { YOLK_IS_IN_WRONG_COLOR_C_3 })
}

val validateParent41X: Validator<ImmutableEgg, ValidationFailure> = { validatedEgg ->
    Either.fx<ValidationFailure, Boolean> {
        val immutableEgg = !validatedEgg
        val operationResult = !throwableOperation3(immutableEgg).unsafeRunSyncEither()
        !operationResult.mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
    }.filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
}

val validateParent42X: Validator<ImmutableEgg, ValidationFailure> = { validatedEgg ->
    Either.fx<ValidationFailure, Boolean> {
        val immutableEgg = !validatedEgg
        val operationResult = !throwableOperation3(immutableEgg).unsafeRunSyncEither()
        !operationResult.mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
    }.filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
}

val validateChild4X: Validator<Yolk, ValidationFailure> = { validatedYolk ->
    Either.fx<ValidationFailure, Boolean> {
        val yolk = !validatedYolk
        val operationResult = !throwableNestedOperation(yolk).unsafeRunSyncEither()
        !operationResult.mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
    }.filterOrElse({ it }, { YOLK_IS_IN_WRONG_COLOR_C_3 })
}
