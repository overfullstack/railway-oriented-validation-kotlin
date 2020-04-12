/* gakshintala created on 4/10/20 */
package domain

enum class Color {
    YELLOW, GOLD, ORANGE
}

enum class Condition {
    GOOD, BAD
}

data class Egg(var daysToHatch: Int, var yolk: Yolk?)

data class ImmutableEgg(val daysToHatch: Int, val yolk: Yolk?)

data class Yolk(val condition: Condition, val color: Color)
