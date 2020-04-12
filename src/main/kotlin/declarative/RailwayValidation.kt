/* gakshintala created on 4/10/20 */
package declarative

import arrow.core.extensions.either.monad.flatten
import arrow.core.filterOrElse
import arrow.core.flatMap
import common.Validator
import domain.ImmutableEgg
import domain.Yolk
import domain.validation.ValidationFailure
import domain.validation.ValidationFailures.*

val validate1Simple: Validator<ValidationFailure, ImmutableEgg> = {
    it.filterOrElse(::simpleOperation1) { NO_EGG_TO_VALIDATE_1 }
}

val validate2Throwable: Validator<ValidationFailure, ImmutableEgg> = { validatedEgg ->
    validatedEgg
        .flatMap {
            throwableOperation2(it).unsafeRunSyncEither().flatten()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { TOO_LATE_TO_HATCH_2 })
        .flatMap { validatedEgg }
}

val validateParent3: Validator<ValidationFailure, ImmutableEgg> = { validatedEgg ->
    validatedEgg
        .flatMap {
            throwableOperation3(it).unsafeRunSyncEither().flatten()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
        .flatMap { validatedEgg }
}

val validateChild31: Validator<ValidationFailure, Yolk> = { validatedYolk ->
    validatedYolk
        .flatMap {
            throwableNestedOperation31(it).unsafeRunSyncEither().flatten()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { YOLK_IS_IN_WRONG_COLOR_C_3 })
        .flatMap { validatedYolk }
}

val validateChild32: Validator<ValidationFailure, Yolk> = { validatedYolk ->
    validatedYolk
        .flatMap {
            throwableNestedOperation31(it).unsafeRunSyncEither().flatten()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { YOLK_IS_IN_WRONG_COLOR_C_3 })
        .flatMap { validatedYolk }
}

val validateParent41: Validator<ValidationFailure, ImmutableEgg> = { validatedEgg ->
    validatedEgg
        .flatMap {
            throwableOperation3(it).unsafeRunSyncEither().flatten()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
        .flatMap { validatedEgg }
}

val validateParent42: Validator<ValidationFailure, ImmutableEgg> = { validatedEgg ->
    validatedEgg
        .flatMap {
            throwableOperation3(it).unsafeRunSyncEither().flatten()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
        .flatMap { validatedEgg }
}

val validateChild4: Validator<ValidationFailure, Yolk> = { validatedYolk ->
    validatedYolk
        .flatMap {
            throwableNestedOperation31(it).unsafeRunSyncEither().flatten()
                .mapLeft { exception -> ValidationFailure.withErrorMessage(exception.message) }
        }
        .filterOrElse({ it }, { YOLK_IS_IN_WRONG_COLOR_C_3 })
        .flatMap { validatedYolk }
}
