package ru.framework.pages.taskfour;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.framework.BasePage;

public class MoviesPage extends BasePage {
    // Элемент ссылки "250 лучших фильмов"
    @FindBy(xpath = "//a[@href='/lists/movies/top250/']")
    private WebElement top250MoviesLink;
    // Проверка перехода на страницу "Фильмы"
    public MoviesPage verifyMoviesPage() {
        wait.until(ExpectedConditions.urlContains("/lists/categories/movies/1/"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/lists/categories/movies/1/"), "Did not navigate to the Movies page");
        return pageManager.getMoviesPage();
    }

    // Нажатие на ссылку "250 лучших фильмов"
    public TopMoviesPage checkClickOnTop250Movies() {
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
