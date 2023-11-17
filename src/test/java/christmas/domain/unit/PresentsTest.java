package christmas.domain.unit;

import christmas.enums.Present;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PresentsTest {
    private static String LINE_SEPARATOR = System.lineSeparator();
    private static final List<Present> PRESENTS = List.of(
            Present.CHAMPAGNE
    ),
            PRESENTS_DUPLICATE = List.of(
                    Present.CHAMPAGNE,
                    Present.CHAMPAGNE
            ),
            PRESENTS_EMPTY = List.of();
    private Presents presents;

    @Test
    void constructor_증정있음() {
        assertThatCode(() -> new Presents(PRESENTS)).doesNotThrowAnyException();
    }

    @Test
    void constructor_증정없음() {
        assertThatCode(() -> new Presents(PRESENTS_EMPTY)).doesNotThrowAnyException();
    }

    @Test
    void constructor_중복증정() {
        assertThatThrownBy(() -> new Presents(PRESENTS_DUPLICATE))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void isEmpty_참() {
        presents = new Presents(PRESENTS_EMPTY);

        assertThat(presents.isEmpty()).isTrue();
    }

    @Test
    void isEmpty_거짓() {
        presents = new Presents(PRESENTS);

        assertThat(presents.isEmpty()).isFalse();
    }

    @Test
    void getProfit() {
        Money expected = new Money(-25_000L);
        presents = new Presents(PRESENTS);

        assertThat(presents.getProfit()).isEqualTo(expected);
    }

    @Test
    void testToString_증정없음() {
        presents = new Presents(PRESENTS_EMPTY);

        assertThat(presents.toString()).isEmpty();
    }

    @Test
    void testToString_증정있음() {
        String expected = "샴페인 1개" + LINE_SEPARATOR;
        presents = new Presents(PRESENTS);

        assertThat(presents.toString()).isEqualTo(expected);
    }
}