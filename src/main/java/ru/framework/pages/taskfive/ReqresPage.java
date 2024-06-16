package ru.framework.pages.taskfive;


import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.managers.TestPropManager;

import java.util.List;

public class ReqresPage extends BasePage {

    private static final TestPropManager props = TestPropManager.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(ReqresPage.class);

    @FindBy(xpath = "/html/body/div[1]/main/div/h2[1]")
    private WebElement title;

    @FindBy(xpath = "//li[@data-id]")
    private List<WebElement> buttonList;

    @FindBy(xpath = "//pre[@data-key='output-response']")
    private WebElement outputResponse;

    @FindBy(xpath = "//pre[@data-key='output-request']")
    private WebElement outputRequest;

    @FindBy(xpath = "//span[@class='url']")
    private WebElement urlRequest;

    @FindBy(xpath = "//span[contains(@class,'response-code')]")
    private WebElement responseCode;

    @Step("Проверка открытия страницы Reqres")
    public ReqresPage checkOpenPage() {
        logger.info(title.getText());
        checkOpenPage("Test your front-end against a real API", title);
        logger.info("Страница успешно открыта");
        return this;
    }

    @Step("Нажать на кнопку {nameButton} и проверить ответ API")
    public ReqresPage clickOnButtonAndCheckAPI(String nameButton, String httpMethod) {
        for (WebElement button : buttonList) {
            WebElement request = button.findElement(By.xpath("./a"));
            if (request.getText().equalsIgnoreCase(nameButton) && button.getAttribute("data-http").equalsIgnoreCase(httpMethod)) {
                moveToElement(button);
                button.click();
                waitUntilElementToBeVisible(outputResponse);
                Assertions.assertEquals("Кнопка не активирована", "active", button.getAttribute("class"));
                validateApiResponse(httpMethod, request);
                Assertions.assertEquals("URL запроса не совпадает", request.getAttribute("href"), "https://reqres.in" + urlRequest.getText());

                logger.info("Ответ на '" + nameButton + "' успешно проверен и соответствует API");
                return this;
            }
        }
        Assertions.fail("Кнопка '" + nameButton + "' не найдена");
        return this;
    }

    private void validateApiResponse(String httpMethod, WebElement request) {
        String requestUrl = request.getAttribute("href");
        String requestBody = outputRequest.getText();
        String responseBody = outputResponse.getText();
        int expectedStatusCode = Integer.parseInt(responseCode.getText());

        switch (httpMethod.toLowerCase()) {
            case "get":
                Assertions.assertEquals("Ответ API не совпадает", get(requestUrl), responseBody);
                Assertions.assertEquals(getStatusCode(requestUrl), expectedStatusCode,"Статус код не совпадает");
                break;
            case "post":
                compareResponses(post(requestUrl, requestBody), responseBody);
                Assertions.assertEquals(postStatusCode(requestUrl, requestBody), expectedStatusCode,"Статус код не совпадает");
                break;
            case "put":
                compareResponses(put(requestUrl, requestBody), responseBody);
                Assertions.assertEquals(putStatusCode(requestUrl, requestBody), expectedStatusCode,"Статус код не совпадает");
                break;
            case "patch":
                compareResponses(patch(requestUrl, requestBody), responseBody);
                Assertions.assertEquals(patchStatusCode(requestUrl, requestBody), expectedStatusCode,"Статус код не совпадает");
                break;
            case "delete":
                Assertions.assertEquals("Ответ API не совпадает", delete(requestUrl), responseBody);
                Assertions.assertEquals(deleteStatusCode(requestUrl), expectedStatusCode,"Статус код не совпадает");
                break;
            default:
                Assertions.fail("Некорректный HTTP метод: " + httpMethod);
        }
    }
}