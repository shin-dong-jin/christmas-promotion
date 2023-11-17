package christmas.domain.discount;

import christmas.domain.period.SpecialDiscountPeriod;
import christmas.domain.unit.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SpecialDiscountTest {
    private static final Money NO_PROFIT = Money.ZERO,
            PROFIT = new Money(-1_000L);
    private static final String SPECIAL_DISCOUNT_STRING = "특별 할인: -1,000원";
    private SpecialDiscountPeriod specialDiscountPeriod;
    private SpecialDiscount specialDiscount;

    @BeforeEach
    void setUp() {
        specialDiscountPeriod = mock(SpecialDiscountPeriod.class);
        specialDiscount = new SpecialDiscount(specialDiscountPeriod);
    }

    @Test
    void getProfit() {
        setMock(true, true);

        assertThat(specialDiscount.getProfit()).isEqualTo(PROFIT);

        verifyCall(1, 1);
    }

    @Test
    void getProfit_이벤트기간종료() {
        setMock(false, true);

        assertThat(specialDiscount.getProfit()).isEqualTo(NO_PROFIT);

        verifyCall(1, 0);
    }

    @Test
    void getProfit_특별할인날짜아님() {
        setMock(true, false);

        assertThat(specialDiscount.getProfit()).isEqualTo(NO_PROFIT);

        verifyCall(1, 1);
    }

    @Test
    void testToString() {
        setMock(true, true);

        assertThat(specialDiscount.toString()).isEqualTo(SPECIAL_DISCOUNT_STRING);

        verifyCall(1, 1);
    }

    @Test
    void testToString_이벤트기간종료() {
        setMock(false, true);

        assertThat(specialDiscount.toString()).isEmpty();

        verifyCall(1, 0);
    }

    @Test
    void testToString_특별할인날짜아님() {
        setMock(true, false);

        assertThat(specialDiscount.toString()).isEmpty();

        verifyCall(1, 1);
    }

    private void setMock(boolean isEventPeriod, boolean isStar) {
        when(specialDiscountPeriod.isEventPeriod()).thenReturn(isEventPeriod);
        when(specialDiscountPeriod.isStar()).thenReturn(isStar);
    }

    private void verifyCall(int eventTimes, int starTimes) {
        verify(specialDiscountPeriod, times(eventTimes)).isEventPeriod();
        verify(specialDiscountPeriod, times(starTimes)).isStar();
    }
}