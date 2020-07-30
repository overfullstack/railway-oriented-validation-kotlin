package algebra

import arrow.core.Either

typealias Validator<ValidatableT, FailureT> = suspend (ValidatableT) -> Either<FailureT, Any?>

typealias FailFastStrategy<ValidatableT, FailureT> = suspend (ValidatableT?) -> Either<FailureT, ValidatableT?>
typealias AccumulationStrategy<ValidatableT, FailureT> = suspend (ValidatableT?) -> List<Either<FailureT, ValidatableT?>>
