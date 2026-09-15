package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HookahEditPage;
import pages.MenuPage;
import pages.OrderPage;
import pages.PageFactory;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FullTest extends BaseTest {

    private static final Logger log = Logger.getLogger(FullTest.class.getName());

    @Test
    @DisplayName("E2E: создание кальянной → меню → заказ")
    void fullFlowTest() {
        String uniqueId = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String shopName    = "SmokeTest Lounge " + uniqueId;
        String description = "Тестовая Кальянная для автотеста " + uniqueId;
        String phone       = "+7 (999) 123-45-67";
        String address     = "ул. Тверская 10";
        String ownerPhone  = "+79990000000";
        String categoryName  = "Тест меню " + uniqueId;
        String positionName  = "Тест кальян " + uniqueId;
        int price            = 1000;
        String portion       = "50";
        String orderPhone    = "+79991112233";
        String commentText   = "Тест комментария " + uniqueId;

        var factory = new PageFactory(page);
        var listPage = factory.loungeListPage();
        var formPage = factory.loungeFormPage();
        var hookahEditPage = new HookahEditPage(page);
        var menuPage = new MenuPage(page);
        var orderPage = new OrderPage(page);

        log.info("=== Запуск E2E теста, ID: " + uniqueId + " ===");

        log.info("=== Шаг 1: Создание кальянной '" + shopName + "' ===");

        listPage.navigateToLoungeList();
        page.waitForLoadState();
        boolean exists = page.locator(".lounge-card").getByText(shopName).isVisible();

        if (!exists) {
            log.info("Кальянная не найдена — создаём новую");
            listPage.clickAddLounge();
            formPage.fillLoungeData(shopName, description, phone, address);
            formPage.submitLounge();
            page.waitForLoadState();
            assertTrue(listPage.isLoungeCardVisible(shopName),
                    "Кальянная не появилась в списке!");
            log.info("Шаг 1 завершён: кальянная создана — " + shopName);
        } else {
            log.info("Кальянная уже существует — пропускаем создание");
        }

        log.info("=== Шаг 2: Назначение владельца и подписка ===");

        hookahEditPage.navigateToLoungeList();
        hookahEditPage.openEditModal(shopName);
        hookahEditPage.assignOwner(ownerPhone);
        hookahEditPage.save();
        log.info("Шаг 2 завершён: владелец назначен, подписка активирована");

        log.info("=== Шаг 3: Создание меню ===");

        hookahEditPage.navigateToSubscriptions(shopName);
        menuPage.navigateToMenuTab();
        menuPage.selectShop(shopName);
        menuPage.createCategory(categoryName);
        menuPage.createPosition(positionName, categoryName, price, portion);

        assertTrue(menuPage.isPositionCreated(positionName),
                "Позиция меню '" + positionName + "' не была создана");
        log.info("Шаг 3 завершён: меню создано — категория='" + categoryName
                + "', позиция='" + positionName + "'");

        log.info("=== Шаг 4: Создание заказа ===");

        orderPage.NavigateToOrderTab();
        orderPage.clickCreateOrder();
        orderPage.selectShopInModal(shopName);
        orderPage.fillPhone(orderPhone);
        orderPage.checkRegisterUser();
        orderPage.fillComment(commentText);
        orderPage.selectHookah();
        orderPage.clickAddHookah();
        orderPage.saveOrder();
        log.info("Шаг 4 завершён: заказ создан");

        log.info("=== E2E ТЕСТ УСПЕШНО ПРОЙДЕН (ID: " + uniqueId + ") ===");
    }
}