package christmas.view.input;

import camp.nextstep.edu.missionutils.Console;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

class ConsoleReaderTest {
    private ConsoleReader consoleReader;

    @BeforeEach
    void setUp() {
        consoleReader = new ConsoleReader();
    }

    @Test
    void readLine() {
        String expected = "크리스마스 프로모션";

        try(MockedStatic<Console> mockedStatic = mockStatic(Console.class)) {
            when(Console.readLine()).thenReturn(expected);

            assertThat(consoleReader.readLine()).isEqualTo(expected);

            mockedStatic.verify(Console::readLine, times(1));
        }
    }

    @Test
    void close() {
        try(MockedStatic<Console> mockedStatic = mockStatic(Console.class)) {
            assertThatCode(() -> consoleReader.close()).doesNotThrowAnyException();

            mockedStatic.verify(Console::close, times(1));
        }
    }
}