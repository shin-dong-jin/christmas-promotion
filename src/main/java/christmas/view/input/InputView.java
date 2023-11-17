package christmas.view.input;

import christmas.domain.unit.Day;
import christmas.domain.unit.OrdersMenuCount;
import christmas.utils.Parser;
import christmas.view.output.OutputView;

import java.util.List;

public class InputView {
    private final Reader reader;
    private final OutputView outputView;

    public InputView(Reader reader, OutputView outputView) {
        this.reader = reader;
        this.outputView = outputView;
    }

    public Day readDate() {
        while (true) {
            try {
                outputView.printReadDate();
                String input = reader.readLine().strip();
                return new Day(Parser.parseInt(input));
            } catch (IllegalArgumentException e) {
                outputView.printReadDateError();
                outputView.printNewLine();
            }
        }
    }

    public OrdersMenuCount readOrdersMenuCount() {
        while (true) {
            try {
                outputView.printReadOrdersMenuCount();
                String outer = reader.readLine();
                List<String> inners = Parser.outerSplit(outer);
                return new OrdersMenuCount(Parser.innersSplit(inners));
            } catch (IllegalArgumentException e) {
                outputView.printReadOrdersMenuCountError();
                outputView.printNewLine();
            }
        }
    }
}
