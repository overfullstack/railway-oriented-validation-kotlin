/* gakshintala created on 4/13/20 */
package imperative

import common.expectedImperativeValidationResults
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ImperativeValidation2Test {
    @Test
    fun `Octopus Orchestrator`() {
        val badEggFailureBucketMap = validateEggCartonImperatively2()
        Assertions.assertEquals(expectedImperativeValidationResults, badEggFailureBucketMap)
    }
}
