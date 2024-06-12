package ru.framework.pages.tasktwo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.framework.BasePage;

public class MainPagePolytech extends BasePage {
    @FindBy(xpath = "//a[@class=\"navigation-head__logo-link is-second\"and@href=\"/ob-universitete/strategiya/\"]")
    private WebElement buttonShedule;

}
