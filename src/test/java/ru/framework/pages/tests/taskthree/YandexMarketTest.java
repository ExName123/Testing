package ru.framework.pages.tests.taskthree;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.framework.managers.TestPropManager;
import ru.framework.pages.tests.BaseTests;
import ru.framework.utils.PropsConst;

public class YandexMarketTest extends BaseTests {
    @BeforeEach
    public void before() {
        driverManager.getDriver().get(TestPropManager.getInstance().getProperty(PropsConst.YANDEX_MARKET_URL));
    }
    @Test
    @DisplayName("YandexMarketTest")
    @Description("This test check market (cart, elements)")
    public void testSteps() {
        pageManager.getMainPageYandexMarket()
                .checkUrl()
                .checkOpenCatalogAndSelectLaptops()
                .checkLogFirstFiveProducts()
                .checkAddToCartSecondProduct()
                .goToCart()
                .checkItemsCart(pageManager.getLaptopsPage().getSecondProductTitle(), pageManager.getLaptopsPage().getSecondProductPrice())
                .checkIncreaseQuantityAndVerifyPrice()
                .checkRemoveProductAndVerifyMessage();
    }
}
