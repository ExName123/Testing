package ru.framework.managers;

import ru.framework.pages.taskOne.MainPage;

public class PageManager {
    private static PageManager INSTANCE = null;
    private MainPage mainPage;

    private PageManager() {

    }

    public static PageManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public MainPage getMainPage() {
        if (mainPage == null) {
            mainPage = new MainPage();
        }
        return mainPage;
    }
}
