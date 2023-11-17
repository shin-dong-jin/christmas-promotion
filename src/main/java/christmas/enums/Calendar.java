package christmas.enums;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Calendar {
    FIRST(1, false), SECOND(2, false), THIRD(3, true), FOURTH(4, false), FIFTH(5, false), SIXTH(6, false), SEVENTH(7, false), EIGHTH(8, false), NINTH(9, false), TENTH(10, true),
    ELEVENTH(11, false), TWELFTH(12, false), THIRTEENTH(13, false), FOURTEENTH(14, false), FIFTEENTH(15, false), SIXTEENTH(16, false), SEVENTEENTH(17, true), EIGHTEENTH(18, false), NINETEENTH(19, false), TWENTIETH(20, false),
    TWENTY_FIRST(21, false), TWENTY_SECOND(22, false), TWENTY_THIRD(23, false), TWENTY_FOURTH(24, true), TWENTY_FIFTH(25, true), TWENTY_SIXTH(26, false), TWENTY_SEVENTH(27, false), TWENTY_EIGHTH(28, false), TWENTY_NINTH(29, false), THIRTIETH(30, false),
    THIRTY_FIRST(31, true);

    public static final int FIRST_DAY = 1, CHRISTMAS_DAY = 25, LAST_DAY = 31;
    private static final int YEAR = 2023, MONTH = 12;
    private static List<DayOfWeek> WEEKDAY = List.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY),
            WEEKEND = List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
    private static final Map<Integer, Calendar> INTEGER_TO_CALENDAR = Arrays.stream(values())
            .collect(Collectors.toMap(value -> value.day, value -> value));
    private final int day;
    private final boolean star;

    public static LocalDate convertIntegerToLocalDate(int day) {
        validateDay(day);
        return LocalDate.of(YEAR, MONTH, INTEGER_TO_CALENDAR.get(day).day);
    }

    public static boolean isStar(LocalDate date) {
        validateLocalDate(date);
        return INTEGER_TO_CALENDAR.get(date.getDayOfMonth()).star;
    }

    public static boolean isWeekday(LocalDate date) {
        validateLocalDate(date);
        return WEEKDAY.contains(date.getDayOfWeek());
    }

    public static boolean isWeekend(LocalDate date) {
        validateLocalDate(date);
        return WEEKEND.contains(date.getDayOfWeek());
    }

    public static boolean contains(int day) {
        return INTEGER_TO_CALENDAR.containsKey(day);
    }

    private static void validateLocalDate(LocalDate date) {
        if (date.getYear() != YEAR || date.getMonthValue() != MONTH || !contains(date.getDayOfMonth())) {
            throw new IllegalStateException();
        }
    }

    private static void validateDay(int day) {
        if (!contains(day)) {
            throw new IllegalStateException();
        }
    }

    Calendar(int day, boolean star) {
        this.day = day;
        this.star = star;
    }
}
