package ru.hookahorder.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ru.hookahorder.base.BaseTest;
import ru.hookahorder.base.PageFactory;
import ru.hookahorder.data.LoungeData;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Order(1)
@Feature("Регистрация заведений")
public class CreateLoungeTest extends BaseTest {

    @Test
    @DisplayName("1. Создание кальянной")
    void createLoungeTest() {
        PageFactory factory = new PageFactory(page);
        LoungeData lounge = new LoungeData(UNIQUE_SUFFIX);

        factory.loungeListPage()
                .navigateToLoungeList()
                .clickAddLounge();

        factory.loungeFormPage()
                .fillLoungeData(lounge.shopName, lounge.description, lounge.phone, lounge.address)
                .submitLounge();

        page.waitForLoadState();

        assertTrue(factory.loungeListPage().isLoungeCardVisible(lounge.shopName),
                "Кальянная не появилась в списке после создания!");

        factory.hookahEditPage()
                .navigateToLoungeList()
                .openEditModal(lounge.shopName)
                .assignOwner(lounge.ownerPhone)
                .save();
    }
}