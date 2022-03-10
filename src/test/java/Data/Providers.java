package Data;

import API.Methods;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;

public class Providers {
    Methods methods = new Methods();
    @DataProvider(name = "API_case")
    public Object[][] ListNames() throws IOException {
        return  new Object[][]{
                {"Comedy","This is an example list","Red Notice","512195"}
        };
    }
}
