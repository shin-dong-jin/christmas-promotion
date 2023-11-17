package christmas.domain.discount;

import christmas.domain.unit.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscountsTest {
    private static final Money NO_PROFIT = Money.ZERO,
            CHRISTMAS_PROFIT = new Money(-1_200L),
            WEEKDAY_PROFIT = new Money(-4_046),
            WEEKEND_PROFIT = NO_PROFIT,
            SPECIAL_PROFIT = new Money(-1_000),
            DISCOUNTS_PROFIT = new Money(-6_246L);
    private static final String LINE_SEPARATOR = System.lineSeparator(),
            EMPTY = "",
            CHRISTMAS_STRING = "크리스마스 디데이 할인: -1,200원",
            WEEKDAY_STRING = "평일 할인: -4,046원",
            SPECIAL_STRING = "특별 할인: -1,000원",
            DISCOUNTS_STRING = CHRISTMAS_STRING + LINE_SEPARATOR
                    + WEEKDAY_STRING + LINE_SEPARATOR
                    + SPECIAL_STRING + LINE_SEPARATOR;
    private Discount christmasDiscount, weekdayDiscount, weekendDiscount, specialDiscount, otherChristmasDiscount;
    private Discounts discounts;

    @BeforeEach
    void setUp() {
        christmasDiscount = mock(ChristmasDiscount.class);
        weekdayDiscount = mock(WeekdayDiscount.class);
        weekendDiscount = mock(WeekendDiscount.class);
        specialDiscount = mock(SpecialDiscount.class);
        otherChristmasDiscount = mock(ChristmasDiscount.class);
    }

    @Test
    void constructor() {
        List<Discount> discountList = List.of(christmasDiscount, weekdayDiscount, weekendDiscount, specialDiscount);

        setMockProfit(NO_PROFIT, NO_PROFIT, NO_PROFIT, NO_PROFIT);

        assertThatCode(() -> new Discounts(discountList)).doesNotThrowAnyException();
        ;
    }

    @Test
    void constructor_중복클래스() {
        List<Discount> duplicateDiscounts = List.of(christmasDiscount, weekdayDiscount, weekendDiscount, specialDiscount, otherChristmasDiscount);

        assertThatThrownBy(() -> new Discounts(duplicateDiscounts))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void getTotalProfit() {
        setMockProfit(CHRISTMAS_PROFIT, WEEKDAY_PROFIT, WEEKEND_PROFIT, SPECIAL_PROFIT);
        discounts = new Discounts(List.of(christmasDiscount, weekdayDiscount, weekendDiscount, specialDiscount));

        assertThat(discounts.getTotalProfit()).isEqualTo(DISCOUNTS_PROFIT);

        verifyProfit(2, 2, 1, 2);
    }

    @Test
    void getTotalProfit_혜택없음() {
        setMockProfit(NO_PROFIT, NO_PROFIT, NO_PROFIT, NO_PROFIT);
        discounts = new Discounts(List.of(christmasDiscount, weekdayDiscount, weekendDiscount, specialDiscount));

        assertThat(discounts.getTotalProfit()).isEqualTo(NO_PROFIT);

        verifyProfit(1, 1, 1, 1);
    }

    @Test
    void testToString() {
        setMockProfit(CHRISTMAS_PROFIT, WEEKDAY_PROFIT, WEEKEND_PROFIT, SPECIAL_PROFIT);
        setMockString(CHRISTMAS_STRING, WEEKDAY_STRING, EMPTY, SPECIAL_STRING);
        discounts = new Discounts(List.of(christmasDiscount, weekdayDiscount, weekendDiscount, specialDiscount));

        assertThat(discounts.toString()).isEqualTo(DISCOUNTS_STRING);

        verifyProfit(1, 1, 1, 1);
    }

    @Test
    void testToString_혜택없음() {
        setMockProfit(NO_PROFIT, NO_PROFIT, NO_PROFIT, NO_PROFIT);
        setMockString(EMPTY, EMPTY, EMPTY, EMPTY);
        discounts = new Discounts(List.of(christmasDiscount, weekdayDiscount, weekendDiscount, specialDiscount));

        assertThat(discounts.toString()).isEmpty();

        verifyProfit(1, 1, 1, 1);
    }

    @Test
    void iterator() {
        setMockProfit(CHRISTMAS_PROFIT, WEEKDAY_PROFIT, WEEKEND_PROFIT, SPECIAL_PROFIT);
        List<Discount> discountList = List.of(christmasDiscount, weekdayDiscount, weekendDiscount, specialDiscount);
        List<Discount> filteredList = List.of(christmasDiscount, weekdayDiscount, specialDiscount);

        discounts = new Discounts(discountList);
        Iterator<Discount> iterator = discounts.iterator();
        List<Discount> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        assertThat(result).isEqualTo(filteredList);
    }

    private void setMockProfit(Money christmas, Money weekday, Money weekend, Money special) {
        when(christmasDiscount.getProfit()).thenReturn(christmas);
        when(weekdayDiscount.getProfit()).thenReturn(weekday);
        when(weekendDiscount.getProfit()).thenReturn(weekend);
        when(specialDiscount.getProfit()).thenReturn(special);
    }

    private void verifyProfit(int christmas, int weekday, int weekend, int special) {
        verify(christmasDiscount, times(christmas)).getProfit();
        verify(weekdayDiscount, times(weekday)).getProfit();
        verify(weekendDiscount, times(weekend)).getProfit();
        verify(specialDiscount, times(special)).getProfit();
    }

    private void setMockString(String christmas, String weekday, String weekend, String special) {
        when(christmasDiscount.toString()).thenReturn(christmas);
        when(weekdayDiscount.toString()).thenReturn(weekday);
        when(weekendDiscount.toString()).thenReturn(weekend);
        when(specialDiscount.toString()).thenReturn(special);
    }
}