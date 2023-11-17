package christmas.utils;

import christmas.enums.Menu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParserTest {

    @ParameterizedTest
    @CsvSource(value = {"-1,-1", "0,0", "1,1"})
    void parseInt(String number, int expected) {
        assertThat(Parser.parseInt(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1,0", "0,1", "1,-1"})
    void parseInt_다른값(String number, int expected) {
        assertThat(Parser.parseInt(number)).isNotEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1,-1", "0,0", "1,1"})
    void parseLong(String number, long expected) {
        assertThat(Parser.parseLong(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1,0", "0,1", "1,-1"})
    void parseLong_다른값(String number, long expected) {
        assertThat(Parser.parseLong(number)).isNotEqualTo(expected);
    }

    @Test
    void outerSplit() {
        String outer = "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1";
        List<String> expected = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1");

        assertThat(Parser.outerSplit(outer)).isEqualTo(expected);
    }

    @Test
    void outerSplit_잘못입력() {
        String outer = "티본스테이크-1x,바비큐립,쵸코케이크-2,제로콜라-,";
        List<String> expected = List.of("티본스테이크-1x", "바비큐립", "쵸코케이크-2", "제로콜라-", "");

        assertThat(Parser.outerSplit(outer)).isEqualTo(expected);
    }

    @Test
    void innersSplit() {
        List<String> inners = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1");
        Map<Menu, Long> expected = Map.of(
                Menu.T_BONE_STEAK, 1L,
                Menu.BARBECUE_LIBS, 1L,
                Menu.CHOCOLATE_CAKE, 2L,
                Menu.ZERO_COKE, 1L
        );

        assertThat(Parser.innersSplit(inners)).isEqualTo(expected);
    }

    @Test
    void innersSplit_잘못입력_빈문자열() {
        List<String> inners = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1", "");

        assertThatThrownBy(() -> Parser.innersSplit(inners))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void innersSplit_잘못입력_구분자없음() {
        List<String> inners = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1", "아이스크림3");

        assertThatThrownBy(() -> Parser.innersSplit(inners))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void innersSplit_잘못입력_개수없음() {
        List<String> inners = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1", "아이스크림-");

        assertThatThrownBy(() -> Parser.innersSplit(inners))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void innersSplit_잘못입력_구분자개수없음() {
        List<String> inners = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1", "아이스크림");

        assertThatThrownBy(() -> Parser.innersSplit(inners))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void innersSplit_잘못입력_없는메뉴() {
        List<String> inners = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1", "해샨물파스타-3");

        assertThatThrownBy(() -> Parser.innersSplit(inners))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void innersSplit_잘못입력_숫자아닌개수() {
        List<String> inners = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1", "타파스-3t");

        assertThatThrownBy(() -> Parser.innersSplit(inners))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void innersSplit_잘못입력_구분자중복() {
        List<String> inners = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1", "타파스-----1");

        assertThatThrownBy(() -> Parser.innersSplit(inners))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void innersSplit_잘못입력_구분자틀림() {
        List<String> inners = List.of("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1", "타파스=1");

        assertThatThrownBy(() -> Parser.innersSplit(inners))
                .isInstanceOf(IllegalArgumentException.class);
    }
}