package christmas.enums;

import christmas.domain.policy.TotalProfitPolicy;
import christmas.domain.unit.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static christmas.enums.Badge.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BadgeTest {
    private static final Badge[] BADGES = {SANTA, TREE, STAR, BLANK};
    private static final List<String> BADGE_STRINGS = List.of("SANTA", "TREE", "STAR", "BLANK");

    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest
    @CsvSource(value = {"0,BLANK,4", "-5000,STAR,3", "-10000,TREE,2", "-20000,SANTA,1"})
    void getBadge(long amount, String badge, int getProfitTimes) {
        Money profit = new Money(amount);
        Badge expected = Badge.valueOf(badge);

        TotalProfitPolicy totalProfitPolicy = mock(TotalProfitPolicy.class);
        when(totalProfitPolicy.getTotalProfit()).thenReturn(profit);

        assertThat(Badge.getBadge(totalProfitPolicy)).isEqualTo(expected);

        verify(totalProfitPolicy, times(getProfitTimes)).getTotalProfit();
    }


    @ParameterizedTest
    @CsvSource(value = {"1000,4", "5000,4", "10000,4", "20000,4"})
    void getBadge_에러(long amount, int getProfitTimes) {
        Money profit = new Money(amount);

        TotalProfitPolicy totalProfitPolicy = mock(TotalProfitPolicy.class);
        when(totalProfitPolicy.getTotalProfit()).thenReturn(profit);

        assertThatThrownBy(() -> Badge.getBadge(totalProfitPolicy))
                .isInstanceOf(IllegalStateException.class);

        verify(totalProfitPolicy, times(getProfitTimes)).getTotalProfit();
    }

    @ParameterizedTest
    @CsvSource(value = {"SANTA,산타", "TREE,트리", "STAR,별", "BLANK,없음"})
    void testToString(String badgeString, String expected) {
        Badge badge = Badge.valueOf(badgeString);

        assertThat(badge.toString()).isEqualTo(expected);
    }

    @Test
    void values() {
        assertThat(Badge.values()).isEqualTo(BADGES);
    }

    @Test
    void valueOf() {
        for (int i = 0; i < BADGE_STRINGS.size(); i++) {
            assertThat(Badge.valueOf(BADGE_STRINGS.get(i)))
                    .isEqualTo(BADGES[i]);
        }
    }
}