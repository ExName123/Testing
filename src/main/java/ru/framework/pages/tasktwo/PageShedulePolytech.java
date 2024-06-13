package ru.framework.pages.tasktwo;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.framework.BasePage;

import java.util.Set;

public class PageShedulePolytech extends BasePage {
    @FindBy(xpath = "//a[@href=\"https://rasp.dmami.ru/\"]")
    private WebElement buttonLookAtWebsite;
    // Новый метод для проверки кликабельности кнопки и перехода на новую страницу
    public RaspDmamiPage checkButtonLookAtWebsiteAndClick() {
        scrollToElementJs(buttonLookAtWebsite);
        waitUtilElementToBeClickable(buttonLookAtWebsite);
        Assertions.assertTrue(buttonLookAtWebsite.isDisplayed(), "Button 'Look at Website' is not visible");

        // Получить текущее окно
        String originalWindow = driver.getWindowHandle();

        // Кликнуть на кнопку
        buttonLookAtWebsite.click();

        // Подождать некоторое время для открытия новой вкладки
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверить, что открылась новая вкладка
        Set<String> windowHandles = driver.getWindowHandles();
        Assertions.assertTrue(windowHandles.size() > 1, "New tab did not open after clicking 'Look at Website' button");

        // Переключиться на новую вкладку
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Подождать пока загрузится новая страница
        wait.until(ExpectedConditions.urlContains("rasp.dmami.ru"));

        // Проверить, что переход на новую страницу произошел
        String newTabUrl = driver.getCurrentUrl();
        Assertions.assertTrue(newTabUrl.contains("rasp.dmami.ru"), "Navigation to the new page was not successful. Current URL: " + newTabUrl);

        return pageManager.getRaspDmamiPage();
    }
}
