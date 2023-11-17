package christmas.utils;

import christmas.domain.unit.Order;
import christmas.domain.unit.Orders;
import christmas.enums.Menu;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Parser {
    private static final String OUTER_DELIMITER = ",", INNER_DELIMITER = "-";
    private static final int NO_LIMIT = -1;
    private Parser() {

    }

    public static int parseInt(String number) {
        return Integer.parseInt(number);
    }

    public static long parseLong(String number) {
        return Long.parseLong(number);
    }

    public static List<String> outerSplit(String outer) {
        return Arrays.stream(outer.split(OUTER_DELIMITER, NO_LIMIT)).map(String::strip).toList();
    }

    public static Map<Menu, Long> innersSplit(List<String> inners) {
        Orders orders = new Orders(inners.stream().map(Parser::innerSplit).toList());
        return orders.convertListToMap();
    }

    private static Order innerSplit(String inner) {
        int index = inner.indexOf(INNER_DELIMITER);
        validateNoDelimiter(index);

        String menu = inner.substring(0, index);
        long count = parseLong(inner.substring(index + 1));

        return new Order(menu, count);
    }

    private static void validateNoDelimiter(int index) {
        if (index == -1) {
            throw new IllegalArgumentException();
        }
    }
}
