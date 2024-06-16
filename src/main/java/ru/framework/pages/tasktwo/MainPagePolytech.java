package ru.framework.pages.tasktwo;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.BasePage;
import ru.framework.pages.taskone.MainPageLambdaSampleApp;
import ru.framework.utils.PropsConst;

public class MainPagePolytech extends BasePage {
    @FindBy(xpath = "//a[@class=\"user-nav__item-link\"and@href=\"/obuchauschimsya/raspisaniya/\"]")
    private WebElement buttonSchedule;
    private static final Logger logger = LoggerFactory.getLogger(MainPagePolytech.class);
    @Step("Check URL Polytech")
    public MainPagePolytech checkUrl() {

        logger.info("Check URL Polytech");

        Assertions.assertTrue(driver.getCurrentUrl().contains("mospolytech"),
                "URL do not contains Polytech");

        return pageManager.getMainPagePolytech();
    }
    @Step("Check schedule button click")
    public PageShedulePolytech checkButtonScheduleAndClick() {
        logger.info("Check schedule button click");

        waitUtilElementToBeClickable(buttonSchedule);
        Assertions.assertTrue(buttonSchedule.isDisplayed(), "Schedule button is not visible");

        buttonSchedule.click();

        wait.until(ExpectedConditions.urlContains("/obuchauschimsya/raspisaniya/"));

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/obuchauschimsya/raspisaniya/"), "Navigation to the new page was not successful. Current URL: " + currentUrl);

        return pageManager.getPageShedulePolytech();
    }
}
