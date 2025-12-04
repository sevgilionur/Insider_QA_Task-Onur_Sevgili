package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.PageObjects.HomePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {

    public WebDriver driver;
    public HomePage homePage;

    //Factory method
    public WebDriver initializeDriver() throws IOException {

        Properties prop = new Properties();

        String propertiesPath = System.getProperty("user.dir") + "/src/test/resources/global.properties";

        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            prop.load(fis);
        } catch (IOException e) {
            System.out.println("Global.properties not found, using default browser: chrome");
            prop.setProperty("browser", "chrome");
        }

        String browserName = prop.getProperty("browser", "chrome"); // null gelirse chrome

        switch (browserName.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "safari":
                WebDriverManager wdmSafari = WebDriverManager.safaridriver().browserInDocker();
                driver = wdmSafari.create();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;
    }

    //data reader method
    public List<HashMap<String, String[]>> getJsonDataToMap(String filePath) throws IOException {
        //read json to string
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        //convert string array to hashmap (Jackson Databind - Object Mapper)
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String[]>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String[]>>>() {
        });
        return data;
        //returns: List {Hmap, Hmap, ...}
    }

    @BeforeMethod(alwaysRun = true)
    public HomePage launchApplication() throws IOException {
        driver = initializeDriver();
        homePage = new HomePage(driver);

        // Mevcut HomePage metodlarına uyarlandı
        homePage.goToUrl("https://useinsider.com/");
        homePage.acceptCookies();

        Assert.assertTrue(homePage.isHomePageOpened(), "Home Page açılmadı!");

        return homePage;
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}