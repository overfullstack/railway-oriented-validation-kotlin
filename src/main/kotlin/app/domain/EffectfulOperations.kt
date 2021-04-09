package app.domain

import app.domain.validation.ValidationFailure
import arrow.core.Either
import arrow.core.right
import kotlinx.coroutines.delay

private const val SIMULATE_BLOCKING_DELAY = 1000L

suspend fun getSomethingFromDb(boolean: Boolean): Either<ValidationFailure, Int> =
    if (boolean) {
        delay(SIMULATE_BLOCKING_DELAY)
        1.right()
    } else {
        0.right()
    }

suspend fun getSomethingElseFromNetwork(id: Int): Either<ValidationFailure, String> =
    if (id == 1) {
        delay(SIMULATE_BLOCKING_DELAY)
        "".right()
    } else {
        "something".right()
    }
