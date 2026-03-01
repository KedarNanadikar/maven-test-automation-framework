package com.example.playwright.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * Base Test Class - Contains common setup and teardown for all tests
 * 
 * Benefits:
 * 1. Centralized browser management
 * 2. Consistent reporting setup
 * 3. Reusable across all test classes
 * 4. Easy configuration changes
 */
public abstract class BaseTest {
    
    // Static instances for browser management
    protected static Playwright playwright;
    protected static Browser browser;
    
    // Reporting instances
    protected static ExtentReports extent;
    protected static ExtentHtmlReporter htmlReporter;
    protected ExtentTest test;
    
    @BeforeSuite
    public void setUpFramework() {
        // Initialize reporting
        initializeReporting();
        
        // Initialize browser
        initializeBrowser();
    }
    
    @AfterSuite
    public void tearDownFramework() {
        // Clean up browser resources
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        
        // Finalize reports
        extent.flush();
    }
    
    @AfterMethod
    public void logTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
        } else {
            test.log(Status.PASS, "Test Passed");
        }
    }
    
    /**
     * Create a new page instance for each test
     * This ensures test isolation
     */
    protected Page createNewPage() {
        return browser.newPage();
    }
    
    /**
     * Initialize ExtentReports with standard configuration
     */
    private void initializeReporting() {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/test-reports/PlaywrightReport_" + timestamp + ".html";
        
        // Create reports directory
        File reportDir = new File(System.getProperty("user.dir") + "/test-reports");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        
        // Configure HTML reporter
        htmlReporter = new ExtentHtmlReporter(reportPath);
        htmlReporter.config().setDocumentTitle("Playwright Test Report");
        htmlReporter.config().setReportName("Page Object Model Tests");
        htmlReporter.config().setTheme(Theme.STANDARD);
        
        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Framework", "Playwright + Page Object Model");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }
    
    /**
     * Initialize browser with standard configuration
     * Override this method in subclasses for custom browser settings
     */
    protected void initializeBrowser() {
        playwright = Playwright.create();
        
        // Default browser configuration - can be overridden
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(false)  // Set to true for CI/CD
            .setSlowMo(500));    // Adjust based on needs
    }
}