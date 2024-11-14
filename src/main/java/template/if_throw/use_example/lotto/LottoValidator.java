package template.if_throw.use_example.lotto;

import java.util.List;
import template.if_throw.common.validation.Validator;

public class LottoValidator {
    private static final int MINIMUM_NUMBER = 1;
    private static final int MAXIMUM_NUMBER = 45;
    private static final int LOTTO_SIZE = 6;
    private static final int MINIMUM_PRICE = 1000;
    private static final int MINIMUM_ROUND = 1;

    private LottoValidator() {
    }

    public static void validate(List<Integer> numbers, int price, int round) {
        validateNumbers(numbers);
        validatePrice(price);
        validateRound(round);
    }

    private static void validateNumbers(List<Integer> numbers) {
        Validator.check(numbers != null)
                .withError(new LottoValidationException.NullNumbers())

                .andCheck(numbers.size() == LOTTO_SIZE)
                .withError(new LottoValidationException.InvalidSize())

                .andCheck(hasNoDuplicates(numbers))
                .withError(new LottoValidationException.DuplicateNumbers())

                .validateAll();

        numbers.forEach(number ->
                Validator.check(isValidRange(number))
                        .withError(new LottoValidationException.InvalidRange(number))
                        .validateAll()
        );
    }

    private static void validatePrice(int price) {
        Validator.check(price >= MINIMUM_PRICE)
                .withError(new LottoValidationException.InvalidPrice(price))
                .validateAll();
    }

    private static void validateRound(int round) {
        Validator.check(round >= MINIMUM_ROUND)
                .withError(new LottoValidationException.InvalidRound(round))
                .validateAll();
    }

    private static boolean isValidRange(int number) {
        return number >= MINIMUM_NUMBER && number <= MAXIMUM_NUMBER;
    }

    private static boolean hasNoDuplicates(List<Integer> numbers) {
        return numbers.size() == numbers.stream().distinct().count();
    }
}