package christmas.domain.unit;

import christmas.enums.Menu;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private final Menu menu;
    private final long count;

    public static Map<Menu, Long> convertListToMap(List<Order> orders) {
        validateMenuNotDuplicates(orders);
        Map<Menu, Long> orderMap = new LinkedHashMap<>();

        for (Order order : orders) {
            orderMap.put(order.menu, order.count);
        }

        return Collections.unmodifiableMap(new LinkedHashMap<>(orderMap));
    }

    public static void validateMenuNotDuplicate(List<Order> orders) {
        if (orders.size() != orders.stream().map(order -> order.menu).distinct().count()) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateMenuNotDuplicates(List<Order> orders) {
        if (orders.size() != orders.stream().map(order -> order.menu).distinct().count()) {
            throw new IllegalStateException();
        }
    }

    public Order(String menu, long count) {
        validateExistMenu(menu);
        validateNotZeroNegative(count);
        this.menu = convertStringToMenu(menu);
        this.count = count;
    }

    private void validateExistMenu(String menu) {
        if (!Menu.contains(menu)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNotZeroNegative(long count) {
        if (count <= 0L) {
            throw new IllegalArgumentException();
        }
    }

    private Menu convertStringToMenu(String menu) {
        return Menu.convertStringToMenu(menu);
    }
}
