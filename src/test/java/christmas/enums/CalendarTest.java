package christmas.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;

import static christmas.enums.Calendar.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalendarTest {
    private static final int YEAR = 2023, MONTH = 12, DAY = 3;
    private static final Calendar[] CALENDARS = {FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH, NINTH, TENTH,
            ELEVENTH, TWELFTH, THIRTEENTH, FOURTEENTH, FIFTEENTH, SIXTEENTH, SEVENTEENTH, EIGHTEENTH, NINETEENTH, TWENTIETH,
            TWENTY_FIRST, TWENTY_SECOND, TWENTY_THIRD, TWENTY_FOURTH, TWENTY_FIFTH, TWENTY_SIXTH, TWENTY_SEVENTH, TWENTY_EIGHTH, TWENTY_NINTH, THIRTIETH,
            THIRTY_FIRST};

    private static final List<String> CALENDAR_STRINGS = List.of("FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH", "SIXTH", "SEVENTH", "EIGHTH", "NINTH", "TENTH",
            "ELEVENTH", "TWELFTH", "THIRTEENTH", "FOURTEENTH", "FIFTEENTH", "SIXTEENTH", "SEVENTEENTH", "EIGHTEENTH", "NINETEENTH", "TWENTIETH",
            "TWENTY_FIRST", "TWENTY_SECOND", "TWENTY_THIRD", "TWENTY_FOURTH", "TWENTY_FIFTH", "TWENTY_SIXTH", "TWENTY_SEVENTH", "TWENTY_EIGHTH", "TWENTY_NINTH", "THIRTIETH",
            "THIRTY_FIRST");

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31})
    void convertIntegerToLocalDate(int day) {
        LocalDate expected = LocalDate.of(YEAR, MONTH, day);

        assertThat(Calendar.convertIntegerToLocalDate(day)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 32})
    void convertIntegerToLocalDate_유효하지않은날짜(int day) {
        assertThatThrownBy(() -> Calendar.convertIntegerToLocalDate(day))
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,false", "2,false", "3,true", "4,false", "5,false", "6,false", "7,false", "8,false", "9,false", "10,true",
            "11,false", "12,false", "13,false", "14,false", "15,false", "16,false", "17,true", "18,false", "19,false", "20,false",
            "21,false", "22,false", "23,false", "24,true", "25,true", "26,false", "27,false", "28,false", "29,false", "30,false", "31,true"})
    void isStar(int day, boolean expected) {
        LocalDate date = LocalDate.of(YEAR, MONTH, day);

        assertThat(Calendar.isStar(date)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 1000, 2000, 2010, 2020, 3000})
    void isStar_유효하지않은년도(int invalidYear) {
        LocalDate date = LocalDate.of(invalidYear, MONTH, DAY);

        assertThatThrownBy(() -> Calendar.isStar(date))
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
    void isStar_유효하지않은달(int invalidMonth) {
        LocalDate date = LocalDate.of(YEAR, invalidMonth, DAY);

        assertThatThrownBy(() -> Calendar.isStar(date))
                .isInstanceOf(IllegalStateException.class);
    }


    @ParameterizedTest
    @CsvSource(value = {"1,false", "2,false", "3,true", "4,true", "5,true", "6,true", "7,true", "8,false", "9,false", "10,true",
            "11,true", "12,true", "13,true", "14,true", "15,false", "16,false", "17,true", "18,true", "19,true", "20,true",
            "21,true", "22,false", "23,false", "24,true", "25,true", "26,true", "27,true", "28,true", "29,false", "30,false", "31,true"})
    void isWeekday(int day, boolean expected) {
        LocalDate date = LocalDate.of(YEAR, MONTH, day);

        assertThat(Calendar.isWeekday(date)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 1000, 2000, 2010, 2020, 3000})
    void isWeekday_유효하지않은년도(int invalidYear) {
        LocalDate date = LocalDate.of(invalidYear, MONTH, DAY);

        assertThatThrownBy(() -> Calendar.isWeekday(date))
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
    void isWeekday_유효하지않은달(int invalidMonth) {
        LocalDate date = LocalDate.of(YEAR, invalidMonth, DAY);

        assertThatThrownBy(() -> Calendar.isWeekday(date))
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,true", "2,true", "3,false", "4,false", "5,false", "6,false", "7,false", "8,true", "9,true", "10,false",
            "11,false", "12,false", "13,false", "14,false", "15,true", "16,true", "17,false", "18,false", "19,false", "20,false",
            "21,false", "22,true", "23,true", "24,false", "25,false", "26,false", "27,false", "28,false", "29,true", "30,true", "31,false"})
    void isWeekend(int day, boolean expected) {
        LocalDate date = LocalDate.of(YEAR, MONTH, day);

        assertThat(Calendar.isWeekend(date)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 1000, 2000, 2010, 2020, 3000})
    void isWeekend_유효하지않은년도(int invalidYear) {
        LocalDate date = LocalDate.of(invalidYear, MONTH, DAY);

        assertThatThrownBy(() -> Calendar.isWeekend(date))
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
    void isWeekend_유효하지않은달(int invalidMonth) {
        LocalDate date = LocalDate.of(YEAR, invalidMonth, DAY);

        assertThatThrownBy(() -> Calendar.isWeekend(date))
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31})
    void contains_참(int day) {
        assertThat(Calendar.contains(day)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 32})
    void contains_거짓(int day) {
        assertThat(Calendar.contains(day)).isFalse();
    }

    @Test
    void values() {
        assertThat(Calendar.values()).isEqualTo(CALENDARS);
    }

    @Test
    void valueOf() {
        for (int i = 0; i < CALENDAR_STRINGS.size(); i++) {
            assertThat(Calendar.valueOf(CALENDAR_STRINGS.get(i)))
                    .isEqualTo(CALENDARS[i]);
        }
    }
}