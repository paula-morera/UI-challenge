import Factory.Driver;
import Factory.Factory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;


import java.util.Properties;

public class Hooks {
    Driver driverChrome;
    protected WebDriver driver;
    Properties properties;

    @Parameters("browser")
    @BeforeClass
    public void SetupPage(String browser){
        driverChrome = Factory.build(browser);
        driver = driverChrome.Browser();
        driver.manage().window().maximize();
    }


    @BeforeMethod
    public void navigateHomePage(){
        driver.navigate().to("https://www.themoviedb.org/");
    }

    @AfterClass
    public void tearDown(){
       driver.quit();
    }

}
