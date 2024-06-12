package ru.framework.pages.tests.tasktwo;

import org.junit.jupiter.api.Test;
import ru.framework.pages.tests.BaseTests;

public class PolytechTest extends BaseTests {
    @Test
    public void testSteps() {
        pageManager.getMainPage()
                .checkExistHeader()
                .checkExistText()
                .checkFirstItemListNotCrossedOut()
                .checkFirstItemAndClick();
        for (int i = 1; i < 5; i++) {
            pageManager.getMainPage().checkItemsOfListAndClick(i);
        }
        pageManager.getMainPage().checkAddNewItem("test")
                .checkClickAddedNewItem();

    }
}
