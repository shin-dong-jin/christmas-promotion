package christmas.domain.policy;

import christmas.domain.unit.Money;

public class TotalProfitPolicy {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final DiscountPresentPolicy discountPresentPolicy;

    public TotalProfitPolicy(DiscountPresentPolicy discountPresentPolicy) {
        this.discountPresentPolicy = discountPresentPolicy;
    }

    public Money getTotalProfit() {
        return discountPresentPolicy.getTotalProfit();
    }

    public Money getDiscountsProfit() {
        return discountPresentPolicy.getDiscountsProfit();
    }

    @Override
    public String toString() {
        return "<총혜택 금액>" + LINE_SEPARATOR
                + getTotalProfit() + "원" + LINE_SEPARATOR;
    }
}
