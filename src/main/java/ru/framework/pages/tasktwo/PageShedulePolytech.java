package ru.framework.pages.tasktwo;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.BasePage;

import java.util.Set;

public class PageShedulePolytech extends BasePage {
    @FindBy(xpath = "//a[@href=\"https://rasp.dmami.ru/\"]")
    private WebElement buttonLookAtWebsite;
    private static final Logger logger = LoggerFactory.getLogger(PageShedulePolytech.class);
    @Step("Check button 'LookAtWebsite' click")
    public RaspDmamiPage checkButtonLookAtWebsiteAndClick() {
        logger.info("Check button 'LookAtWebsite' click");

        scrollToElementJs(buttonLookAtWebsite);
        waitUtilElementToBeClickable(buttonLookAtWebsite);
        Assertions.assertTrue(buttonLookAtWebsite.isDisplayed(), "Button 'Look at Website' is not visible");

        String originalWindow = driver.getWindowHandle();

        buttonLookAtWebsite.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check open a new tab
        Set<String> windowHandles = driver.getWindowHandles();
        Assertions.assertTrue(windowHandles.size() > 1, "New tab did not open after clicking 'Look at Website' button");

        // Switch to the new tab
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        wait.until(ExpectedConditions.urlContains("rasp.dmami.ru"));

        String newTabUrl = driver.getCurrentUrl();
        Assertions.assertTrue(newTabUrl.contains("rasp.dmami.ru"), "Navigation to the new page was not successful. Current URL: " + newTabUrl);

        return pageManager.getRaspDmamiPage();
    }
}
