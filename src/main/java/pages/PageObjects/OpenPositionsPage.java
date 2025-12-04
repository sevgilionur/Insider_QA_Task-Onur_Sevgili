package pages.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseComponents.BasePage;

import java.util.List;

public class OpenPositionsPage extends BasePage {

    private By filterLocationDropdown = By.id("select2-filter-by-location-container");
    private By filterJobsDropdown= By.xpath("//*[@title=\"Quality Assurance\"]");
    private By jobList = By.id("jobs-list");
    private By jobListItems = By.xpath("//div[@id='jobs-list']//div[contains(@class, 'position-list-item')]");
    private By viewRoleButton = By.xpath(".//a[text()='View Role']"); // Liste elemanı içindeki buton
    private By jobTitleLocator = By.xpath(".//p[contains(@class, 'position-title')]");
    private By jobDepartmentLocator = By.xpath(".//span[contains(@class, 'position-department')]");
    private By jobLocationLocator = By.xpath(".//div[contains(@class, 'position-location')]");

    public OpenPositionsPage(WebDriver driver) {
        super(driver);
    }

    public void openQAPage() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
    }

    public void clickSeeAllQAJobs() {
        clickWithJS(By.xpath("//a[contains(text(), 'See all QA jobs')]"));
    }

    public void filterJobs(String location, String department) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterJobsDropdown));
        wait.until(ExpectedConditions.elementToBeClickable(filterLocationDropdown)).click();
        By locationOption = By.xpath("//li[contains(text(), '" + location + "') or contains(@id, '" + location + "')]");
        click(locationOption);
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobList));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(jobListItems, 0));
        By firstJobLocation = By.xpath("(//div[contains(@class, 'position-location')])[1]");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(firstJobLocation, location));
    }

        
    public boolean isJobListPresent() {
        return driver.findElements(jobList).size() > 0;
    }

    public boolean checkJobDetails(String expectedPositionKeyword, String expectedDept, String expectedLoc) {
        List<WebElement> jobs = driver.findElements(jobListItems);

        if (jobs.isEmpty()) {
            System.out.println("No jobs found!");
            return false;
        }

        boolean allMatch = true;

        for (WebElement job : jobs) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", job);

            String positionText = job.findElement(jobTitleLocator).getText();
            String departmentText = job.findElement(jobDepartmentLocator).getText();
            String locationText = job.findElement(jobLocationLocator).getText();

            System.out.println("Checking Job -> Position: " + positionText + " | Dept: " + departmentText + " | Loc: " + locationText);

            boolean isPositionValid = positionText.contains(expectedPositionKeyword) || positionText.contains("QA");
            boolean isDepartmentValid = departmentText.contains(expectedDept);
            boolean isLocationValid = locationText.contains(expectedLoc);

            if (!isPositionValid || !isDepartmentValid || !isLocationValid) {
                System.out.println("!!! ERROR!!!");
                System.out.println("Expected: " + expectedPositionKeyword + " | " + expectedDept + " | " + expectedLoc);
                System.out.println("Actual : " + positionText + " | " + departmentText + " | " + locationText);
                allMatch = false;
            }
        }
        return allMatch;
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