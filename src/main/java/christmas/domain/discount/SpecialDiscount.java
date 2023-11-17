package christmas.domain.discount;

import christmas.domain.period.SpecialDiscountPeriod;
import christmas.domain.unit.Money;

public class SpecialDiscount implements Discount {
    private final static Money NO_PROFIT = Money.ZERO,
            PROFIT = Money.THOUSAND.negative();
    private final SpecialDiscountPeriod specialDiscountPeriod;

    public SpecialDiscount(SpecialDiscountPeriod specialDiscountPeriod) {
        this.specialDiscountPeriod = specialDiscountPeriod;
    }

    @Override
    public Money getProfit() {
        if (!specialDiscountPeriod.isEventPeriod() || !specialDiscountPeriod.isStar()) {
            return NO_PROFIT;
        }
        return PROFIT;
    }

    @Override
    public String toString() {
        Money profit = getProfit();

        if (profit.equals(NO_PROFIT)) {
            return "";
        }

        return "특별 할인: " + profit + "원";
    }
}
