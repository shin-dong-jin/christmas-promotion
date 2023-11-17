package christmas.domain.unit;

import christmas.enums.Present;

import java.util.List;

public class Presents {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final List<Present> presents;

    public Presents(List<Present> presents) {
        validateNotDuplicate(presents);
        this.presents = presents;
    }

    public boolean isEmpty() {
        return presents.isEmpty();
    }

    public Money getProfit() {
        Money totalProfit = Money.ZERO;

        for(Present present : presents) {
            totalProfit = totalProfit.add(present.getPresentProfit());
        }

        return totalProfit;
    }

    @Override
    public String toString() {
        if (presents.isEmpty()) {
            return "";
        }

        StringBuilder presentsBuilder = new StringBuilder();

        for(Present present : presents) {
            presentsBuilder.append(present).append(LINE_SEPARATOR);
        }

        return presentsBuilder.toString();
    }

    private void validateNotDuplicate(List<Present> presents) {
        if (presents.size() != presents.stream().distinct().count()) {
            throw new IllegalStateException();
        }
    }
}
