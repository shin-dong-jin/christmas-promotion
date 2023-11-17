package christmas.enums;

import christmas.domain.unit.Money;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static christmas.enums.MenuCategory.*;

public enum Menu {
    SOUP(APPETIZER, "양송이수프", new Money(6_000L)),
    TAPAS(APPETIZER, "타파스", new Money(5_500L)),
    CAESAR_SALAD(APPETIZER, "시저샐러드", new Money(8_000L)),
    T_BONE_STEAK(MAIN, "티본스테이크", new Money(55_000L)),
    BARBECUE_LIBS(MAIN, "바비큐립", new Money(54_000L)),
    SEAFOOD_PASTA(MAIN, "해산물파스타", new Money(35_000L)),
    CHRISTMAS_PASTA(MAIN, "크리스마스파스타", new Money(25_000L)),
    CHOCOLATE_CAKE(DESSERT, "초코케이크", new Money(15_000L)),
    ICE_CREAM(DESSERT, "아이스크림", new Money(5_000L)),
    ZERO_COKE(DRINK, "제로콜라", new Money(3_000L)),
    RED_WINE(DRINK, "레드와인", new Money(60_000L)),
    CHAMPAGNE(DRINK, "샴페인", new Money(25_000L));

    private static final Map<String, Menu> STRING_TO_MENU = Arrays.stream(values()).collect(Collectors.toMap(value -> value.name, value -> value));
    private final MenuCategory category;
    private final String name;
    private final Money price;

    public static Menu convertStringToMenu(String name) {
        validateName(name);
        return STRING_TO_MENU.get(name);
    }

    public static boolean contains(String name) {
        return STRING_TO_MENU.containsKey(name);
    }

    private static void validateName(String name) {
        if (!contains(name)) {
            throw new IllegalStateException();
        }
    }

    Menu(MenuCategory category, String name, Money price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public boolean isMain() {
        return category.equals(MAIN);
    }

    public boolean isDessert() {
        return category.equals(DESSERT);
    }

    public boolean isDrink() {
        return category.equals(DRINK);
    }

    public Money multiply(long count) {
        return price.multiply(count);
    }

    @Override
    public String toString() {
        return name;
    }
}
