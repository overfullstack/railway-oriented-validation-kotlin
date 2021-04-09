/* gakshintala created on 4/10/20 */
package app.declarative

import algebra.Validator
import app.common.MAX_DAYS_TO_SHIP
import app.domain.*
import app.domain.validation.THROWABLE_VALIDATION_3
import app.domain.validation.ValidationFailure
import app.domain.validation.ValidationFailures.*
import arrow.core.computations.either
import arrow.core.filterOrElse
import arrow.core.identity
import arrow.core.left
import arrow.core.right

/**
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
 */

val validate1SimpleX: Validator<Egg, ValidationFailure> = {
    if (simpleRule(it)) true.right() else NO_EGG_TO_VALIDATE_1.left()
}

/**
 * A Validation which does blocking operations.
 */
val validate2ThrowableX: Validator<Egg, ValidationFailure> = {
    // Awesome DSL from arrow to work with either in an imperative way (Best of both worlds).
    either<ValidationFailure, String> {
        val result = throwableRule2(it)
        val id = getSomethingFromDb(result).bind()
        getSomethingElseFromNetwork(id).bind()
    }.filterOrElse({ data -> data.isEmpty() }, { TOO_LATE_TO_HATCH_2 })
}

val validateParent3X: Validator<Egg, ValidationFailure> = {
    either<ValidationFailure, Boolean> {
        if (it.daysToHatch <= 0) {
            throw IllegalArgumentException(THROWABLE_VALIDATION_3)
        } else {
            it.daysToHatch >= MAX_DAYS_TO_SHIP // Otherwise, Might hatch too soon
        }
    }.filterOrElse(::identity) { ABOUT_TO_HATCH_P_3 }
}

val validateChild31X: Validator<Yolk, ValidationFailure> = {
    either<ValidationFailure, Boolean> {
        throwableNestedRule(it)
    }.filterOrElse(::identity) { YOLK_IS_IN_WRONG_COLOR_C_3 }
}

val validateChild32X: Validator<Yolk, ValidationFailure> = {
    either<ValidationFailure, Boolean> {
        throwableNestedRule(it)
    }.filterOrElse(::identity) { YOLK_IS_IN_WRONG_COLOR_C_3 }
}

val validateParent41X: Validator<Egg, ValidationFailure> = {
    either<ValidationFailure, Boolean> {
        throwableRule3(it)
    }.filterOrElse(::identity) { ABOUT_TO_HATCH_P_3 }
}

val validateParent42X: Validator<Egg, ValidationFailure> = {
    either<ValidationFailure, Boolean> {
        throwableRule3(it)
    }.filterOrElse(::identity) { ABOUT_TO_HATCH_P_3 }
}

val validateChild4X: Validator<Yolk, ValidationFailure> = {
    either<ValidationFailure, Boolean> {
        throwableNestedRule(it)
    }.filterOrElse(::identity) { YOLK_IS_IN_WRONG_COLOR_C_3 }
}
