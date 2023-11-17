package christmas.enums;

import christmas.domain.total.OrderTotalBeforeDiscount;
import christmas.domain.unit.Money;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static christmas.enums.Present.*;

class PresentTest {
    private static final List<Present> PRESENTS = List.of(CHAMPAGNE);
    private static final Money PRESENT_PROFIT = new Money(-25_000L);
    private static final String PRESENT_STRING = "샴페인 1개";
    private static final Present[] PRESENT_VALUES = {CHAMPAGNE};
    private static final List<String> PRESENT_STRINGS = List.of("CHAMPAGNE");


    @Test
    void getPresents_증정() {
        OrderTotalBeforeDiscount orderTotalBeforeDiscount = mock(OrderTotalBeforeDiscount.class);
        when(orderTotalBeforeDiscount.notLessThan(any())).thenReturn(true);

        assertThat(Present.getPresents(orderTotalBeforeDiscount)).isEqualTo(PRESENTS);

        verify(orderTotalBeforeDiscount, times(1)).notLessThan(any());
    }

    @Test
    void getPresents_증정없음() {
        OrderTotalBeforeDiscount orderTotalBeforeDiscount = mock(OrderTotalBeforeDiscount.class);
        when(orderTotalBeforeDiscount.notLessThan(any())).thenReturn(false);

        assertThat(Present.getPresents(orderTotalBeforeDiscount)).isEmpty();

        verify(orderTotalBeforeDiscount, times(1)).notLessThan(any());
    }

    @Test
    void getPresentProfit() {
        Present present = CHAMPAGNE;
        assertThat(present.getPresentProfit()).isEqualTo(PRESENT_PROFIT);
    }

    @Test
    void testToString() {
        Present present = CHAMPAGNE;
        assertThat(present.toString()).isEqualTo(PRESENT_STRING);
    }

    @Test
    void values() {
        assertThat(Present.values()).isEqualTo(PRESENT_VALUES);
    }

    @Test
    void valueOf() {
        for (int i = 0; i < PRESENT_STRINGS.size(); i++) {
            assertThat(Present.valueOf(PRESENT_STRINGS.get(i)))
                    .isEqualTo(PRESENT_VALUES[i]);
        }
    }
}