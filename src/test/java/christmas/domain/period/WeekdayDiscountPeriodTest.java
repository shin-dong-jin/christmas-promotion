package christmas.domain.period;

import christmas.domain.unit.Day;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeekdayDiscountPeriodTest {
    private static final Day START_DAY = Day.FIRST_DAY,
            END_DAY = Day.LAST_DAY;
    private Day day;
    private WeekdayDiscountPeriod weekdayDiscountPeriod;

    @BeforeEach
    void setUp() {
        day = mock(Day.class);
        weekdayDiscountPeriod = new WeekdayDiscountPeriod(day);
    }

    @Test
    void isEventPeriod_이벤트기간() {
        setMock(false, false);

        assertThat(weekdayDiscountPeriod.isEventPeriod()).isTrue();

        verifyCalls(1, 1);
    }

    @Test
    void isEventPeriod_이벤트기간전() {
        setMock(true, false);

        assertThat(weekdayDiscountPeriod.isEventPeriod()).isFalse();

        verifyCalls(1, 0);
    }

    @Test
    void isEventPeriod_이벤트기간후() {
        setMock(false, true);

        assertThat(weekdayDiscountPeriod.isEventPeriod()).isFalse();

        verifyCalls(1, 1);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isWeekday(boolean expected) {
        when(day.isWeekday()).thenReturn(expected);

        assertThat(weekdayDiscountPeriod.isWeekday()).isEqualTo(expected);

        verify(day, times(1)).isWeekday();
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