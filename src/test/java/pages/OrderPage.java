package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;

import java.util.logging.Logger;

public class OrderPage {
    private static final Logger log = Logger.getLogger(OrderPage.class.getName());

    private final Page page;

    public OrderPage(Page page) {
        this.page = page;
    }

    public void NavigateToOrderTab() {
        log.info("Переход на вкладку «Заказы»");
        page.locator("aside.sidebar").getByText("Заказы").first().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        log.info("Вкладка «Заказы» загружена");
    }

    public void clickCreateOrder() {
        log.info("Нажатие «+ Добавить заказ»");
        page.locator("button").getByText("+ Добавить заказ").click();

        Locator modalOverlay = page.locator(".modal-overlay.open");
        modalOverlay.locator("div.modal-head").getByText("Новый заказ")
                .waitFor(new Locator.WaitForOptions().setTimeout(5000));
        log.info("Модальное окно «Новый заказ» открыто");
    }

    public void selectShopInModal(String shopName) {
        log.info("Выбор кальянной: " + shopName);
        Locator shopSelect = page.locator("div.modal-col select");
        shopSelect.selectOption(shopName);
        log.info("Кальянная выбрана: " + shopName);
    }

    public void fillPhone(String phoneNumber) {
        log.info("Заполнение телефона: " + phoneNumber);
        Locator phoneInput = page.locator("input[placeholder='Посл. 4 цифры или +7 999 123-45-67']");
        phoneInput.fill(phoneNumber);
    }

    public void checkRegisterUser() {
        Locator checkbox = page.locator("#add-order-register-user");
        if (!checkbox.isChecked()) {
            log.info("Чекбокс «Зарегистрировать» не отмечен — ставим");
            checkbox.check();
        } else {
            log.info("Чекбокс «Зарегистрировать» уже отмечен");
        }
    }

    public void fillComment(String commentText) {
        log.info("Заполнение комментария: " + commentText);
        Locator commentField = page.locator("xpath=//div[@class='modal-overlay open']//textarea");
        commentField.fill(commentText);
    }

    public void selectHookah() {
        log.info("Выбор кальяна (второй select, index=1)");
        page.locator(".modal-overlay.open").waitFor();
        page.locator(".modal-overlay.open select").nth(1)
                .selectOption(new SelectOption().setIndex(1));
        log.info("Кальян выбран");
    }

    public void clickAddHookah() {
        log.info("Нажатие «+» для добавления кальяна в заказ");
        page.locator(".modal-overlay.open").getByText("+").click();
    }

    public void saveOrder() {
        log.info("Нажатие «Создать заказ»");
        page.locator(".modal-overlay.open").getByText("Создать заказ").click();
        log.info("Заказ отправлен");
    }
}