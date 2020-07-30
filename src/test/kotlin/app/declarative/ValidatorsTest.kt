/* gakshintala created on 4/12/20 */
package app.declarative

import algebra.*
import app.common.eggCartonImmutable
import app.common.expectedDeclarativeValidationResults
import app.domain.Egg
import app.domain.validation.ValidationFailure
import app.domain.validation.ValidationFailures.NOTHING_TO_VALIDATE
import arrow.core.Either
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

class RailwayValidationTest {
    @Test
    fun `plain Old Imperative Orchestration`() = runBlocking {
        val validationResults = runAllValidationsFailFastStrategyImperative(
            eggCartonImmutable,
            EGG_VALIDATION_CHAIN,
            (ValidationFailure)::withException,
            NOTHING_TO_VALIDATE
        )
        logger.info { validationResults }
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    @Test
    fun `Fail Fast`() = runBlocking {
        // No CoroutineScope, so all suspend validations run sequentially blocking each-other.
        // This can be optimized to run on default dispatcher.
        // Individual validations can run on their own IO dispatchers if needed, for IO operations.
        val validationResults =
            eggCartonImmutable.asFlow()
                .map(
                    failFastStrategy(
                        EGG_VALIDATION_CHAIN,
                        (ValidationFailure)::withException,
                        NOTHING_TO_VALIDATE
                    )
                )
                .toList()
        logger.info { validationResults }
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    @Test
    fun `Fail Fast In Parallel`() = runBlocking {
        val validationResults =
            // Apply all validations FF on each egg, iterating eggs in parallel, with Default Thread Pool
            validateAndFailFast(
                eggCartonImmutable,
                EGG_VALIDATION_CHAIN,
                (ValidationFailure)::withException,
                NOTHING_TO_VALIDATE
            )
        logger.info { validationResults }
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    @Test
    fun `Fail Fast 2`() = runBlocking {
        // No CoroutineScope, so all suspend validations run sequentially blocking each-other.
        // This can be optimized to run on default dispatcher.
        // Individual validations can run on their own IO dispatchers if needed, for IO operations.
        val validationResults =
            eggCartonImmutable.asFlow()
                .map(
                    failFastStrategy2(
                        EGG_VALIDATION_CHAIN,
                        (ValidationFailure)::withException,
                        NOTHING_TO_VALIDATE
                    )
                )
                .toList()
        logger.info { validationResults }
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    @Test
    fun `Error Accumulation`() = runBlocking {
        val validationResultsAccumulated = eggCartonImmutable.asFlow()
            .map(
                accumulationStrategy(
                    EGG_VALIDATION_CHAIN,
                    (ValidationFailure)::withException,
                    NOTHING_TO_VALIDATE
                )
            )
            .toList()
        logger.info { validationResultsAccumulated }
    }

    @Test
    fun `Error Accumulation in Parallel`() = runBlocking {
        val validationResultsAccumulatedInParallel: List<List<Either<ValidationFailure, Egg?>>> =
            validateAndAccumulateErrors(
                eggCartonImmutable,
                EGG_VALIDATION_CHAIN,
                (ValidationFailure)::withException,
                NOTHING_TO_VALIDATE
            )
        logger.info { validationResultsAccumulatedInParallel }
    }
}
