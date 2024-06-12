package ru.framework.tests.taskOne;

import org.junit.jupiter.api.Test;
import ru.framework.pages.taskOne.MainPage;
import ru.framework.tests.BaseTests;

public class MainPageTest extends BaseTests {
    private final MainPage mainPage = new MainPage();
    @Test
    public void testSteps() {
        pageManager.getMainPage()
                .checkExistHeader()
                .checkExistText()
                .checkFirstItemListNotCrossedOut()
                .checkFirstItemAndClick();
        for (int i = 1; i < 5; i++) {
           mainPage.checkItemsOfListAndClick(i);
        }
        mainPage.checkAddNewItem("test");

    }
}