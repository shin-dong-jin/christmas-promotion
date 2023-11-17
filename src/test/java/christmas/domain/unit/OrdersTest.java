package christmas.domain.unit;

import christmas.enums.Menu;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class OrdersTest {
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
    private Orders orders;

    @Test
    void constructor_성공() {
        assertThatCode(() -> new Orders(ORDERS_NOT_DUPLICATE)).doesNotThrowAnyException();
    }

    @Test
    void constructor_중복메뉴() {
        assertThatThrownBy(() -> new Orders(ORDERS_DUPLICATE)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void convertListToMap() {
        Map<Menu, Long> expected = Map.of(
                Menu.T_BONE_STEAK, 1L,
                Menu.BARBECUE_LIBS, 1L,
                Menu.CHOCOLATE_CAKE, 2L,
                Menu.ZERO_COKE, 1L
        );

        orders = new Orders(ORDERS_NOT_DUPLICATE);

        assertThat(orders.convertListToMap()).isEqualTo(expected);
    }
}