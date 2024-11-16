package template.pattern.VO_ThreeValues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProductRequestPriceQuantityRequestParserTest {
    @Nested
    @DisplayName("상품-가격-수량 패턴 테스트")
    class ProductRequestPriceQuantityPatternTest {
        private final Delimiter defaultDelimiter = new Delimiter("[", "]", ";", ",");

        @Test
        @DisplayName("정상적인 입력값이 주어지면 올바르게 파싱되어야 한다")
        void parseValidInput() {
            // given
            String input = "[콜라, 1500, 20];[사이다,1000,10]";

            // when
            List<ProductRequest> productRequests = ProductPriceQuantityRequestParser.toProducts(input,
                    defaultDelimiter);

            // then
            assertThat(productRequests)
                    .hasSize(2)
                    .satisfies(list -> {
                        // 첫 번째 상품 검증
                        ProductRequest cola = list.get(0);
                        assertThat(cola)
                                .extracting(
                                        ProductRequest::getName,
                                        ProductRequest::getPrice,
                                        ProductRequest::getQuantity
                                )
                                .containsExactly("콜라", 1500, 20);

                        // 두 번째 상품 검증
                        ProductRequest cider = list.get(1);
                        assertThat(cider)
                                .extracting(
                                        ProductRequest::getName,
                                        ProductRequest::getPrice,
                                        ProductRequest::getQuantity
                                )
                                .containsExactly("사이다", 1000, 10);
                    });
        }

        @Test
        @DisplayName("공백이 포함된 입력값도 올바르게 파싱되어야 한다")
        void parseInputWithSpaces() {
            // given
            String input = "[ 콜라 , 1500 , 20 ] ; [ 사이다 , 1000 , 10 ]";

            // when
            List<ProductRequest> productRequests = ProductPriceQuantityRequestParser.toProducts(input,
                    defaultDelimiter);

            // then
            assertThat(productRequests)
                    .hasSize(2)
                    .first()
                    .satisfies(cola -> {
                        assertThat(cola.getName()).isEqualTo("콜라");
                        assertThat(cola.getPrice()).isEqualTo(1500);
                        assertThat(cola.getQuantity()).isEqualTo(20);
                    });
        }

        @Test
        @DisplayName("잘못된 형식의 입력값은 예외가 발생해야 한다")
        void throwExceptionForInvalidFormat() {
            // given
            String input = "[콜라,1500];[사이다,1000,10]";

            // when & then
            assertThatThrownBy(() ->
                    ProductPriceQuantityRequestParser.toProducts(input, defaultDelimiter)
            )
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 잘못된 형식입니다. 다시 입력해주세요.");
        }

        @Test
        @DisplayName("숫자가 아닌 가격이나 수량이 입력되면 예외가 발생해야 한다")
        void throwExceptionForNonNumericValues() {
            // given
            String input = "[콜라,가격,20];[사이다,1000,10]";

            // when & then
            assertThatThrownBy(() ->
                    ProductPriceQuantityRequestParser.toProducts(input, defaultDelimiter)
            )
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 잘못된 형식입니다. 다시 입력해주세요.");
        }
    }

}
