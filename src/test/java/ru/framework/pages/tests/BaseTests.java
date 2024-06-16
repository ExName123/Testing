package ru.framework.pages.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.framework.managers.DriverManager;
import ru.framework.managers.InitManager;
import ru.framework.managers.PageManager;
import ru.framework.managers.TestPropManager;
import ru.framework.utils.PropsConst;

public class BaseTests {
    private final TestPropManager propManager = TestPropManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();
    protected DriverManager driverManager = DriverManager.getInstance();

    @BeforeAll
    public static void beforeClass() {
        InitManager.initFramework();
    }
    @AfterAll
    public static void afterClass() {
        InitManager.quitFramework();
    }
}