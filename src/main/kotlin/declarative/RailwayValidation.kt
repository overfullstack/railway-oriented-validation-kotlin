/* gakshintala created on 4/10/20 */
package declarative

import arrow.core.extensions.either.monad.flatten
import arrow.core.filterOrElse
import arrow.core.flatMap
import common.Validator
import domain.ImmutableEgg
import domain.Yolk
import domain.validation.ValidationFailure
import domain.validation.ValidationFailures.ABOUT_TO_HATCH_P_3
import domain.validation.ValidationFailures.NO_EGG_TO_VALIDATE_1

val validate1: Validator<ValidationFailure, ImmutableEgg> = {
    it.filterOrElse(::simpleOperation1) { NO_EGG_TO_VALIDATE_1 }
}

val validate2: Validator<ValidationFailure, ImmutableEgg> = { validatedEgg ->
    validatedEgg
        .flatMap {
            throwableOperation2(it).unsafeRunSyncEither().flatten()
                .mapLeft { ValidationFailure.withErrorMessage(it.message) }
        }
        .filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
        .flatMap { validatedEgg }
}

val validate31: Validator<ValidationFailure, Yolk> = { validatedYolk ->
    validatedYolk
        .flatMap {
            throwableNestedOperation31(it).unsafeRunSyncEither().flatten()
                .mapLeft { ValidationFailure.withErrorMessage(it.message) }
        }
        .filterOrElse({ it }, { ABOUT_TO_HATCH_P_3 })
        .flatMap { validatedYolk }
}
