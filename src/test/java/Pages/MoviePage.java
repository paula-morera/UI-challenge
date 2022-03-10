package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MoviePage extends BasePage{
    private By genresBy = By.cssSelector(".genres a");
    private By castListBy = By.cssSelector("#cast_scroller p>a");

    private static final Logger log = getLogger(MoviePage.class);

    public MoviePage(WebDriver driver) {
        super(driver);
    }

    @Step("Search genre")
    public boolean searchGenre(String genre){
        List<WebElement> genres = mapWebElements(genresBy);
        List<String> genresName = new ArrayList<>();
        log.info("Searching {} in genres of the movie",genre);
        for(WebElement element: genres){
            genresName.add(element.getText());
        }
        return genresName.contains(genre);
    }
    @Step("Select actor")
    public ActorPage selectActor(int actor){
        log.info("Selecting actor {}",mapWebElements(castListBy).get(0).getText());
        mapWebElements(castListBy).get(0).click();
        return new ActorPage(driver);
    }
}
