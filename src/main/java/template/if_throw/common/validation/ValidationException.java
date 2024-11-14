package template.if_throw.common.validation;

public class ValidationException extends IllegalArgumentException {
    protected ValidationException(String message) {
        super(message);
    }
}
