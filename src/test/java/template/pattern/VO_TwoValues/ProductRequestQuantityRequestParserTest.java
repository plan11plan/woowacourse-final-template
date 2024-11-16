package template.pattern.VO_TwoValues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProductRequestQuantityRequestParserTest {

    @Nested
    @DisplayName("상품-수량 패턴 테스트")
    class ProductRequestQuantityPatternTest {
        private final Delimiter defaultDelimiter = new Delimiter("[", "]", ",", "-");

        @Test
        @DisplayName("정상적인 입력값이 주어지면 올바르게 파싱되어야 한다")
        void parseValidInput() {
            // given
            String input = "[콜라-3],[사이다-10]";

            // when
            List<ProductQuantityRequest> products = ProductQuantityRequestParser.toProductQuantities(input,
                    defaultDelimiter);

            // then
            assertThat(products)
                    .hasSize(2)
                    .satisfies(list -> {
                        // 첫 번째 상품 검증
                        assertThat(list.get(0))
                                .extracting(
                                        ProductQuantityRequest::getName,
                                        ProductQuantityRequest::getQuantity
                                )
                                .containsExactly("콜라", 3);

                        // 두 번째 상품 검증
                        assertThat(list.get(1))
                                .extracting(
                                        ProductQuantityRequest::getName,
                                        ProductQuantityRequest::getQuantity
                                )
                                .containsExactly("사이다", 10);
                    });
        }

        @Test
        @DisplayName("공백이 포함된 입력값도 올바르게 파싱되어야 한다")
        void parseInputWithSpaces() {
            // given
            String input = "[ 콜라 - 3 ] , [ 사이다 - 10 ]";

            // when
            List<ProductQuantityRequest> products = ProductQuantityRequestParser.toProductQuantities(input,
                    defaultDelimiter);

            // then
            assertThat(products)
                    .hasSize(2)
                    .first()
                    .satisfies(cola -> {
                        assertThat(cola)
                                .hasFieldOrPropertyWithValue("name", "콜라")
                                .hasFieldOrPropertyWithValue("quantity", 3);
                    });
        }

        @Test
        @DisplayName("잘못된 형식의 입력값은 예외가 발생해야 한다")
        void throwExceptionForInvalidFormat() {
            // given
            String input = "[콜라];[사이다-10]";

            // when & then
            assertThatIllegalArgumentException()
                    .isThrownBy(() ->
                            ProductQuantityRequestParser.toProductQuantities(input, defaultDelimiter)
                    )
                    .withMessage("[ERROR] 잘못된 형식입니다. 다시 입력해주세요.");
        }

        @Test
        @DisplayName("숫자가 아닌 수량이 입력되면 예외가 발생해야 한다")
        void throwExceptionForNonNumericQuantity() {
            // given
            String input = "[콜라-많이],[사이다-10]";

            // when & then
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() ->
                            ProductQuantityRequestParser.toProductQuantities(input, defaultDelimiter)
                    )
                    .withMessage("[ERROR] 잘못된 형식입니다. 다시 입력해주세요.");
        }

        @Test
        @DisplayName("다른 구분자를 사용해도 올바르게 파싱되어야 한다")
        void parseWithDifferentDelimiter() {
            // given
            Delimiter customDelimiter = new Delimiter("(", ")", ";", ":");
            String input = "(콜라:3);(사이다:10)";

            // when
            List<ProductQuantityRequest> products = ProductQuantityRequestParser.toProductQuantities(input,
                    customDelimiter);

            // then
            assertThat(products)
                    .hasSize(2)
                    .first()
                    .satisfies(product -> {
                        assertThat(product)
                                .extracting(
                                        ProductQuantityRequest::getName,
                                        ProductQuantityRequest::getQuantity
                                )
                                .containsExactly("콜라", 3);
                    });
        }
    }

}
