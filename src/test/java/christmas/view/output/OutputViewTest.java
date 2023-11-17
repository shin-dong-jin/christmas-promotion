package christmas.view.output;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

class OutputViewTest {
    private static final String NEW_LINE = "",
            WELCOME = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.",
            READ_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)",
            READ_ORDERS_MENU_COUNT = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)",
            READ_DATE_ERROR = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.",
            READ_ORDERS_MENU_COUNT_ERROR = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private Writer writer;
    private OutputView outputView;

    @BeforeEach
    void setUp() {
        writer = mock(Writer.class);
        outputView = new OutputView(writer);
    }

    @Test
    void printNewLine() {
        assertThatCode(() -> outputView.printNewLine()).doesNotThrowAnyException();

        verify(writer, times(1)).write(NEW_LINE);
    }

    @Test
    void printWelcome() {
        assertThatCode(() -> outputView.printWelcome()).doesNotThrowAnyException();

        verify(writer, times(1)).write(WELCOME);
    }

    @Test
    void printReadDate() {
        assertThatCode(() -> outputView.printReadDate()).doesNotThrowAnyException();

        verify(writer, times(1)).write(READ_DATE);
    }

    @Test
    void printReadOrdersMenuCount() {
        assertThatCode(() -> outputView.printReadOrdersMenuCount()).doesNotThrowAnyException();

        verify(writer, times(1)).write(READ_ORDERS_MENU_COUNT);
    }

    @Test
    void printReadDateError() {
        assertThatCode(() -> outputView.printReadDateError()).doesNotThrowAnyException();

        verify(writer, times(1)).write(READ_DATE_ERROR);
    }

    @Test
    void printReadOrdersMenuCountError() {
        assertThatCode(() -> outputView.printReadOrdersMenuCountError()).doesNotThrowAnyException();

        verify(writer, times(1)).write(READ_ORDERS_MENU_COUNT_ERROR);
    }

    @Test
    void print() {
        assertThatCode(() -> outputView.print(any())).doesNotThrowAnyException();

        verify(writer, times(1)).write(any());
    }
}