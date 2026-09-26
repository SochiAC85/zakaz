package ru.hookahorder.base;

import com.microsoft.playwright.Page;
import ru.hookahorder.pages.HookahEditPage;
import ru.hookahorder.pages.LoungeFormPage;
import ru.hookahorder.pages.LoungeListPage;
import ru.hookahorder.pages.MenuPage;
import ru.hookahorder.pages.OrderPage;

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

    public HookahEditPage hookahEditPage() {
        return new HookahEditPage(page);
    }

    public MenuPage menuPage() {
        return new MenuPage(page);
    }

    public OrderPage orderPage() {
        return new OrderPage(page);
    }
}