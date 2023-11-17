package christmas.controller;

import christmas.domain.period.*;
import christmas.domain.present.MenuPresentGive;
import christmas.domain.discount.*;
import christmas.domain.unit.Day;
import christmas.domain.total.ExpectedTotalAfterDiscount;
import christmas.domain.total.OrderTotalBeforeDiscount;
import christmas.domain.policy.TotalProfitPolicy;
import christmas.domain.unit.OrdersMenuCount;
import christmas.domain.policy.*;
import christmas.domain.unit.Presents;
import christmas.enums.Present;

import java.util.List;

public class DomainController {

    public OrderTotalBeforeDiscount getOrderTotalBeforeDiscount(OrdersMenuCount ordersMenuCount) {
        return new OrderTotalBeforeDiscount(ordersMenuCount);
    }

    public DiscountPresentPolicy getProfitPresentPolicy(Day day, OrdersMenuCount ordersMenuCount, OrderTotalBeforeDiscount orderTotalBeforeDiscount) {
        MenuPresentGive menuPresentGive = generateMenuPresent(day, orderTotalBeforeDiscount);

        ChristmasDiscount christmasDiscount = generateChristmasDiscount(day);
        WeekdayDiscount weekdayDiscount = generateWeekdayDiscount(day, ordersMenuCount);
        WeekendDiscount weekendDiscount = generateWeekendDiscount(day, ordersMenuCount);
        SpecialDiscount specialDiscount = generateSpecialDiscount(day);

        List<Discount> profitPoliciesList = List.of(christmasDiscount, weekdayDiscount, weekendDiscount, specialDiscount);
        Discounts profitPolicies = new Discounts(List.copyOf(profitPoliciesList));

        MinimumOrderTotalPolicy minimumOrderTotalPolicy = new MinimumOrderTotalPolicy(orderTotalBeforeDiscount);

        return new DiscountPresentPolicy(menuPresentGive, profitPolicies, minimumOrderTotalPolicy);
    }

    public TotalProfitPolicy getTotalProfitPolicy(DiscountPresentPolicy discountPresentPolicy) {
        return new TotalProfitPolicy(discountPresentPolicy);
    }

    public ExpectedTotalAfterDiscount getExpectedTotalAfterDiscount(OrderTotalBeforeDiscount orderTotalBeforeDiscount, TotalProfitPolicy totalProfitPolicy) {
        return new ExpectedTotalAfterDiscount(orderTotalBeforeDiscount, totalProfitPolicy);
    }

    public BadgePolicy getBadgePolicy(Day day, TotalProfitPolicy totalProfitPolicy) {
        return new BadgePolicy(day, totalProfitPolicy);
    }

    private MenuPresentGive generateMenuPresent(Day day, OrderTotalBeforeDiscount orderTotalBeforeDiscount) {
        MenuPresentGivePeriod presentGivePeriod = new MenuPresentGivePeriod(day);
        Presents presents = new Presents(Present.getPresents(orderTotalBeforeDiscount));
        return new MenuPresentGive(presentGivePeriod, presents);
    }

    private ChristmasDiscount generateChristmasDiscount(Day day) {
        ChristmasDiscountPeriod christmasDiscountPeriod = new ChristmasDiscountPeriod(day);
        return new ChristmasDiscount(christmasDiscountPeriod);
    }

    private WeekdayDiscount generateWeekdayDiscount(Day day, OrdersMenuCount ordersMenuCount) {
        WeekdayDiscountPeriod weekdayDiscountPeriod = new WeekdayDiscountPeriod(day);
        return new WeekdayDiscount(weekdayDiscountPeriod, ordersMenuCount);
    }

    private WeekendDiscount generateWeekendDiscount(Day day, OrdersMenuCount ordersMenuCount) {
        WeekendDiscountPeriod weekendDiscountPeriod = new WeekendDiscountPeriod(day);
        return new WeekendDiscount(weekendDiscountPeriod, ordersMenuCount);
    }

    private SpecialDiscount generateSpecialDiscount(Day day) {
        SpecialDiscountPeriod specialDiscountPeriod = new SpecialDiscountPeriod(day);
        return new SpecialDiscount(specialDiscountPeriod);
    }
}
