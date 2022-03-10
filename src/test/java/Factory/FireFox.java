package Factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FireFox implements Driver{
    WebDriver driver;
    public FireFox() {
        this.driver = new FirefoxDriver();
    }
    @Override
    public WebDriver Browser() {
        return this.driver;
    }
}
