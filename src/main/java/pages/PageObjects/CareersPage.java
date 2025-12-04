package pages.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BaseComponents.BasePage;

public class CareersPage extends BasePage {

    private By locationsBlock = By.id("career-our-location");
    private By teamsBlock = By.id("career-find-our-calling");
    private By lifeAtInsiderBlock = By.xpath("//h2[text()='Life at Insider']");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        return driver.getCurrentUrl().contains("careers") &&
                driver.getTitle().contains("Careers");
    }

    public boolean isLocationsBlockOpened() {
        return isDisplayed(locationsBlock);
    }

    public boolean isTeamsBlockOpened() {
        return isDisplayed(teamsBlock);
    }

    public boolean isLifeAtInsiderBlockOpened() {
        return isDisplayed(lifeAtInsiderBlock);
    }
}
