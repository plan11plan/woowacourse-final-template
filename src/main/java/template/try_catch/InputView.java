package template.try_catch;

import static camp.nextstep.edu.missionutils.Console.readLine;

import java.util.Arrays;
import java.util.List;

class InputView {

    public static void main(String[] args) {
        InputView InputView = new InputView();
        InputView.readNumber();
    }

    void readNumber() {
        List<String> strings = RetryTemplate.executeWithRetry(this::inputNumber);

    }


    public List<String> inputNumber() {
        System.out.println("숫자를 입력해주세요. 최소길이 4");
        List<String> input = Arrays.stream(readLine().trim().split(",")).toList();
        if (input.size() <= 3) {
            throw new IllegalArgumentException("땡");
        }
        if (input.contains("100")) {
            throw new IllegalArgumentException("100은 안되용");
        }
        return input;
    }

}
