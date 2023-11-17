package christmas.view.output;

import java.io.PrintStream;

public class StdoutWriter implements Writer {
    private final PrintStream stdout;

    public StdoutWriter(PrintStream stdout) {
        this.stdout = stdout;
    }

    @Override
    public void write(Object object) {
        stdout.println(object);
    }
}
