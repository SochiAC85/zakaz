package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class HookahEditPage extends BasePage {

    private static final String NAV_ITEM_LOUNGES = ".nav-item";
    private static final String LOUNGE_CARD = ".lounge-card";
    private static final String MODAL_TITLE = "div[class='modal-overlay open'] h2";
    private static final String SCROLL_CONTAINER = "body > div:nth-child(1) > div:nth-child(5) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2)";
    private static final String OWNER_PHONE_INPUT = "input[placeholder='+79991234567']";
    private static final String SAVE_BUTTON = "div[class='modal-overlay open'] button[class='btn-save']";
    private static final String SIDEBAR = "aside[class='sidebar']";

    public HookahEditPage(Page page) {
        super(page);
    }

    public void navigateToLoungeList() {
        log.info("Переход к разделу «Кальянные»");
        page.locator(NAV_ITEM_LOUNGES).getByText("Кальянные").click();
        waitForLoad();
        log.info("Раздел «Кальянные» загружен");
    }

    public void openEditModal(String shopName) {
        log.info("Открытие формы редактирования для: " + shopName);
        Locator card = page.locator(LOUNGE_CARD)
                .filter(new Locator.FilterOptions().setHasText(shopName));

        if (card.count() == 0) {
            log.severe("Не найдена кальянная с названием: " + shopName);
            throw new RuntimeException("Не найдена кальянная с названием: " + shopName);
        }

        log.info("Карточка найдена, клик по кнопке редактирования");
        card.locator("button").first().click();
        waitForVisible(MODAL_TITLE);
        log.info("Модальное окно редактирования открыто");
    }

    public void assignOwner(String phone) {
        log.info("Назначение владельца: " + phone);
        scrollIntoView(SCROLL_CONTAINER);
        fill(OWNER_PHONE_INPUT, phone);
        page.getByText("Назначить владельца").click();
        log.info("Владелец назначен: " + phone);
    }

    public void save() {
        log.info("Сохранение изменений");
        click(SAVE_BUTTON);
        waitForLoad();

        Locator closeBtn = page.locator("div[class='modal-overlay open'] button[class='modal-close']");
        if (closeBtn.isVisible()) {
            log.info("Модальное окно всё ещё открыто — закрываем");
            closeBtn.click();
        }
        log.info("Сохранение завершено, модальное окно закрыто");
    }

    public void navigateToSubscriptions(String shopName) {
        log.info("Переход в раздел «Подписки»");
        page.locator(SIDEBAR)
                .locator("div")
                .getByText("Подписки")
                .click();
        waitForLoad();
        log.info("Раздел «Подписки» загружен");

        Locator row = page.locator("table tbody tr")
                .filter(new Locator.FilterOptions().setHasText(shopName));

        log.info("Поиск строки с кальянной: " + shopName);
        row.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(15_000));
        log.info("Строка найдена и видима");

        Locator menuHeader = page.locator("table thead th:has-text('МЕНЮ')");

        int menuColIndex = (int) menuHeader.first().evaluateHandle(
                "el => Array.from(el.parentNode.children).indexOf(el)"
        ).jsonValue();

        Locator menuCell = row.locator("td").nth(menuColIndex);

        if (menuCell.getByText("Выключено").first().isVisible()) {
            log.info("Подписка выключена — включаем");
            menuCell.getByText("Выключено").first().click();
            menuCell.getByText("Включено").waitFor(
                    new Locator.WaitForOptions().setTimeout(10_000));
            log.info("Подписка включена");
        } else {
            log.info("Подписка уже включена — пропускаем");
        }

        navigateToMenu();
    }

    private void navigateToMenu() {
        log.info("Переход в раздел «Меню»");
        page.locator(SIDEBAR)
                .locator("div")
                .getByText("Меню")
                .click();
        waitForLoad();
        log.info("Раздел «Меню» загружен");
    }
}