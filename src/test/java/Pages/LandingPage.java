package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;


public class LandingPage extends BasePage{
    private By searchBarBy = By.id("inner_search_v4");
    private By searchButtonBy = By.cssSelector("input[value='Search']");
    private By movieDropdownBy = By.cssSelector(".dropdown_menu>.k-item");
    private By movieItemsBy =By.cssSelector(".k-menu-group .k-item");
    private By loginButtonBy = By.cssSelector("header ul[class='primary'] a");

    private static final Logger log = getLogger(LandingPage.class);

    public LandingPage(WebDriver driver){
        super(driver);
    }

    @Step("Search query")
    public ResultPage searchQuery(String query){
        log.info("Typing " + query+ " in the searchbar");
        mapWebElement(searchBarBy).sendKeys(query);
        log.info("Clicking search button");
        mapWebElement(searchButtonBy).click();
        return new ResultPage(driver);
    }

    @Step("Movies dropdown")
    public LandingPage clickMoviesDropdown(){
        log.info("Hover over Movies dropdown");
        mapWebElements(movieDropdownBy).get(0).click();
        mapWebElements(movieDropdownBy).get(0).click();
        return this;
    }

    @Step("Item in dropdown")
    public TopRatedPage clickItemDropdown(){
        log.info("Clicking option in Movie dropdown");
        mapWebElements(movieItemsBy).get(3).click();
        return new TopRatedPage(driver);
    }

    @Step("Login button")
    public LoginPage Login(){
        log.info("Clicking login button");
        mapWebElements(loginButtonBy).get(1).click();
        return new LoginPage(driver);
    }

}
