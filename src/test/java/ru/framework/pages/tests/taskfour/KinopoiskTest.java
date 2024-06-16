package ru.framework.pages.tests.taskfour;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.framework.managers.TestPropManager;
import ru.framework.pages.tests.BaseTests;
import ru.framework.utils.PropsConst;

public class KinopoiskTest extends BaseTests {
    @BeforeEach
    public void before() {
        driverManager.getDriver().get(TestPropManager.getInstance().getProperty(PropsConst.KINOPOISK_URL));
    }
    @Test
    @DisplayName("TU01")
    @Description("This test check kinopoisk (searching)")
    public void testCaseTU01() {
        pageManager.getMainPageKinopoisk()
                .checkOpenSite()
                .checkSelectMovieFromResults("Форрест Гамп")
                .verifyPageTitle();
    }

    @Test
    @DisplayName("TU02")
    @Description("This test check kinopoisk (filtering)")
    public void testCaseTU02() {
        pageManager.getMainPageKinopoisk()
                .checkClickOnMovies()
                .verifyMoviesPage()
                .checkClickOnTop250Movies()
                .checkVerifyTop250MoviesPage()
                .checkSelectCountry("СССР");
    }

    @Test
    @DisplayName("TU03")
    @Description("This test check kinopoisk (sorting)")
    public void testCaseTU03() {
        pageManager.getMainPageKinopoisk()
                .checkClickOnMovies()
                .verifyMoviesPage()
                .checkClickOnTop250Movies()
                .checkSortingByNumberOfRatings();
    }
}
