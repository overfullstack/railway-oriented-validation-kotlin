package algebra

import arrow.core.Either

typealias Validator<ValidatableT, FailureT> = (Either<FailureT, ValidatableT>) -> Either<FailureT, Any?>
typealias FailFastStrategy<ValidatableT, FailureT> = (ValidatableT?) -> Either<FailureT, Any?>
typealias AccumulationStrategy<ValidatableT, FailureT> = (ValidatableT?) -> List<Either<FailureT, Any?>>
