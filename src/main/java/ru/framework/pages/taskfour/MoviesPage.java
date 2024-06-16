package ru.framework.pages.taskfour;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.BasePage;
import ru.framework.managers.TestPropManager;
import ru.framework.utils.PropsConst;

public class MoviesPage extends BasePage {
    // Элемент ссылки "250 лучших фильмов"
    @FindBy(xpath = "//a[@href='/lists/movies/top250/']")
    private WebElement top250MoviesLink;
    private static final Logger logger = LoggerFactory.getLogger(MoviesPage.class);
    @Step("Check verify movies page")
    public MoviesPage verifyMoviesPage() {
        logger.info("Check verify movies page");

        wait.until(ExpectedConditions.urlContains("/lists/categories/movies/1/"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/lists/categories/movies/1/"), "Did not navigate to the Movies page");
        return pageManager.getMoviesPage();
    }
    @Step("Check click on top 250 movies")
    public TopMoviesPage checkClickOnTop250Movies() {
        logger.info("Check click on top 250 movies");

        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.visibilityOf(top250MoviesLink));
        wait.until(ExpectedConditions.elementToBeClickable(top250MoviesLink));
        Assertions.assertTrue(top250MoviesLink.isDisplayed(), "Top 250 Movies link is not displayed");
        top250MoviesLink.click();

        return pageManager.getTopMoviesPage();
    }
}
