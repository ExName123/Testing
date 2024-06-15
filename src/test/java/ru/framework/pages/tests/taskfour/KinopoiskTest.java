package ru.framework.pages.tests.taskfour;

import org.junit.jupiter.api.Test;

import ru.framework.pages.tests.BaseTests;

public class KinopoiskTest extends BaseTests {
    @Test
    public void testCaseTU01() {
        pageManager.getMainPageKinopoisk()
                .checkOpenSite()
                .checkSelectMovieFromResults("Форрест Гамп")
                .verifyPageTitle();
    }

    @Test
    public void testCaseTU02() {
        pageManager.getMainPageKinopoisk()
                .checkClickOnMovies()
                .verifyMoviesPage()
                .checkClickOnTop250Movies()
                .checkVerifyTop250MoviesPage()
                .checkSelectCountry("СССР");
    }

    @Test
    public void testCaseTU03() {
        pageManager.getMainPageKinopoisk()
                .checkClickOnMovies()
                .verifyMoviesPage()
                .checkClickOnTop250Movies()
                .checkSortingByNumberOfRatings();
    }
}
