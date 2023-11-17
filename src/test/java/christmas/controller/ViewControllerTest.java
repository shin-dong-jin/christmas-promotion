package christmas.controller;

import christmas.view.input.Reader;
import christmas.view.output.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

class ViewControllerTest {
    private static final String DAY_INPUT = "3",
            DAY_OUTPUT = "3일",
            MENU_ORDER_INPUT = "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1",
            WELCOME = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final List<String> MENU_COUNT_OUTPUT = List.of(
            "티본스테이크 1개", "바비큐립 1개", "초코케이크 2개", "제로콜라 1개"
    );
    Reader reader;
    Writer writer;
    private ViewController viewController;

    @BeforeEach
    void setUp() {
        reader = mock(Reader.class);
        writer = mock(Writer.class);

        viewController = new ViewController(reader, writer);
    }

    @Test
    void inputDate() {
        when(reader.readLine()).thenReturn(DAY_INPUT);

        assertThat(viewController.inputDate().toString()).contains(DAY_OUTPUT);
    }

    @Test
    void inputOrdersMenuCount() {
        when(reader.readLine()).thenReturn(MENU_ORDER_INPUT);

        assertThat(viewController.inputOrdersMenuCount().toString()).contains(MENU_COUNT_OUTPUT);
    }

    @Test
    void outputWelcome() {
        assertThatCode(() -> viewController.outputWelcome()).doesNotThrowAnyException();

        verify(writer, times(1)).write(WELCOME);
    }

    @Test
    void output() {
        Object object = mock(Object.class);

        assertThatCode(() -> viewController.output(object)).doesNotThrowAnyException();

        verify(writer, times(1)).write(object);
    }
}