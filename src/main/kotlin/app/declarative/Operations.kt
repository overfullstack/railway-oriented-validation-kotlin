/* gakshintala created on 4/10/20 */
package app.declarative

import app.common.MAX_DAYS_TO_HATCH
import app.common.MAX_DAYS_TO_SHIP
import app.common.MIN_DAYS_TO_HATCH
import app.domain.Color
import app.domain.Condition
import app.domain.ImmutableEgg
import app.domain.Yolk
import app.domain.validation.THROWABLE_NESTED_OPERATION_31
import app.domain.validation.THROWABLE_NESTED_OPERATION_32
import app.domain.validation.THROWABLE_OPERATION_2
import app.domain.validation.THROWABLE_VALIDATION_3
import arrow.fx.IO
import arrow.fx.flattenEither

// These check positive cases, true = success ; false = ValidationFailure
// -----------------------|5----------------|15-------------------|21-------------------
// ----About to hatch----|------Valid-------|--Might never hatch--|--Too late to hatch--|

fun simpleOperation1(eggToBeValidated: ImmutableEgg?): Boolean = eggToBeValidated != null

/**
 * Imagine these operations are doing effectful operations and we don't have any control over the exception they throw
 * Otherwise, one should never throw exceptions, instead use `Either` to model failre.
 */
fun throwableOperation2(eggToBeValidated: ImmutableEgg): IO<Throwable, Boolean> = IO {
    if (eggToBeValidated.daysToHatch >= MAX_DAYS_TO_HATCH) {
        throw IllegalArgumentException(THROWABLE_OPERATION_2)
    } else {
        eggToBeValidated.daysToHatch <= MIN_DAYS_TO_HATCH
    }
}.attempt().flattenEither()

fun throwableOperation3(eggToBeValidated: ImmutableEgg): IO<Throwable, Boolean> = IO {
    if (eggToBeValidated.daysToHatch <= 0) {
        throw IllegalArgumentException(THROWABLE_VALIDATION_3)
    } else {
        eggToBeValidated.daysToHatch >= MAX_DAYS_TO_SHIP
    }
}.attempt().flattenEither()

fun throwableNestedOperation(yolk: Yolk?): IO<Throwable, Boolean> = IO {
    when {
        yolk == null -> throw IllegalArgumentException(THROWABLE_NESTED_OPERATION_31)
        yolk.condition == Condition.BAD -> throw IllegalArgumentException(THROWABLE_NESTED_OPERATION_32)
        else -> yolk.color === Color.GOLD || yolk.color === Color.YELLOW
    }
}.attempt().flattenEither()
