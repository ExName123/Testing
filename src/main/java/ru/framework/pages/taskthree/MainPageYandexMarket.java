package ru.framework.pages.taskthree;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.framework.BasePage;

import java.util.List;

public class MainPageYandexMarket extends BasePage {
    @FindBy(xpath = "//button[@class='_30-fz button-focus-ring Hkr1q _1pHod _2rdh3 _3rbM-']")
    private WebElement buttonCatalog;

    @FindBy(xpath = "//a[@href='/catalog--kompiuternaia-tekhnika/54425']/span[@class='_3W4t0']")
    private WebElement itemMenu;

    @FindBy(xpath = "//a[@href='/catalog--noutbuki/54544/list?hid=91013' and contains(@class, '_2TBT0')]")
    private WebElement itemSubMenu;

    public LaptopsPage checkOpenCatalogAndSelectLaptops() {
        Actions actions = new Actions(driverManager.getDriver());

        // Ensure the "Catalog" button is visible and clickable
        waitUtilElementToBeVisible(buttonCatalog);
        waitUtilElementToBeClickable(buttonCatalog);

        // Click on the "Catalog" button
        buttonCatalog.click();
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Ensure the "Notebooks and Computers" menu item is visible and clickable
        waitUtilElementToBeVisible(itemMenu);
        waitUtilElementToBeClickable(itemMenu);
        Assertions.assertTrue(itemMenu.isDisplayed(), "Menu item 'Ноутбуки и компьютеры' is not visible");

        // Hover over the "Notebooks and Computers" menu item
        actions.moveToElement(itemMenu).perform();

        // Ensure the "Notebooks" submenu item is visible and clickable
        waitUtilElementToBeVisible(itemSubMenu);
        waitUtilElementToBeClickable(itemSubMenu);
        Assertions.assertTrue(itemSubMenu.isDisplayed(), "Submenu item 'Ноутбуки' is not visible");

        // Click on the "Notebooks" submenu item
        itemSubMenu.click();

        // Validate the URL to ensure the correct page is opened
        wait.until(ExpectedConditions.urlContains("/catalog--noutbuki/54544/list?hid=91013"));
        Assertions.assertTrue(driverManager.getDriver().getCurrentUrl().contains("/catalog--noutbuki/54544/list?hid=91013"), "URL does not contain the expected path");
        return pageManager.getLaptopsPage();
    }
}
