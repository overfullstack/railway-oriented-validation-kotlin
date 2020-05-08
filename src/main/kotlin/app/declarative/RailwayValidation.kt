/* gakshintala created on 4/10/20 */
package app.declarative

import arrow.core.extensions.either.monad.flatten
import arrow.core.filterOrElse
import arrow.core.flatMap
import algebra.Validator
import app.domain.ImmutableEgg
import app.domain.Yolk
import app.domain.validation.ValidationFailure
import app.domain.validation.ValidationFailures.*

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

val validate1Simple: Validator<ImmutableEgg, ValidationFailure> = {
    it.filterOrElse(::simpleOperation1) { NO_EGG_TO_VALIDATE_1 }
}

val validate2Throwable: Validator<ImmutableEgg, ValidationFailure> = { validatedEgg ->
    validatedEgg
        .flatMap {
            throwableOperation2(it).unsafeRunSyncEither()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { TOO_LATE_TO_HATCH_2 })
}

val validateParent3: Validator<ImmutableEgg, ValidationFailure> = { validatedEgg ->
    validatedEgg
        .flatMap {
            throwableOperation3(it).unsafeRunSyncEither()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
}

val validateChild31: Validator<Yolk, ValidationFailure> = { validatedYolk ->
    validatedYolk
        .flatMap {
            throwableNestedOperation(it).unsafeRunSyncEither()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { YOLK_IS_IN_WRONG_COLOR_C_3 })
}

val validateChild32: Validator<Yolk, ValidationFailure> = { validatedYolk ->
    validatedYolk
        .flatMap {
            throwableNestedOperation(it).unsafeRunSyncEither()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { YOLK_IS_IN_WRONG_COLOR_C_3 })
}

val validateParent41: Validator<ImmutableEgg, ValidationFailure> = { validatedEgg ->
    validatedEgg
        .flatMap {
            throwableOperation3(it).unsafeRunSyncEither()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
}

val validateParent42: Validator<ImmutableEgg, ValidationFailure> = { validatedEgg ->
    validatedEgg
        .flatMap {
            throwableOperation3(it).unsafeRunSyncEither()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
}

val validateChild4: Validator<Yolk, ValidationFailure> = { validatedYolk ->
    validatedYolk
        .flatMap {
            throwableNestedOperation(it).unsafeRunSyncEither()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { YOLK_IS_IN_WRONG_COLOR_C_3 })
}
