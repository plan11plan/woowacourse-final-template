package template.if_throw.common.validation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ValidatorTest {

    @Nested
    @DisplayName("check 메서드는")
    class CheckMethod {

        @Test
        @DisplayName("조건이 true이면 예외를 발생시키지 않는다")
        void whenConditionIsTrue_thenNoException() {
            // when & then
            Validator.check(true)
                    .withError(new IllegalArgumentException())
                    .validate();
        }

        @Test
        @DisplayName("조건이 false이면 설정된 예외를 발생시킨다")
        void whenConditionIsFalse_thenThrowException() {
            // when & then
            assertThatThrownBy(() ->
                    Validator.check(false)
                            .withError(new IllegalArgumentException("error"))
                            .validate()
            )
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("error");
        }
    }

    @Nested
    @DisplayName("andCheck 메서드는")
    class AndCheckMethod {

        @Test
        @DisplayName("모든 조건이 true이면 예외를 발생시키지 않는다")
        void whenAllConditionsAreTrue_thenNoException() {
            // when & then
            Validator.check(true)
                    .withError(new IllegalArgumentException())
                    .and()
                    .check(true)
                    .withError(new IllegalArgumentException())
                    .validate();
        }

        @Test
        @DisplayName("첫 번째 조건이 false이면 첫 번째 예외를 발생시킨다")
        void whenFirstConditionIsFalse_thenThrowFirstException() {
            // given
            String firstExceptionMessage = "first error";
            String secondExceptionMessage = "second error";

            // when & then
            assertThatThrownBy(() ->
                    Validator.check(false)
                            .withError(new IllegalArgumentException(firstExceptionMessage))
                            .check(true)
                            .withError(new IllegalArgumentException(secondExceptionMessage))
                            .validate()
            )
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(firstExceptionMessage);
        }

        @Test
        @DisplayName("두 번째 조건이 false이면 두 번째 예외를 발생시킨다")
        void whenSecondConditionIsFalse_thenThrowSecondException() {
            // given
            String firstExceptionMessage = "first error";
            String secondExceptionMessage = "second error";

            // when & then
            assertThatThrownBy(() ->
                    Validator.check(true)
                            .withError(new IllegalArgumentException(firstExceptionMessage))
                            .and()
                            .check(false)
                            .withError(new IllegalArgumentException(secondExceptionMessage))
                            .validate()
            )
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(secondExceptionMessage);
        }
    }

    @Nested
    @DisplayName("실제 사용 사례")
    class RealWorldExamples {

        @Test
        @DisplayName("나이 유효성 검증 - 정상 케이스")
        void validateAge_validAge() {
            // given
            int age = 25;

            // when & then
            Validator.check(age > 0)
                    .withError(new IllegalArgumentException("나이는 양수여야 합니다"))
                    .and()
                    .check(age < 150)
                    .withError(new IllegalArgumentException("나이가 너무 큽니다"))
                    .validate();
        }

        @Test
        @DisplayName("나이 유효성 검증 - 음수 나이")
        void validateAge_negativeAge() {
            // given
            int age = -1;

            // when & then
            assertThatThrownBy(() ->
                    Validator.check(age > 0)
                            .withError(new IllegalArgumentException("나이는 양수여야 합니다"))
                            .and()
                            .check(age < 150)
                            .withError(new IllegalArgumentException("나이가 너무 큽니다"))
                            .validate()
            )
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("나이는 양수여야 합니다");
        }

        @Test
        @DisplayName("문자열 유효성 검증")
        void validateString_nullAndEmpty() {
            // given
            String text = null;

            // when & then
            assertThatThrownBy(() ->
                    Validator.check(text != null)
                            .withError(new IllegalArgumentException("텍스트는 null일 수 없습니다"))
                            .check(text != null && !text.isBlank())
                            .withError(new IllegalArgumentException("텍스트는 비어있을 수 없습니다"))
                            .validate()
            )
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("텍스트는 null일 수 없습니다");
        }
    }
}
