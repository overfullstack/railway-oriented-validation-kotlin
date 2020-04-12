/* gakshintala created on 4/12/20 */
package declarative

import common.EGG_VALIDATION_CHAIN
import common.expectedDeclarativeValidationResults
import common.getStreamBySize
import common.immutableEggCarton
import domain.validation.ValidationFailures.NOTHING_TO_VALIDATE
import mu.KLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.streams.toList

class RailwayValidationTest {
    @Test
    fun `plain Old Imperative Orchestration`() {
        val validationResults = runAllValidationsFailFastImperative(
            immutableEggCarton,
            EGG_VALIDATION_CHAIN,
            NOTHING_TO_VALIDATE
        )
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    @Test
    fun `declarative Orchestration FailFast`() {
        val validationResults =
            immutableEggCarton.asSequence()
                .map(getFailFastStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
                .toList()
        logger.info { validationResults }
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    @Test
    fun `declarative Orchestration Error Accumulation`() {
        val validationResultsAccumulated = immutableEggCarton.asSequence()
            .map(getErrorAccumulationStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
            .toList()
        logger.info { validationResultsAccumulated }
    }

    @Test
    fun `declarative Orchestration Parallel`() {
        val validationResults = getStreamBySize(immutableEggCarton)
            .map(getFailFastStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
            .toList()
        logger.info { validationResults }
        Assertions.assertEquals(expectedDeclarativeValidationResults, validationResults)
    }

    companion object : KLogging()
}
