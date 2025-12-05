# Insider Quality Assurance Automation Task

This project contains an automated test suite for the [Insider](https://useinsider.com/) web application. The project focuses on verifying the "Careers" page functionality, specifically for the Quality Assurance department, validating job filtering, and application redirection.

## 🏗️ Project Architecture

The project follows the **Page Object Model (POM)** design pattern to ensure code reusability, maintainability and readability.

### Technology Stack
*   **Language:** Java 11
*   **Web Automation:** Selenium WebDriver (4.16.1)
*   **Test Runner:** TestNG (7.7.1)
*   **Build Tool:** Maven
*   **Driver Management:** WebDriverManager / Selenium Manager
*   **Logging:** Log4j2
*   **Reporting:** Allure Reports
*   **CI/CD:** GitHub Actions
*   **Code Quality:** Qodana

## 📋 Test Scenario

The automation script covers the following end-to-end flow:

1.  **Home Page Verification:**
    *   Visit `https://useinsider.com/` and verify the home page is opened.
2.  **Careers Page Navigation:**
    *   Navigate to **Company** > **Careers**.
    *   Verify that the Careers page, "Locations", "Teams", and "Life at Insider" blocks are visible.
3.  **Job Filtering (Quality Assurance):**
    *   Go to the Quality Assurance Careers page.
    *   Click "See all QA jobs".
    *   Filter jobs by **Location:** "Istanbul, Turkey" and **Department:** "Quality Assurance".
    *   Verify the presence of the job list.
4.  **Validation of Results:**
    *   Check that all listed jobs match the criteria:
        *   **Position** contains "Quality Assurance"
        *   **Department** contains "Quality Assurance"
        *   **Location** contains "Istanbul, Turkiye"
5.  **Redirection Check:**
    *   Click the "View Role" button on a job card.
    *   Verify that the user is redirected to the **Lever Application form page**.

## ✅ Implementation Details

*   **No BDD Frameworks:** As per requirements, no Cucumber/Gherkin frameworks were used. The focus is on pure Java + Selenium.
*   **Configuration Management:** Test data (URL, Location, Department) and browser settings are managed externally via `src/test/resources/global.properties` using a custom `ConfigReader` utility.
*   **Optimized Selectors:** Robust CSS Selectors and XPath locators are used for stability.
*   **Assertions:** TestNG assertions are utilized to validate test steps.
*   **Clean Code:** The project follows standard Java coding conventions and separates logic (Pages) from execution (Tests).

## 📂 Project Structure

```text
src
├── main
│   └── java
│       └── pages
│           ├── BaseComponents  # BasePage with common actions
│           └── PageObjects     # HomePage, CareersPage, OpenPositionsPage
├── test
│   ├── java
│   │   ├── tests           # BaseTest & InsiderTests (Test Execution)
│   │   └── utils           # Utilities (ConfigReader, TestListener)
│   └── resources
│       ├── global.properties # Test Configuration (Browser, URL, Data)
│       └── log4j2.xml        # Logging configuration
```

## 🚀 How to Run

### Prerequisites
*   Java JDK 11 or higher
*   Maven installed and configured
*   Chrome Browser (default)

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/Insider-QA-Task.git
cd Insider-QA-Task
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Run Tests
You can run the tests using Maven:
```bash
mvn clean test
```
*By default, the test runs on **Chrome**. You can change the browser in `src/test/resources/global.properties`.*

### 4. Generate Allure Report
After the test execution, generate and view the HTML report using:
```bash
mvn allure:serve
```

## 🤖 CI/CD Pipeline (GitHub Actions)

This project uses GitHub Actions for Continuous Integration.

### Workflows
1.  **Java CI with Maven (`maven.yml`):**
    *   Triggers on `push` and `pull_request`.
    *   Sets up JDK 11.
    *   Runs the Maven test suite.
    *   Executes tests in **Headless Mode** to ensure compatibility with CI environments.

2.  **Qodana Code Quality (`qodana_code_quality.yml`):**
    *   Analyzes the code for potential bugs, vulnerabilities, and maintainability issues using JetBrains Qodana.

### Checking Test Results in CI
1.  Go to the **Actions** tab in the GitHub repository.
2.  Select the latest workflow run.
3.  Check the console output for test execution logs.
4.  Artifacts (screenshots on failure) are uploaded if configured in the workflow.

## 📊 Configuration

You can configure the test execution details in `src/test/resources/global.properties`:

```properties
# Web Browser Configuration
browser=chrome
# Options: chrome, firefox, edge, safari

# Application URLs
baseUrl=https://useinsider.com/
qaPageUrl=https://useinsider.com/careers/quality-assurance/

# Headless Mode (Recommended for CI)
headless=false
```

## 👤 Author
**Onur Sevgili**