package christmas.domain.policy;

import christmas.domain.unit.Money;
import christmas.domain.total.OrderTotalBeforeDiscount;

public class MinimumOrderTotalPolicy {
    private static final Money minimumOrderTotal = Money.TEN_THOUSAND;
    private final OrderTotalBeforeDiscount orderTotalBeforeDiscount;

    public MinimumOrderTotalPolicy(OrderTotalBeforeDiscount orderTotalBeforeDiscount) {
        this.orderTotalBeforeDiscount = orderTotalBeforeDiscount;
    }

    public boolean isEventAvailable() {
        return orderTotalBeforeDiscount.isEventAvailable(minimumOrderTotal);
    }
}
