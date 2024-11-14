package template.if_throw.use_example.lotto;

import java.util.List;
import java.util.function.Supplier;
import template.if_throw.common.validation.Validator;
import template.if_throw.common.validation.Validator.ValidationBuilder;

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
        checkNonNull(() -> numbers)
                .and()
                .checkSize(() -> numbers)
                .and()
                .checkDuplicates(() -> numbers)
                .validate();

        numbers.forEach(number -> checkRange(() -> number).validate());
    }

    private static ValidationBuilder checkNonNull(Supplier<List<Integer>> numbers) {
        return Validator.check(numbers.get() != null)
                .withError(new LottoValidationException.NullNumbers());
    }

    private static ValidationBuilder checkSize(Supplier<List<Integer>> numbers) {
        return Validator.check(numbers.get().size() == LOTTO_SIZE)
                .withError(new LottoValidationException.InvalidSize());
    }

    private static ValidationBuilder checkDuplicates(Supplier<List<Integer>> numbers) {
        return Validator.check(hasNoDuplicates(numbers.get()))
                .withError(new LottoValidationException.DuplicateNumbers());
    }

    private static ValidationBuilder checkRange(Supplier<Integer> number) {
        return Validator.check(isValidRange(number.get()))
                .withError(new LottoValidationException.InvalidRange(number.get()));
    }

    private static void validatePrice(int price) {
        checkPrice(() -> price).validate();
    }

    private static ValidationBuilder checkPrice(Supplier<Integer> price) {
        return Validator.check(price.get() >= MINIMUM_PRICE)
                .withError(new LottoValidationException.InvalidPrice(price.get()));
    }

    private static void validateRound(int round) {
        checkRound(() -> round).validate();
    }

    private static ValidationBuilder checkRound(Supplier<Integer> round) {
        return Validator.check(round.get() >= MINIMUM_ROUND)
                .withError(new LottoValidationException.InvalidRound(round.get()));
    }

    private static boolean isValidRange(int number) {
        return number >= MINIMUM_NUMBER && number <= MAXIMUM_NUMBER;
    }

    private static boolean hasNoDuplicates(List<Integer> numbers) {
        return numbers.size() == numbers.stream().distinct().count();
    }
}
