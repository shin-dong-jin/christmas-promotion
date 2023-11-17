package christmas.domain.unit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

public class Money {
    public static final Money ZERO = new Money(0L),
            HUNDRED = new Money(100L),
            THOUSAND = new Money(1_000L),
            YEAR_AMOUNT = new Money(2_023L),
            TEN_THOUSAND = new Money(10_000L);
    private static final int SAME = 0;
    private final BigDecimal amount;

    public Money(long amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money add(Money money) {
        BigDecimal result = amount.add(money.amount);
        return new Money(result);
    }

    public Money multiply(long scalar) {
        BigDecimal result = amount.multiply(BigDecimal.valueOf(scalar));
        return new Money(result);
    }

    public Money negative() {
        return multiply(-1L);
    }

    public boolean notLessThan(Money other) {
        return this.biggerThan(other) || this.equals(other);
    }

    @Override
    public String toString() {
        DecimalFormat moneyFormat = new DecimalFormat("###,###");
        return moneyFormat.format(amount);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Money money = (Money) other;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    private boolean biggerThan(Money other) {
        return amount.compareTo(other.amount) > SAME;
    }
}
