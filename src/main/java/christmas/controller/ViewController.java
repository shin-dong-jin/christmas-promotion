package christmas.controller;

import christmas.domain.unit.Day;
import christmas.domain.unit.OrdersMenuCount;
import christmas.view.input.InputView;
import christmas.view.input.Reader;
import christmas.view.output.OutputView;
import christmas.view.output.Writer;

public class ViewController {
    private final InputView inputView;
    private final OutputView outputView;

    public ViewController(Reader reader, Writer writer) {
        this.outputView = new OutputView(writer);
        this.inputView = new InputView(reader, outputView);
    }

    public Day inputDate() {
        return inputView.readDate();
    }

    public OrdersMenuCount inputOrdersMenuCount() {
        return inputView.readOrdersMenuCount();
    }

    public void outputWelcome() {
        outputView.printWelcome();
    }

    public void output(Object object) {
        outputView.print(object);
    }
}
