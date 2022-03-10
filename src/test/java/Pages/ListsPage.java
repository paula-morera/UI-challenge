package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.testng.SkipException;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ListsPage extends BasePage{

    private By moviesListBy = By.cssSelector(".image a");

    private static final Logger log = getLogger(ListsPage.class);
    public ListsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Selecting list")
    public ListsPage selectList(int listID){
        WebElement lista = mapWebElement(By.cssSelector("a[href*='"+listID+"'"));
        log.info("Selecting list "+ lista.getText());
        lista.click();
        return this;
    }

    @Step("Verifying movie")
    public Boolean varifyMovie(String movieID){
        log.info("Verirying movieID "+ movieID+" is in the list");
        List<WebElement> movies = mapWebElements(moviesListBy);
        for(WebElement element: movies){
            if(element.getAttribute("href").contains(movieID)){
                return true;
            }
        }
        log.error("Movie not found");
        return false;
    }
}
