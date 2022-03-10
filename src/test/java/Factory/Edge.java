package Factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Edge implements Driver{
    WebDriver driver;
    public Edge() {
        this.driver = new EdgeDriver();
    }

    @Override
    public WebDriver Browser() {
        return this.driver;
    }
}
