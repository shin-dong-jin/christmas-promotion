package christmas.domain.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class DayTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final int MONTH = 12, DAY = 3;
    private Day day;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31})
    void constructor_성공(int day) {
        assertThatCode(() -> new Day(day)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 32})
    void constructor_예외발생(int day) {
        assertThatThrownBy(() -> new Day(day)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,false", "4,true", "3,false"})
    void isBefore(int otherDay, boolean expected) {
        day = new Day(DAY);
        Day other = new Day(otherDay);
        assertThat(day.isBefore(other)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,true", "4,false", "3,false"})
    void isAfter(int otherDay, boolean expected) {
        day = new Day(DAY);
        Day other = new Day(otherDay);
        assertThat(day.isAfter(other)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2", "2,1"})
    void betweenFrom(int otherDay, long expected) {
        day = new Day(DAY);
        Day other = new Day(otherDay);
        assertThat(day.betweenFrom(other)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,false", "3,true"})
    void isStar(int day, boolean expected) {
        this.day = new Day(day);
        assertThat(this.day.isStar()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,false", "3,true"})
    void isWeekday(int day, boolean expected) {
        this.day = new Day(day);
        assertThat(this.day.isWeekday()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,true", "3,false"})
    void isWeekend(int day, boolean expected) {
        this.day = new Day(day);
        assertThat(this.day.isWeekend()).isEqualTo(expected);
    }

    @Test
    void month() {
        day = new Day(DAY);
        String expected = MONTH + "월";

        assertThat(day.month()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,12월 1일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!",
            "31,12월 31일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"})
    void testToString(int day, String expected) {
        this.day = new Day(day);
        assertThat(this.day.toString()).isEqualTo(expected + LINE_SEPARATOR);
    }
}