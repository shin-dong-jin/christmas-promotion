package christmas.domain.policy;

import christmas.domain.total.OrderTotalBeforeDiscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MinimumOrderTotalPolicyTest {
    private OrderTotalBeforeDiscount orderTotalBeforeDiscount;
    private MinimumOrderTotalPolicy minimumOrderTotalPolicy;

    @BeforeEach
    void setUp() {
        orderTotalBeforeDiscount = mock(OrderTotalBeforeDiscount.class);
        minimumOrderTotalPolicy = new MinimumOrderTotalPolicy(orderTotalBeforeDiscount);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isEventAvailable(boolean expected) {
        when(orderTotalBeforeDiscount.isEventAvailable(any())).thenReturn(expected);

        assertThat(minimumOrderTotalPolicy.isEventAvailable()).isEqualTo(expected);

        verify(orderTotalBeforeDiscount, times(1)).isEventAvailable(any());
    }
}