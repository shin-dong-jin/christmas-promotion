package christmas.domain.period;

import christmas.domain.unit.Day;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeekendDiscountPeriodTest {
    private static final Day START_DAY = Day.FIRST_DAY,
            END_DAY = Day.LAST_DAY;
    private Day day;
    private WeekendDiscountPeriod weekendDiscountPeriod;

    @BeforeEach
    void setUp() {
        day = mock(Day.class);
        weekendDiscountPeriod = new WeekendDiscountPeriod(day);
    }

    @Test
    void isEventPeriod_이벤트기간() {
        setMock(false, false);

        assertThat(weekendDiscountPeriod.isEventPeriod()).isTrue();

        verifyCalls(1, 1);
    }

    @Test
    void isEventPeriod_이벤트기간전() {
        setMock(true, false);

        assertThat(weekendDiscountPeriod.isEventPeriod()).isFalse();

        verifyCalls(1, 0);
    }

    @Test
    void isEventPeriod_이벤트기간후() {
        setMock(false, true);

        assertThat(weekendDiscountPeriod.isEventPeriod()).isFalse();

        verifyCalls(1, 1);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isWeekend(boolean expected) {
        when(day.isWeekend()).thenReturn(expected);

        assertThat(weekendDiscountPeriod.isWeekend()).isEqualTo(expected);

        verify(day, times(1)).isWeekend();
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