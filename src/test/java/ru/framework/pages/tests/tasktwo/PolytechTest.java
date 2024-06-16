package ru.framework.pages.tests.tasktwo;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.framework.managers.TestPropManager;
import ru.framework.pages.tests.BaseTests;
import ru.framework.utils.PropsConst;

public class PolytechTest extends BaseTests {
    @BeforeEach
    public void before() {
        driverManager.getDriver().get(TestPropManager.getInstance().getProperty(PropsConst.MOSPOLYTECH_URL));
    }
    @Test
    @DisplayName("PolytechTest")
    @Description("This test check schedule of Polytech")
    public void testSteps() {
     pageManager.getMainPagePolytech()
             .checkUrl()
             .checkButtonScheduleAndClick()
             .checkButtonLookAtWebsiteAndClick()
             .checkSearchForGroupAndCheckResults("23А-221")
             .checkClickSearchForGroup();
    }
}
