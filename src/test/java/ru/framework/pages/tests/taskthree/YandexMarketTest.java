package ru.framework.pages.tests.taskthree;

import org.junit.jupiter.api.Test;
import ru.framework.pages.tests.BaseTests;

public class YandexMarketTest extends BaseTests {
    @Test
    public void testSteps() {
        pageManager.getMainPageYandexMarket()
                .checkOpenCatalogAndSelectLaptops()
                .checkLogFirstFiveProducts()
                .checkAddToCartSecondProduct()
                .goToCart(pageManager.getLaptopsPage().getSecondProductTitle(), pageManager.getLaptopsPage().getSecondProductPrice())
                .checkIncreaseQuantityAndVerifyPrice()
                .checkRemoveProductAndVerifyMessage();
    }
}
