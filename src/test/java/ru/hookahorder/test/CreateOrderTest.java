package ru.hookahorder.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ru.hookahorder.base.BaseTest;
import ru.hookahorder.base.PageFactory;
import ru.hookahorder.data.LoungeData;
import ru.hookahorder.data.OrderData;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Order(3)
@Feature("Работа с заказами")
public class CreateOrderTest extends BaseTest {

    @Test
    @DisplayName("3. Создание заказа")
    void createOrderTest() {

        PageFactory factory = new PageFactory(page);
        LoungeData lounge = new LoungeData(UNIQUE_SUFFIX);
        OrderData order = new OrderData(UNIQUE_SUFFIX);

        factory.orderPage()
                .navigateToOrderTab()
                .clickCreateOrder()
                .selectShopInModal(lounge.shopName)
                .fillPhone(order.orderPhone)
                .checkRegisterUser()
                .selectHookah()
                .clickAddHookah()
                .fillComment(order.commentText)
                .saveOrder();

        String expectedHookahName = "Тест кальян " + UNIQUE_SUFFIX;

        boolean orderCreated = factory.orderPage().isOrderCreatedByHookahName(expectedHookahName);

        assertTrue(orderCreated,
                "Заказ с позицией кальяна '" + expectedHookahName + "' не появился в списке заказов!");
    }
}