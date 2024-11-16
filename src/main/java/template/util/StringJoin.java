package template.util;

import java.util.List;
import java.util.stream.Collectors;

public class StringJoin {
    /**
     * 여러 패턴을 하나의 문자열로 결합합니다.
     */
    public static String joinPatterns(List<String> patterns, String patternDelimiter) {
        return patterns.stream()
                .collect(Collectors.joining(patternDelimiter));
    }
}
