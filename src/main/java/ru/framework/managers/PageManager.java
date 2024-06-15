package ru.framework.managers;

import ru.framework.pages.taskfour.MainPageKinopoisk;
import ru.framework.pages.taskfour.MoviePage;
import ru.framework.pages.taskfour.MoviesPage;
import ru.framework.pages.taskfour.TopMoviesPage;
import ru.framework.pages.taskone.MainPageLambdaSampleApp;
import ru.framework.pages.taskthree.CartPage;
import ru.framework.pages.taskthree.LaptopsPage;
import ru.framework.pages.taskthree.MainPageYandexMarket;
import ru.framework.pages.tasktwo.MainPagePolytech;
import ru.framework.pages.tasktwo.PageShedulePolytech;
import ru.framework.pages.tasktwo.RaspDmamiPage;

public class PageManager {
    private static PageManager INSTANCE = null;
    private MainPageLambdaSampleApp mainPageLambdaSampleApp;
    private MainPagePolytech mainPagePolytech;
    private PageShedulePolytech pageShedulePolytech;
    private RaspDmamiPage raspDmamiPage;
    private MainPageYandexMarket mainPageYandexMarket;
    private LaptopsPage laptopsPage;
    private CartPage cartPage;
    private MainPageKinopoisk mainPageKinopoisk;
    private MoviePage moviePage;
    private MoviesPage moviesPage;
    private TopMoviesPage topMoviesPage;
    private PageManager() {

    }

    public static PageManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }
    public MainPageLambdaSampleApp getMainPageLambdaSampleApp() {
        if (mainPageLambdaSampleApp == null) {
            mainPageLambdaSampleApp = new MainPageLambdaSampleApp();
        }
        return mainPageLambdaSampleApp;
    }
    public MainPagePolytech getMainPagePolytech() {
        if (mainPagePolytech == null) {
            mainPagePolytech = new MainPagePolytech();
        }
        return mainPagePolytech;
    }
    public PageShedulePolytech getPageShedulePolytech() {
        if (pageShedulePolytech == null) {
            pageShedulePolytech = new PageShedulePolytech();
        }
        return pageShedulePolytech;
    }
    public RaspDmamiPage getRaspDmamiPage() {
        if (raspDmamiPage == null) {
            raspDmamiPage = new RaspDmamiPage();
        }
        return raspDmamiPage;
    }
    public MainPageYandexMarket getMainPageYandexMarket() {
        if (mainPageYandexMarket == null) {
            mainPageYandexMarket = new MainPageYandexMarket();
        }
        return mainPageYandexMarket;
    }
    public LaptopsPage getLaptopsPage() {
        if (laptopsPage == null) {
            laptopsPage = new LaptopsPage();
        }
        return laptopsPage;
    }
    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }
    public MainPageKinopoisk getMainPageKinopoisk() {
        if (mainPageKinopoisk == null) {
            mainPageKinopoisk = new MainPageKinopoisk();
        }
        return mainPageKinopoisk;
    }
    public MoviePage getMoviePage() {
        if (moviePage == null) {
            moviePage = new MoviePage();
        }
        return moviePage;
    }
    public MoviesPage getMoviesPage() {
        if (moviesPage == null) {
            moviesPage = new MoviesPage();
        }
        return moviesPage;
    }
    public TopMoviesPage getTopMoviesPage() {
        if (topMoviesPage == null) {
            topMoviesPage = new TopMoviesPage();
        }
        return topMoviesPage;
    }
}
