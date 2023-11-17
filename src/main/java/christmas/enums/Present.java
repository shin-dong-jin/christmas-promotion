package christmas.enums;

import christmas.domain.unit.Money;
import christmas.domain.total.OrderTotalBeforeDiscount;

import java.util.Arrays;
import java.util.List;

public enum Present {
    CHAMPAGNE(new Money(120_000L), "샴페인", 1);
    private final Money min;
    private final String name;
    private final int count;

    public static List<Present> getPresents(OrderTotalBeforeDiscount orderTotalBeforeDiscount) {
        return Arrays.stream(values()).filter(value -> orderTotalBeforeDiscount.notLessThan(value.min)).toList();
    }

    Present(Money min, String name, int count) {
        this.min = min;
        this.name = name;
        this.count = count;
    }

    public Money getPresentProfit() {
        Menu menu = Menu.convertStringToMenu(name);
        return menu.multiply(count).negative();
    }

    @Override
    public String toString() {
        return name + " " + count + "개";
    }
}
