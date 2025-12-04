package pages.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BaseComponents.BasePage;

public class HomePage extends BasePage {

    private By acceptCookiesButton = By.id("wt-cli-accept-all-btn");
    private By companyMenu = By.linkText("Company");
    private By careersOption = By.xpath("//a[contains(text(),'Careers')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    public void acceptCookies() {
        if (isDisplayed(acceptCookiesButton)) {
            click(acceptCookiesButton);
        }
    }

    public boolean isHomePageOpened() {
        return driver.getTitle().contains("Insider");
    }

    public void goToCareersPage() {
        click(companyMenu);
        click(careersOption);
    }
}