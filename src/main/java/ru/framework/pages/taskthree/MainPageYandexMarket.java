package ru.framework.pages.taskthree;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.BasePage;
import ru.framework.pages.tasktwo.MainPagePolytech;
import ru.framework.pages.tasktwo.PageShedulePolytech;
import ru.framework.utils.PropsConst;

import java.util.List;

public class MainPageYandexMarket extends BasePage {
    @FindBy(xpath = "//button[@class='_30-fz button-focus-ring Hkr1q _1pHod _2rdh3 _3rbM-']")
    private WebElement buttonCatalog;

    @FindBy(xpath = "//a[@href='/catalog--kompiuternaia-tekhnika/54425']/span[@class='_3W4t0']")
    private WebElement itemMenu;

    @FindBy(xpath = "//a[@href='/catalog--noutbuki/54544/list?hid=91013' and contains(@class, '_2TBT0')]")
    private WebElement itemSubMenu;
    private static final Logger logger = LoggerFactory.getLogger(MainPageYandexMarket.class);
    @Step("Check URL YandexMarket")
    public MainPageYandexMarket checkUrl() {

        logger.info("Check URL YandexMarket");

        Assertions.assertTrue(driver.getCurrentUrl().contains("yandex"),
                "URL do not contains YandexMarket ");

        return pageManager.getMainPageYandexMarket();
    }
    @Step("Check open catalog and select laptops")
    public LaptopsPage checkOpenCatalogAndSelectLaptops() {
        logger.info("Check open catalog and select laptops");

        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Actions actions = new Actions(driverManager.getDriver());

        waitUtilElementToBeVisible(buttonCatalog);
        waitUtilElementToBeClickable(buttonCatalog);

        buttonCatalog.click();

        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waitUtilElementToBeVisible(itemMenu);
        waitUtilElementToBeClickable(itemMenu);
        Assertions.assertTrue(itemMenu.isDisplayed(), "Menu item 'Ноутбуки и компьютеры' is not visible");

        // Hover over the "Notebooks and Computers" menu item
        actions.moveToElement(itemMenu).perform();

        waitUtilElementToBeVisible(itemSubMenu);
        waitUtilElementToBeClickable(itemSubMenu);
        Assertions.assertTrue(itemSubMenu.isDisplayed(), "Submenu item 'Ноутбуки' is not visible");

        itemSubMenu.click();

        wait.until(ExpectedConditions.urlContains("/catalog--noutbuki/54544/list?hid=91013"));
        Assertions.assertTrue(driverManager.getDriver().getCurrentUrl().contains("/catalog--noutbuki/54544/list?hid=91013"), "URL does not contain the expected path");

        return pageManager.getLaptopsPage();
    }
}
