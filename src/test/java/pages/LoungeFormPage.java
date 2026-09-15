package pages;

import com.microsoft.playwright.Page;

import java.util.logging.Logger;

public class LoungeFormPage {
    private static final Logger log = Logger.getLogger(LoungeFormPage.class.getName());

    private final Page page;

    public LoungeFormPage(Page page) {
        this.page = page;
    }

    public void fillName(String name) {
        log.info("Заполнение поля «Название»: " + name);
        page.locator("div[class='form-panel'] input[placeholder='Cloud Lounge']")
                .first().fill(name);
    }

    public void fillDescription(String description) {
        log.info("Заполнение поля «Описание»: " + description);
        page.locator("textarea[placeholder='Описание заведения...']")
                .first().fill(description);
    }

    public void fillPhone(String phone) {
        log.info("Заполнение поля «Телефон»: " + phone);
        page.locator("div[class='lounge-layout'] input[placeholder='+7 999 123-45-67']")
                .first().fill(phone);
    }

    public void fillAddress(String address) {
        log.info("Заполнение поля «Адрес»: " + address);
        page.locator("div[class='form-panel'] input[placeholder='Поиск адреса...']")
                .first().fill(address);
        page.waitForTimeout(300);
        log.info("Адрес обработан картой");
    }

    public void fillLoungeData(String name, String description, String phone, String address) {
        log.info("Заполнение формы кальянной: name=" + name + ", phone=" + phone + ", address=" + address);
        fillName(name);
        fillDescription(description);
        fillPhone(phone);
        fillAddress(address);
    }

    public void submitLounge() {
        log.info("Прокрутка формы вниз и клик «Создать кальянную»");
        scrollToBottomOfForm();
        page.locator("div[class='lounge-layout'] button[class='btn-primary']")
                .last().click();
        log.info("Форма отправлена");
    }

    private void scrollToBottomOfForm() {
        var formPanel = page.locator("div[class='form-panel']").first();
        formPanel.waitFor();
        page.evaluate("el => el.scrollTop = el.scrollHeight", formPanel.elementHandle());
        page.waitForTimeout(500);
    }
}