
package ru.hookahorder.base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BasePage {
    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected void waitForVisible(String selector) {
        page.locator(selector).waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    protected void click(String selector) {
        page.locator(selector).first().click();
    }

    protected void fill(String selector, String value) {
        page.locator(selector).first().fill(value);
    }
}