package christmas.domain.period;

import christmas.domain.unit.Day;

public class WeekdayDiscountPeriod {
    private static final Day START_DAY = Day.FIRST_DAY,
            END_DAY = Day.LAST_DAY;
    private final Day day;

    public WeekdayDiscountPeriod(Day day) {
        this.day = day;
    }

    public boolean isEventPeriod() {
        return !(day.isBefore(START_DAY) || day.isAfter(END_DAY));
    }

    public boolean isWeekday() {
        return day.isWeekday();
    }
}
