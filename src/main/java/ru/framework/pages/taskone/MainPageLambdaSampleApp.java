package ru.framework.pages.taskone;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.BasePage;
import org.junit.jupiter.api.Assertions;
import ru.framework.pages.taskthree.LaptopsPage;
import ru.framework.utils.PropsConst;

import java.util.List;

public class MainPageLambdaSampleApp extends BasePage {
    // Локатор заголовка
    @FindBy(xpath = "//h2")
    private WebElement title;
    @FindBy(xpath = "//div/div/span[@class=\"ng-binding\"]")
    private WebElement labelSpan;
    @FindBy(xpath = "//ul/li[@class=\"ng-scope\"][1]/span")
    private WebElement firstItemLi;
    @FindBy(xpath = "//ul/li[@class=\"ng-scope\"][1]/input")
    private WebElement firstInputItemLi;
    @FindBy(xpath = "//ul[@class='list-unstyled']//li/span")
    private List<WebElement> itemsOfList;
    @FindBy(xpath = "//ul[@class='list-unstyled']//li/input")
    private List<WebElement> inputCheckboxes;
    @FindBy(xpath = "//input[@type=\"text\"]")
    private WebElement newItemInputText;
    @FindBy(xpath = "//input[@class=\"btn btn-primary\"and@type=\"submit\"]")
    private WebElement buttonAddClick;
    private static final Logger logger = LoggerFactory.getLogger(MainPageLambdaSampleApp.class);

    @Step("Check URL LambdaTest")
    public MainPageLambdaSampleApp checkUrl() {

        logger.info("Check URL 'LambdaTest Sample App");

        Assertions.assertTrue(driver.getCurrentUrl().contains("lambdatest"), "URL do not contains LambdaTest");

        return pageManager.getMainPageLambdaSampleApp();
    }

    @Step("Check title 'LambdaTest Sample App'")
    public MainPageLambdaSampleApp checkExistHeader() {

        WebElement headerElement = waitUtilElementToBeVisible(title);
        Assertions.assertTrue(headerElement.isDisplayed(), "Header is not visible");

        logger.info("Check title 'LambdaTest Sample App");
        Assertions.assertEquals("LambdaTest Sample App", headerElement.getText(), "Header text \"LambdaTest Sample App\" is not found");
        logger.info("Check title 'LambdaTest Sample App': success");

        return pageManager.getMainPageLambdaSampleApp();
    }

    @Step("Check exists '5 of 5 remaining' text")
    public MainPageLambdaSampleApp checkExistText() {
        waitUtilElementToBeVisible(labelSpan);

        logger.info("Check for existence of '5 of 5 remaining' text");
        Assertions.assertEquals("5 of 5 remaining", labelSpan.getText(), "Label text \"5 of 5 remaining\" is not found");
        logger.info("Check for existence of '5 of 5 remaining' text: success");

        return pageManager.getMainPageLambdaSampleApp();
    }

    @Step("Check first item is not strikethrough")
    public MainPageLambdaSampleApp checkFirstItemListNotCrossedOut() {
        WebElement firstItem = firstItemLi;
        waitUtilElementToBeVisible(firstItem);
        String classAttribute = firstItem.getAttribute("class");

        logger.info("Check if the first item is not strikethrough");
        Assertions.assertTrue(classAttribute.contains("done-false"), "First item is strikethrough");
        logger.info("Check if the first item is not strikethrough: success");

        return pageManager.getMainPageLambdaSampleApp();
    }

    @Step("Check set complete first item of list")
    public MainPageLambdaSampleApp checkFirstItemAndClick() {
        logger.info("Check set complete first item of list");

        WebElement firstItem = firstItemLi;
        WebElement firstInput = firstInputItemLi;

        // Get counter
        String remainingText = labelSpan.getText();
        int remainingBefore = Integer.parseInt(remainingText.split(" ")[0]);

        // Set complete first item
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        firstInput.click();
        waitUtilElementToBeClickable(firstInput);

        // First item is strikethrough
        String classAttribute = firstItem.getAttribute("class");
        Assertions.assertTrue(classAttribute.contains("done-true"), "First item is not strikethrough after checking the checkbox");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Remaining items count decrease by 1
        String newRemainingText = labelSpan.getText();
        int remainingAfter = Integer.parseInt(newRemainingText.split(" ")[0]);
        Assertions.assertEquals(remainingBefore - 1, remainingAfter, "Remaining items count did not decrease by 1 after checking the checkbox");

        logger.info("Check set complete first item of list: success");

        return pageManager.getMainPageLambdaSampleApp();
    }

    @Step("Check other items but first (click and item has attribute class - done-true)")
    public MainPageLambdaSampleApp checkItemsOfListAndClick(int index) {
        logger.info("Check other items but first (click and item has attribute class - done-true)");

        WebElement item = itemsOfList.get(index);
        WebElement checkbox = inputCheckboxes.get(index);

        // Get size
        String remainingText = labelSpan.getText();
        int remainingBefore = Integer.parseInt(remainingText.split(" ")[0]);

        waitUtilElementToBeClickable(checkbox);
        checkbox.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String classAttribute = item.getAttribute("class");
        Assertions.assertTrue(classAttribute.contains("done-true"), "Item at index " + index + " is not strikethrough after checking the checkbox");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check remaining items count decrease by 1
        String newRemainingText = labelSpan.getText();
        int remainingAfter = Integer.parseInt(newRemainingText.split(" ")[0]);
        Assertions.assertEquals(remainingBefore - 1, remainingAfter, "Remaining items count did not decrease by 1 after checking the checkbox at index " + index);

        logger.info("Check other items but first (click and item has attribute class - done-true): success");

        return pageManager.getMainPageLambdaSampleApp();
    }

    @Step("Check add a new item")
    public MainPageLambdaSampleApp checkAddNewItem(String itemText) {
        logger.info("Check add a new item");
        // Get size
        String remainingText = labelSpan.getText();
        int remainingBefore = Integer.parseInt(remainingText.split(" ")[0]);

        newItemInputText.sendKeys(itemText);
        waitUtilElementToBeClickable(buttonAddClick);
        buttonAddClick.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='list-unstyled']//li/span[contains(text(), '" + itemText + "')]")));

        int newItemIndex = itemsOfList.size() - 1;

        String newRemainingText = labelSpan.getText();
        int remainingAfter = Integer.parseInt(newRemainingText.split(" ")[0]);

        Assertions.assertEquals(remainingBefore + 1, remainingAfter, "Remaining items count did not increase by 1 after adding a new item");

        // Check new item is not strikethrough
        WebElement newItem = itemsOfList.get(newItemIndex);
        String classAttribute = newItem.getAttribute("class");
        Assertions.assertTrue(classAttribute.contains("done-false"), "New item is strikethrough");

        logger.info("Check add a new item: success");

        return pageManager.getMainPageLambdaSampleApp();
    }

    @Step("Check click a new item")
    public MainPageLambdaSampleApp checkClickAddedNewItem() {
        logger.info("Check click a new item");

        int index = itemsOfList.size() - 1;
        WebElement item = itemsOfList.get(index);
        WebElement checkbox = inputCheckboxes.get(index);

        String remainingText = labelSpan.getText();
        int remainingBefore = Integer.parseInt(remainingText.split(" ")[0]);

        waitUtilElementToBeClickable(checkbox);
        checkbox.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String classAttribute = item.getAttribute("class");
        Assertions.assertTrue(classAttribute.contains("done-true"), "New item at index " + index + " is not strikethrough after checking the checkbox");

        String newRemainingText = labelSpan.getText();
        int remainingAfter = Integer.parseInt(newRemainingText.split(" ")[0]);
        Assertions.assertEquals(remainingBefore - 1, remainingAfter, "Remaining items count did not decrease by 1 after checking the checkbox for the new item at index " + index);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Check click a new item: success");

        return pageManager.getMainPageLambdaSampleApp();
    }
}