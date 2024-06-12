package ru.framework.pages.taskone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.framework.BasePage;

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

    public MainPageLambdaSampleApp checkExistHeader() {
        WebElement headerElement = waitUtilElementToBeVisible(title);
        Assertions.assertTrue(headerElement.isDisplayed(), "Header is not visible");
        Assertions.assertEquals("LambdaTest Sample App", headerElement.getText(), "Header text \"LambdaTest Sample App\" is not found");
        return pageManager.getMainPage();
    }

    public MainPageLambdaSampleApp checkExistText() {
        waitUtilElementToBeVisible(labelSpan);
        Assertions.assertEquals("5 of 5 remaining", labelSpan.getText(), "Label text \"5 of 5 remaining\" is not found");
        return pageManager.getMainPage();
    }

    public MainPageLambdaSampleApp checkFirstItemListNotCrossedOut() {
        WebElement firstItem = firstItemLi;
        waitUtilElementToBeVisible(firstItem);
        String classAttribute = firstItem.getAttribute("class");
        Assertions.assertTrue(classAttribute.contains("done-false"), "First item is strikethrough");
        return pageManager.getMainPage();
    }

    public MainPageLambdaSampleApp checkFirstItemAndClick() {
        WebElement firstItem = firstItemLi;
        WebElement firstInput = firstInputItemLi;

        // Получить текущее значение счетчика оставшихся элементов
        String remainingText = labelSpan.getText();
        int remainingBefore = Integer.parseInt(remainingText.split(" ")[0]);

        // Установить галочку у первого элемента списка
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        firstInput.click();
        waitUtilElementToBeClickable(firstInput);
        // Проверить, что первый элемент списка зачеркнут
        String classAttribute = firstItem.getAttribute("class");
        Assertions.assertTrue(classAttribute.contains("done-true"), "First item is not strikethrough after checking the checkbox");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Проверить, что число оставшихся элементов уменьшилось на 1
        String newRemainingText = labelSpan.getText();
        int remainingAfter = Integer.parseInt(newRemainingText.split(" ")[0]);
        Assertions.assertEquals(remainingBefore - 1, remainingAfter, "Remaining items count did not decrease by 1 after checking the checkbox");
        return pageManager.getMainPage();
    }

    public MainPageLambdaSampleApp checkItemsOfListAndClick(int index) {
        WebElement item = itemsOfList.get(index);
        WebElement checkbox = inputCheckboxes.get(index);

        // Получить текущее значение счетчика оставшихся элементов
        String remainingText = labelSpan.getText();
        int remainingBefore = Integer.parseInt(remainingText.split(" ")[0]);

        // Установить галочку у элемента списка
        waitUtilElementToBeClickable(checkbox);
        checkbox.click();
        // Подождать некоторое время после клика
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверить, что элемент списка зачеркнут
        String classAttribute = item.getAttribute("class");
        Assertions.assertTrue(classAttribute.contains("done-true"), "Item at index " + index + " is not strikethrough after checking the checkbox");

        // Подождать некоторое время перед проверкой
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверить, что число оставшихся элементов уменьшилось на 1
        String newRemainingText = labelSpan.getText();
        int remainingAfter = Integer.parseInt(newRemainingText.split(" ")[0]);
        Assertions.assertEquals(remainingBefore - 1, remainingAfter, "Remaining items count did not decrease by 1 after checking the checkbox at index " + index);
        return pageManager.getMainPage();
    }

    public MainPageLambdaSampleApp checkAddNewItem(String itemText) {
        // Получить текущее значение счетчика оставшихся элементов
        String remainingText = labelSpan.getText();
        int remainingBefore = Integer.parseInt(remainingText.split(" ")[0]);

        // Ввести текст нового элемента и нажать кнопку добавления
        newItemInputText.sendKeys(itemText);
        waitUtilElementToBeClickable(buttonAddClick);
        buttonAddClick.click();

        // Явное ожидание для обновления страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='list-unstyled']//li/span[contains(text(), '" + itemText + "')]")));

        // Получить новый индекс элемента списка
        int newItemIndex = itemsOfList.size() - 1;

        // Получить новое значение счетчика оставшихся элементов
        String newRemainingText = labelSpan.getText();
        int remainingAfter = Integer.parseInt(newRemainingText.split(" ")[0]);

        // Проверить, что число оставшихся элементов увеличилось на 1
        Assertions.assertEquals(remainingBefore + 1, remainingAfter, "Remaining items count did not increase by 1 after adding a new item");

        // Проверить, что новый элемент не зачеркнут
        WebElement newItem = itemsOfList.get(newItemIndex);
        String classAttribute = newItem.getAttribute("class");
        Assertions.assertTrue(classAttribute.contains("done-false"), "New item is strikethrough");
        return pageManager.getMainPage();
    }

    public MainPageLambdaSampleApp checkClickAddedNewItem() {
        int index = itemsOfList.size() - 1; // Получить индекс последнего элемента
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
        return pageManager.getMainPage();
    }
}