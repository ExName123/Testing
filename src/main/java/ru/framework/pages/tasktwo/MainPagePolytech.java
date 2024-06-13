package ru.framework.pages.tasktwo;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.framework.BasePage;

public class MainPagePolytech extends BasePage {
    @FindBy(xpath = "//a[@class=\"user-nav__item-link\"and@href=\"/obuchauschimsya/raspisaniya/\"]")
    private WebElement buttonShedule;
    public PageShedulePolytech checkButtonSheduleAndClick() {
        waitUtilElementToBeClickable(buttonShedule);
        Assertions.assertTrue(buttonShedule.isDisplayed(), "Schedule button is not visible");

        // Кликнуть на кнопку
        buttonShedule.click();

        // Подождать пока загрузится новая страница
        wait.until(ExpectedConditions.urlContains("/obuchauschimsya/raspisaniya/"));

        // Проверить, что переход на новую страницу произошел
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/obuchauschimsya/raspisaniya/"), "Navigation to the new page was not successful. Current URL: " + currentUrl);

        return pageManager.getPageShedulePolytech();
    }
}
