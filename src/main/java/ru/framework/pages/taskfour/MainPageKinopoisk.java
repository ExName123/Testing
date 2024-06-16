package ru.framework.pages.taskfour;

import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.framework.BasePage;
import ru.framework.managers.TestPropManager;
import ru.framework.pages.taskone.MainPageLambdaSampleApp;
import ru.framework.utils.PropsConst;

public class MainPageKinopoisk extends BasePage {
    @FindBy(xpath = "//input[@aria-label='Фильмы, сериалы, персоны']")
    private WebElement searchInput;

    @FindBy(xpath = "//h4[@class='styles_title__7ZVXS kinopoisk-header-suggest-item__title']")
    private WebElement searchResultTitle;
    @FindBy(xpath = "//a[@href='/lists/categories/movies/1/' and @class='styles_root__7mPJN styles_lightThemeItem__BSbZW']")
    private WebElement moviesButton;
    private static final Logger logger = LoggerFactory.getLogger(MainPageKinopoisk.class);

    @Step("Check URL Kinopoisk")
    public MainPageKinopoisk checkOpenSite() {
        logger.info("Check URL 'LambdaTest Sample App");

        wait.until(ExpectedConditions.urlContains("kinopoisk"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("kinopoisk"), "URL does not contain 'kinopoisk'");

        return pageManager.getMainPageKinopoisk();
    }

    @Step("Check select movie from results")
    public MoviePage checkSelectMovieFromResults(String movieName) {
        logger.info("Check select movie from results");

        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.sendKeys(movieName);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h4[@class='styles_title__7ZVXS kinopoisk-header-suggest-item__title']")));

        WebElement movieElement = driver.findElements(By.xpath("//h4[@class='styles_title__7ZVXS kinopoisk-header-suggest-item__title']")).stream()
                .filter(element -> element.getText().equals(movieName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Movie not found in search results"));

        wait.until(ExpectedConditions.elementToBeClickable(movieElement)).click();

        pageManager.getMoviePage().setNameMovie(movieName);

        return pageManager.getMoviePage();
    }
    @Step("Check click on movies")
    public MoviesPage checkClickOnMovies() {
        logger.info("Check click on movies");

        wait.until(ExpectedConditions.visibilityOf(moviesButton));
        wait.until(ExpectedConditions.elementToBeClickable(moviesButton));

        Assertions.assertTrue(moviesButton.isDisplayed(), "Movies button is not displayed");
        moviesButton.click();
        wait.until(ExpectedConditions.urlContains("/lists/categories/movies/1/"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/lists/categories/movies/1/"), "Did not navigate to the Movies page");

        return pageManager.getMoviesPage();
    }
}
