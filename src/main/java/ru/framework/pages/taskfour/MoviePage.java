package ru.framework.pages.taskfour;

import ru.framework.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoviePage extends BasePage {

    private String NameMovie;
    @FindBy(xpath = "//span[@data-tid='75209b22']")
    private WebElement pageTitle;

    public void verifyPageTitle() {
        String actual = "";
        wait.until(ExpectedConditions.visibilityOf(pageTitle));

        Assertions.assertTrue(pageTitle.isDisplayed(), "Page title is not displayed");
        Pattern pattern = Pattern.compile("^(.*?)\\s*\\(");
        Matcher matcher = pattern.matcher(pageTitle.getText());

        if (matcher.find()) {
            actual = matcher.group(1);
        }
        Assertions.assertEquals(getNameMovie(), actual, "Page title does not match the expected title");
    }

    public String getNameMovie() {
        return NameMovie;
    }

    public void setNameMovie(String nameMovie) {
        NameMovie = nameMovie;
    }
}
