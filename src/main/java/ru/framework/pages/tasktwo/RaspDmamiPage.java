package ru.framework.pages.tasktwo;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    @FindBy(xpath = "//div[@class='schedule-day schedule-day_today']")
    private WebElement todayScheduleDay;
    @FindBy(xpath = "//div[@class='schedule-day schedule-day_today']/div[1]")
    private WebElement todayScheduleDayTitle;

    // Метод для ввода номера группы и проверки результатов поиска
    public RaspDmamiPage checkSearchForGroupAndCheckResults(String groupNumber) {
        // Убедиться, что поле поиска группы видно и кликабельно
        waitUtilElementToBeVisible(inputGroup);
        waitUtilElementToBeClickable(inputGroup);

        // Ввести номер группы
        inputGroup.clear();
        inputGroup.sendKeys(groupNumber);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Подождать, пока результаты поиска появятся
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"found-groups row not-print\"]")));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Убедиться, что найден ровно один элемент
        Assertions.assertEquals(1, resultGroups.size(), "Expected exactly one search result for group number: " + groupNumber + " but found " + resultGroups.size());

        // Проверить, что найденный элемент содержит номер группы
        WebElement searchResult = resultGroup;
        Assertions.assertTrue(searchResult.getAttribute("id").contains(groupNumber), "Search result does not contain the group number: " + groupNumber);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pageManager.getRaspDmamiPage();
    }

    // Метод для выбора найденной группы
    public RaspDmamiPage checkClickSearchForGroup() {
        // Подождать, пока элемент станет кликабельным
        waitUtilElementToBeClickable(resultGroup);

        // Прокрутка к элементу, если он не видим
        scrollWithOffset(resultGroup, 0, 100);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Кликнуть по элементу
        resultGroup.click();

        // Убедиться, что текущий день недели выделен
        waitUtilElementToBeVisible(todayScheduleDay);

        // Получить текущий день недели
        String currentDay = getCurrentDayOfWeek();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Проверить, что текущий день недели выделен и текст совпадает с текущим днем
        Assertions.assertTrue(todayScheduleDay.getAttribute("class").contains("schedule-day_today"), "Current day is not highlighted on the schedule page");
        Assertions.assertTrue(todayScheduleDayTitle.getText().contains(currentDay), "Highlighted day does not match the current day of the week");

        return pageManager.getRaspDmamiPage();
    }

    // Метод для определения текущего дня недели
    public String getCurrentDayOfWeek() {
        // Получаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Получаем день недели текущей даты
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

        // Возвращаем день недели в нужном формате (например, на русском языке)
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
