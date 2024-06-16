package ru.framework.pages.tests.taskone;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.framework.managers.TestPropManager;
import ru.framework.pages.tests.BaseTests;
import ru.framework.utils.PropsConst;
public class LambdaSampleAppTest extends BaseTests {
    @BeforeEach
    public void before() {
        driverManager.getDriver().get(TestPropManager.getInstance().getProperty(PropsConst.LAMBDATEST_URL));
    }
    @Test
    @DisplayName("LambdaTest")
    @Description("This test check todo's LambdaSampleAppTest")
    public void testSteps() {
        pageManager.getMainPageLambdaSampleApp()
                .checkUrl()
                .checkExistHeader()
                .checkExistText()
                .checkFirstItemListNotCrossedOut()
                .checkFirstItemAndClick();
        for (int i = 1; i < 5; i++) {
           pageManager.getMainPageLambdaSampleApp().checkItemsOfListAndClick(i);
        }
        pageManager.getMainPageLambdaSampleApp().checkAddNewItem("test")
                .checkClickAddedNewItem();

    }
}