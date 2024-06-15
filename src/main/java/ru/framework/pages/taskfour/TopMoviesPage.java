package ru.framework.pages.taskfour;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.framework.BasePage;

import java.util.List;

public class TopMoviesPage extends BasePage {

    // Элемент кнопки "Все страны"
    @FindBy(xpath = "//div[@class='styles_selectButton__4xHt7 styles_button__3dBmr styles_thin__zGEcp']/span[text()='Все страны']")
    private WebElement allCountriesButton;

    // Список стран
    @FindBy(xpath = "//div[@class='styles_itemWrapper__2tNUh']/label/span")
    private List<WebElement> countryList;
    // Элементы фильмов в списке
    @FindBy(xpath = "//div[@class='styles_root__ti07r']//span[@class='desktop-list-main-info_truncatedText__IMQRP']")
    private List<WebElement> movieList;
    // Элемент кнопки сортировки
    @FindBy(xpath = "//button[@class='styles_dropdownButton__3zGAH']")
    private WebElement sortButton;

    // Элемент сортировки по количеству оценок
    @FindBy(xpath = "//span[text()='По количеству оценок']")
    private WebElement sortByRatings;
    // Нажатие на кнопку "Все страны" и выбор страны
    public TopMoviesPage checkSelectCountry(String nameOfCountry) {
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.visibilityOf(allCountriesButton));
        wait.until(ExpectedConditions.elementToBeClickable(allCountriesButton));
        Assertions.assertTrue(allCountriesButton.isDisplayed(), "All Countries button is not displayed");
        allCountriesButton.click();

        wait.until(ExpectedConditions.visibilityOfAllElements(countryList));
        boolean countryFound = false;
        for (WebElement country : countryList) {
            if (country.getText().equalsIgnoreCase(nameOfCountry)) {
                wait.until(ExpectedConditions.elementToBeClickable(country));
                country.click();
                countryFound = true;
                break;
            }
        }
        Assertions.assertTrue(countryFound, "Country '" + nameOfCountry + "' not found in the list");
        verifyMoviesFromCountry(nameOfCountry);
        return pageManager.getTopMoviesPage();
    }
    public void verifyMoviesFromCountry(String nameOfCountry) {
        wait.until(ExpectedConditions.visibilityOfAllElements(movieList));
        Assertions.assertFalse(movieList.isEmpty(), "No movies found in the list");

        boolean movieFound = false;
        for (WebElement movie : movieList) {
            if (movie.getText().contains(nameOfCountry)) {
                movieFound = true;
                break;
            }
        }
        Assertions.assertTrue(movieFound, "No movies from '" + nameOfCountry + "' found in the list");
    }

    // Проверка перехода на страницу "250 лучших фильмов"
    public TopMoviesPage checkVerifyTop250MoviesPage() {
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.urlContains("/lists/movies/top250/"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/lists/movies/top250/"), "Did not navigate to the Top 250 Movies page");
        return pageManager.getTopMoviesPage();
    }
    public void checkSortingByNumberOfRatings() {
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOf(sortButton));
        wait.until(ExpectedConditions.elementToBeClickable(sortButton));
        Assertions.assertTrue(sortButton.isDisplayed(), "Sort button is not displayed");
        sortButton.click();

        wait.until(ExpectedConditions.visibilityOf(sortByRatings));
        wait.until(ExpectedConditions.elementToBeClickable(sortByRatings));
        Assertions.assertTrue(sortByRatings.isDisplayed(), "Sort by number of ratings option is not displayed");
        sortByRatings.click();
        try {
            Thread.sleep(1000); // Consider replacing with a proper wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
