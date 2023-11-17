package christmas.domain.policy;

import christmas.domain.unit.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TotalProfitPolicyTest {
    private static final String LINE_SEPARATOR = System.lineSeparator(),
            TOTAL_PROFIT_STRING = "<총혜택 금액>" + LINE_SEPARATOR + "-31,246원" + LINE_SEPARATOR;
    private static final Money DISCOUNTS_PROFIT = new Money(-25_246L),
            TOTAL_PROFIT = new Money(-31_246L);
    private DiscountPresentPolicy discountPresentPolicy;
    private TotalProfitPolicy totalProfitPolicy;

    @BeforeEach
    void setUp() {
        discountPresentPolicy = mock(DiscountPresentPolicy.class);
        totalProfitPolicy = new TotalProfitPolicy(discountPresentPolicy);
    }

    @Test
    void getTotalProfit() {
        when(discountPresentPolicy.getTotalProfit()).thenReturn(TOTAL_PROFIT);

        assertThat(totalProfitPolicy.getTotalProfit()).isEqualTo(TOTAL_PROFIT);

        verify(discountPresentPolicy, times(1)).getTotalProfit();
    }

    @Test
    void getDiscountsProfit() {
        when(discountPresentPolicy.getDiscountsProfit()).thenReturn(DISCOUNTS_PROFIT);

        assertThat(totalProfitPolicy.getDiscountsProfit()).isEqualTo(DISCOUNTS_PROFIT);

        verify(discountPresentPolicy, times(1)).getDiscountsProfit();
    }

    @Test
    void testToString() {
        when(discountPresentPolicy.getTotalProfit()).thenReturn(TOTAL_PROFIT);

        assertThat(totalProfitPolicy.toString()).isEqualTo(TOTAL_PROFIT_STRING);

        verify(discountPresentPolicy, times(1)).getTotalProfit();
    }
}