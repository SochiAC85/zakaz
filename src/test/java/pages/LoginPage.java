package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.logging.Logger;

public class LoginPage {
    private static final Logger log = Logger.getLogger(LoginPage.class.getName());

    private final Page page;

    private static final String PHONE_SELECTOR = "#login-uid";
    private static final String PASSWORD_SELECTOR = "#login-pwd";
    private static final String SUBMIT_SELECTOR = "#login-box button.btn-save";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void login(String phone, String password) {
        log.info("Ввод логина: " + phone);
        page.fill(PHONE_SELECTOR, phone);

        log.info("Ввод пароля");
        page.fill(PASSWORD_SELECTOR, password);

        log.info("Нажатие кнопки входа");
        page.click(SUBMIT_SELECTOR);

        String dashboardLocator = ".logo-sub >> text=Панель управления";

        page.locator(dashboardLocator)
                .waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(15000));
        log.info("Вход выполнен, панель управления загружена");
    }
}