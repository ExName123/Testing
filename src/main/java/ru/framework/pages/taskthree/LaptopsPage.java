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
    @FindBy(xpath = "//div//article[@aria-label='Товар']//button[@data-auto='remove-button']")
    private WebElement removeButton;

    @FindBy(xpath = "//span[contains(text(), 'Войдите в аккаунт')]")
    private WebElement loginMessage;
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

    public LaptopsPage goToCart(String expectedTitle, String expectedPrice) {
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Нажать кнопку "Корзина"
        waitUtilElementToBeClickable(cartButton).click();

        // Убедиться, что открылась страница "Корзина"
        wait.until(ExpectedConditions.urlContains("/my/cart"));
        Assertions.assertTrue(driverManager.getDriver().getCurrentUrl().contains("/my/cart"), "Cart page did not open");

        // Найти все товары в корзине
        List<WebElement> cartItems = driverManager.getDriver().findElements(By.xpath("//div//article[@aria-label='Товар']"));
        try {
            Thread.sleep(2000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean found = false;
        for (WebElement item : cartItems) {
            String itemTitle = item.findElement(By.xpath(".//a[@rel='nofollow noopener']")).getText();
            String itemPrice = item.findElement(By.xpath("//div//article[@aria-label=\"Товар\"]//h3[@class=\"Jdxhz\"]")).getText().replaceAll("\\D", "");
            expectedPrice = expectedPrice.replaceAll("[^\\d\\s]", "").replaceAll("\n", "");

            if (itemTitle.equals(expectedTitle) && itemPrice.equals(expectedPrice)) {
                found = true;
                break;
            }
        }
СДЕЛАТЬ СТРАНИЦУ КОРЗИНЫ!!!!
        // Проверить, что товар найден в корзине
        Assertions.assertTrue(found, "The product with the expected title and price was not found in the cart");
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pageManager.getLaptopsPage();
    }
    public LaptopsPage checkIncreaseQuantityAndVerifyPrice() {
        // Найти кнопку "Увеличить"
        WebElement increaseButton = driver.findElement(By.xpath("//div//article[@aria-label='Товар']//button[@aria-label='Увеличить']"));

        // Найти поле ввода количества товара
        WebElement quantityInput = driver.findElement(By.xpath("//div//article[@aria-label='Товар']//input[@aria-label='Количество товара']"));

        // Найти элемент стоимости товара
        WebElement priceElement = driver.findElement(By.xpath("//div//article[@aria-label=\"Товар\"]//h3[@class=\"Jdxhz\"]"));

        // Получить текущее количество товара и стоимость
        int oldQuantity = Integer.parseInt(quantityInput.getAttribute("value").trim());
        double oldPrice = extractPrice(priceElement.getText());

        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitUtilElementToBeVisible(increaseButton);
        waitUtilElementToBeClickable(increaseButton);
        // Нажать на кнопку "Увеличить"
        increaseButton.click();
        try {
            Thread.sleep(2000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Дождаться обновления значения количества товара
        wait.until(ExpectedConditions.textToBePresentInElementValue(quantityInput, String.valueOf(oldQuantity + 1)));

        // Получить новое количество товара и стоимость после увеличения
        int newQuantity = Integer.parseInt(quantityInput.getAttribute("value").trim());
        double newPrice = extractPrice(priceElement.getText());

        // Проверка увеличения количества товара
        Assertions.assertEquals(oldQuantity + 1, newQuantity, "Количество товаров не увеличилось на 1");

        // Проверка изменения стоимости
        double expectedPrice = oldPrice / oldQuantity * newQuantity;
        Assertions.assertEquals(expectedPrice, newPrice, 0.01, "Стоимость не соответствует ожидаемой после увеличения количества товаров");

        try {
            Thread.sleep(2000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pageManager.getLaptopsPage();
    }
    public void checkRemoveProductAndVerifyMessage() {
        // Дождаться видимости кнопки удаления товара
        wait.until(ExpectedConditions.visibilityOf(removeButton));

        // Нажать на кнопку удаления товара
        removeButton.click();

        // Дождаться появления сообщения "Войдите в аккаунт"
        wait.until(ExpectedConditions.visibilityOf(loginMessage));

        // Проверить, что сообщение отображается корректно
        Assertions.assertTrue(loginMessage.isDisplayed(), "Сообщение 'Войдите в аккаунт' не отображается после удаления товара");
    }
    private double extractPrice(String text) {
        // Удаление всех символов, кроме цифр и десятичной точки
        String cleanText = text.replaceAll("[^\\d.]", "");
        return Double.parseDouble(cleanText);
    }
        public String getSecondProductTitle() {
        return secondProductTitle;
    }

    public String getSecondProductPrice() {
        return secondProductPrice;
    }
}
