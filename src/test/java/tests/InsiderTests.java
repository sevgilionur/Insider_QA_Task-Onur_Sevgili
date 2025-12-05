package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.PageObjects.CareersPage;
import pages.PageObjects.HomePage;
import pages.PageObjects.OpenPositionsPage;
import utils.ConfigReader;
import utils.TestListener;


@Listeners({TestListener.class})
public class InsiderTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(InsiderTests.class);

    @Test(description = "Insider Career Page and Job Filtering Test")
    public void testInsiderCareers() throws InterruptedException {

        logger.info("Step 1: Visit home page and check if opened");
        Assert.assertTrue(homePage.isHomePageOpened(), "Home page is not opened!");
        //homePage.acceptCookies(); need to activate it for --headless

        logger.info("Step 2: Check Career page, its Locations, Teams and Life at Insider blocks are open or not");
        homePage.goToCareersPage();
        CareersPage careersPage = new CareersPage(driver);
        Assert.assertTrue(careersPage.isPageOpened(), "Careers page is not opened! URL or Title mismatch.");
        Assert.assertTrue(careersPage.isLocationsBlockOpened(), "Locations block is not visible!");
        Assert.assertTrue(careersPage.isTeamsBlockOpened(), "Teams block is not visible!");
        Assert.assertTrue(careersPage.isLifeAtInsiderBlockOpened(), "Life at Insider block is not visible!");

        logger.info("Step 3: Go to QA Page, filter jobs and check list presence");
        OpenPositionsPage positionsPage = new OpenPositionsPage(driver);
        positionsPage.openQAPage(ConfigReader.getProperty("qaPageUrl"));
        positionsPage.clickSeeAllQAJobs();
        positionsPage.filterJobs("Istanbul, Turkiye", "Quality Assurance");
        Assert.assertTrue(positionsPage.isJobListPresent(), "Job list is empty after filtering!");

        logger.info("Step 4: Check all jobs' Position, Department and Location");
        boolean detailsMatch = positionsPage.checkJobDetails("Quality Assurance", "Quality Assurance", "Istanbul, Turkiye");
        Assert.assertTrue(detailsMatch, "Some jobs do not match the filter criteria!");

        logger.info("Step 5: Click View Role and check redirection to Lever");
        positionsPage.clickFirstViewRoleButton();
        Assert.assertTrue(positionsPage.isRedirectedToLever(), "Not redirected to Lever application page!");

    }
}
