package ru.hookahorder.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ru.hookahorder.base.BaseTest;
import ru.hookahorder.base.PageFactory;
import ru.hookahorder.data.LoungeData;
import ru.hookahorder.data.MenuData;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Order(2)
@Feature("Управление меню")
public class CreateMenuTest extends BaseTest {

    @Test
    @DisplayName("2. Создание меню для кальянной")
    void createMenuTest() {
        PageFactory factory = new PageFactory(page);

        LoungeData lounge = new LoungeData(UNIQUE_SUFFIX);
        MenuData menu = new MenuData(UNIQUE_SUFFIX);

        factory.hookahEditPage().navigateToSubscriptions(lounge.shopName);

        factory.menuPage()
                .navigateToMenuTab()
                .selectShop(lounge.shopName)
                .createCategory(menu.categoryName)
                .createPosition(menu.positionName, menu.categoryName, menu.price, menu.portion);

        assertTrue(factory.menuPage().isPositionCreated(menu.positionName.trim()),
                "Позиция меню '" + menu.positionName.trim() + "' не была создана");
    }
}