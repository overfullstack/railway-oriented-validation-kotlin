/* gakshintala created on 4/12/20 */
package app.declarative

import algebra.getErrorAccumulationStrategy2
import algebra.getFailFastStrategyStrategy
import algebra.runAllValidationsFailFastStrategyImperative
import app.common.expectedDeclarativeValidationResults
import app.common.immutableEggCarton
import app.domain.validation.ValidationFailures.NOTHING_TO_VALIDATE
import mu.KLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.streams.toList

class RailwayValidationTest {
    @Test
    fun `plain Old Imperative Orchestration`() {
        val validationResults = runAllValidationsFailFastStrategyImperative(
            immutableEggCarton,
            EGG_VALIDATION_CHAIN,
            NOTHING_TO_VALIDATE
        )
        logger.info { validationResults }
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    @Test
    fun `declarative Orchestration FailFast`() {
        val validationResults =
            immutableEggCarton.asSequence()
                .map(getFailFastStrategyStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
                .toList()
        logger.info { validationResults }
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    @Test
    fun `declarative Orchestration Error Accumulation`() {
        val validationResultsAccumulated = immutableEggCarton.asSequence()
            .map(getErrorAccumulationStrategy2(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
            .toList()
        logger.info { validationResultsAccumulated }
    }

    @Test
    fun `declarative Orchestration Parallel`() {
        val validationResults = getStreamBySize(immutableEggCarton)
            .map(getFailFastStrategyStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
            .toList()
        logger.info { validationResults }
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    companion object : KLogging()
}
