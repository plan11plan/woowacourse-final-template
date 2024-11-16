package template.while_try_catch_throw;

import java.util.function.Supplier;


public class RetryTemplate {
    private RetryTemplate() {
    }

    // TODO: Supplier타입 파라미터 주입
    // TODO: while( try - catch )구조 뼈대 제작
    // TODO: 예외 발생시, 예외 메세지 출력
    // TODO: Supplier타입 객체 그대로 반환
    public static <T> T executeWithRetry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("예상치 못한 오류: " + e.getMessage());
            }
        }
    }

}
