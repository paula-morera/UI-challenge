package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;


public class LoginPage extends BasePage{
    private By usernameBarBy = By.id("username");
    private By passwordBarBy=By.id("password");
    private By loginButtonBy = By.id("login_button");
    private By errorMessage = By.cssSelector(".error_status span");

    private static final Logger log = getLogger(LoginPage.class);

    public LoginPage(WebDriver driver){
        super(driver);
    }

    @Step("Enter username")
    public LoginPage enterUsername(String username){
        log.info("Entering username {Username}",username);
        mapWebElement(usernameBarBy).sendKeys(username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password){
        log.info("Entering password");
        mapWebElement(passwordBarBy).sendKeys(password);
        return this;
    }

    @Step("Click login")
    public UserPage clickLogin(){
        log.info("Clicking login button");
        mapWebElement(loginButtonBy).click();
        return new UserPage(driver);
    }

    @Step("verify error message")
    public String verifyError(){
        log.info("Verifying error message");
        return mapWebElement(errorMessage).getText();
    }

}
