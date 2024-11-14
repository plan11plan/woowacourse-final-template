package template.if_throw.common.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Validator {
    public static ValidationStep check(boolean condition) {
        return new ValidationStep(new Validation(condition, null));
    }

    public static class ValidationStep {
        private final List<Validation> validations;

        private ValidationStep(Validation validation) {
            this.validations = Collections.singletonList(validation);
        }

        private ValidationStep(List<Validation> validations) {
            this.validations = Collections.unmodifiableList(validations);
        }

        public ValidationStep withError(RuntimeException exception) {
            // 마지막 validation을 새로운 exception으로 교체
            List<Validation> newValidations = new ArrayList<>(validations);
            Validation lastValidation = newValidations.remove(newValidations.size() - 1);
            newValidations.add(new Validation(lastValidation.condition, exception));

            return new ValidationStep(newValidations);
        }

        public ValidationStep andCheck(boolean condition) {
            List<Validation> newValidations = new ArrayList<>(validations);
            newValidations.add(new Validation(condition, null));

            return new ValidationStep(newValidations);
        }

        public void validateAll() {
            validations.forEach(Validation::validate);
        }
    }

    private static final class Validation {
        private final boolean condition;
        private final RuntimeException exception;

        private Validation(boolean condition, RuntimeException exception) {
            this.condition = condition;
            this.exception = exception;
        }

        void validate() {
            if (!condition) {
                throw exception;
            }
        }
    }
}
