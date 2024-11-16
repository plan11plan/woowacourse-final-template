package template.pattern.VO_ThreeValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 문자열 파싱의 핵심 기능을 제공하는 유틸리티 클래스입니다.
 */
public class ParseUtil {
    private static final String ERROR_NULL_OR_EMPTY = "[ERROR] 입력값이 null이거나 비어있습니다.";
    private static final String ERROR_INVALID_FORMAT = "[ERROR] 잘못된 형식입니다. 다시 입력해주세요.";

    public static List<List<String>> parsePatterns(String input, Delimiter delimiter) {
        validateInput(input);
        validatePattern(input, delimiter);
        String[] patterns = input.split(escapeDelimiter(delimiter.pattern()));
        List<List<String>> result = new ArrayList<>();

        for (String pattern : patterns) {
            validateSinglePattern(pattern, delimiter);
            result.add(parsePattern(pattern, delimiter));
        }
        return result;
    }

    private static void validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_NULL_OR_EMPTY);
        }
    }

    /**
     * 전체 입력 문자열의 패턴을 검증합니다. 예: "[값1,값2,값3];[값4,값5,값6]" 형식 검증
     */
    private static void validatePattern(String input, Delimiter delimiter) {
        // 시작과 끝 구분자가 올바르게 짝지어져 있는지 확인
        String patternRegex = buildPatternRegex(delimiter);
        if (!input.trim().matches(patternRegex)) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT);
        }

        // 구분자 사이에 올바르지 않은 문자가 있는지 확인
        String[] parts = input.split(escapeDelimiter(delimiter.pattern()));
        for (String part : parts) {
            if (!part.trim().matches(buildSinglePatternRegex(delimiter))) {
                throw new IllegalArgumentException(ERROR_INVALID_FORMAT);
            }
        }
    }

    /**
     * 단일 패턴의 형식을 검증합니다. 예: "[값1,값2,값3]" 형식 검증
     */
    private static void validateSinglePattern(String pattern, Delimiter delimiter) {
        String trimmed = pattern.trim();
        if (!trimmed.matches(buildSinglePatternRegex(delimiter))) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT);
        }
    }

    /**
     * 전체 패턴의 정규식을 생성합니다. 예: "\\[.*\\](;\\[.*\\])*" 형식의 정규식 생성
     */
    private static String buildPatternRegex(Delimiter delimiter) {
        String singlePattern = buildSinglePatternRegex(delimiter);
        return String.format("%s(%s%s)*",
                singlePattern,
                escapeDelimiter(delimiter.pattern()),
                singlePattern
        );
    }

    /**
     * 단일 패턴의 정규식을 생성합니다. 예: "\\[([^\\[\\]]*)\\]" 형식의 정규식 생성
     */
    private static String buildSinglePatternRegex(Delimiter delimiter) {
        return String.format("\\s*%s[^%s%s]*%s\\s*",
                escapeForRegex(delimiter.start()),
                escapeForRegex(delimiter.start()),
                escapeForRegex(delimiter.end()),
                escapeForRegex(delimiter.end())
        );
    }

    private static String escapeForRegex(String str) {
        return Pattern.quote(str);
    }

    private static List<String> parsePattern(String pattern, Delimiter delimiter) {
        // 대괄호 제거 및 공백 처리
        String cleaned = pattern.trim()
                .replaceAll(String.format("^\\s*%s|%s\\s*$",
                                escapeForRegex(delimiter.start()),
                                escapeForRegex(delimiter.end())),
                        ""
                ).trim();

        // 값을 구분자로 분리
        return Arrays.stream(cleaned.split(escapeDelimiter(delimiter.value())))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static String escapeDelimiter(String delimiter) {
        return Pattern.quote(delimiter);
    }
}
