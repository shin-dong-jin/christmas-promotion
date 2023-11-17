package christmas.domain.discount;

import christmas.domain.period.ChristmasDiscountPeriod;
import christmas.domain.unit.Money;

public class ChristmasDiscount implements Discount {
    private static final Money NO_PROFIT = Money.ZERO;
    private final ChristmasDiscountPeriod christmasDiscountPeriod;

    public ChristmasDiscount(ChristmasDiscountPeriod christmasDiscountPeriod) {
        this.christmasDiscountPeriod = christmasDiscountPeriod;
    }

    @Override
    public Money getProfit() {
        if (!christmasDiscountPeriod.isEventPeriod()) {
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

        return "크리스마스 디데이 할인: " + profit + "원";
    }

    private Money calculateProfit() {
        Money profit = Money.THOUSAND;
        return profit.add(Money.HUNDRED.multiply(christmasDiscountPeriod.calculateDaysFromStart())).negative();
    }
}
