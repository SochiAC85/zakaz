package ru.hookahorder.pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoungeFormPage {
    private final Page page;

    public LoungeFormPage(Page page) {
        this.page = page;
    }

    @Step("Заполнение поля «Название»: '{name}'")
    public LoungeFormPage fillName(String name) {
        log.info("Заполнение поля «Название»: {}", name);
        page.locator("div[class='form-panel'] input[placeholder='Cloud Lounge']")
                .first().fill(name);
        return this;
    }

    @Step("Заполнение поля «Описание»: '{description}'")
    public LoungeFormPage fillDescription(String description) {
        log.info("Заполнение поля «Описание»: {}", description);
        page.locator("textarea[placeholder='Описание заведения...']")
                .first().fill(description);
        return this;
    }

    @Step("Заполнение поля «Телефон»: '{phone}'")
    public LoungeFormPage fillPhone(String phone) {
        log.info("Заполнение поля «Телефон»: {}", phone);
        page.locator("div[class='lounge-layout'] input[placeholder='+7 999 123-45-67']")
                .first().fill(phone);
        return this;
    }

    @Step("Заполнение поля «Адрес»: '{address}'")
    public LoungeFormPage fillAddress(String address) {
        log.info("Заполнение поля «Адрес»: {}", address);
        page.locator("div[class='form-panel'] input[placeholder='Поиск адреса...']")
                .first().fill(address);
        page.waitForTimeout(300);
        log.info("Адрес обработан картой");
        return this;
    }

    @Step("Заполнение формы кальянной: название='{name}', телефон='{phone}', адрес='{address}'")
    public LoungeFormPage fillLoungeData(String name, String description, String phone, String address) {
        log.info("Заполнение формы кальянной: name={}, phone={}, address={}", name, phone, address);
        fillName(name);
        fillDescription(description);
        fillPhone(phone);
        fillAddress(address);
        return this;
    }

    @Step("Отправка формы кальянной")
    public LoungeFormPage submitLounge() {
        log.info("Прокрутка формы вниз и клик «Создать кальянную»");
        scrollToBottomOfForm();
        page.locator("div[class='lounge-layout'] button[class='btn-primary']")
                .last().click();
        log.info("Форма отправлена");
        return this;
    }

    private void scrollToBottomOfForm() {
        var formPanel = page.locator("div[class='form-panel']").first();
        formPanel.waitFor();
        page.evaluate("el => el.scrollTop = el.scrollHeight", formPanel.elementHandle());
        page.waitForTimeout(500);
    }
}