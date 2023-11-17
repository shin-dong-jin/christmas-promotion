package christmas.domain.unit;

import christmas.enums.Menu;

import java.util.Map;

public class OrdersMenuCount {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final long MAX_TOTAL_COUNT = 20L;
    private final Map<Menu, Long> ordersMenuCount;

    public OrdersMenuCount(Map<Menu, Long> ordersMenuCount) {
        validateKeysContainsNotDrink(ordersMenuCount);
        validateTotalValuesNotOverMaxTotalCount(ordersMenuCount);

        this.ordersMenuCount = ordersMenuCount;
    }

    public Money calculateOrderTotal() {
        Money orderTotal = Money.ZERO;

        for (Map.Entry<Menu, Long> order : ordersMenuCount.entrySet()) {
            Menu menu = order.getKey();
            long count = order.getValue();

            orderTotal = orderTotal.add(menu.multiply(count));
        }

        return orderTotal;
    }

    public long getDessertCount() {
        return ordersMenuCount.keySet().stream()
                .filter(Menu::isDessert)
                .mapToLong(ordersMenuCount::get)
                .sum();
    }

    public long getMainCount() {
        return ordersMenuCount.keySet().stream()
                .filter(Menu::isMain)
                .mapToLong(ordersMenuCount::get)
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder ordersBuilder = new StringBuilder("<주문 메뉴>").append(LINE_SEPARATOR);

        for (Map.Entry<Menu, Long> order : ordersMenuCount.entrySet()) {
            ordersBuilder.append(order.getKey())
                    .append(" ")
                    .append(order.getValue())
                    .append("개")
                    .append(LINE_SEPARATOR);
        }

        return ordersBuilder.toString();
    }

    private void validateKeysContainsNotDrink(Map<Menu, Long> ordersMenuCount) {
        if (ordersMenuCount.keySet().stream().allMatch(Menu::isDrink)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateTotalValuesNotOverMaxTotalCount(Map<Menu, Long> ordersMenuCount) {
        if (ordersMenuCount.values().stream().mapToLong(Long::longValue).sum() > MAX_TOTAL_COUNT) {
            throw new IllegalArgumentException();
        }
    }
}
