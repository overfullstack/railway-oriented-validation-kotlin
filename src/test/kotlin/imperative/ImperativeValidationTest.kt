package imperative

import common.expectedImperativeValidationResults
import mu.KLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/* gakshintala created on 4/12/20 */
class ImperativeValidationTest {
    @Test
    fun cyclomaticCode() {
        val badEggFailureBucketMap = validateEggCartonImperatively()
        logger.info { badEggFailureBucketMap }
        Assertions.assertEquals(expectedImperativeValidationResults, badEggFailureBucketMap)
    }

    companion object : KLogging()
}
