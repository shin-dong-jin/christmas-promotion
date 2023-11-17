package christmas.domain.discount;

import christmas.domain.unit.Money;

import java.util.Iterator;
import java.util.List;

public class Discounts implements Iterable<Discount> {
    private static final Money NO_PROFIT = Money.ZERO;
    private final List<Discount> discounts;

    public Discounts(List<Discount> discounts) {
        validateNotDuplicateClasses(discounts);
        this.discounts = discounts.stream().filter(discount -> !discount.getProfit().equals(NO_PROFIT)).toList();
    }

    public Money getTotalProfit() {
        Money totalProfit = NO_PROFIT;

        for(Discount discountProfit : discounts) {
            totalProfit = totalProfit.add(discountProfit.getProfit());
        }

        return totalProfit;
    }

    @Override
    public String toString() {
        if (discounts.isEmpty()) {
            return "";
        }

        StringBuilder profitBuilder = new StringBuilder();

        for(Discount discount : discounts) {
            profitBuilder.append(discount).append(System.lineSeparator());
        }

        return profitBuilder.toString();
    }

    @Override
    public Iterator<Discount> iterator() {
        return discounts.iterator();
    }

    private void validateNotDuplicateClasses(List<Discount> profitPolicies) {
        if (profitPolicies.size() != profitPolicies.stream().map(Discount::getClass).distinct().count()) {
            throw new IllegalStateException();
        }
    }
}
