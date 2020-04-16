/* gakshintala created on 4/10/20 */
package app.domain.validation

interface ValidationFailure {
    companion object {
        fun withErrorMessage(expMsg: String?): ValidationFailure {
            return ValidationWithException(expMsg)
        }
    }

    data class ValidationWithException(val expMsg: String?) : ValidationFailure
}
