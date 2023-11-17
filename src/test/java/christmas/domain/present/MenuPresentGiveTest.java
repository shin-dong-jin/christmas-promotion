package christmas.domain.present;

import christmas.domain.period.MenuPresentGivePeriod;
import christmas.domain.unit.Money;
import christmas.domain.unit.Presents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MenuPresentGiveTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final Money NO_PRESENT = Money.ZERO,
            PRESENT_PROFIT = new Money(-25_000L);
    private MenuPresentGivePeriod menuPresentGivePeriod;
    private Presents presents;
    private MenuPresentGive menuPresentGive;

    @BeforeEach
    void setUp() {
        menuPresentGivePeriod = mock(MenuPresentGivePeriod.class);
        presents = mock(Presents.class);
        menuPresentGive = new MenuPresentGive(menuPresentGivePeriod, presents);
    }

    @Test
    void getProfit_증정있음() {
        setMock_증정있음();

        assertThat(menuPresentGive.getProfit()).isEqualTo(PRESENT_PROFIT);

        verify_증정있음();
    }

    @Test
    void getProfit_증정없음() {
        setMock_증정없음();

        assertThat(menuPresentGive.getProfit()).isEqualTo(NO_PRESENT);

        verify_증정없음();
    }

    @Test
    void getProfit_이벤트기간종료() {
        setMock_이벤트기간종료();

        assertThat(menuPresentGive.getProfit()).isEqualTo(NO_PRESENT);

        verify_이벤트기간종료();
    }

    @Test
    void showPresents() {
        String expected = "샴페인 1개" + LINE_SEPARATOR;
        when(presents.toString()).thenReturn(expected);

        assertThat(menuPresentGive.showPresents()).isEqualTo(expected);
    }

    @Test
    void testToString_증정있음() {
        String expected = "증정 이벤트: -25,000원" + LINE_SEPARATOR;

        setMock_증정있음();

        assertThat(menuPresentGive.toString()).isEqualTo(expected);

        verify_증정있음();
    }

    @Test
    void testToString_증정없음() {
        setMock_증정없음();

        assertThat(menuPresentGive.toString()).isEmpty();

        verify_증정없음();
    }

    @Test
    void testToString_이벤트기간종료() {
        setMock_이벤트기간종료();

        assertThat(menuPresentGive.toString()).isEmpty();

        verify_이벤트기간종료();
    }

    private void setMock_증정있음() {
        when(menuPresentGivePeriod.isEventPeriod()).thenReturn(true);
        when(presents.isEmpty()).thenReturn(false);
        when(presents.getProfit()).thenReturn(PRESENT_PROFIT);
    }

    private void setMock_증정없음() {
        when(menuPresentGivePeriod.isEventPeriod()).thenReturn(true);
        when(presents.isEmpty()).thenReturn(true);
    }

    private void setMock_이벤트기간종료() {
        when(menuPresentGivePeriod.isEventPeriod()).thenReturn(false);
        when(presents.isEmpty()).thenReturn(false);
    }

    private void verify_증정있음() {
        verify(menuPresentGivePeriod, times(1)).isEventPeriod();
        verify(presents, times(1)).isEmpty();
        verify(presents, times(1)).getProfit();
    }

    private void verify_증정없음() {
        verify(menuPresentGivePeriod, times(1)).isEventPeriod();
        verify(presents, times(1)).isEmpty();
        verify(presents, times(0)).getProfit();
    }

    private void verify_이벤트기간종료() {
        verify(menuPresentGivePeriod, times(1)).isEventPeriod();
        verify(presents, times(0)).isEmpty();
        verify(presents, times(0)).getProfit();
    }
}