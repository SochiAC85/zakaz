package ru.hookahorder.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuPage {
    private final Page page;

    public MenuPage(Page page) {
        this.page = page;
    }

    @Step("Переход на вкладку «Меню»")
    public MenuPage navigateToMenuTab() {
        log.info("Переход на вкладку «Меню»");
        page.locator("aside.sidebar").getByText("Меню").first().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        log.info("Вкладка «Меню» загружена");
        return this;
    }

    @Step("Выбор кальянной в выпадающем списке: '{shopName}'")
    public MenuPage selectShop(String shopName) {
        log.info("Выбор кальянной в выпадающем списке: {}", shopName);
        Locator shopSelect = page.locator("div[class='view active'] div div select");
        shopSelect.selectOption(shopName);
        log.info("Кальянная выбрана: {}", shopName);
        return this;
    }

    @Step("Создание категории: '{categoryName}'")
    public MenuPage createCategory(String categoryName) {
        log.info("Создание категории: {}", categoryName);
        page.locator("input[placeholder='Новая категория']").fill(categoryName);
        Locator container = page.locator("#app > .main > .content > .view.active");
        container.getByText("+ Добавить").click();
        log.info("Категория добавлена: {}", categoryName);
        return this;
    }

    @Step("Создание позиции: название='{positionName}', категория='{categoryName}', цена={price}, порция='{portion}'")
    public MenuPage createPosition(String positionName, String categoryName, int price, String portion) {
        log.info("Создание позиции: name={}, category={}, price={}, portion={}",
                positionName, categoryName, price, portion);

        Locator addBtn = page.locator("button:has-text('+ Позиция')")
                .filter(new Locator.FilterOptions().setVisible(true));

        addBtn.first().click();
        log.info("Клик по кнопке «+ Позиция» выполнен");

        Locator formRow = page.locator(".table-wrap > div:has(button:has-text('Сохранить'))");
        formRow.waitFor(new Locator.WaitForOptions().setTimeout(10_000));
        log.info("Форма позиции открыта");

        formRow.locator("input[placeholder='Название позиции']").fill(positionName);
        log.info("Название позиции заполнено: {}", positionName);

        Locator categorySelect = formRow.locator("div:has-text('Категория') + select");
        categorySelect.selectOption(categoryName);
        log.info("Категория выбрана: {}", categoryName);

        formRow.locator("input[type='number']").fill(String.valueOf(price));
        log.info("Цена указана: {}", price);

        formRow.locator("input[placeholder='напр. 50 мл']").fill(portion);
        log.info("Порция указана: {}", portion);

        formRow.locator("button:has-text('Сохранить')").first().click();
        log.info("Клик «Сохранить» для позиции выполнен");

        formRow.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(5000));
        log.info("Форма добавления позиции успешно закрылась");

        return this;
    }

    @Step("Проверка: позиция '{positionName}' отображается в таблице")
    public boolean isPositionCreated(String positionName) {
        log.info("Проверка: позиция '{}' должна появиться в таблице", positionName);
        String searchText = positionName.trim();

        Locator targetRow = page.locator(".view.active table tbody tr")
                .filter(new Locator.FilterOptions().setHasText(searchText))
                .first();

        try {
            targetRow.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                    .setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED)
                    .setTimeout(10_000));

            log.info("Позиция меню '{}' успешно найдена в таблице.", searchText);
            return true;
        } catch (Exception e) {
            log.error("Позиция меню с текстом '{}' не найдена в DOM за 10 секунд.", searchText, e);
            return false;
        }
    }
}