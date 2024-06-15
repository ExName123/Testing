package ru.framework.pages.taskthree;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.framework.BasePage;

import java.util.List;

public class CartPage extends BasePage {
    @FindBy(xpath = "//div//article[@aria-label='Товар']//button[@data-auto='remove-button']")
    private WebElement removeButton;
    @FindBy(xpath = "//span[contains(text(), 'Войдите в аккаунт')]")
    private WebElement loginMessage;
    public CartPage checkItemsCart(String expectedTitle, String expectedPrice){
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        System.out.println(expectedPrice);
        System.out.println(expectedTitle);

        boolean found = false;
        for (WebElement item : cartItems) {
            String itemTitle = item.findElement(By.xpath(".//a[@rel='nofollow noopener']")).getText();
            String itemPrice = item.findElement(By.xpath("//div//article[@aria-label=\"Товар\"]//h3[@class=\"Jdxhz\"]")).getText().replaceAll("\\D", "");
            System.out.println(itemPrice);
            System.out.println(itemTitle);
            expectedPrice = expectedPrice.replaceAll("[^\\d\\s]", "").replaceAll("\n", "");

            if (itemTitle.equals(expectedTitle) && itemPrice.equals(expectedPrice)) {
                found = true;
                break;
            }
        }
        // Проверить, что товар найден в корзине
        Assertions.assertTrue(found, "The product with the expected title and price was not found in the cart");
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pageManager.getCartPage();
    }
    public CartPage checkIncreaseQuantityAndVerifyPrice() {
        try {
            Thread.sleep(2000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        return pageManager.getCartPage();
    }
    public CartPage checkRemoveProductAndVerifyMessage() {
        // Дождаться видимости кнопки удаления товара
        wait.until(ExpectedConditions.visibilityOf(removeButton));

        // Нажать на кнопку удаления товара
        removeButton.click();

        // Дождаться появления сообщения "Войдите в аккаунт"
        wait.until(ExpectedConditions.visibilityOf(loginMessage));

        // Проверить, что сообщение отображается корректно
        Assertions.assertTrue(loginMessage.isDisplayed(), "Сообщение 'Войдите в аккаунт' не отображается после удаления товара");
        return pageManager.getCartPage();
    }
    private double extractPrice(String text) {
        // Удаление всех символов, кроме цифр и десятичной точки
        String cleanText = text.replaceAll("[^\\d.]", "");
        return Double.parseDouble(cleanText);
    }
}
