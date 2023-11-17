package christmas.domain.present;

import christmas.domain.period.MenuPresentGivePeriod;
import christmas.domain.unit.Money;
import christmas.domain.unit.Presents;

public class MenuPresentGive implements PresentGive {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final Money NO_PRESENT = Money.ZERO;
    private final MenuPresentGivePeriod menuPresentGivePeriod;
    private final Presents presents;

    public MenuPresentGive(MenuPresentGivePeriod menuPresentGivePeriod, Presents presents) {
        this.menuPresentGivePeriod = menuPresentGivePeriod;
        this.presents = presents;
    }

    @Override
    public Money getProfit() {
        if (!menuPresentGivePeriod.isEventPeriod() || presents.isEmpty()) {
            return NO_PRESENT;
        }
        return presents.getProfit();
    }

    @Override
    public String showPresents() {
        return presents.toString();
    }

    @Override
    public String toString() {
        Money profit = getProfit();

        if (profit.equals(NO_PRESENT)) {
            return "";
        }

        return "증정 이벤트: " + profit + "원" + LINE_SEPARATOR;
    }
}
