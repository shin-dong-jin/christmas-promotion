package christmas.domain.period;

import christmas.domain.unit.Day;

public class ChristmasDiscountPeriod {
    private static final Day START_DAY = Day.FIRST_DAY,
            END_DAY = Day.CHRISTMAS;
    private final Day day;

    public ChristmasDiscountPeriod(Day day) {
        this.day = day;
    }

    public boolean isEventPeriod() {
        return !(day.isBefore(START_DAY) || day.isAfter(END_DAY));
    }

    public long calculateDaysFromStart() {
        return day.betweenFrom(START_DAY);
    }
}
