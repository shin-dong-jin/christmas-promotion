package christmas.domain.period;

import christmas.domain.unit.Day;

public class SpecialDiscountPeriod {
    private static final Day START_DAY = Day.FIRST_DAY,
            END_DAY = Day.LAST_DAY;
    private final Day day;

    public SpecialDiscountPeriod(Day day) {
        this.day = day;
    }

    public boolean isEventPeriod() {
        return !(day.isBefore(START_DAY) || day.isAfter(END_DAY));
    }

    public boolean isStar() {
        return day.isStar();
    }
}
