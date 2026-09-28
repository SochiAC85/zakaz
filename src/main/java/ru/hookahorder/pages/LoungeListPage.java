package ru.hookahorder.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoungeListPage {
    private final Page page;

    public LoungeListPage(Page page) {
        this.page = page;
    }

    @Step("Переход к разделу «Кальянные»")
    public LoungeListPage navigateToLoungeList() {
        log.info("Переход к разделу «Кальянные»");
        page.locator(".nav-item").getByText("Кальянные").click();
        page.waitForLoadState();
        log.info("Раздел «Кальянные» загружен");
        return this;
    }

    @Step("Открытие формы добавления кальянной")
    public LoungeListPage clickAddLounge() {
        log.info("Открытие формы добавления кальянной");
        page.locator("div[class='lounge-layout'] button[class='btn-primary']")
                .first().click();
        return this;
    }

    public Locator getLoungeCardByText(String shopName) {
        return page.locator(".lounge-card").getByText(shopName);
    }

    @Step("Проверка видимости карточки кальянной: '{shopName}'")
    public boolean isLoungeCardVisible(String shopName) {
        var card = getLoungeCardByText(shopName);
        card.waitFor();
        boolean visible = card.isVisible();
        log.info("Карточка кальянной '{}' видима: {}", shopName, visible);
        return visible;
    }
}