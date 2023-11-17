package christmas.domain.total;

import christmas.domain.policy.TotalProfitPolicy;
import christmas.domain.unit.Money;
import christmas.domain.unit.OrdersMenuCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderTotalBeforeDiscountTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final Money MINIMUM_ORDER_TOTAL = new Money(10_000L),
            ORDER_TOTAL = new Money(142_000L),
            ORDER_TOTAL_NOT_AVAILABLE = new Money(5_000L),
            TOTAL_PROFIT_EXCEPT_PRESENT = new Money(-6_246L),
            EXPECTED_TOTAL = new Money(135_754L);
    private OrdersMenuCount ordersMenuCount;
    private TotalProfitPolicy totalProfitPolicy;
    private OrderTotalBeforeDiscount orderTotalBeforeDiscount;

    @BeforeEach
    void setUp() {
        ordersMenuCount = mock(OrdersMenuCount.class);
        when(ordersMenuCount.calculateOrderTotal()).thenReturn(ORDER_TOTAL);

        orderTotalBeforeDiscount = new OrderTotalBeforeDiscount(ordersMenuCount);
    }

    @Test
    void isEventAvailable_참() {
        assertThat(orderTotalBeforeDiscount.isEventAvailable(MINIMUM_ORDER_TOTAL)).isTrue();
    }

    @Test
    void isEventAvailable_거짓() {
        OrdersMenuCount ordersMenuCountNotAvailable = mock(OrdersMenuCount.class);
        when(ordersMenuCountNotAvailable.calculateOrderTotal()).thenReturn(ORDER_TOTAL_NOT_AVAILABLE);

        OrderTotalBeforeDiscount orderTotalBeforeDiscountNotAvailable = new OrderTotalBeforeDiscount(ordersMenuCountNotAvailable);

        assertThat(orderTotalBeforeDiscountNotAvailable.isEventAvailable(MINIMUM_ORDER_TOTAL)).isFalse();
    }

    @Test
    void calculateExpectedTotal() {
        totalProfitPolicy = mock(TotalProfitPolicy.class);
        when(totalProfitPolicy.getDiscountsProfit()).thenReturn(TOTAL_PROFIT_EXCEPT_PRESENT);

        assertThat(orderTotalBeforeDiscount.calculateExpectedTotal(totalProfitPolicy)).isEqualTo(EXPECTED_TOTAL);
    }

    @ParameterizedTest
    @CsvSource(value = {"10000,true", "142000,true", "200000,false"})
    void notLessThan(long otherAmount, boolean expected) {
        Money other = new Money(otherAmount);

        assertThat(orderTotalBeforeDiscount.notLessThan(other)).isEqualTo(expected);
    }

    @Test
    void testToString() {
        String expected = "<할인 전 총주문 금액>" + LINE_SEPARATOR
                + "142,000원" + LINE_SEPARATOR;

        assertThat(orderTotalBeforeDiscount.toString()).isEqualTo(expected);
    }
}