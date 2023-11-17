package christmas.domain.policy;

import christmas.domain.unit.Day;
import christmas.enums.Badge;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BadgePolicyTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @ParameterizedTest
    @CsvSource(value = {"SANTA,산타", "TREE,트리", "STAR,별", "BLANK,없음"})
    void testToString(String badgeString, String badgeName) {
        String expected = "<12월 이벤트 배지>" + LINE_SEPARATOR
                + badgeName + LINE_SEPARATOR;

        Badge badge = Badge.valueOf(badgeString);

        try(MockedStatic<Badge> badgeMockedStatic = mockStatic(Badge.class)) {
            TotalProfitPolicy totalProfitPolicy = mock(TotalProfitPolicy.class);
            Day day = mock(Day.class);

            when(Badge.getBadge(totalProfitPolicy)).thenReturn(badge);
            when(day.month()).thenReturn("12월");

            BadgePolicy badgePolicy = new BadgePolicy(day, totalProfitPolicy);

            assertThat(badgePolicy.toString()).isEqualTo(expected);

            verify(day, times(1)).month();
            badgeMockedStatic.verify(() -> Badge.getBadge(totalProfitPolicy), times(1));
        }
    }
}