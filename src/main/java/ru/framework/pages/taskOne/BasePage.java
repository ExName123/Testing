package ru.framework.pages.taskOne;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.framework.managers.DriverManager;
import ru.framework.managers.PageManager;

public class BasePage {
    protected DriverManager driverManager = DriverManager.getInstance();
    protected WebDriver driver = driverManager.getDriver();
    protected PageManager pageManager = PageManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driver, 10, 1000);

    public BasePage() {
        PageFactory.initElements(driver, this);
    }
}
