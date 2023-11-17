package christmas.domain.discount;

import christmas.domain.period.ChristmasDiscountPeriod;
import christmas.domain.unit.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ChristmasDiscountTest {
    private static final long DAYS_FROM_START = 2;
    private static final Money NO_PROFIT = Money.ZERO,
            CHRISTMAS_DISCOUNT_PROFIT = new Money(-1_200L);
    private static final String CHRISTMAS_DISCOUNT_STRING = "크리스마스 디데이 할인: -1,200원";
    private ChristmasDiscountPeriod christmasDiscountPeriod;
    private ChristmasDiscount christmasDiscount;

    @BeforeEach
    void setUp() {
        christmasDiscountPeriod = mock(ChristmasDiscountPeriod.class);
        christmasDiscount = new ChristmasDiscount(christmasDiscountPeriod);
    }

    @Test
    void getProfit() {
        setMock_이벤트기간();

        assertThat(christmasDiscount.getProfit()).isEqualTo(CHRISTMAS_DISCOUNT_PROFIT);

        verify_이벤트기간();
    }

    @Test
    void getProfit_이벤트기간종료() {
        setMock_이벤트기간종료();

        assertThat(christmasDiscount.getProfit()).isEqualTo(NO_PROFIT);

        verify_이벤트기간종료();
    }

    @Test
    void testToString() {
        setMock_이벤트기간();

        assertThat(christmasDiscount.toString()).isEqualTo(CHRISTMAS_DISCOUNT_STRING);

        verify_이벤트기간();
    }

    @Test
    void testToString_이벤트기간종료() {
        setMock_이벤트기간종료();

        assertThat(christmasDiscount.toString()).isEmpty();

        verify_이벤트기간종료();
    }

    private void setMock_이벤트기간() {
        when(christmasDiscountPeriod.isEventPeriod()).thenReturn(true);
        when(christmasDiscountPeriod.calculateDaysFromStart()).thenReturn(DAYS_FROM_START);
    }

    private void setMock_이벤트기간종료() {
        when(christmasDiscountPeriod.isEventPeriod()).thenReturn(false);
    }

    private void verify_이벤트기간() {
        verify(christmasDiscountPeriod, times(1)).isEventPeriod();
        verify(christmasDiscountPeriod, times(1)).calculateDaysFromStart();
    }

    private void verify_이벤트기간종료() {
        verify(christmasDiscountPeriod, times(1)).isEventPeriod();
        verify(christmasDiscountPeriod, times(0)).calculateDaysFromStart();
    }
}