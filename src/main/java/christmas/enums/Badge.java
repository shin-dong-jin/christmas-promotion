package christmas.enums;

import christmas.domain.unit.Money;
import christmas.domain.policy.TotalProfitPolicy;

import java.util.Arrays;

public enum Badge {
    SANTA("산타", new Money(20_000L)),
    TREE("트리", new Money(10_000L)),
    STAR("별", new Money(5_000L)),
    BLANK("없음", Money.ZERO);

    private final String name;
    private final Money minProfit;

    public static Badge getBadge(TotalProfitPolicy profitTotal) {
        return Arrays.stream(values())
                .filter(value -> profitTotal.getTotalProfit().negative().notLessThan(value.minProfit))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    Badge(String name, Money minProfit) {
        this.name = name;
        this.minProfit = minProfit;
    }

    @Override
    public String toString() {
        return name;
    }
}
