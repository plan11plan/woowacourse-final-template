package template.if_throw.use_example.lotto;

import java.util.Collections;
import java.util.List;

public class Lotto {
    private final List<Integer> numbers;
    private final int price;
    private final int round;

    private Lotto(List<Integer> numbers, int price, int round) {
        validate(numbers, price, round);
        this.numbers = Collections.unmodifiableList(numbers);
        this.price = price;
        this.round = round;
    }

    public static Lotto of(List<Integer> numbers, int price, int round) {
        return new Lotto(numbers, price, round);
    }

    private void validate(List<Integer> numbers, int price, int round) {
        LottoValidator.validate(numbers, price, round);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public int getPrice() {
        return price;
    }

    public int getRound() {
        return round;
    }
}
