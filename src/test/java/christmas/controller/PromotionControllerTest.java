package christmas.controller;

import christmas.view.input.Reader;
import christmas.view.output.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

class PromotionControllerTest {
    private static final String VALID_DAY_INPUT = "3",
            VALID_MENU_INPUT = "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1",
            VALID_DAY_INPUT_WITHOUT_PROFIT = "26",
            VALID_MENU_INPUT_WITHOUT_PROFIT = "타파스-1,제로콜라-1";
    private Reader reader;
    private Writer writer;
    private PromotionController promotionController;

    @BeforeEach
    void setUp() {
        reader = mock(Reader.class);
        writer = mock(Writer.class);
        promotionController = new PromotionController(reader, writer);
    }

    @Test
    void run() {
        when(reader.readLine()).thenReturn(VALID_DAY_INPUT).thenReturn(VALID_MENU_INPUT);

        assertThatCode(() -> promotionController.run()).doesNotThrowAnyException();

        verify(reader, times(2)).readLine();
    }

    @Test
    void run_혜택없음() {
        when(reader.readLine()).thenReturn(VALID_DAY_INPUT_WITHOUT_PROFIT).thenReturn(VALID_MENU_INPUT_WITHOUT_PROFIT);

        assertThatCode(() -> promotionController.run()).doesNotThrowAnyException();

        verify(reader, times(2)).readLine();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "", " ", "　", "...", "***", "twenty fifth", "21일", "-1", "0", "32", "33"})
    void run_날짜입력오류(String invalidDayInput) {
        when(reader.readLine()).thenReturn(invalidDayInput).thenReturn(VALID_DAY_INPUT).thenReturn(VALID_MENU_INPUT);

        assertThatCode(() -> promotionController.run()).doesNotThrowAnyException();

        verify(reader, times(3)).readLine();
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
            "티본스테이크-1,타파스=1"})
    void run_주문입력오류(String invalidMenuInput) {
        when(reader.readLine()).thenReturn(VALID_DAY_INPUT).thenReturn(invalidMenuInput).thenReturn(VALID_MENU_INPUT);

        assertThatCode(() -> promotionController.run()).doesNotThrowAnyException();

        verify(reader, times(3)).readLine();
    }
}