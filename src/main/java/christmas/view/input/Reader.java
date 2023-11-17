package christmas.view.input;

import java.io.Closeable;

public interface Reader extends Closeable {
    String readLine();

    @Override
    void close();
}
