package christmas.controller;

import christmas.domain.discount.*;
import christmas.domain.period.*;
import christmas.domain.policy.DiscountPresentPolicy;
import christmas.domain.policy.MinimumOrderTotalPolicy;
import christmas.domain.policy.TotalProfitPolicy;
import christmas.domain.present.MenuPresentGive;
import christmas.domain.present.PresentGive;
import christmas.domain.total.OrderTotalBeforeDiscount;
import christmas.domain.unit.Day;
import christmas.domain.unit.OrdersMenuCount;
import christmas.domain.unit.Presents;
import christmas.enums.Menu;
import christmas.enums.Present;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DomainControllerTest {
    private static final String ORDER_TOTAL_STRING = "142,000원",
            TOTAL_PROFIT_STRING = "-31,246원",
            EXPECTED_TOTAL_STRING = "135,754원",
            BADGE_STRING = "산타";
    private static final List<String> PRESENT_DETAILS = List.of(
            "샴페인 1개"
    ),
            PROFIT_DETAILS = List.of(
                    "크리스마스 디데이 할인: -1,200원",
                    "평일 할인: -4,046원",
                    "특별 할인: -1,000원",
                    "증정 이벤트: -25,000원"
            );
    private static final Day DAY = new Day(3);
    private static final OrdersMenuCount ORDERS_MENU_COUNT = new OrdersMenuCount(Map.of(
            Menu.T_BONE_STEAK, 1L,
            Menu.BARBECUE_LIBS, 1L,
            Menu.CHOCOLATE_CAKE, 2L,
            Menu.ZERO_COKE, 1L
    ));
    private static final OrderTotalBeforeDiscount ORDER_TOTAL_BEFORE_DISCOUNT = new OrderTotalBeforeDiscount(ORDERS_MENU_COUNT);
    private static final PresentGive PRESENT_GIVE = new MenuPresentGive(new MenuPresentGivePeriod(DAY), new Presents(Present.getPresents(ORDER_TOTAL_BEFORE_DISCOUNT)));
    private static final Discounts DISCOUNTS = new Discounts(List.of(
            new ChristmasDiscount(new ChristmasDiscountPeriod(DAY)),
            new WeekdayDiscount(new WeekdayDiscountPeriod(DAY), ORDERS_MENU_COUNT),
            new WeekendDiscount(new WeekendDiscountPeriod(DAY), ORDERS_MENU_COUNT),
            new SpecialDiscount(new SpecialDiscountPeriod(DAY))
    ));
    private static final MinimumOrderTotalPolicy MINIMUM_ORDER_TOTAL_POLICY = new MinimumOrderTotalPolicy(ORDER_TOTAL_BEFORE_DISCOUNT);
    private static final DiscountPresentPolicy DISCOUNT_PRESENT_POLICY = new DiscountPresentPolicy(PRESENT_GIVE, DISCOUNTS, MINIMUM_ORDER_TOTAL_POLICY);
    private static final TotalProfitPolicy TOTAL_PROFIT_POLICY = new TotalProfitPolicy(DISCOUNT_PRESENT_POLICY);
    private DomainController domainController;

    @BeforeEach
    void setUp() {
        domainController = new DomainController();
    }

    @Test
    void getOrderTotalBeforeDiscount() {
        assertThat(domainController.getOrderTotalBeforeDiscount(ORDERS_MENU_COUNT).toString())
                .contains(ORDER_TOTAL_STRING);
    }

    @Test
    void getProfitPresentPolicy_presentDetails() {
        assertThat(domainController.getProfitPresentPolicy(DAY, ORDERS_MENU_COUNT, ORDER_TOTAL_BEFORE_DISCOUNT).presentsDetails())
                .contains(PRESENT_DETAILS);
    }

    @Test
    void getProfitPresentPolicy_profitDetails() {
        assertThat(domainController.getProfitPresentPolicy(DAY, ORDERS_MENU_COUNT, ORDER_TOTAL_BEFORE_DISCOUNT).profitDetails())
                .contains(PROFIT_DETAILS);
    }

    @Test
    void getTotalProfitPolicy() {
        assertThat(domainController.getTotalProfitPolicy(DISCOUNT_PRESENT_POLICY).toString())
                .contains(TOTAL_PROFIT_STRING);
    }

    @Test
    void getExpectedTotalAfterDiscount() {
        assertThat(domainController.getExpectedTotalAfterDiscount(ORDER_TOTAL_BEFORE_DISCOUNT, TOTAL_PROFIT_POLICY).toString())
                .contains(EXPECTED_TOTAL_STRING);
    }

    @Test
    void getBadgePolicy() {
        assertThat(domainController.getBadgePolicy(DAY, TOTAL_PROFIT_POLICY).toString())
                .contains(BADGE_STRING);
    }
}