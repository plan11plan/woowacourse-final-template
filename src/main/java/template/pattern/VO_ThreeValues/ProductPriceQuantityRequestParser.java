package template.pattern.VO_ThreeValues;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPriceQuantityRequestParser {
    private static final String INVALID_FORMAT = "[ERROR] 잘못된 형식입니다. 다시 입력해주세요.";
    private static final int EXPECTED_SIZE = 3;

    private final List<String> values;

    private ProductPriceQuantityRequestParser(List<String> values) {
        validateSize(values, EXPECTED_SIZE);
        this.values = values;
    }

    /**
     * 문자열을 파싱하여 ProductRequest 객체 리스트로 변환합니다.
     *
     * @param input     입력 문자열 (예: "[콜라,1500,20];[사이다,1000,10]")
     * @param delimiter 구분자 정보
     * @return ProductRequest 객체 리스트
     */
    public static List<ProductRequest> toProducts(String input, Delimiter delimiter) {
        return ParseUtil.parsePatterns(input, delimiter)
                .stream()
                .map(ProductPriceQuantityRequestParser::new)
                .map(ProductPriceQuantityRequestParser::toProduct)
                .collect(Collectors.toList());
    }

    /**
     * 현재 값들을 ProductRequest 객체로 변환합니다.
     */
    private ProductRequest toProduct() {
        try {
            return new ProductRequest(
                    values.get(0),
                    Integer.parseInt(values.get(1)),
                    Integer.parseInt(values.get(2))
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_FORMAT);
        }
    }

    private static void validateSize(List<String> values, int expectedSize) {
        if (values.size() != expectedSize) {
            throw new IllegalArgumentException(INVALID_FORMAT);
        }
    }
}
