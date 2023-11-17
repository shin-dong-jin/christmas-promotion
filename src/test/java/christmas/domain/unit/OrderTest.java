package christmas.domain.unit;

import christmas.enums.Menu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class OrderTest {
    private static final List<Order> ORDERS_NOT_DUPLICATE = List.of(
            new Order("티본스테이크", 1L),
            new Order("바비큐립", 1L),
            new Order("초코케이크", 2L),
            new Order("제로콜라", 1L)
    ),
            ORDERS_DUPLICATE = List.of(
                    new Order("티본스테이크", 1L),
                    new Order("티본스테이크", 6L),
                    new Order("제로콜라", 4L)
            );
    private static final List<String> ALL_MENU = List.of(
            "양송이수프", "시저샐러드", "티본스테이크", "해산물파스타", "크리스마스파스타",
            "초코케이크", "아이스크림", "제로콜라", "레드와인", "샴페인"
    );
    private Order order;

    @Test
    void convertListToMap_성공() {
        Map<Menu, Long> expected = Map.of(
                Menu.T_BONE_STEAK, 1L,
                Menu.BARBECUE_LIBS, 1L,
                Menu.CHOCOLATE_CAKE, 2L,
                Menu.ZERO_COKE, 1L
        );

        assertThat(Order.convertListToMap(ORDERS_NOT_DUPLICATE)).isEqualTo(expected);
    }

    @Test
    void convertListToMap_중복주문() {
        assertThatThrownBy(() -> Order.convertListToMap(ORDERS_DUPLICATE))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void validateMenuNotDuplicate_예외없음() {
        assertThatCode(() -> Order.validateMenuNotDuplicate(ORDERS_NOT_DUPLICATE))
                .doesNotThrowAnyException();
    }

    @Test
    void validateMenuNotDuplicate_예외발생() {
        assertThatThrownBy(() -> Order.validateMenuNotDuplicate(ORDERS_DUPLICATE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_성공() {
        long count = 10L;

        for(String menu : ALL_MENU) {
            assertThatCode(() -> new Order(menu, count)).doesNotThrowAnyException();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"해샨물파스타", "샴폐인", "쵸코케이크"})
    void constructor_없는메뉴주문(String menu) {
        long count = 10L;

        assertThatThrownBy(() -> new Order(menu, count)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(longs = {-10L, -1L, 0L})
    void constructor_주문개수0또는음수(long count) {
        String menu = "티본스테이크";

        assertThatThrownBy(() -> new Order(menu, count)).isInstanceOf(IllegalArgumentException.class);
    }
}