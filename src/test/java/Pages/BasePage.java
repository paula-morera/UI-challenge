package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
    public WebElement mapWebElement(By locator){
        return driver.findElement(locator);
    }

    public List<WebElement> mapWebElements(By locator){
        return driver.findElements(locator);
    }

}
