package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;


public class ResultPage extends BasePage {

    private By titlesBy = By.cssSelector(".title h2");

    private static final Logger log = getLogger(ResultPage.class);

    public ResultPage(WebDriver driver){
        super(driver);
    }

    @Step("Getting first title")
    public String getFirstTitle(){
        log.info("Getting title of the first result");
        return mapWebElements(titlesBy).get(0).getText();
    }

    @Step("Selecting movie")
    public MoviePage selectMovie(int index){
        log.info("Selecting {} in results",mapWebElements(titlesBy).get(index).getText());
        mapWebElements(titlesBy).get(index).click();
        return new MoviePage(driver);
    }

}
