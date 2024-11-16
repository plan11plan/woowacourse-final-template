package template.util;

public class StringLengthValidator {
    private StringLengthValidator() {
    }

    public static boolean isLengthBetween(String text, int min, int max) {
        if (text == null) {
            return false;
        }
        int length = text.length();
        return length >= min && length <= max;
    }
}
