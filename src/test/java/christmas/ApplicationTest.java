package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicationTest extends NsTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    void 모든_타이틀_출력() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                    "<주문 메뉴>",
                    "<할인 전 총주문 금액>",
                    "<증정 메뉴>",
                    "<혜택 내역>",
                    "<총혜택 금액>",
                    "<할인 후 예상 결제 금액>",
                    "<12월 이벤트 배지>"
            );
        });
    }

    @Test
    void 혜택_내역_없음_출력() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains("<혜택 내역>" + LINE_SEPARATOR + "없음");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", " ", "　", "...", "***", "twenty fifth", "21일", "-1", "0", "32", "33"})
    void 날짜_예외_테스트(String day) {
        assertSimpleTest(() -> {
            runException(day);
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"티본스테이크-1x,바비큐립,쵸코케이크-2,제로콜라-,",
            "티본스테이크-1,제로콜라-1,",
            "티본스테이크-1,아이스크림3",
            "티본스테이크-1,아이스크림-",
            "티본스테이크-1,아이스크림",
            "티본스테이크-1,해샨물파스타-3",
            "티본스테이크-1,타파스-3t",
            "티본스테이크-1,타파스-----1",
            "티본스테이크-1,타파스=1",
            "티본스테이크-10,해산물파스타-10,크리스마스파스타-10",
            "제로콜라-5,레드와인-5",
            "초코케이크-0,크라스마스파스타-3",
            "양송이수프-4,시저샐러드--3",
            "초코케이크-3,티본스테이크-1,초코케이크-4"})
    void 주문_예외_테스트(String order) {
        assertSimpleTest(() -> {
            runException("3", order);
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
