package christmas.domain.discount;

import christmas.domain.period.WeekendDiscountPeriod;
import christmas.domain.unit.Money;
import christmas.domain.unit.OrdersMenuCount;

public class WeekendDiscount implements Discount {
    private static final Money NO_PROFIT = Money.ZERO;
    private final WeekendDiscountPeriod weekendDiscountPeriod;
    private final OrdersMenuCount ordersMenuCount;

    public WeekendDiscount(WeekendDiscountPeriod weekendDiscountPeriod, OrdersMenuCount ordersMenuCount) {
        this.weekendDiscountPeriod = weekendDiscountPeriod;
        this.ordersMenuCount = ordersMenuCount;
    }

    @Override
    public Money getProfit() {
        if (!weekendDiscountPeriod.isEventPeriod() || !weekendDiscountPeriod.isWeekend()) {
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

        return "주말 할인: " + profit + "원";
    }

    private Money calculateProfit() {
        long mainCount = ordersMenuCount.getMainCount();
        return Money.YEAR_AMOUNT.multiply(mainCount).negative();
    }
}
