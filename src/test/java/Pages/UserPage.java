package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class UserPage extends BasePage{
    private By userBy = By.cssSelector(".about a");
    private By userButtonBy = By.cssSelector(".avatar");
    private By settingBox = By.cssSelector(".k-tooltip .settings_content a");

    private static final Logger log = getLogger(UserPage.class);

    public UserPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify user")
    public String verifyUser(){
        log.info("Verifying user login");
        return mapWebElement(userBy).getText();
    }

    @Step("Clicking user button")
    public UserPage UserButton(){
        log.info("Clicking user button");
        mapWebElement(userButtonBy).click();
        return this;
    }

    @Step("Click lists of user")
    public ListsPage clickLists(){
        log.info("Click lists of user");
        mapWebElements(settingBox).get(3).click();
        return new ListsPage(driver);
    }
}

