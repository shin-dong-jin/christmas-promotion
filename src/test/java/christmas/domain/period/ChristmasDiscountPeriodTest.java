package christmas.domain.period;

import christmas.domain.unit.Day;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ChristmasDiscountPeriodTest {
    private static long DAY_FROM_START = 10L;
    private static final Day START_DAY = Day.FIRST_DAY,
            END_DAY = Day.CHRISTMAS;
    private Day day;
    private ChristmasDiscountPeriod christmasDiscountPeriod;

    @BeforeEach
    void setUp() {
        day = mock(Day.class);
        christmasDiscountPeriod = new ChristmasDiscountPeriod(day);
    }

    @Test
    void isEventPeriod_이벤트기간() {
        setMock(false, false);

        assertThat(christmasDiscountPeriod.isEventPeriod()).isTrue();

        verifyCalls(1, 1);
    }

    @Test
    void isEventPeriod_이벤트기간전() {
        setMock(true, false);

        assertThat(christmasDiscountPeriod.isEventPeriod()).isFalse();

        verifyCalls(1, 0);
    }

    @Test
    void isEventPeriod_이벤트기간후() {
        setMock(false, true);

        assertThat(christmasDiscountPeriod.isEventPeriod()).isFalse();

        verifyCalls(1, 1);
    }

    @Test
    void calculateDaysFromStart() {
        when(day.betweenFrom(START_DAY)).thenReturn(DAY_FROM_START);

        assertThat(christmasDiscountPeriod.calculateDaysFromStart()).isEqualTo(DAY_FROM_START);

        verify(day, times(1)).betweenFrom(START_DAY);
    }

    private void setMock(boolean beforeStart, boolean afterEnd) {
        when(day.isBefore(START_DAY)).thenReturn(beforeStart);
        when(day.isAfter(END_DAY)).thenReturn(afterEnd);
    }

    private void verifyCalls(int startTimes, int endTimes) {
        verify(day, times(startTimes)).isBefore(START_DAY);
        verify(day, times(endTimes)).isAfter(END_DAY);
    }
}