package christmas.domain.total;

import christmas.domain.policy.TotalProfitPolicy;
import christmas.domain.unit.Money;
import christmas.domain.unit.OrdersMenuCount;

public class OrderTotalBeforeDiscount {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final Money orderTotal;

    public OrderTotalBeforeDiscount(OrdersMenuCount menuCount) {
        this.orderTotal = menuCount.calculateOrderTotal();
    }

    public boolean isEventAvailable(Money minimumOrderTotal) {
        return orderTotal.notLessThan(minimumOrderTotal);
    }

    public Money calculateExpectedTotal(TotalProfitPolicy totalProfitPolicy) {
        return orderTotal.add(totalProfitPolicy.getDiscountsProfit());
    }

    public boolean notLessThan(Money other) {
        return orderTotal.notLessThan(other);
    }

    @Override
    public String toString() {
        return "<할인 전 총주문 금액>" + LINE_SEPARATOR
                + orderTotal + "원" + LINE_SEPARATOR;
    }
}
