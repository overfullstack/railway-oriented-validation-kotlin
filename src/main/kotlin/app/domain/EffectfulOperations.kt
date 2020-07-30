package app.domain

import app.domain.validation.ValidationFailure
import arrow.core.Either
import arrow.core.right

fun getSomethingFromDb(boolean: Boolean): Either<ValidationFailure, Int> = if (boolean) 1.right() else 0.right()

fun getSomethingElseFromNetwork(id: Int): Either<ValidationFailure, String> =
    if (id == 1) "".right() else "something".right()
