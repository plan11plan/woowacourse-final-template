package template.if_throw.use_example.lotto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LottoValidatorTest {

    @Nested
    @DisplayName("로또 번호 검증")
    class ValidateNumbers {

        @Test
        @DisplayName("유효한 로또 번호는 검증을 통과한다")
        void validNumbers_shouldPass() {
            // given
            List<Integer> validNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);

            // when & then
            assertDoesNotThrow(() ->
                    LottoValidator.validate(validNumbers, 1000, 1)
            );
        }

        @Test
        @DisplayName("6개가 아닌 로또 번호는 예외를 발생시킨다")
        void invalidSize_shouldThrowException() {
            // given
            List<Integer> invalidSizeNumbers = Arrays.asList(1, 2, 3, 4, 5);

            // when & then
            assertThatThrownBy(() ->
                    LottoValidator.validate(invalidSizeNumbers, 1000, 1)
            )
                    .isInstanceOf(LottoValidationException.InvalidSize.class);
        }

        @Test
        @DisplayName("중복된 로또 번호는 예외를 발생시킨다")
        void duplicateNumbers_shouldThrowException() {
            // given
            List<Integer> duplicateNumbers = Arrays.asList(1, 2, 3, 4, 5, 5);

            // when & then
            assertThatThrownBy(() ->
                    LottoValidator.validate(duplicateNumbers, 1000, 1)
            )
                    .isInstanceOf(LottoValidationException.DuplicateNumbers.class);
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 46, -1, 100})
        @DisplayName("범위를 벗어난 로또 번호는 예외를 발생시킨다")
        void numberOutOfRange_shouldThrowException(int invalidNumber) {
            // given
            List<Integer> numbersWithInvalidNumber = Arrays.asList(1, 2, 3, 4, 5, invalidNumber);

            // when & then
            assertThatThrownBy(() ->
                    LottoValidator.validate(numbersWithInvalidNumber, 1000, 1)
            )
                    .isInstanceOf(LottoValidationException.InvalidRange.class);
        }
    }

    @Nested
    @DisplayName("가격 검증")
    class ValidatePrice {

        @Test
        @DisplayName("1000원 이상의 가격은 검증을 통과한다")
        void validPrice_shouldPass() {
            // given
            List<Integer> validNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);

            // when & then
            assertDoesNotThrow(() ->
                    LottoValidator.validate(validNumbers, 1000, 1)
            );
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 500, 999, -1000})
        @DisplayName("1000원 미만의 가격은 예외를 발생시킨다")
        void invalidPrice_shouldThrowException(int invalidPrice) {
            // given
            List<Integer> validNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);

            // when & then
            assertThatThrownBy(() ->
                    LottoValidator.validate(validNumbers, invalidPrice, 1)
            )
                    .isInstanceOf(LottoValidationException.InvalidPrice.class);
        }
    }

    @Nested
    @DisplayName("회차 검증")
    class ValidateRound {

        @Test
        @DisplayName("1 이상의 회차는 검증을 통과한다")
        void validRound_shouldPass() {
            // given
            List<Integer> validNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);

            // when & then
            assertDoesNotThrow(() ->
                    LottoValidator.validate(validNumbers, 1000, 1)
            );
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, -100})
        @DisplayName("1 미만의 회차는 예외를 발생시킨다")
        void invalidRound_shouldThrowException(int invalidRound) {
            // given
            List<Integer> validNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);

            // when & then
            assertThatThrownBy(() ->
                    LottoValidator.validate(validNumbers, 1000, invalidRound)
            )
                    .isInstanceOf(LottoValidationException.InvalidRound.class);
        }
    }

    @Nested
    @DisplayName("복합 검증")
    class CombinedValidation {

        @Test
        @DisplayName("모든 값이 유효하면 검증을 통과한다")
        void allValid_shouldPass() {
            // given
            List<Integer> validNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
            int validPrice = 1000;
            int validRound = 1;

            // when & then
            assertDoesNotThrow(() ->
                    LottoValidator.validate(validNumbers, validPrice, validRound)
            );
        }

        @Test
        @DisplayName("순차적인 번호도 검증을 통과한다")
        void sequentialNumbers_shouldPass() {
            // given
            List<Integer> sequentialNumbers = IntStream.rangeClosed(1, 6)
                    .boxed()
                    .collect(Collectors.toList());

            // when & then
            assertDoesNotThrow(() ->
                    LottoValidator.validate(sequentialNumbers, 1000, 1)
            );
        }

        @Test
        @DisplayName("최대값을 사용한 번호도 검증을 통과한다")
        void maxNumbers_shouldPass() {
            // given
            List<Integer> maxNumbers = Arrays.asList(40, 41, 42, 43, 44, 45);

            // when & then
            assertDoesNotThrow(() ->
                    LottoValidator.validate(maxNumbers, 1000, 1)
            );
        }
    }
}
