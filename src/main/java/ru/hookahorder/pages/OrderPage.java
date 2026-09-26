package ru.hookahorder.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderPage {
    private final Page page;

    public OrderPage(Page page) {
        this.page = page;
    }

    @Step("Переход на вкладку «Заказы»")
    public OrderPage navigateToOrderTab() {
        log.info("Переход на вкладку «Заказы»");
        page.locator("aside.sidebar").getByText("Заказы").first().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        log.info("Вкладка «Заказы» загружена");
        return this;
    }

    @Step("Нажатие «+ Добавить заказ»")
    public OrderPage clickCreateOrder() {
        log.info("Нажатие «+ Добавить заказ»");
        page.locator("button").getByText("+ Добавить заказ").click();

        Locator modalOverlay = page.locator(".modal-overlay.open");
        modalOverlay.locator("div.modal-head").getByText("Новый заказ")
                .waitFor(new Locator.WaitForOptions().setTimeout(5000));
        log.info("Модальное окно «Новый заказ» открыто");
        return this;
    }

    @Step("Выбор кальянной в модальном окне: '{shopName}'")
    public OrderPage selectShopInModal(String shopName) {
        log.info("Выбор кальянной: {}", shopName);
        Locator shopSelect = page.locator("div.modal-col select");
        shopSelect.selectOption(shopName);
        log.info("Кальянная выбрана: {}", shopName);
        return this;
    }

    @Step("Заполнение телефона клиента: '{phoneNumber}'")
    public OrderPage fillPhone(String phoneNumber) {
        log.info("Заполнение телефона: {}", phoneNumber);
        Locator phoneInput = page.locator("input[placeholder='Посл. 4 цифры или +7 999 123-45-67']");
        phoneInput.fill(phoneNumber);
        return this;
    }

    @Step("Проверка и установка чекбокса «Зарегистрировать пользователя»")
    public OrderPage checkRegisterUser() {
        Locator checkbox = page.locator("#add-order-register-user");
        if (!checkbox.isChecked()) {
            log.info("Чекбокс «Зарегистрировать» не отмечен — ставим");
            checkbox.check();
        } else {
            log.info("Чекбокс «Зарегистрировать» уже отмечен");
        }
        return this;
    }

    @Step("Заполнение комментария к заказу: '{commentText}'")
    public OrderPage fillComment(String commentText) {
        log.info("Заполнение комментария: {}", commentText);
        Locator commentField = page.locator(".modal-overlay.open textarea");
        commentField.fill(commentText);
        return this;
    }

    @Step("Выбор кальяна в списке")
    public OrderPage selectHookah() {
        log.info("Выбор кальяна (второй select, index=1)");
        page.locator(".modal-overlay.open").waitFor();
        page.locator(".modal-overlay.open select").nth(1)
                .selectOption(new SelectOption().setIndex(1));
        log.info("Кальян выбран");
        return this;
    }

    @Step("Нажатие «+» для добавления кальяна в заказ")
    public OrderPage clickAddHookah() {
        log.info("Нажатие «+» для добавления кальяна в заказ");
        page.locator(".modal-overlay.open").getByText("+").click();
        return this;
    }

    @Step("Сохранение и создание заказа")
    public OrderPage saveOrder() {
        log.info("Нажатие «Создать заказ»");
        Locator modal = page.locator(".modal-overlay.open");
        modal.getByText("Создать заказ").click();
        log.info("Заказ отправлен");

        modal.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(5000));
        log.info("Модальное окно закрылось");
        return this;
    }

    @Step("Проверка: заказ с позицией кальяна '{hookahName}' отображается в списке заказов")
    public boolean isOrderCreatedByHookahName(String hookahName) {
        log.info("Проверка появления заказа по имени кальяна: {}", hookahName);
        if (hookahName == null || hookahName.isBlank()) {
            log.error("Пустое имя кальяна для проверки");
            return false;
        }

        String searchText = hookahName.trim();

        Locator targetRow = page.locator("div.content table tbody tr")
                .filter(new Locator.FilterOptions().setHasText(searchText))
                .first();

        try {
            targetRow.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.ATTACHED)
                    .setTimeout(10_000));
            log.info("Заказ с позицией кальяна '{}' найден в таблице.", searchText);
            return true;
        } catch (Exception e) {
            log.error("Заказ с позицией кальяна '{}' не найден за 10 секунд.", searchText);
            return false;
        }
    }
}