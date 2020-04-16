package algebra

import arrow.core.Either

typealias Validator<ValidatableT, FailureT> = (Either<FailureT, ValidatableT>) -> Either<FailureT, ValidatableT>
typealias FailFastStrategy<ValidatableT, FailureT> = (ValidatableT?) -> Either<FailureT, ValidatableT>
typealias AccumulationStrategy<ValidatableT, FailureT> = (ValidatableT?) -> List<Either<FailureT, ValidatableT>>
