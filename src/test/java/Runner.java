
import API.UserAPI;
import Data.Providers;
import Pages.*;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static io.restassured.RestAssured.baseURI;
import static org.slf4j.LoggerFactory.getLogger;


public class Runner extends Hooks{
    private static final Logger log = getLogger(Runner.class);
    /*
    @Test
    public void SuccessfulLogin(){
        log.info("------------Successful Login test ---------");
        LandingPage landingPage = new LandingPage(driver);
        LoginPage loginpage = landingPage.Login();
        loginpage.enterUsername("paula.morera14")
                .enterPassword("paulamorera14");
        UserPage userPage = loginpage.clickLogin();
        Assert.assertEquals(userPage.verifyUser(),"paula.morera14");
    }
    @Test
    public void FailLogin(){
        log.info("------------Faile Login test ---------");
        LandingPage landingPage = new LandingPage(driver);
        LoginPage loginPage = landingPage.Login();
        loginPage.enterUsername("paula.morera14")
                .enterPassword("otherpassword")
                .clickLogin();
        Assert.assertEquals(loginPage.verifyError()," There was a problem");

    }

     */
    @Test
    public void SuccessfulSearch(){
        log.info("------------Successful search test ---------");
        LandingPage landingPage = new LandingPage(driver);
        ResultPage resultPage = landingPage.searchQuery("Fight club");
        Assert.assertEquals(resultPage.getFirstTitle(),"Fight Club");
    }

    @Test
    public void VerifyGenre(){
        log.info("------------Verify genre test ---------");
        LandingPage landingPage = new LandingPage(driver);
        TopRatedPage topRatedPage = landingPage.clickMoviesDropdown()
                                                .clickItemDropdown();
        MoviePage moviePage = topRatedPage.selectFilter("Action")
                .applyFilter()
                .selectMovie(2);
        Assert.assertTrue(moviePage.searchGenre("Action"));
    }

    @Test
    public void ValidateActing(){
        log.info("------------Validate acting test ---------");
        LandingPage landingPage = new LandingPage(driver);
        ResultPage resultPage = landingPage.searchQuery("Fight Club");
        MoviePage moviePage = resultPage.selectMovie(1);
        ActorPage actorPage = moviePage.selectActor(2);
        Assert.assertTrue(actorPage.movieInTimeline("Fight club"));
    }

    @Test
    public void SortDates() throws ParseException {
        log.info("------------Sorting dates test ---------");
        LandingPage landingPage = new LandingPage(driver);
        TopRatedPage topRatedPage = landingPage.clickMoviesDropdown()
                .clickItemDropdown();
        topRatedPage.clickSortDropdown()
                .selectSort()
                .applyFilter();
        List<Date> dates = topRatedPage.verifyDates();
        List<Date> datesSort = new ArrayList<>(dates);
        Collections.sort(datesSort);
        Assert.assertEquals(dates,datesSort);
    }
    @Test(dataProvider = "API_case",
            dataProviderClass = Providers.class)
    public void addMovieAPI(String listGenre, String listInfo, String movieName, String movieID) throws IOException {
        UserAPI userAPI = new UserAPI();
        baseURI = "https://api.themoviedb.org/3";
        int listID=userAPI.setCredentials()
                .createList(listGenre, listInfo)
                .addMovie(movieName, Integer.parseInt(movieID))
                .getListID();

        LandingPage landingPage = new LandingPage(driver);
        LoginPage loginpage = landingPage.Login();
        loginpage.enterUsername("paula.morera14")
                .enterPassword("paulamorera14");
        UserPage userPage = loginpage.clickLogin()
                            .UserButton();
        ListsPage listsPage = userPage.clickLists()
                            .selectList(listID);
        Assert.assertTrue(listsPage.varifyMovie(movieID));
    }
}
