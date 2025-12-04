package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.PageObjects.HomePage;
import utils.ConfigReader;

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

        String browserName = ConfigReader.getProperty("browser"); //

        boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));

        switch (browserName.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--window-size=1920,1080");
                }
                // WebDriverManager.firefoxdriver().setup(); // Selenium 4.16+ ile gerek kalmadı
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--window-size=1920,1080");
                }
                // WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(edgeOptions);
                break;

            case "safari":
                WebDriverManager wdmSafari = WebDriverManager.safaridriver().browserInDocker();
                driver = wdmSafari.create();
                break;

            default:
                // Chrome
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new"); // Yeni ve daha stabil headless modu
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--no-sandbox"); // CI ortamları için gerekli olabilir
                    chromeOptions.addArguments("--disable-dev-shm-usage"); // Hafıza yönetimi için
                    chromeOptions.addArguments("--disable-gpu");
                }
                // WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public HomePage launchApplication() throws IOException {
        driver = initializeDriver();
        homePage = new HomePage(driver);

        // URL bilgisini properties dosyasından okuyoruz
        homePage.goToUrl(ConfigReader.getProperty("baseUrl"));
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