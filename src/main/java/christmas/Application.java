package christmas;

import christmas.controller.PromotionController;
import christmas.view.input.ConsoleReader;
import christmas.view.input.Reader;
import christmas.view.output.StdoutWriter;
import christmas.view.output.Writer;

public class Application {
    public static void main(String[] args) {
        try (Reader reader = new ConsoleReader()) {
            Writer writer = new StdoutWriter(System.out);
            PromotionController promotionController = new PromotionController(reader, writer);
            promotionController.run();
        }
    }
}
