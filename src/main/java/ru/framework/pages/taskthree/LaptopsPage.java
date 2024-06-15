package ru.framework.pages.taskthree;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.BasePage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;

public class LaptopsPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(LaptopsPage.class);

    @FindBy(xpath = "//div[@class='_3RyfO SerpLayout serverList-item _1MOwX _1bCJz _28-dA']//h3")
    private List<WebElement> productTitles;

    @FindBy(xpath = "//div[@class='_3RyfO SerpLayout serverList-item _1MOwX _1bCJz _28-dA']//span[@class='_1ArMm']")
    private List<WebElement> productPrices;
    @FindBy(xpath = "//div[@class='_3RyfO SerpLayout serverList-item _1MOwX _1bCJz _28-dA']//button[@aria-label='В корзину']")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//div[@data-auto='notification']")
    private WebElement notification;

    @FindBy(xpath = "//div[@class='_3z_bn cia-vs cia-cs']")
    private WebElement cartCounter;

    @FindBy(xpath = "//div[@class='_3z_bn cia-vs cia-cs']//div[@data-auto='decrease']")
    private WebElement decreaseButton;

    @FindBy(xpath = "//div[@class='_3z_bn cia-vs cia-cs']//div[@data-auto='increase']")
    private WebElement increaseButton;

    @FindBy(xpath = "//div[@class='_3z_bn cia-vs cia-cs']//span[@class='_2cyeu']")

    private WebElement itemsCount;

    @FindBy(xpath = "//a[@href='/my/cart' and @class='EQlfk _2h0Ng _1IKOp']")
    private WebElement cartButton;
    private String secondProductTitle;
    private String secondProductPrice;

    public LaptopsPage checkLogFirstFiveProducts() {
        // Убедиться, что элементы с названиями и ценами продуктов видимы
        waitUtilElementsToBeVisible(productTitles);
        waitUtilElementsToBeVisible(productPrices);

        // Проверить, что список товаров и цен не пустой
        Assertions.assertFalse(productTitles.isEmpty(), "Product titles list is empty");
        Assertions.assertFalse(productPrices.isEmpty(), "Product prices list is empty");

        // Проверить, что все элементы товаров и цен отображаются
        for (WebElement titleElement : productTitles) {
            Assertions.assertTrue(titleElement.isDisplayed(), "Product title element is not displayed");
        }
        for (WebElement priceElement : productPrices) {
            Assertions.assertTrue(priceElement.isDisplayed(), "Product price element is not displayed");
        }
        // Логировать первые пять найденных товаров
        for (int i = 0; i < 5 && i < productTitles.size() && i < productPrices.size(); i++) {
            String title = productTitles.get(i).getText();
            String price = productPrices.get(i).getText();
            logger.info("Product {}: Title - {}, Price - {}", i + 1, title, price);
        }
        return pageManager.getLaptopsPage();
    }

    public LaptopsPage checkAddToCartSecondProduct() {
        // Убедиться, что все элементы видимы
        waitUtilElementsToBeVisible(productTitles);
        waitUtilElementsToBeVisible(productPrices);
        waitUtilElementsToBeVisible(addToCartButtons);

        // Проверить, что есть хотя бы два товара
        Assertions.assertTrue(productTitles.size() >= 2, "There are less than two products");

        // Запомнить вторую позицию из списка товаров (название и цену)
        secondProductTitle = productTitles.get(1).getText();
        secondProductPrice = productPrices.get(1).getText();
        logger.info("Second product: Title - {}, Price - {}", secondProductTitle, secondProductPrice);

        // Нажать кнопку "В корзину" для второго товара
        WebElement secondAddToCartButton = addToCartButtons.get(1);
        waitUtilElementToBeClickable(secondAddToCartButton).click();

        // Проверить, что после нажатия кнопки "В корзину" появляются необходимые элементы
        wait.until(ExpectedConditions.visibilityOf(cartCounter));
        Assertions.assertTrue(cartCounter.isDisplayed(), "Cart counter is not displayed");
        Assertions.assertTrue(decreaseButton.isDisplayed(), "Decrease button is not displayed");
        Assertions.assertTrue(increaseButton.isDisplayed(), "Increase button is not displayed");
        Assertions.assertTrue(itemsCount.isDisplayed(), "Items count is not displayed");
        Assertions.assertEquals("1", itemsCount.getText(), "Items count is not '1'");

        // Проверить, что всплывающее сообщение "Товар успешно добавлен в корзину" появляется
        wait.until(ExpectedConditions.visibilityOf(notification));
        Assertions.assertTrue(notification.isDisplayed(), "Add to cart success notification is not displayed");
        Assertions.assertTrue(notification.getAttribute("data-zone-data").contains("Товар успешно добавлен в корзину"), "Notification message does not match");
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pageManager.getLaptopsPage();
        // Проверить, что внутри всплывающего сообщения есть кнопка "Перейти в корзину"
//        WebElement goToCartButton = notification.findElement(By.xpath(".//button[contains(text(), 'Перейти в корзину')]"));
//        Assertions.assertTrue(goToCartButton.isDisplayed(), "Go to cart button inside success message is not displayed");
    }

    public CartPage goToCart() {
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Нажать кнопку "Корзина"
        waitUtilElementToBeClickable(cartButton).click();

        return pageManager.getCartPage();
    }
        public String getSecondProductTitle() {
        return secondProductTitle;
    }

    public String getSecondProductPrice() {
        return secondProductPrice;
    }
}
