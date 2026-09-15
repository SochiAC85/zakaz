package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.util.logging.Logger;

public class MenuPage {
    private static final Logger log = Logger.getLogger(MenuPage.class.getName());

    private final Page page;

    public MenuPage(Page page) {
        this.page = page;
    }

    public void navigateToMenuTab() {
        log.info("Переход на вкладку «Меню»");
        page.locator("aside.sidebar").getByText("Меню").first().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        log.info("Вкладка «Меню» загружена");
    }

    public void selectShop(String shopName) {
        log.info("Выбор кальянной в выпадающем списке: " + shopName);
        Locator shopSelect = page.locator("div[class='view active'] div div select");
        shopSelect.selectOption(shopName);
        log.info("Кальянная выбрана: " + shopName);
    }

    public void createCategory(String categoryName) {
        log.info("Создание категории: " + categoryName);
        page.locator("input[placeholder='Новая категория']").fill(categoryName);
        Locator container = page.locator("#app > .main > .content > .view.active");
        container.getByText("+ Добавить").click();
        log.info("Категория добавлена: " + categoryName);
    }

    public void createPosition(String positionName, String categoryName, int price, String portion) {
        log.info("Создание позиции: name=" + positionName + ", category=" + categoryName
                + ", price=" + price + ", portion=" + portion);

        Locator addBtn = page.locator("button:has-text('+ Позиция')")
                .filter(new Locator.FilterOptions().setVisible(true));

        addBtn.first().click();
        log.info("Клик по кнопке «+ Позиция» выполнен");

        Locator formRow = page.locator(".table-wrap > div:has(button:has-text('Сохранить'))");
        formRow.waitFor(new Locator.WaitForOptions().setTimeout(10_000));
        log.info("Форма позиции открыта");

        formRow.locator("input[placeholder='Название позиции']").fill(positionName);
        log.info("Название позиции заполнено: " + positionName);

        Locator categorySelect = formRow.locator("div:has-text('Категория') + select");
        categorySelect.selectOption(categoryName);
        log.info("Категория выбрана: " + categoryName);

        formRow.locator("input[type='number']").fill(String.valueOf(price));
        log.info("Цена указана: " + price);

        formRow.locator("input[placeholder='напр. 50 мл']").fill(portion);
        log.info("Порция указана: " + portion);

        formRow.locator("button:has-text('Сохранить')").first().click();
        log.info("Клик «Сохранить» для позиции выполнен");
    }

    public boolean isPositionCreated(String positionName) {
        log.info("Проверка: позиция '" + positionName + "' должна появиться в таблице");
        Locator position = page.locator("table tbody tr")
                .filter(new Locator.FilterOptions().setHasText(positionName));

        try {
            position.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            boolean visible = position.isVisible();
            log.info("Позиция '" + positionName + "' видна в таблице: " + visible);
            return visible;
        } catch (Exception e) {
            log.warning("Позиция '" + positionName + "' не найдена за 5 секунд");
            return false;
        }
    }
}