package ru.framework.managers;

import ru.framework.pages.taskone.MainPageLambdaSampleApp;
import ru.framework.pages.tasktwo.MainPagePolytech;

public class PageManager {
    private static PageManager INSTANCE = null;
    private MainPageLambdaSampleApp mainPageLambdaSampleApp;
    private MainPagePolytech mainPagePolytech;

    private PageManager() {

    }

    public static PageManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public MainPageLambdaSampleApp getMainPage() {
        if (mainPageLambdaSampleApp == null) {
            mainPageLambdaSampleApp = new MainPageLambdaSampleApp();
        }
        return mainPageLambdaSampleApp;
    }
}
