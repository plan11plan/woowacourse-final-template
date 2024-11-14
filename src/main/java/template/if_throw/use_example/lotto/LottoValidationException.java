package template.if_throw.use_example.lotto;

public sealed class LottoValidationException extends IllegalArgumentException {
    private LottoValidationException(String message) {
        super(message);
    }

    public static final class NullNumbers extends LottoValidationException {
        public NullNumbers() {
            super("로또 번호는 null일 수 없습니다.");
        }
    }

    public static final class InvalidSize extends LottoValidationException {
        public InvalidSize() {
            super("로또 번호는 6개여야 합니다.");
        }
    }

    public static final class InvalidRange extends LottoValidationException {
        public InvalidRange(int number) {
            super(String.format("유효하지 않은 로또 번호입니다: %d", number));
        }
    }

    public static final class DuplicateNumbers extends LottoValidationException {
        public DuplicateNumbers() {
            super("로또 번호에 중복이 있습니다.");
        }
    }

    public static final class InvalidPrice extends LottoValidationException {
        public InvalidPrice(int price) {
            super(String.format("유효하지 않은 로또 가격입니다: %d", price));
        }
    }

    public static final class InvalidRound extends LottoValidationException {
        public InvalidRound(int round) {
            super(String.format("유효하지 않은 회차입니다: %d", round));
        }
    }
}
