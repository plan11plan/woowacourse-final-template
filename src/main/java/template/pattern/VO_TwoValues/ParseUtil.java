package template.pattern.VO_TwoValues;

import static template.if_throw.common.validation.Validator.check;

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

    // 정규식 관련 상수
    private static final String REGEX_ANY_CHAR = ".*";
    private static final String REGEX_NOT_BRACKETS = "[^%s%s]*";
    private static final String REGEX_SPACE = "\\s*";
    private static final String REGEX_START_SPACE = "^\\s*%s";
    private static final String REGEX_END_SPACE = "%s\\s*$";

    // 패턴 포맷 상수
    private static final String SINGLE_PATTERN_FORMAT = REGEX_SPACE + "%s" + REGEX_NOT_BRACKETS + "%s" + REGEX_SPACE;
    private static final String FULL_PATTERN_FORMAT = "%s(%s%s)*";

    public static List<List<String>> parsePatterns(String input, Delimiter delimiter) {
        validateInput(input);
        validatePattern(input, delimiter);
        return Arrays.stream(input.split(escapeDelimiter(delimiter.pattern())))
                .peek(pattern -> validateSinglePattern(pattern, delimiter))
                .map(pattern -> parsePattern(pattern, delimiter))
                .collect(Collectors.toList());
    }

    private static void validateInput(String input) {
        check(input == null || input.trim().isEmpty())
                .withError(new IllegalArgumentException(ERROR_NULL_OR_EMPTY))
                .validate();
    }

    /**
     * 전체 입력 문자열의 패턴을 검증합니다. 예: "[값1,값2,값3];[값4,값5,값6]" 형식 검증
     */
    private static void validatePattern(String input, Delimiter delimiter) {
        String patternRegex = buildPatternRegex(delimiter);
        check(!input.trim().matches(patternRegex))
                .withError(new IllegalArgumentException(ERROR_INVALID_FORMAT))
                .validate();

        Arrays.stream(input.split(escapeDelimiter(delimiter.pattern())))
                .map(String::trim)
                .forEach(part ->
                        check(!part.matches(buildSinglePatternRegex(delimiter)))
                                .withError(new IllegalArgumentException(ERROR_INVALID_FORMAT))
                                .validate()
                );
    }

    /**
     * 단일 패턴의 형식을 검증합니다. 예: "[값1,값2,값3]" 형식 검증
     */
    private static void validateSinglePattern(String pattern, Delimiter delimiter) {
        String trimmed = pattern.trim();
        check(!trimmed.matches(buildSinglePatternRegex(delimiter)))
                .withError(new IllegalArgumentException(ERROR_INVALID_FORMAT))
                .validate();
    }

    /**
     * 전체 패턴의 정규식을 생성합니다. 예: "\\[.*\\](;\\[.*\\])*" 형식의 정규식 생성
     */
    private static String buildPatternRegex(Delimiter delimiter) {
        String singlePattern = buildSinglePatternRegex(delimiter);
        return String.format(FULL_PATTERN_FORMAT,
                singlePattern,
                escapeDelimiter(delimiter.pattern()),
                singlePattern);
    }

    /**
     * 단일 패턴의 정규식을 생성합니다. 예: "\\[([^\\[\\]])\\]" 형식의 정규식 생성
     */
    private static String buildSinglePatternRegex(Delimiter delimiter) {
        return String.format(SINGLE_PATTERN_FORMAT,
                escapeForRegex(delimiter.start()),
                escapeForRegex(delimiter.start()),
                escapeForRegex(delimiter.end()),
                escapeForRegex(delimiter.end()));
    }

    private static String escapeForRegex(String str) {
        return Pattern.quote(str);
    }

    private static List<String> parsePattern(String pattern, Delimiter delimiter) {
        String cleaned = pattern.trim()
                .replaceAll(
                        String.format(REGEX_START_SPACE + "|" + REGEX_END_SPACE,
                                escapeForRegex(delimiter.start()),
                                escapeForRegex(delimiter.end())),
                        "").trim();
        return Arrays.stream(cleaned.split(escapeDelimiter(delimiter.value())))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static String escapeDelimiter(String delimiter) {
        return Pattern.quote(delimiter);
    }
}
