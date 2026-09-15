package base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.logging.Logger;

public abstract class BasePage {
    protected final Page page;
    protected final Logger log;

    protected BasePage(Page page) {
        this.page = page;
        this.log = Logger.getLogger(this.getClass().getName());
    }

    protected void waitForLoad() {
        page.waitForLoadState(LoadState.LOAD);
    }

    protected void waitForVisible(String selector) {
        page.locator(selector).waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    protected void scrollIntoView(String selector) {
        page.locator(selector).first().scrollIntoViewIfNeeded();
    }

    protected void click(String selector) {
        page.locator(selector).first().click();
    }

    protected void fill(String selector, String value) {
        page.locator(selector).first().fill(value);
    }
}
