package christmas.domain.total;

import christmas.domain.policy.TotalProfitPolicy;
import christmas.domain.unit.Money;

public class ExpectedTotalAfterDiscount {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final Money expectedTotal;

    public ExpectedTotalAfterDiscount(OrderTotalBeforeDiscount orderTotalBeforeDiscount, TotalProfitPolicy totalProfitPolicy) {
        this.expectedTotal = orderTotalBeforeDiscount.calculateExpectedTotal(totalProfitPolicy);
    }

    @Override
    public String toString() {
        return "<할인 후 예상 결제 금액>" + LINE_SEPARATOR
                + expectedTotal + "원" + LINE_SEPARATOR;
    }
}
