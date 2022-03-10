package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.SkipException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class TopRatedPage extends BasePage{
    private By filterBy = By.cssSelector(".filter_panel");
    private By genresBy = By.cssSelector("#with_genres a");
    private By searchButtonBy = By.cssSelector(".apply");
    private By searchButtonDisableBy = By.cssSelector(".apply[class*='disabled']");
    private By sortDropdownBy = By.cssSelector(".k-dropdown");
    private By sortOptionBy = By.cssSelector("#sort_by-list li");
    private By moviesGridBy = By.cssSelector("#page_1");
    private By dateMoviesBy = By.cssSelector(".content>p");
    private By moviesTitleBy = By.cssSelector("h2>a");

    private static final Logger log = getLogger(TopRatedPage.class);

    public TopRatedPage(WebDriver driver) {
        super(driver);
    }

    @Step("Selecting filter")
    public TopRatedPage selectFilter(String genre){
        log.info("Clicking Filters panel dropdown");
        mapWebElements(filterBy).get(1).click();
        mapWebElements(filterBy).get(1).click();
        List<WebElement> genres = mapWebElements(genresBy);
        log.info("Selecting genre {} filter ",genre);
        for (WebElement element: genres) {
            if (element.getText().toLowerCase().trim().equals(genre.toLowerCase().trim())){
                element.click();
                return this;
            }
        }
        log.error("Genre not found in options");
        throw new SkipException("Skipping test");
    }

    @Step("Apply filter")
    public TopRatedPage applyFilter(){
        log.info("Clicking search button to apply filter");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        mapWebElement(searchButtonBy).click();
        log.info("Waiting for results to load");
        WebDriverWait wait=new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButtonDisableBy));
        return new TopRatedPage(driver);
    }

    @Step("Select movie")
    public MoviePage selectMovie(int index){
        WebElement movieGrid = mapWebElement(moviesGridBy);
        log.info("Selecting movie: {}",movieGrid.findElements(moviesTitleBy).get(index).getText());
        movieGrid.findElements(By.cssSelector("h2>a")).get(index).click();
        return new MoviePage(driver);
    }

    @Step("Sort dropdown")
    public TopRatedPage clickSortDropdown(){
        log.info("Clicking Sort by drowndown");
        WebDriverWait wait=new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(sortDropdownBy));
        mapWebElements(sortDropdownBy).get(0).click();
        return this;
    }

    @Step("Select sorting")
    public TopRatedPage selectSort(){
        log.info("Selecting type of sort");
        mapWebElements(sortOptionBy).get(5).click();
        return this;
    }

    @Step("Verify dates")
    public List<Date> verifyDates() throws ParseException {
        WebElement movieGrid = mapWebElement(moviesGridBy);
        log.info("Getting dates of the 4 first movies");
        List<WebElement> datesWE = movieGrid.findElements(dateMoviesBy);
        List<Date> dates = new ArrayList<>();
        for(int i=0;i<4;i++){
            dates.add(new SimpleDateFormat("MMM dd, yyyy").parse(datesWE.get(i).getText()));
        }
        return dates;
    }


}
