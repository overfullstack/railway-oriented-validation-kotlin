/* gakshintala created on 4/12/20 */
package app.common

import app.domain.Color.*
import app.domain.Condition.BAD
import app.domain.Condition.GOOD
import app.domain.Egg
import app.domain.Yolk
import app.domain.validation.*
import app.domain.validation.ValidationFailure.Companion.withErrorMessage
import app.domain.validation.ValidationFailures.*
import arrow.core.Either
import arrow.core.left
import arrow.core.right

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
    2 to withErrorMessage(THROWABLE_NESTED_OPERATION_32),
    3 to withErrorMessage(THROWABLE_OPERATION_2),
    5 to withErrorMessage(THROWABLE_VALIDATION_3),
    6 to TOO_LATE_TO_HATCH_2,
    8 to withErrorMessage(THROWABLE_VALIDATION_3),
    9 to withErrorMessage(THROWABLE_NESTED_OPERATION_32),
    10 to YOLK_IS_IN_WRONG_COLOR_C_3,
    11 to withErrorMessage(THROWABLE_NESTED_OPERATION_31)
)

val expectedDeclarativeValidationResults: List<Either<ValidationFailure, Egg>> = listOf(
    NOTHING_TO_VALIDATE.left(),
    ABOUT_TO_HATCH_P_3.left(),
    withErrorMessage(THROWABLE_NESTED_OPERATION_32).left(),
    withErrorMessage(THROWABLE_OPERATION_2).left(),
    Egg(5, Yolk(GOOD, YELLOW)).right(),
    withErrorMessage(THROWABLE_VALIDATION_3).left(),
    TOO_LATE_TO_HATCH_2.left(),
    Egg(14, Yolk(GOOD, GOLD)).right(),
    withErrorMessage(THROWABLE_VALIDATION_3).left(),
    withErrorMessage(THROWABLE_NESTED_OPERATION_32).left(),
    YOLK_IS_IN_WRONG_COLOR_C_3.left(),
    NO_CHILD_TO_VALIDATE.left()
)
