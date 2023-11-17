package christmas.view.output;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class StdoutWriterTest {
    private static final String LINE_SEPARATOR = System.lineSeparator(),
            MESSAGE = "OBJECT WRITING...";
    private static final Object OBJECT = new Object() {
        @Override
        public String toString() {
            return MESSAGE;
        }
    };
    private OutputStream captor;
    private Writer stdoutWriter;

    @BeforeEach
    void setUp() {
        captor = new ByteArrayOutputStream();
        stdoutWriter = new StdoutWriter(new PrintStream(captor));
    }

    @Test
    void write() {
        stdoutWriter.write(OBJECT);

        assertThat(captor.toString()).isEqualTo(MESSAGE + LINE_SEPARATOR);
    }
}