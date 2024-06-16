package ru.framework.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import ru.framework.utils.PropsConst;

public class DriverManager {
    private static DriverManager INSTANCE = null;
    private WebDriver driver;
    private TestPropManager propManager = TestPropManager.getInstance();

    private DriverManager() {

    }

    public static DriverManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void initDriver() {
        if (propManager.getProperty(PropsConst.TYPE_BROWSER).equals("edge")) {
            System.setProperty("webdriver.http.factory", "jdk-http-client");
            System.setProperty("webdriver.edge.driver", propManager.getProperty(PropsConst.PATH_EDGE_DRIVER_WINDOWS));
            driver = new EdgeDriver();
        } else {
            System.out.println("Absent driver for your browser");
            return;
        }
    }
}
