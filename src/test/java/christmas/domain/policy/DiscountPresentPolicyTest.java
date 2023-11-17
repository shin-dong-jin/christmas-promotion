package christmas.domain.policy;

import christmas.domain.discount.Discounts;
import christmas.domain.present.PresentGive;
import christmas.domain.unit.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DiscountPresentPolicyTest {
    private static final String LINE_SEPARATOR = System.lineSeparator(),
            PRESENTS_DETAILS_LIST = "샴페인 1개",
            PRESENTS_DETAILS = "<증정 메뉴>" + LINE_SEPARATOR + PRESENTS_DETAILS_LIST,
            PRESENTS_DETAILS_NOT_EXIST = "<증정 메뉴>" + LINE_SEPARATOR + "없음" + LINE_SEPARATOR,
            DISCOUNTS_PROFIT_LIST = "크리스마스 디데이 할인: -1,200원" + LINE_SEPARATOR
                    + "평일 할인: -4,046원" + LINE_SEPARATOR
                    + "특별 할인: -1,000원" + LINE_SEPARATOR,
            PRESENTS_PROFIT_LIST = "증정 이벤트: -25,000원" + LINE_SEPARATOR,
            PROFIT_DETAILS = "<혜택 내역>" + LINE_SEPARATOR + DISCOUNTS_PROFIT_LIST + PRESENTS_PROFIT_LIST,
            PROFIT_DETAILS_NOT_EXIST = "<혜택 내역>" + LINE_SEPARATOR + "없음" + LINE_SEPARATOR;
    private static final Money NO_PROFIT = Money.ZERO,
            DISCOUNTS_PROFIT = new Money(-6_246L),
            PRESENTS_PROFIT = new Money(-25_000L),
            TOTAL_PROFIT = DISCOUNTS_PROFIT.add(PRESENTS_PROFIT);
    private PresentGive presentGive;
    private Discounts discounts;
    private MinimumOrderTotalPolicy minimumOrderTotalPolicy;
    private DiscountPresentPolicy discountPresentPolicy;

    @BeforeEach
    void setUp() {
        presentGive = mock(PresentGive.class);
        discounts = mock(Discounts.class);
        minimumOrderTotalPolicy = mock(MinimumOrderTotalPolicy.class);

        discountPresentPolicy = new DiscountPresentPolicy(presentGive, discounts, minimumOrderTotalPolicy);
    }

    @Test
    void getTotalProfit_최소주문금액이상() {
        setMock_최소주문금액이상();

        assertThat(discountPresentPolicy.getTotalProfit()).isEqualTo(TOTAL_PROFIT);

        verify_최소주문금액이상(1, 1, 0);
    }

    @Test
    void getTotalProfit_최소주문금액미만() {
        setMock_최소주문금액미만();

        assertThat(discountPresentPolicy.getTotalProfit()).isEqualTo(NO_PROFIT);

        verify_최소주문금액미만();
    }

    @Test
    void getTotalProfitExceptPresent_최소주문금액이상() {
        setMock_최소주문금액이상();

        assertThat(discountPresentPolicy.getDiscountsProfit()).isEqualTo(DISCOUNTS_PROFIT);

        verify_최소주문금액이상(1, 0, 0);
    }

    @Test
    void getTotalProfitExceptPresent_최소주문금액미만() {
        setMock_최소주문금액미만();

        assertThat(discountPresentPolicy.getDiscountsProfit()).isEqualTo(NO_PROFIT);

        verify_최소주문금액미만();
    }

    @Test
    void presentsDetails_최소주문금액이상() {
        setMock_최소주문금액이상();

        assertThat(discountPresentPolicy.presentsDetails()).isEqualTo(PRESENTS_DETAILS);

        verify_최소주문금액이상(0, 1, 1);
    }

    @Test
    void presentsDetails_최소주문금액이상_혜택없음() {
        setMock_최소주문금액이상_혜택없음();

        assertThat(discountPresentPolicy.presentsDetails()).isEqualTo(PRESENTS_DETAILS_NOT_EXIST);

        verify_최소주문금액이상(0, 1, 0);
    }

    @Test
    void presentsDetails_최소주문금액미만() {
        setMock_최소주문금액미만();

        assertThat(discountPresentPolicy.presentsDetails()).isEqualTo(PRESENTS_DETAILS_NOT_EXIST);

        verify_최소주문금액미만();
    }

    @Test
    void profitDetails_최소주문금액이상() {
        setMock_최소주문금액이상();

        assertThat(discountPresentPolicy.profitDetails()).isEqualTo(PROFIT_DETAILS);

        verify_최소주문금액이상(1, 1, 0);
    }

    @Test
    void profitDetails_최소주문금액이상_혜택없음() {
        setMock_최소주문금액이상_혜택없음();

        assertThat(discountPresentPolicy.profitDetails()).isEqualTo(PROFIT_DETAILS_NOT_EXIST);

        verify_최소주문금액이상(1, 1, 0);
    }

    @Test
    void profitDetails_최소주문금액미만() {
        setMock_최소주문금액미만();

        assertThat(discountPresentPolicy.profitDetails()).isEqualTo(PROFIT_DETAILS_NOT_EXIST);

        verify_최소주문금액미만();
    }

    private void setMock_최소주문금액이상() {
        when(minimumOrderTotalPolicy.isEventAvailable()).thenReturn(true);
        when(discounts.getTotalProfit()).thenReturn(DISCOUNTS_PROFIT);
        when(presentGive.getProfit()).thenReturn(PRESENTS_PROFIT);
        when(presentGive.showPresents()).thenReturn(PRESENTS_DETAILS_LIST);
        when(discounts.toString()).thenReturn(DISCOUNTS_PROFIT_LIST);
        when(presentGive.toString()).thenReturn(PRESENTS_PROFIT_LIST);
    }

    private void setMock_최소주문금액이상_혜택없음() {
        when(minimumOrderTotalPolicy.isEventAvailable()).thenReturn(true);
        when(discounts.getTotalProfit()).thenReturn(NO_PROFIT);
        when(presentGive.getProfit()).thenReturn(NO_PROFIT);
    }

    private void setMock_최소주문금액미만() {
        when(minimumOrderTotalPolicy.isEventAvailable()).thenReturn(false);
    }

    private void verify_최소주문금액이상(int discountsProfit, int presentProfit, int presentsList) {
        verify(discounts, times(discountsProfit)).getTotalProfit();
        verify(presentGive, times(presentProfit)).getProfit();
        verify(presentGive, times(presentsList)).showPresents();
        verify(minimumOrderTotalPolicy, times(1)).isEventAvailable();
    }

    private void verify_최소주문금액미만() {
        verify(minimumOrderTotalPolicy, times(1)).isEventAvailable();
    }
}
