package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ActorPage extends BasePage{
    private By creditsListBy = By.cssSelector(".credits_list");
    private By moviesListBy = By.cssSelector("bdi");
    public ActorPage(WebDriver driver) {
        super(driver);
    }

    private static final Logger log = getLogger(ActorPage.class);

    @Step("Movie in timeline")
    public Boolean movieInTimeline(String movie){
        log.info("Searching {} in timeline",movie);
        WebElement actingList = mapWebElements(creditsListBy).get(0);
        List<WebElement> movies = actingList.findElements(moviesListBy);
        List<String> moviesName = new ArrayList<>();
        for(WebElement element: movies){
            moviesName.add(element.getText().toLowerCase());
            if(element.getText().toLowerCase().contains(movie.toLowerCase())){
                return true;
            }
        }
        log.error("Movie not found in timeline");
        return false;
    }
}
