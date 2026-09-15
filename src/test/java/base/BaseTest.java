package base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeEach
    void setUp() {
        String adminUrl = getRequiredProperty("admin.url", "URL админ-панели");
        String adminLogin = getRequiredProperty("admin.login", "Логин админа");
        String adminPassword = getRequiredProperty("admin.password", "Пароль админа");

        playwright = Playwright.create();

        String browserType = System.getProperty("browser", "chromium");

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(java.util.List.of("--disable-blink-features=AutomationControlled"));

        switch (browserType) {
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;
            default:
                browser = playwright.chromium().launch(launchOptions);
        }

        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));

        page = context.newPage();
        page.navigate(adminUrl);

        ensureAgeGatePassed();
        login(adminLogin, adminPassword);
    }

    @AfterEach
    void tearDown() {
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    private void login(String phone, String password) {
        new pages.LoginPage(page).login(phone, password);
    }

    private void ensureAgeGatePassed() {
        var ageGateBtn = page.locator(".age-gate-btn-yes");

        ageGateBtn.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(10000));

        ageGateBtn.click();

        page.waitForLoadState(com.microsoft.playwright.options.LoadState.LOAD);

        ageGateBtn.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                .setTimeout(10000));
    }

    private String getRequiredProperty(String key, String description) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Требуется параметр -" + key + ". Пример запуска: " +
                            "mvn test -Dadmin.url=http://******/ -Dadmin.login=****** -Dadmin.password=******"
            );
        }
        return value;
    }
}