package christmas.domain.unit;

import christmas.enums.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class OrdersMenuCountTest {
    private static final long MAX_TOTAL_COUNT = 20L;
    private static final Map<Menu, Long> MENU_COUNT = Map.of(
            Menu.T_BONE_STEAK, 1L,
            Menu.BARBECUE_LIBS, 1L,
            Menu.CHOCOLATE_CAKE, 2L,
            Menu.ZERO_COKE, 1L
    ),
            MENU_COUNT_ONLY_DRINK = Map.of(
                    Menu.ZERO_COKE, 2L,
                    Menu.RED_WINE, 1L,
                    Menu.CHAMPAGNE, 3L
            ),
            MENU_COUNT_OVER_MAX_TOTAL = Map.of(
                    Menu.T_BONE_STEAK, MAX_TOTAL_COUNT + 10L
            );
    private OrdersMenuCount ordersMenuCount;

    @BeforeEach
    void setUp() {
        ordersMenuCount = new OrdersMenuCount(MENU_COUNT);
    }

    @Test
    void constructor_성공() {
        assertThatCode(() -> new OrdersMenuCount(MENU_COUNT)).doesNotThrowAnyException();
    }

    @Test
    void constructor_음료만주문() {
        assertThatThrownBy(() -> new OrdersMenuCount(MENU_COUNT_ONLY_DRINK))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_최대주문개수초과() {
        assertThatThrownBy(() -> new OrdersMenuCount(MENU_COUNT_OVER_MAX_TOTAL))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void calculateOrderTotal() {
        Money expected = new Money(142_000L);
        assertThat(ordersMenuCount.calculateOrderTotal()).isEqualTo(expected);
    }

    @Test
    void getDessertCount() {
        long expected = 2L;
        assertThat(ordersMenuCount.getDessertCount()).isEqualTo(expected);
    }

    @Test
    void getMainCount() {
        long expected = 2L;
        assertThat(ordersMenuCount.getMainCount()).isEqualTo(expected);
    }

    @Test
    void testToString() {
        assertThat(ordersMenuCount.toString()).contains(List.of(
                "<주문 메뉴>",
                "티본스테이크 1개",
                "바비큐립 1개",
                "초코케이크 2개",
                "제로콜라 1개"
        ));
    }
}