package template.if_throw.common.validation;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    private final List<Validation> validations;

    private Validator(List<Validation> validations) {
        this.validations = validations;
    }

    public static ValidationBuilder check(boolean condition) {
        return new ValidationBuilder(condition);
    }

    public static class ValidationBuilder {
        private final List<Validation> validations = new ArrayList<>();
        private boolean lastCondition;

        private ValidationBuilder(boolean condition) {
            this.lastCondition = condition;
        }

        public ValidationBuilder withError(RuntimeException exception) {
            validations.add(new Validation(lastCondition, exception));
            return this;
        }

        public ValidationBuilder and() {
            return this;
        }

        public ValidationBuilder check(boolean condition) {
            this.lastCondition = condition;
            return this;
        }

        public void validate() {
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
