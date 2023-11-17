package christmas.enums;

import org.junit.jupiter.api.Test;

import java.util.List;

import static christmas.enums.MenuCategory.*;
import static org.assertj.core.api.Assertions.assertThat;

class MenuCategoryTest {
    private static final MenuCategory[] MENU_CATEGORIES = {APPETIZER, MAIN, DESSERT, DRINK};
    private static final List<String> MENU_CATEGORY_STRINGS = List.of("APPETIZER", "MAIN", "DESSERT", "DRINK");

    @Test
    void values() {
        assertThat(MenuCategory.values()).isEqualTo(MENU_CATEGORIES);
    }

    @Test
    void valueOf() {
        for (int i = 0; i < MENU_CATEGORY_STRINGS.size(); i++) {
            assertThat(MenuCategory.valueOf(MENU_CATEGORY_STRINGS.get(i)))
                    .isEqualTo(MENU_CATEGORIES[i]);
        }
    }
}