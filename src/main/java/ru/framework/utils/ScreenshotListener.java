package ru.framework.utils;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.framework.managers.DriverManager;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ScreenshotListener implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof DriverManager) {
            WebDriver driver = ((DriverManager) testInstance).getDriver();
            if (driver != null) {
                System.out.println("00000000000000000000000000000000");
                saveScreenshot(driver, context.getDisplayName());
            }
        }
    }

    private void saveScreenshot(WebDriver driver, String testCaseName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = testCaseName + "_" + timestamp + ".png";

        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
    }

}
