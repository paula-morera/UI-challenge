package Factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Chrome implements Driver{
    WebDriver driver;
    public Chrome() {
        this.driver = new ChromeDriver();
    }
    @Override
    public WebDriver Browser() {
        return this.driver;
    }
}
