package template.pattern.VO_TwoValues;

public enum PatternFormat {
    // 3개 값을 가진 패턴 (상품,가격,수량)
    BRACKET_PRODUCT(new Delimiter("[", "]", ";", ",")),

    // 2개 값을 가진 패턴 (상품-수량)
    BRACKET_QUANTITY(new Delimiter("[", "]", ",", "-")),

    // 소괄호를 사용하는 패턴
    PARENTHESIS_PRODUCT(new Delimiter("(", ")", ";", ",")),

    // @ 구분자를 사용하는 패턴
    AT_SIGN_PRODUCT(new Delimiter("@", "@", ",", ":"));

    private final Delimiter delimiter;

    PatternFormat(Delimiter delimiter) {
        this.delimiter = delimiter;
    }

    public Delimiter getDelimiter() {
        return delimiter;
    }
}
