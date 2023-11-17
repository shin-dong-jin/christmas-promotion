package christmas.domain.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MoneyTest {
    private static final long AMOUNT = 1_000L;
    private Money money;

    @ParameterizedTest
    @ValueSource(longs = {-1_000_000_000_000L, -1_000_000_000L, -1_000_000L, -1_000L, -10L, 0L,
            10L, 1_000L, 1_000_000L, 1_000_000_000L, 1_000_000_000_000L})
    void constructor(long amount) {
        assertThatCode(() -> new Money(amount)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"1000,2000", "-1000,0", "1000000000000, 1000000001000", "-1000000000000, -999999999000"})
    void add(long otherAmount, long expectedAmount) {
        money = new Money(AMOUNT);
        Money other = new Money(otherAmount);
        Money expected = new Money(expectedAmount);

        assertThat(money.add(other)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1000,1000000", "-1000,-1000000"})
    void multiply(long scalar, long expectedAmount) {
        money = new Money(AMOUNT);
        Money expected = new Money(expectedAmount);

        assertThat(money.multiply(scalar)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,-1000", "2,1000", "3,-1000"})
    void negative(int times, long expectedAmount) {
        money = new Money(AMOUNT);

        for (int i = 0; i < times; i++) {
            money = money.negative();
        }

        Money expected = new Money(expectedAmount);

        assertThat(money).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1000,true", "0, true", "100,true", "1000,true", "2000,false"})
    void notLessThan(long otherAmount, boolean expected) {
        money = new Money(AMOUNT);
        Money other = new Money(otherAmount);

        assertThat(money.notLessThan(other)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1000000:-1,000,000", "-1000:-1,000", "-100:-100", "0:0", "100:100", "1000:1,000", "1000000:1,000,000"}, delimiter = ':')
    void testToString(long amount, String expected) {
        money = new Money(amount);

        assertThat(money.toString()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1000,false", "0,false", "1000,true", "2000,false"})
    void testEquals(long otherAmount, boolean expected) {
        money = new Money(AMOUNT);
        Money other = new Money(otherAmount);

        assertThat(money.equals(other)).isEqualTo(expected);
    }

    @Test
    void testEquals_ExactlySame() {
        money = new Money(AMOUNT);

        assertThat(money.equals(money)).isTrue();
    }

    @Test
    void testEquals_null() {
        money = new Money(AMOUNT);

        assertThat(money.equals(null)).isFalse();
    }

    @Test
    void testEquals_otherClass() {
        money = new Money(AMOUNT);
        Long amount = Long.valueOf(AMOUNT);

        assertThat(money.equals(amount)).isFalse();
    }

    @Test
    void testHashCode_exactlyEquals() {
        money = new Money(AMOUNT);

        assertThat(money.hashCode()).isEqualTo(money.hashCode());
    }

    @Test
    void testHashCode_equals() {
        money = new Money(AMOUNT);
        Money other = new Money(AMOUNT);

        assertThat(money.hashCode()).isEqualTo(other.hashCode());
    }

    @ParameterizedTest
    @ValueSource(longs = {-1_000L, 0L, 2_000L})
    void testHashCode_notEquals(long otherAmount) {
        money = new Money(AMOUNT);
        Money other = new Money(otherAmount);

        assertThat(money.hashCode()).isNotEqualTo(other.hashCode());
    }
}