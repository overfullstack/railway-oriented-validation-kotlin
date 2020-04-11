/* gakshintala created on 4/10/20 */
package declarative

import arrow.fx.IO
import common.MAX_DAYS_TO_HATCH
import common.MAX_DAYS_TO_SHIP
import common.MIN_DAYS_TO_HATCH
import domain.Color
import domain.Condition
import domain.ImmutableEgg
import domain.Yolk
import domain.validation.THROWABLE_NESTED_OPERATION_31
import domain.validation.THROWABLE_NESTED_OPERATION_32
import domain.validation.THROWABLE_OPERATION_2
import domain.validation.THROWABLE_VALIDATION_3

fun simpleOperation1(eggToBeValidated: ImmutableEgg?): Boolean = eggToBeValidated != null

fun throwableOperation2(eggToBeValidated: ImmutableEgg) = IO {
    if (eggToBeValidated.daysToHatch >= MAX_DAYS_TO_HATCH) {
        throw IllegalArgumentException(THROWABLE_OPERATION_2)
    } else {
        eggToBeValidated.daysToHatch <= MIN_DAYS_TO_HATCH
    }
}.attempt()

fun throwableOperation3(eggToBeValidated: ImmutableEgg) = IO {
    if (eggToBeValidated.daysToHatch <= 0) {
        throw IllegalArgumentException(THROWABLE_VALIDATION_3)
    } else {
        eggToBeValidated.daysToHatch >= MAX_DAYS_TO_SHIP
    }
}.attempt()

fun throwableNestedOperation31(yolk: Yolk?) = IO {
    when {
        yolk == null -> throw IllegalArgumentException(THROWABLE_NESTED_OPERATION_31)
        yolk.condition == Condition.BAD -> throw IllegalArgumentException(THROWABLE_NESTED_OPERATION_32)
        else -> yolk.color === Color.GOLD || yolk.color === Color.YELLOW
    }
}.attempt()
