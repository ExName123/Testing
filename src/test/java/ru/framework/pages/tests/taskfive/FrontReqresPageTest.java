package ru.framework.pages.tests.taskfive;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.framework.managers.DriverManager;
import ru.framework.managers.InitManager;
import ru.framework.pages.taskfive.ReqresPage;

public class FrontReqresPageTest {
    private static final DriverManager driverManager = DriverManager.getInstance();

    @BeforeAll
    public static void beforeClass() {
        InitManager.initFramework();
        driverManager.getDriver().get("https://reqres.in/");
    }

    @Test
    @DisplayName("Checking that when you click on the button to send a sample request, the result is the same as through the API")
    public void test() {
        ReqresPage startPage = new ReqresPage();
        startPage.checkOpenPage()
                .clickOnButtonAndCheckAPI("List users", "get")
                .clickOnButtonAndCheckAPI("Single user", "get")
                .clickOnButtonAndCheckAPI("Single user not found", "get")
                .clickOnButtonAndCheckAPI("List <resource>", "get")
                .clickOnButtonAndCheckAPI("Single <resource>", "get")
                .clickOnButtonAndCheckAPI("Single <resource> not found", "get")
                .clickOnButtonAndCheckAPI("Create", "post")
                .clickOnButtonAndCheckAPI("Update", "put")
                .clickOnButtonAndCheckAPI("Update", "patch")
                .clickOnButtonAndCheckAPI("Delete", "delete")
                .clickOnButtonAndCheckAPI("Register - successful", "post")
                .clickOnButtonAndCheckAPI("Register - unsuccessful", "post")
                .clickOnButtonAndCheckAPI("Login - successful", "post")
                .clickOnButtonAndCheckAPI("Login - unsuccessful", "post")
                .clickOnButtonAndCheckAPI("Delayed response", "get");
    }

    @AfterAll
    public static void after() {
        InitManager.quitFramework();
    }

}