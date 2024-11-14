"예외사항이 발생하는 경우 예외를 던진 후 예외사항이 발생한 지점부터 다시 입력을 받아라" 라는 요구사항이 있었다.

### 문제점

재귀함수는 예외가 발생한다고 가정하면 메서드가 종료되기 전에 메서드 자기 자신을 한번 더 호출한다.
즉, call stack 이 쌓이게 된다. 메모리에는 stack 영역이 제한적이기 때문에 무한정으로 반복되는 경우 StackOverFlow 문제가 발생하게 된다.

그렇기 때문에 재귀함수가 조금 더 직관적이여 보이고, 코드가 간결해질 순 있으나 코드가 반복되는 문제를 해결할 수 없을 뿐더러 StackOverFlow 라는 부가적인 문제까지 불러일으킬 수 있다.

### 다른 클래스에서의 사용 예시

````java
public class GameController {

    public void playGame() {
        RetryTemplate.executeWithRetry(() -> {
            // 게임 로직
            validateGameState();
            return gameResult;
        });
    }
}
```
