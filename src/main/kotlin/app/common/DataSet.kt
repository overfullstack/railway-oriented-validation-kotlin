/* gakshintala created on 4/12/20 */
package app.common

import app.domain.Color.*
import app.domain.Condition.BAD
import app.domain.Condition.GOOD
import app.domain.Egg
import app.domain.Yolk
import app.domain.validation.*
import app.domain.validation.ValidationFailures.*
import arrow.core.Either

val eggCarton: MutableList<Egg?> = mutableListOf(
    null, // No egg to validate
    Egg(1, Yolk(GOOD, GOLD)), // About to hatch
    Egg(8, Yolk(BAD, ORANGE)), // Yolk is bad
    Egg(25, Yolk(GOOD, ORANGE)), // Might never hatch
    Egg(5, Yolk(GOOD, YELLOW)), // Valid ✅
    Egg(-1, Yolk(BAD, GOLD)), // Chicken might already be out
    Egg(16, Yolk(GOOD, GOLD)), // Too late to hatch 
    Egg(14, Yolk(GOOD, GOLD)), // Valid ✅
    Egg(0, Yolk(BAD, YELLOW)), // Chicken might already be out
    Egg(6, Yolk(BAD, ORANGE)), // Yolk is bad
    Egg(12, Yolk(GOOD, ORANGE)), // Yolk in wrong color
    Egg(6, null) // No Yolk to validate
)

val eggCartonImmutable: List<Egg?> = listOf(
    null, // No Egg to validate
    Egg(1, Yolk(GOOD, GOLD)), // About to hatch
    Egg(8, Yolk(BAD, ORANGE)), // Yolk is bad
    Egg(25, Yolk(GOOD, ORANGE)), // Might never hatch
    Egg(5, Yolk(GOOD, YELLOW)), // Valid ✅
    Egg(-1, Yolk(BAD, GOLD)), // Chicken might already be out
    Egg(16, Yolk(GOOD, GOLD)), // Too late to hatch 
    Egg(14, Yolk(GOOD, GOLD)), // Valid ✅
    Egg(0, Yolk(BAD, YELLOW)), // Chicken might already be out
    Egg(6, Yolk(BAD, ORANGE)), // Yolk is bad
    Egg(12, Yolk(GOOD, ORANGE)), // Yolk in wrong color
    Egg(6, null) // No Yolk to validate
)

val expectedImperativeValidationResults: Map<Int, ValidationFailure> = mapOf(
    0 to NO_EGG_TO_VALIDATE_1,
    1 to ABOUT_TO_HATCH_P_3,
    2 to ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32),
    3 to ValidationFailure.withErrorMessage(THROWABLE_OPERATION_2),
    5 to ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3),
    6 to TOO_LATE_TO_HATCH_2,
    8 to ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3),
    9 to ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32),
    10 to YOLK_IS_IN_WRONG_COLOR_C_3,
    11 to ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_31)
)

val expectedDeclarativeValidationResults: List<Either<ValidationFailure, Egg>> = listOf(
    Either.left(NOTHING_TO_VALIDATE),
    Either.left(ABOUT_TO_HATCH_P_3),
    Either.left(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
    Either.left(ValidationFailure.withErrorMessage(THROWABLE_OPERATION_2)),
    Either.right(Egg(5, Yolk(GOOD, YELLOW))),
    Either.left(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
    Either.left(TOO_LATE_TO_HATCH_2),
    Either.right(Egg(14, Yolk(GOOD, GOLD))),
    Either.left(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
    Either.left(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
    Either.left(YOLK_IS_IN_WRONG_COLOR_C_3),
    Either.left(NO_CHILD_TO_VALIDATE)
)
