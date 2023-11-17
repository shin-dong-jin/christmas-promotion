package christmas.domain.total;

import christmas.domain.policy.TotalProfitPolicy;
import christmas.domain.unit.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExpectedTotalAfterDiscountTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private OrderTotalBeforeDiscount orderTotalBeforeDiscount;
    private TotalProfitPolicy totalProfitPolicy;
    private ExpectedTotalAfterDiscount expectedTotalAfterDiscount;

    @BeforeEach
    void setUp() {
        orderTotalBeforeDiscount = mock(OrderTotalBeforeDiscount.class);
        totalProfitPolicy = mock(TotalProfitPolicy.class);
        when(orderTotalBeforeDiscount.calculateExpectedTotal(totalProfitPolicy))
                .thenReturn(new Money(135_754L));

        expectedTotalAfterDiscount = new ExpectedTotalAfterDiscount(orderTotalBeforeDiscount, totalProfitPolicy);
    }

    @Test
    void testToString() {
        String expected = "<할인 후 예상 결제 금액>" + LINE_SEPARATOR
                + "135,754원" + LINE_SEPARATOR;

        assertThat(expectedTotalAfterDiscount.toString()).isEqualTo(expected);
    }
}