package template.if_throw.common.validation;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static ValidationStep check(boolean condition) {
        return new ValidationStep(condition);
    }

    public static class ValidationStep {
        private final List<Validation> validations = new ArrayList<>();

        private ValidationStep(boolean condition) {
            validations.add(new Validation(condition));
        }

        public ValidationStep withError(RuntimeException exception) {
            validations.get(validations.size() - 1).setException(exception);
            return this;
        }

        public ValidationStep andCheck(boolean condition) {
            validations.add(new Validation(condition));
            return this;
        }

        public void validateAll() {
            validations.forEach(Validation::validate);
        }
    }

    private static class Validation {
        private final boolean condition;
        private RuntimeException exception;

        Validation(boolean condition) {
            this.condition = condition;
        }

        void setException(RuntimeException exception) {
            this.exception = exception;
        }

        void validate() {
            if (!condition) {
                throw exception;
            }
        }
    }
}
