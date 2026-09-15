package pages;

import com.microsoft.playwright.Page;

public class PageFactory {
    private final Page page;

    public PageFactory(Page page) {
        this.page = page;
    }

    public LoungeListPage loungeListPage() {
        return new LoungeListPage(page);
    }

    public LoungeFormPage loungeFormPage() {
        return new LoungeFormPage(page);
    }

    public HookahEditPage hookahShopEditPage() {
        return new HookahEditPage(page);
    }
}