package ru.framework.pages.tasktwo;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.BasePage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class RaspDmamiPage extends BasePage {
    @FindBy(xpath = "//input[@class=\"groups\"and@placeholder=\"группа ...\"]")
    private WebElement inputGroup;
    @FindBy(xpath = "//div[@class=\"found-groups row not-print\"]/div[1]")
    private WebElement resultGroup;
    @FindBy(xpath = "//div[@class=\"found-groups row not-print\"]")
    private List<WebElement> resultGroups;
    @FindBy(xpath = "//div[@class='schedule-day schedule-day_today']/div[1]")
    private WebElement todayScheduleDayTitle;
    private static final Logger logger = LoggerFactory.getLogger(RaspDmamiPage.class);

    @Step("Check check search for group and check results")
    //Method for entering group number and checking search results
    public RaspDmamiPage checkSearchForGroupAndCheckResults(String groupNumber) {
        logger.info("Check search for group and check results");

        waitUtilElementToBeVisible(inputGroup);
        waitUtilElementToBeClickable(inputGroup);

        // input number group
        inputGroup.clear();
        inputGroup.sendKeys(groupNumber);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"found-groups row not-print\"]")));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Make sure exactly one element is found
        Assertions.assertEquals(1, resultGroups.size(), "Expected exactly one search result for group number: " + groupNumber + " but found " + resultGroups.size());

        // Check that the found element contains the group number
        WebElement searchResult = resultGroup;
        Assertions.assertTrue(searchResult.getAttribute("id").contains(groupNumber), "Search result does not contain the group number: " + groupNumber);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return pageManager.getRaspDmamiPage();
    }
    @Step("Check click search for group")
    // Метод для выбора найденной группы
    public RaspDmamiPage checkClickSearchForGroup() {
        logger.info("Check click search for group");

        waitUtilElementToBeClickable(resultGroup);

        // Scroll to an element if it is not visible
        scrollWithOffset(resultGroup, 0, 100);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resultGroup.click();

        List<WebElement> scheduleDays = driver.findElements(By.xpath("//div[@class='bold schedule-day__title']"));
        String currentDay = getCurrentDayOfWeek();

        boolean isAnyDayMatching = false;

        for (WebElement element : scheduleDays) {
            String dayText = element.getText().trim();
            if (dayText.equalsIgnoreCase(currentDay)) {
                isAnyDayMatching = true;
                break;
            }
        }

        Assertions.assertTrue(isAnyDayMatching, "Current day of week is not displayed among schedule days");

        WebElement todayScheduleDay = driver.findElement(By.xpath("//div[@class='schedule-day schedule-day_today']"));
        Assertions.assertTrue(todayScheduleDay.isDisplayed(), "Today schedule day is not displayed");
        waitUtilElementToBeVisible(todayScheduleDay);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(todayScheduleDay.getAttribute("class").contains("schedule-day_today"), "Current day is not highlighted on the schedule page");
        Assertions.assertTrue(todayScheduleDayTitle.getText().contains(currentDay), "Highlighted day does not match the current day of the week");

        return pageManager.getRaspDmamiPage();
    }

    // Method for determining the current day of the week
    public String getCurrentDayOfWeek() {
        LocalDate currentDate = LocalDate.now();

        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return "Понедельник";
            case TUESDAY:
                return "Вторник";
            case WEDNESDAY:
                return "Среда";
            case THURSDAY:
                return "Четверг";
            case FRIDAY:
                return "Пятница";
            case SATURDAY:
                return "Суббота";
            case SUNDAY:
                return "Воскресенье";
            default:
                return "";
        }
    }
}
