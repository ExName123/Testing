package ru.framework.pages.tests.tasktwo;

import org.junit.jupiter.api.Test;
import ru.framework.pages.tests.BaseTests;

public class PolytechTest extends BaseTests {
    @Test
    public void testSteps() {
     pageManager.getMainPagePolytech()
             .checkButtonSheduleAndClick()
             .checkButtonLookAtWebsiteAndClick()
             .checkSearchForGroupAndCheckResults("23А-221")
             .checkClickSearchForGroup();
    }
}
