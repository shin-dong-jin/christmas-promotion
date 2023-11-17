package christmas.domain.discount;

import christmas.domain.period.WeekendDiscountPeriod;
import christmas.domain.unit.Money;
import christmas.domain.unit.OrdersMenuCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeekendDiscountTest {
    private static final long MAIN_COUNT = 3;
    private static final Money NO_PROFIT = Money.ZERO,
            WEEKEND_DISCOUNT_PROFIT = new Money(-6_069L);
    private static final String WEEKEND_DISCOUNT_STRING = "주말 할인: -6,069원";
    private WeekendDiscountPeriod weekendDiscountPeriod;
    private OrdersMenuCount ordersMenuCount;
    private WeekendDiscount weekendDiscount;

    @BeforeEach
    void setUp() {
        weekendDiscountPeriod = mock(WeekendDiscountPeriod.class);
        ordersMenuCount = mock(OrdersMenuCount.class);
        weekendDiscount = new WeekendDiscount(weekendDiscountPeriod, ordersMenuCount);
    }

    @Test
    void getProfit() {
        setMock(true, true);

        assertThat(weekendDiscount.getProfit()).isEqualTo(WEEKEND_DISCOUNT_PROFIT);

        verifyCalls(1, 1, 1);
    }
    
    @Test
    void getProfit_이벤트기간종료() {
        setMock(false, true);

        assertThat(weekendDiscount.getProfit()).isEqualTo(NO_PROFIT);

        verifyCalls(1, 0, 0);
    }

    @Test
    void getProfit_주말이아닌날짜() {
        setMock(true, false);

        assertThat(weekendDiscount.getProfit()).isEqualTo(NO_PROFIT);

        verifyCalls(1, 1, 0);
    }

    @Test
    void testToString() {
        setMock(true, true);

        assertThat(weekendDiscount.toString()).isEqualTo(WEEKEND_DISCOUNT_STRING);

        verifyCalls(1, 1, 1);
    }

    @Test
    void testToString_이벤트기간종료() {
        setMock(false, true);

        assertThat(weekendDiscount.toString()).isEmpty();

        verifyCalls(1, 0, 0);
    }

    @Test
    void testToString_주말이아닌날짜() {
        setMock(true, false);

        assertThat(weekendDiscount.toString()).isEmpty();

        verifyCalls(1, 1, 0);
    }
    

    private void setMock(boolean eventPeriod, boolean weekend) {
        when(weekendDiscountPeriod.isEventPeriod()).thenReturn(eventPeriod);
        when(weekendDiscountPeriod.isWeekend()).thenReturn(weekend);
        when(ordersMenuCount.getMainCount()).thenReturn(MAIN_COUNT);
    }

    private void verifyCalls(int eventPeriod, int weekend, int getMainCount) {
        verify(weekendDiscountPeriod, times(eventPeriod)).isEventPeriod();
        verify(weekendDiscountPeriod, times(weekend)).isWeekend();
        verify(ordersMenuCount, times(getMainCount)).getMainCount();
    }
}