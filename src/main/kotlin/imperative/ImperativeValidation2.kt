/* gakshintala created on 4/12/20 */
package imperative

import common.eggCarton
import domain.Egg
import domain.Yolk
import domain.validation.ValidationFailure
import domain.validation.ValidationFailures

fun validate1(
    badEggFailureBucketMap: MutableMap<Int, ValidationFailure>,
    eggIndex: Int,
    iterator: MutableIterator<Egg?>,
    eggToBeValidated: Egg?
): Boolean {
    if (!simpleOperation1(eggToBeValidated)) {
        iterator.remove()
        badEggFailureBucketMap[eggIndex] = ValidationFailures.NO_EGG_TO_VALIDATE_1
        return false
    }
    return true
}

@Suppress("ReturnCount")
fun validate2(
    badEggFailureBucketMap: MutableMap<Int, ValidationFailure>,
    eggIndex: Int,
    iterator: MutableIterator<Egg?>,
    eggToBeValidated: Egg?
): Boolean {
    try {
        if (!throwableOperation2(eggToBeValidated!!)) {
            iterator.remove()
            badEggFailureBucketMap[eggIndex] = ValidationFailures.TOO_LATE_TO_HATCH_2
            return false
        }
    } catch (e: Exception) {
        iterator.remove()
        badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
        return false
    }
    return true
}

@Suppress("ReturnCount")
fun validateParent3(
    badEggFailureBucketMap: MutableMap<Int, ValidationFailure>,
    eggIndex: Int,
    iterator: MutableIterator<Egg?>,
    eggToBeValidated: Egg
): Boolean {
    try {
        if (throwableOperation3(eggToBeValidated)) {
            val yolkTobeValidated = eggToBeValidated.yolk
            if (!validateChild31(
                    badEggFailureBucketMap,
                    eggIndex,
                    iterator,
                    yolkTobeValidated
                )
            ) {
                return false
            }
            if (!validateChild32(
                    badEggFailureBucketMap,
                    eggIndex,
                    iterator,
                    yolkTobeValidated
                )
            ) {
                return false
            }
        } else {
            iterator.remove()
            badEggFailureBucketMap[eggIndex] = ValidationFailures.ABOUT_TO_HATCH_P_3
            return false
        }
    } catch (e: java.lang.Exception) {
        iterator.remove()
        badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
        return false
    }
    return true
}

@Suppress("ReturnCount")
fun validateChild31(
    badEggFailureBucketMap: MutableMap<Int, ValidationFailure>,
    eggIndex: Int,
    iterator: MutableIterator<Egg?>,
    yolkTobeValidated: Yolk?
): Boolean {
    try {
        if (!throwableNestedOperation3(yolkTobeValidated)) {
            iterator.remove()
            badEggFailureBucketMap[eggIndex] = ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3
            return false
        }
    } catch (e: java.lang.Exception) {
        iterator.remove()
        badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
        return false
    }
    return true
}

@Suppress("ReturnCount")
fun validateChild32(
    badEggFailureBucketMap: MutableMap<Int, ValidationFailure>,
    eggIndex: Int,
    iterator: MutableIterator<Egg?>,
    yolkTobeValidated: Yolk?
): Boolean {
    try {
        if (!throwableNestedOperation3(yolkTobeValidated)) {
            iterator.remove()
            badEggFailureBucketMap[eggIndex] = ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3
            return false
        }
    } catch (e: java.lang.Exception) {
        iterator.remove()
        badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
        return false
    }
    return true
}

@Suppress("ReturnCount")
fun validateChild4(
    badEggFailureBucketMap: MutableMap<Int, ValidationFailure>,
    eggIndex: Int,
    iterator: MutableIterator<Egg?>,
    eggToBeValidated: Egg
): Boolean {
    if (!validateParent41(
            badEggFailureBucketMap,
            eggIndex,
            iterator,
            eggToBeValidated
        )
    ) {
        return false
    }
    if (!validateParent42(
            badEggFailureBucketMap,
            eggIndex,
            iterator,
            eggToBeValidated
        )
    ) {
        return false
    }
    val yolkTobeValidated = eggToBeValidated.yolk
    try {
        if (!throwableNestedOperation3(yolkTobeValidated)) {
            iterator.remove()
            badEggFailureBucketMap[eggIndex] = ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3
            return false
        }
    } catch (e: java.lang.Exception) {
        iterator.remove()
        badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
        return false
    }
    return true
}

@Suppress("ReturnCount")
fun validateParent41(
    badEggFailureBucketMap: MutableMap<Int, ValidationFailure>,
    eggIndex: Int,
    iterator: MutableIterator<Egg?>,
    eggToBeValidated: Egg?
): Boolean {
    try {
        if (!throwableOperation3(eggToBeValidated!!)) {
            iterator.remove()
            badEggFailureBucketMap[eggIndex] = ValidationFailures.ABOUT_TO_HATCH_P_3
            return false
        }
    } catch (e: java.lang.Exception) {
        iterator.remove()
        badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
        return false
    }
    return true
}

@Suppress("ReturnCount")
fun validateParent42(
    badEggFailureBucketMap: MutableMap<Int, ValidationFailure>,
    eggIndex: Int,
    iterator: MutableIterator<Egg?>,
    eggToBeValidated: Egg?
): Boolean {
    try {
        if (!throwableOperation3(eggToBeValidated!!)) {
            iterator.remove()
            badEggFailureBucketMap[eggIndex] = ValidationFailures.ABOUT_TO_HATCH_P_3
            return false
        }
    } catch (e: java.lang.Exception) {
        iterator.remove()
        badEggFailureBucketMap[eggIndex] = ValidationFailure.withErrorMessage(e.message)
        return false
    }
    return true
}

@Suppress("LoopWithTooManyJumpStatements")
fun validateEggCartonImperatively2(): MutableMap<Int, ValidationFailure> {
    // R3 - Trying to be the owner of all state.
    val badEggFailureBucketMap = mutableMapOf<Int, ValidationFailure>()
    var eggIndex = 0
    val iterator = eggCarton.iterator()
    while (iterator.hasNext()) {
        // R-1: Iterate through eggs
        val eggToBeValidated = iterator.next()

        // Global state is dangerous. badEggFailureBucketMap and iterator being passed to each and every function, difficult to keep track of how they are being mutated during debugging.
        if (!validate1(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
            eggIndex++
            continue // R-2: Manage fail-fast
        }

        // Adding a new validation in-between requires you to understand all the validations above and below, which slows down development and makes it prone to bugs.
        if (!validate2(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
            eggIndex++
            continue
        }

        // Parent with multiple Child Validations
        if (!validateParent3(
                badEggFailureBucketMap,
                eggIndex,
                iterator,
                eggToBeValidated!!
            )
        ) {
            eggIndex++
            continue
        }

        // Child with multiple Parent Validations
        if (!validateChild4(
                badEggFailureBucketMap,
                eggIndex,
                iterator,
                eggToBeValidated
            )
        ) {
            eggIndex++
            continue
        }
        eggIndex++
    }
    /*for (entry in badEggFailureBucketMap.entries) {
        log.info(entry)
    }*/
    return badEggFailureBucketMap
}
