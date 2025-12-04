package pages.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;

import java.util.List;

public class OpenPositionsPage extends BasePage {

    // Locators
    private By filterLocationDropdown = By.id("select2-filter-by-location-container");
    private By filterJobsDropdown= By.xpath("//*[@title=\"Quality Assurance\"]");
    private By istanbulOption = By.xpath("//li[contains(@id, 'Istanbul, Turkiye')]");
    private By jobList = By.id("jobs-list");
    private By jobPositionTitle = By.xpath("//p[text()='Software Quality Assurance Engineer (Remote)']");
    private By jobDepartment = By.xpath("(//span[text()='Quality Assurance'])[2]");
    private By jobLocation = By.xpath("//div[@class='position-location text-large']");
    private By viewRoleButton = By.xpath(".//a[text()='View Role']"); // Liste elemanı içindeki buton

    public OpenPositionsPage(WebDriver driver) {
        super(driver);
    }

    public void openQAPage() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
    }

    public void clickSeeAllQAJobs() {
        clickWithJS(By.xpath("//a[contains(text(), 'See all QA jobs')]"));
    }

    public void filterJobs() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterJobsDropdown));
        click(filterLocationDropdown);
        click(istanbulOption);
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobPositionTitle));
    }

    public boolean isJobListPresent() {
        return driver.findElements(jobList).size() > 0;
    }

    public boolean checkJobDetails(String expectedPosition, String expectedDept, String expectedLoc) {
        List<WebElement> jobs = driver.findElements(jobList);

        //wait.until(ExpectedConditions.visibilityOfElementLocated(jobPositionTitle));

        for (WebElement job : jobs) {
            String position = job.findElement(jobPositionTitle).getText();
            String department = job.findElement(jobDepartment).getText();
            String location = job.findElement(jobLocation).getText();

            System.out.println("Checking Job: " + position + " | " + department + " | " + location);

            if (!position.contains(expectedPosition) ||
                    !department.contains(expectedDept) ||
                    !location.contains(expectedLoc)) {
                return false;
            }
        }
        return true;
    }

    public void clickFirstViewRoleButton() {
        List<WebElement> jobs = driver.findElements(jobList);
        if (jobs.size() > 0) {
            WebElement btn = jobs.get(0).findElement(viewRoleButton);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
    }

    public boolean isRedirectedToLever() {
        switchToNewTab();
        return driver.getCurrentUrl().contains("jobs.lever.co");
    }
}