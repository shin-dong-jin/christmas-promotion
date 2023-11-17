package christmas.view.input;

import christmas.view.output.OutputView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class InputViewTest {
    private static final String DAY_INPUT = "3",
            DAY_OUTPUT = "3일",
            ORDER_INPUT = "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1";
    private final List<String> ORDER_OUTPUT = List.of("티본스테이크 1개", "바비큐립 1개", "초코케이크 2개", "제로콜라 1개");

    private Reader reader;
    private OutputView outputView;
    private InputView inputView;

    @BeforeEach
    void setUp() {
        reader = mock(Reader.class);
        outputView = mock(OutputView.class);
        inputView = new InputView(reader, outputView);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,1일", "2,2일", "3,3일", "4,4일", "5,5일", "6,6일", "7,7일", "8,8일", "9,9일", "10,10일",
            "11,11일", "12,12일", "13,13일", "14,14일", "15,15일", "16,16일", "17,17일", "18,18일", "19,19일", "20,20일",
            "21,21일", "22,22일", "23,23일", "24,24일", "25,25일", "26,26일", "27,27일", "28,28일", "29,29일", "30,30일",
            "31,31일"})
    void readDate(String input, String expected) {
        when(reader.readLine()).thenReturn(input);

        assertThat(inputView.readDate().toString()).contains(expected);

        verify_readDate(1, 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "", " ", "　", "...", "***", "twenty fifth", "21일"})
    void readDate_숫자가아닌입력(String invalidInput) {
        when(reader.readLine()).thenReturn(invalidInput).thenReturn(DAY_INPUT);

        assertThat(inputView.readDate().toString()).contains(DAY_OUTPUT);

        verify_readDate(2, 1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "32", "33"})
    void readDate_1부터31사이가아닌숫자입력(String invalidInput) {
        when(reader.readLine()).thenReturn(invalidInput).thenReturn(DAY_INPUT);

        assertThat(inputView.readDate().toString()).contains(DAY_OUTPUT);

        verify_readDate(2, 1);
    }

    @Test
    void readOrdersMenuCount() {
        when(reader.readLine()).thenReturn(ORDER_INPUT);

        assertThat(inputView.readOrdersMenuCount().toString()).contains(ORDER_OUTPUT);

        verify_readOrdersMenuCount(1, 0);
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
    void readOrdersMenuCount_잘못된입력(String invalidInput) {
        when(reader.readLine()).thenReturn(invalidInput).thenReturn(ORDER_INPUT);

        assertThat(inputView.readOrdersMenuCount().toString()).contains(ORDER_OUTPUT);

        verify_readOrdersMenuCount(2, 1);
    }

    private void verify_readDate(int tries, int catches) {
        verify(reader, times(tries)).readLine();
        verify(outputView, times(tries)).printReadDate();
        verify(outputView, times(catches)).printReadDateError();
        verify(outputView, times(catches)).printNewLine();
    }

    private void verify_readOrdersMenuCount(int tries, int catches) {
        verify(reader, times(tries)).readLine();
        verify(outputView, times(tries)).printReadOrdersMenuCount();
        verify(outputView, times(catches)).printReadOrdersMenuCountError();
        verify(outputView, times(catches)).printNewLine();
    }
}