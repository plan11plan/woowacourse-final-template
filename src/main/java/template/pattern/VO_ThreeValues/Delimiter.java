package template.pattern.VO_ThreeValues;

public record Delimiter(
        String start,      // 시작 구분자 (예: "[", "(", "@")
        String end,        // 종료 구분자 (예: "]", ")", "@")
        String pattern,   // 패턴 간 구분자 (예: ";", ",")
        String value    // 값 구분자 (예: ",", "-")
) {
}
