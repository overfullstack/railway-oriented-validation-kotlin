/* gakshintala created on 4/12/20 */
package app.imperative

import app.domain.Egg
import app.domain.validation.ValidationFailure
import app.domain.validation.ValidationFailures

/**
 * <pre>
 * Problems:
 * ∙ Complexity
 * ∙ Mutation
 * . Non-sharable
 * ∙ Unit-Testability
 * ∙ Validation Jenga
 * </pre>
 */
@Suppress("LoopWithTooManyJumpStatements", "TooGenericExceptionCaught")
fun validateEggCartonImperatively(eggCarton: MutableList<Egg?>): Map<Int, ValidationFailure>? {
    val badEggFailureBucketMap = mutableMapOf<Int, ValidationFailure>()
    var eggIndex = 0
    val iterator = eggCarton.iterator()
    while (iterator.hasNext()) {
        val eggToBeValidated = iterator.next()
        if (!simpleOperation1(eggToBeValidated)) {
            iterator.remove() // Mutation
            // How can you cleanly map validation-failure to which validation-method failed?
            badEggFailureBucketMap[eggIndex] = ValidationFailures.NO_EGG_TO_VALIDATE_1
            eggIndex++
            continue
        }
        try {
            if (!throwableOperation2(eggToBeValidated!!)) {
                iterator.remove()
                badEggFailureBucketMap[eggIndex] = ValidationFailures.TOO_LATE_TO_HATCH_2
                eggIndex++
                continue
            }
        } catch (e: Exception) { // Repetition of same logic for exception handling
            iterator.remove()
            badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
            eggIndex++
            continue
        }
        try { // Inter-dependent validations
            if (throwableOperation3(eggToBeValidated)) {
                val yolkTobeValidated = eggToBeValidated.yolk
                try {
                    if (!throwableNestedOperation(yolkTobeValidated)) {
                        iterator.remove()
                        badEggFailureBucketMap[eggIndex] = ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3
                    }
                } catch (e: Exception) {
                    iterator.remove()
                    badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
                }
            } else {
                iterator.remove()
                badEggFailureBucketMap[eggIndex] = ValidationFailures.ABOUT_TO_HATCH_P_3
                eggIndex++
                continue
            }
        } catch (e: Exception) {
            iterator.remove()
            badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
        }
        eggIndex++
    }
    return badEggFailureBucketMap
}
