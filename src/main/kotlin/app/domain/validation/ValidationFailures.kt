/* gakshintala created on 4/10/20 */
package app.domain.validation

enum class ValidationFailures(failureMsg: String) : ValidationFailure {
    NONE("NONE"),
    NO_PARENT_TO_VALIDATE_CHILD("Child-1: No parent to validate child 👨‍👦"),
    NO_CHILD_TO_VALIDATE("Parent: No child to validate 👦"),
    NOTHING_TO_VALIDATE("It's a Null, nothing to validate"),
    NO_EGG_TO_VALIDATE_1("1: No egg to validate 🧐"),
    TOO_LATE_TO_HATCH_2("2: Too late to hatch ⏳"),
    ABOUT_TO_HATCH_P_3("Parent-3: About to hatch 🥚"), YOLK_IS_IN_WRONG_COLOR_C_3("Child-3: Yolk is in wrong color ✴️");
}
