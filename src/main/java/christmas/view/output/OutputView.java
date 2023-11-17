package christmas.view.output;

public class OutputView {
    private static final String NEW_LINE = "",
            WELCOME = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.",
            READ_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)",
            READ_ORDERS_MENU_COUNT = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)",
            READ_DATE_ERROR = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.",
            READ_ORDERS_MENU_COUNT_ERROR = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";

    private final Writer writer;

    public OutputView(Writer writer) {
        this.writer = writer;
    }

    public void printNewLine() {
        print(NEW_LINE);
    }

    public void printWelcome() {
        print(WELCOME);
    }

    public void printReadDate() {
        print(READ_DATE);
    }

    public void printReadOrdersMenuCount() {
        print(READ_ORDERS_MENU_COUNT);
    }

    public void printReadDateError() {
        print(READ_DATE_ERROR);
    }

    public void printReadOrdersMenuCountError() {
        print(READ_ORDERS_MENU_COUNT_ERROR);
    }

    public void print(Object object) {
        writer.write(object);
    }
}
