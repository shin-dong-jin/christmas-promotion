package christmas.domain.discount;

import christmas.domain.period.WeekdayDiscountPeriod;
import christmas.domain.unit.Money;
import christmas.domain.unit.OrdersMenuCount;

public class WeekdayDiscount implements Discount {
    private static final Money NO_PROFIT = Money.ZERO;
    private final WeekdayDiscountPeriod weekdayDiscountPeriod;
    private final OrdersMenuCount ordersMenuCount;

    public WeekdayDiscount(WeekdayDiscountPeriod weekdayDiscountPeriod, OrdersMenuCount ordersMenuCount) {
        this.weekdayDiscountPeriod = weekdayDiscountPeriod;
        this.ordersMenuCount = ordersMenuCount;
    }

    @Override
    public Money getProfit() {
        if (!weekdayDiscountPeriod.isEventPeriod() || !weekdayDiscountPeriod.isWeekday()) {
            return NO_PROFIT;
        }
        return calculateProfit();
    }

    @Override
    public String toString() {
        Money profit = getProfit();

        if (profit.equals(NO_PROFIT)) {
            return "";
        }

        return "평일 할인: " + profit + "원";
    }

    private Money calculateProfit() {
        long desertCount = ordersMenuCount.getDessertCount();
        return Money.YEAR_AMOUNT.multiply(desertCount).negative();
    }
}
