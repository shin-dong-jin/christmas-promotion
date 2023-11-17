package christmas.domain.discount;

import christmas.domain.period.WeekdayDiscountPeriod;
import christmas.domain.unit.Money;
import christmas.domain.unit.OrdersMenuCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeekdayDiscountTest {
    private static final long DESSERT_COUNT = 2L;
    private static final Money NO_PROFIT = Money.ZERO,
            WEEKDAY_DISCOUNT_PROFIT = new Money(-4_046L);
    private static final String WEEKDAY_DISCOUNT_STRING = "평일 할인: -4,046원";
    private WeekdayDiscountPeriod weekdayDiscountPeriod;
    private OrdersMenuCount ordersMenuCount;
    private WeekdayDiscount weekdayDiscount;

    @BeforeEach
    void setUp() {
        weekdayDiscountPeriod = mock(WeekdayDiscountPeriod.class);
        ordersMenuCount = mock(OrdersMenuCount.class);
        weekdayDiscount = new WeekdayDiscount(weekdayDiscountPeriod, ordersMenuCount);
    }

    @Test
    void getProfit() {
        setMock(true, true);

        assertThat(weekdayDiscount.getProfit()).isEqualTo(WEEKDAY_DISCOUNT_PROFIT);

        verifyCalls(1, 1, 1);
    }

    @Test
    void getProfit_이벤트기간종료() {
        setMock(false, true);

        assertThat(weekdayDiscount.getProfit()).isEqualTo(NO_PROFIT);

        verifyCalls(1, 0, 0);
    }

    @Test
    void getProfit_평일이아닌날짜() {
        setMock(true, false);

        assertThat(weekdayDiscount.getProfit()).isEqualTo(NO_PROFIT);

        verifyCalls(1, 1, 0);
    }

    @Test
    void testToString() {
        setMock(true, true);

        assertThat(weekdayDiscount.toString()).isEqualTo(WEEKDAY_DISCOUNT_STRING);

        verifyCalls(1, 1, 1);
    }

    @Test
    void testToString_이벤트기간종료() {
        setMock(false, true);

        assertThat(weekdayDiscount.toString()).isEmpty();

        verifyCalls(1, 0, 0);
    }

    @Test
    void testToString_평일이아닌날짜() {
        setMock(true, false);

        assertThat(weekdayDiscount.toString()).isEmpty();

        verifyCalls(1, 1, 0);
    }

    private void setMock(boolean eventPeriod, boolean weekday) {
        when(weekdayDiscountPeriod.isEventPeriod()).thenReturn(eventPeriod);
        when(weekdayDiscountPeriod.isWeekday()).thenReturn(weekday);
        when(ordersMenuCount.getDessertCount()).thenReturn(DESSERT_COUNT);

    }

    private void verifyCalls(int eventPeriod, int weekday, int getDessertCount) {
        verify(weekdayDiscountPeriod, times(eventPeriod)).isEventPeriod();
        verify(weekdayDiscountPeriod, times(weekday)).isWeekday();
        verify(ordersMenuCount, times(getDessertCount)).getDessertCount();
    }
}