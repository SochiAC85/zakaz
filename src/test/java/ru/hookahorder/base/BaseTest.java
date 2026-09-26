package ru.hookahorder.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.util.List;

import ru.hookahorder.pages.LoginPage;
import ru.hookahorder.utils.TestConfig;

public abstract class BaseTest {

    protected static final String UNIQUE_SUFFIX = initUniqueSuffix();

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    private static String initUniqueSuffix() {
        String existingSuffix = System.getProperty("test.unique.suffix");
        if (existingSuffix == null || existingSuffix.isBlank()) {
            String newSuffix = String.valueOf(System.currentTimeMillis());
            System.setProperty("test.unique.suffix", newSuffix);
            return newSuffix;
        }
        return existingSuffix;
    }

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(List.of("--disable-blink-features=AutomationControlled"));

        browser = playwright.chromium().launch(launchOptions);
    }

    @BeforeEach
    void setUp() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));
        page = context.newPage();
        page.navigate(TestConfig.ADMIN_URL);
        ensureAgeGatePassed();
        login(TestConfig.ADMIN_LOGIN, TestConfig.ADMIN_PASSWORD);
    }

    @AfterEach
    void closeContext() {
        if (page != null) page.close();
        if (context != null) context.close();
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    private void login(String phone, String password) {
        new LoginPage(page).login(phone, password);
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
}