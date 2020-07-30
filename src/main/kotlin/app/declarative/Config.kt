/* gakshintala created on 4/10/20 */
package app.declarative

import algebra.Validator
import algebra.liftAllToParentValidationType
import algebra.liftToParentValidationType
import app.domain.Egg
import app.domain.Yolk
import app.domain.validation.ValidationFailure
import app.domain.validation.ValidationFailures.NO_CHILD_TO_VALIDATE
import app.domain.validation.ValidationFailures.NO_PARENT_TO_VALIDATE_CHILD

/**
 * The Validation Chains.<br>
 * If these parent-child dependencies are complex, we can make use of some graph algorithm to create a linear dependency graph of all validations.
 */
val PARENT_VALIDATION_CHAIN: List<Validator<Egg, ValidationFailure>> =
    listOf(validate1SimpleX, validate2ThrowableX, validateParent3X)

val CHILD_VALIDATION_CHAIN: List<Validator<Yolk, ValidationFailure>> =
    listOf(validateChild31X, validateChild32X)

val EGG_VALIDATION_CHAIN: List<Validator<Egg, ValidationFailure>> = (
        PARENT_VALIDATION_CHAIN + liftAllToParentValidationType(
            CHILD_VALIDATION_CHAIN,
            Egg::yolk,
            NO_PARENT_TO_VALIDATE_CHILD,
            NO_CHILD_TO_VALIDATE
        ) + listOf(
            validateParent41X,
            validateParent42X,
            liftToParentValidationType(
                validateChild4X,
                Egg::yolk,
                NO_PARENT_TO_VALIDATE_CHILD,
                NO_CHILD_TO_VALIDATE
            )
        ))
