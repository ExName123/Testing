package ru.framework.pages.taskfour;

import ru.framework.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.framework.BasePage;

public class MainPageKinopoisk extends BasePage {
    @FindBy(xpath = "//input[@aria-label='Фильмы, сериалы, персоны']")
    private WebElement searchInput;

    @FindBy(xpath = "//h4[@class='styles_title__7ZVXS kinopoisk-header-suggest-item__title']")
    private WebElement searchResultTitle;
    @FindBy(xpath = "//a[@href='/lists/categories/movies/1/' and @class='styles_root__7mPJN styles_lightThemeItem__BSbZW']")
    private WebElement moviesButton;

    public MainPageKinopoisk checkOpenSite() {
        wait.until(ExpectedConditions.urlContains("kinopoisk"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("kinopoisk"), "URL does not contain 'kinopoisk'");
        return pageManager.getMainPageKinopoisk();
    }

    public MoviePage checkSelectMovieFromResults(String movieName) {
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

    public MoviesPage checkClickOnMovies() {
        wait.until(ExpectedConditions.visibilityOf(moviesButton));
        wait.until(ExpectedConditions.elementToBeClickable(moviesButton));

        Assertions.assertTrue(moviesButton.isDisplayed(), "Movies button is not displayed");
        moviesButton.click();
        wait.until(ExpectedConditions.urlContains("/lists/categories/movies/1/"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/lists/categories/movies/1/"), "Did not navigate to the Movies page");

        return pageManager.getMoviesPage();
    }
}
