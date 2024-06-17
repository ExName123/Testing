package ru.framework.pages.taskthree;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.BasePage;

import java.util.List;

public class CartPage extends BasePage {
    @FindBy(xpath = "//div//article[@aria-label='Товар']//button[@data-auto='remove-button']")
    private WebElement removeButton;
    @FindBy(xpath = "//span[contains(text(), 'Войдите в аккаунт')]")
    private WebElement loginMessage;
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);

    @Step("Check items cart")
    public CartPage checkItemsCart(String expectedTitle, String expectedPrice) {
        logger.info("Check items cart");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.urlContains("/my/cart"));
        Assertions.assertTrue(driverManager.getDriver().getCurrentUrl().contains("/my/cart"), "Cart page did not open");

        // Find all items
        List<WebElement> cartItems = driverManager.getDriver().findElements(By.xpath("//div//article[@aria-label='Товар']"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean found = false;
        for (WebElement item : cartItems) {
            String itemTitle = item.findElement(By.xpath(".//a[@rel='nofollow noopener']")).getText();

            // Find price element
            WebElement priceElement = null;
            String priceText = "";

            // Check which price class is present on the item
            if (item.findElements(By.xpath("//div//article[@aria-label='Товар']//span[contains(@class, '_2G6-h _3Jitr wiNbX _33Eco _1X1Ia _1A5yJ')]")).size() > 0) {
                priceElement = item.findElement(By.xpath("//div//article[@aria-label='Товар']//span[contains(@class, '_2G6-h _3Jitr wiNbX _33Eco _1X1Ia _1A5yJ')]"));
                priceText = priceElement.getText().trim();
            } else if (item.findElements(By.xpath("//div//article[@aria-label='Товар']//span[contains(@class, '_1KTJE')]")).size() > 0) {
                priceElement = item.findElement(By.xpath("//div//article[@aria-label='Товар']//span[contains(@class, '_1KTJE')]"));
                priceText = priceElement.getText().trim();
            } else if (item.findElements(By.xpath("//div//article[@aria-label='Товар']//h3[contains(@class, 'Jdxhz')]")).size() > 0) {
                priceElement = item.findElement(By.xpath("//div//article[@aria-label='Товар']//h3[contains(@class, 'Jdxhz')]"));
                priceText = priceElement.getText().trim();
            } else {
                continue;  // If no price class is found, skip this item
            }
            logger.info(expectedTitle);
            logger.info(priceText);
            // Clean the expected price string
            expectedPrice = expectedPrice.replaceAll("[^\\d\\s]", "").replaceAll("\n", "");
            logger.info(expectedPrice);
            // Perform calculations with priceText
            // For example, converting priceText to numeric format:
            String itemPrice = priceText.replaceAll("\\D", "");
            logger.info(itemPrice);
            if (itemTitle.equals(expectedTitle) && itemPrice.equals(expectedPrice)) {
                found = true;
                break;
            }
        }

        Assertions.assertTrue(found, "The product with the expected title and price was not found in the cart");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return pageManager.getCartPage();
    }

    @Step("Check increase quantity and verify price")
    public CartPage checkIncreaseQuantityAndVerifyPrice() {
        logger.info("Check increase quantity and verify price");
        logger.info("Check increase quantity and verify price");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement increaseButton = driver.findElement(By.xpath("//div//article[@aria-label='Товар']//button[@aria-label='Увеличить']"));
        WebElement quantityInput = driver.findElement(By.xpath("//div//article[@aria-label='Товар']//input[@aria-label='Количество товара']"));

        // Find price element and determine the class of price
        WebElement priceElement = null;
        if (driver.findElements(By.xpath("//div//article[@aria-label='Товар']//span[contains(@class, '_2G6-h _3Jitr wiNbX _33Eco _1X1Ia _1A5yJ')]")).size() > 0) {
            priceElement = driver.findElement(By.xpath("//div//article[@aria-label='Товар']//span[contains(@class, '_2G6-h _3Jitr wiNbX _33Eco _1X1Ia _1A5yJ')]"));
        } else if (driver.findElements(By.xpath("//div//article[@aria-label='Товар']//span[contains(@class, '_1KTJE')]")).size() > 0) {
            priceElement = driver.findElement(By.xpath("//div//article[@aria-label='Товар']//span[contains(@class, '_1KTJE')]"));
        } else if (driver.findElements(By.xpath("//div//article[@aria-label='Товар']//h3[contains(@class, 'Jdxhz')]")).size() > 0) {
            priceElement = driver.findElement(By.xpath("//div//article[@aria-label='Товар']//h3[contains(@class, 'Jdxhz')]"));
        } else {
            Assertions.fail("Price class element not found");
        }

        // Get the current quantity of goods and cost
        int oldQuantity = Integer.parseInt(quantityInput.getAttribute("value").trim());
        double oldPrice = extractPrice(priceElement.getText());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waitUtilElementToBeVisible(increaseButton);
        waitUtilElementToBeClickable(increaseButton);

        increaseButton.click();

        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.textToBePresentInElementValue(quantityInput, String.valueOf(oldQuantity + 1)));

        // Receive a new quantity of goods and cost after an increase
        int newQuantity = Integer.parseInt(quantityInput.getAttribute("value").trim());
        double newPrice = extractPrice(priceElement.getText());

        Assertions.assertEquals(oldQuantity + 1, newQuantity, "Check that the current day of the week is not displayed among the schedule elements");

        // Calculating expected price based on the selected price class
        double expectedPrice = oldPrice / oldQuantity * newQuantity;

        Assertions.assertEquals(expectedPrice, newPrice, 0.01, "The number of products has not increased by 1");

        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pageManager.getCartPage();
    }

    @Step("Check remove product and verify message")
    public CartPage checkRemoveProductAndVerifyMessage() {
        logger.info("Check remove product and verify message");

        wait.until(ExpectedConditions.visibilityOf(removeButton));

        removeButton.click();

        wait.until(ExpectedConditions.visibilityOf(loginMessage));

        Assertions.assertTrue(loginMessage.isDisplayed(), "Message 'Войдите в аккаунт' not displayed after deleting a product");
        return pageManager.getCartPage();
    }

    private double extractPrice(String text) {
        // Remove all characters except numbers and decimal point
        String cleanText = text.replaceAll("[^\\d.]", "");
        return Double.parseDouble(cleanText);
    }
}
