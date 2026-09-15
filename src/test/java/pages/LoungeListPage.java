package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.logging.Logger;

public class LoungeListPage {
    private static final Logger log = Logger.getLogger(LoungeListPage.class.getName());

    private final Page page;

    public LoungeListPage(Page page) {
        this.page = page;
    }

    public void navigateToLoungeList() {
        log.info("Переход к разделу «Кальянные»");
        page.locator(".nav-item").getByText("Кальянные").click();
        page.waitForLoadState();
        log.info("Раздел «Кальянные» загружен");
    }

    public void clickAddLounge() {
        log.info("Открытие формы добавления кальянной");
        page.locator("div[class='lounge-layout'] button[class='btn-primary']")
                .first().click();
    }

    public Locator getLoungeCardByText(String shopName) {
        return page.locator(".lounge-card").getByText(shopName);
    }

    public boolean isLoungeCardVisible(String shopName) {
        var card = getLoungeCardByText(shopName);
        card.waitFor();
        boolean visible = card.isVisible();
        log.info("Карточка кальянной '" + shopName + "' видима: " + visible);
        return visible;
    }
}