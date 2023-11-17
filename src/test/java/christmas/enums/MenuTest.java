package christmas.enums;

import christmas.domain.unit.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static christmas.enums.Menu.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {
    private static final Menu[] MENUS = {SOUP, TAPAS, CAESAR_SALAD,
            T_BONE_STEAK, BARBECUE_LIBS, SEAFOOD_PASTA, CHRISTMAS_PASTA,
            CHOCOLATE_CAKE, ICE_CREAM,
            ZERO_COKE, RED_WINE, CHAMPAGNE};
    private static final List<String> MENU_STRINGS = List.of("SOUP", "TAPAS", "CAESAR_SALAD",
            "T_BONE_STEAK", "BARBECUE_LIBS", "SEAFOOD_PASTA", "CHRISTMAS_PASTA",
            "CHOCOLATE_CAKE", "ICE_CREAM",
            "ZERO_COKE", "RED_WINE", "CHAMPAGNE");

    @ParameterizedTest
    @CsvSource(value = {"양송이수프,SOUP", "타파스,TAPAS", "시저샐러드,CAESAR_SALAD", "티본스테이크,T_BONE_STEAK",
            "바비큐립,BARBECUE_LIBS", "해산물파스타,SEAFOOD_PASTA", "크리스마스파스타,CHRISTMAS_PASTA", "초코케이크,CHOCOLATE_CAKE",
            "아이스크림,ICE_CREAM", "제로콜라,ZERO_COKE", "레드와인,RED_WINE", "샴페인,CHAMPAGNE"})
    void convertStringToMenu(String input, String menu) {
        Menu expected = Menu.valueOf(menu);

        assertThat(Menu.convertStringToMenu(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"TAPAS", "삼페인", "쵸코케이크", "해샨물파스타", "비비큐립"})
    void convertStringToMenu_유효하지않은메뉴(String input) {
        assertThatThrownBy(() -> Menu.convertStringToMenu(input)).isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"양송이수프", "타파스", "시저샐러드",
            "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타",
            "초코케이크", "아이스크림",
            "제로콜라", "레드와인", "샴페인"})
    void contains_참(String input) {
        assertThat(Menu.contains(input)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"TAPAS", "삼페인", "쵸코케이크", "해샨물파스타", "비비큐립"})
    void contains_거짓(String input) {
        assertThat(Menu.contains(input)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"T_BONE_STEAK", "BARBECUE_LIBS", "SEAFOOD_PASTA", "CHRISTMAS_PASTA"})
    void isMain_참(String name) {
        Menu menu = Menu.valueOf(name);

        assertThat(menu.isMain()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"SOUP", "TAPAS", "CAESAR_SALAD",
            "CHOCOLATE_CAKE", "ICE_CREAM",
            "ZERO_COKE", "RED_WINE", "CHAMPAGNE"})
    void isMain_거짓(String name) {
        Menu menu = Menu.valueOf(name);

        assertThat(menu.isMain()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"CHOCOLATE_CAKE", "ICE_CREAM"})
    void isDessert_참(String name) {
        Menu menu = Menu.valueOf(name);

        assertThat(menu.isDessert()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"SOUP", "TAPAS", "CAESAR_SALAD",
            "T_BONE_STEAK", "BARBECUE_LIBS", "SEAFOOD_PASTA", "CHRISTMAS_PASTA",
            "ZERO_COKE", "RED_WINE", "CHAMPAGNE"})
    void isDessert_거짓(String name) {
        Menu menu = Menu.valueOf(name);

        assertThat(menu.isDessert()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ZERO_COKE", "RED_WINE", "CHAMPAGNE"})
    void isDrink_참(String name) {
        Menu menu = Menu.valueOf(name);

        assertThat(menu.isDrink()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"SOUP", "TAPAS", "CAESAR_SALAD",
            "T_BONE_STEAK", "BARBECUE_LIBS", "SEAFOOD_PASTA", "CHRISTMAS_PASTA",
            "CHOCOLATE_CAKE", "ICE_CREAM",})
    void isDrink_거짓(String name) {
        Menu menu = Menu.valueOf(name);

        assertThat(menu.isDrink()).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"SOUP,2,12000", "SEAFOOD_PASTA,3,105000", "CHOCOLATE_CAKE,2,30000", "ZERO_COKE,2,6000"})
    void multiply(String name, long count, long amount) {
        Menu menu = Menu.valueOf(name);
        Money expected = new Money(amount);

        assertThat(menu.multiply(count)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"양송이수프,SOUP", "타파스,TAPAS", "시저샐러드,CAESAR_SALAD", "티본스테이크,T_BONE_STEAK",
            "바비큐립,BARBECUE_LIBS", "해산물파스타,SEAFOOD_PASTA", "크리스마스파스타,CHRISTMAS_PASTA", "초코케이크,CHOCOLATE_CAKE",
            "아이스크림,ICE_CREAM", "제로콜라,ZERO_COKE", "레드와인,RED_WINE", "샴페인,CHAMPAGNE"})
    void testToString(String name, String value) {
        Menu menu = Menu.valueOf(value);

        assertThat(menu.toString()).isEqualTo(name);
    }

    @Test
    void values() {
        assertThat(Menu.values()).isEqualTo(MENUS);
    }

    @Test
    void valueOf() {
        for (int i = 0; i < MENU_STRINGS.size(); i++) {
            assertThat(Menu.valueOf(MENU_STRINGS.get(i)))
                    .isEqualTo(MENUS[i]);
        }
    }
}