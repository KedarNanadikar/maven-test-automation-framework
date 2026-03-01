package com.example.playwright;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * Playwright Integration Test - Modern Web Automation
 * 
 * Key Playwright Advantages:
 * 1. Auto-waiting: No need for explicit waits
 * 2. Fast execution: Faster than Selenium
 * 3. Modern API: More intuitive than Selenium
 * 4. Better reliability: Less flaky tests
 */
public class PlaywrightIntegrationTest {
    
    private static ExtentReports extent;
    private static ExtentHtmlReporter htmlReporter;
    private ExtentTest test;
    
    // Playwright objects - these replace Selenium's WebDriver
    private static Playwright playwright;
    private static Browser browser;
    
    @BeforeSuite
    public void setUpReport() {
        // Create timestamp for report
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/test-reports/PlaywrightReport_" + timestamp + ".html";
        
        // Create reports directory if it doesn't exist
        File reportDir = new File(System.getProperty("user.dir") + "/test-reports");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        
        // Initialize HTML reporter
        htmlReporter = new ExtentHtmlReporter(reportPath);
        htmlReporter.config().setDocumentTitle("Playwright Test Report");
        htmlReporter.config().setReportName("Modern Web Automation Results");
        htmlReporter.config().setTheme(Theme.STANDARD);
        
        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Automation Framework", "Playwright");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        
        // Initialize Playwright - this replaces WebDriverManager.setup()
        playwright = Playwright.create();
        
        // Launch browser - Playwright can launch Chrome, Firefox, Safari
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(false)  // Set to true for headless mode
            .setSlowMo(1000));   // Slow down actions for demo (remove for real tests)
    }
    
    @AfterSuite
    public void tearDownReport() {
        // Clean up Playwright resources
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        
        // Flush ExtentReports
        extent.flush();
    }
    
    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
        } else {
            test.log(Status.PASS, "Test Passed");
        }
    }
    
    /**
     * Take screenshot with Playwright - much simpler than Selenium!
     */
    public String captureScreenshot(Page page, String testName) {
        try {
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String screenshotPath = System.getProperty("user.dir") + "/test-reports/screenshots/PW_" + testName + "_" + timestamp + ".png";
            
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File(System.getProperty("user.dir") + "/test-reports/screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            // Playwright screenshot - much simpler than Selenium!
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
            
            return screenshotPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Test
    public void testPlaywrightBasicAPI() {
        test = extent.createTest("testPlaywrightBasicAPI", "Test Playwright basic functionality");
        test.log(Status.INFO, "Starting Playwright basic API test");
        
        // Create a new page (like opening a new tab)
        Page page = browser.newPage();
        
        try {
            // Navigate to a simple page
            page.navigate("https://example.com");
            test.log(Status.INFO, "Navigated to example.com");
            
            // Get page title - much simpler than Selenium
            String title = page.title();
            Assert.assertEquals(title, "Example Domain", "Title should match");
            test.log(Status.PASS, "Page title verified: " + title);
            
            // Take screenshot
            String screenshotPath = captureScreenshot(page, "ExamplePage");
            if (screenshotPath != null) {
                try {
                    test.addScreenCaptureFromPath(screenshotPath, "Example.com Page");
                } catch (IOException e) {
                    test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
                }
            }
            
        } finally {
            page.close();
        }
    }
    
    @Test(groups = {"web"})
    public void testGoogleSearchPlaywright() {
        test = extent.createTest("testGoogleSearchPlaywright", "Test Google search with Playwright - Compare to Selenium");
        
        // Create new page
        Page page = browser.newPage();
        
        try {
            test.log(Status.INFO, "Starting Playwright Google search test");
            
            // Navigate to Google - same as Selenium
            page.navigate("https://www.google.com");
            test.log(Status.INFO, "Navigated to Google.com");
            
            // Take screenshot
            String screenshotPath = captureScreenshot(page, "GoogleHomePage");
            if (screenshotPath != null) {
                try {
                    test.addScreenCaptureFromPath(screenshotPath, "Google Home Page (Playwright)");
                } catch (IOException e) {
                    test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
                }
            }
            
            // Find search box - Playwright uses Locators (better than Selenium's WebElement)
            // Multiple fallback selectors - Playwright advantage!
            Locator searchBox = page.locator("input[name='q'], textarea[name='q'], #APjFqb");
            
            // Wait for search box and validate it's visible - AUTO-WAITING!
            // No need for WebDriverWait like in Selenium
            Assert.assertTrue(searchBox.isVisible(), "Search box should be visible");
            test.log(Status.INFO, "Search box located successfully");
            
            // Type in search box - Playwright auto-waits for element to be ready
            searchBox.fill("playwright automation");  // fill() is better than sendKeys()
            test.log(Status.INFO, "Typed search term: playwright automation");
            
            // Press Enter - more reliable than submit()
            searchBox.press("Enter");
            test.log(Status.INFO, "Search submitted");
            
            // Wait for search results - Playwright auto-waits!
            page.waitForURL("**/search?q=*");
            test.log(Status.INFO, "Search results loaded");
            
            // Take screenshot of results
            screenshotPath = captureScreenshot(page, "SearchResults");
            if (screenshotPath != null) {
                try {
                    test.addScreenCaptureFromPath(screenshotPath, "Search Results (Playwright)");
                } catch (IOException e) {
                    test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
                }
            }
            
            // Find search results - more reliable selectors
            Locator searchResults = page.locator("h3");
            
            // Verify we have results - Playwright auto-waits for elements
            Assert.assertTrue(searchResults.count() > 0, "Should have search results");
            test.log(Status.INFO, "Found " + searchResults.count() + " search result headers");
            
            // Get first result text - easier than Selenium
            String firstResultText = searchResults.first().textContent();
            test.log(Status.INFO, "First result: " + firstResultText);
            
            // Click first result - Playwright auto-waits and scrolls if needed
            searchResults.first().click();
            test.log(Status.INFO, "Clicked first search result");
            
            // Wait for navigation - auto-waits for new page to load
            page.waitForLoadState();
            
            // Verify we navigated away from Google
            String currentUrl = page.url();
            Assert.assertFalse(currentUrl.contains("google.com/search"), 
                "Should have navigated away from Google search");
            test.log(Status.INFO, "Successfully navigated to: " + currentUrl);
            
            // Take final screenshot
            screenshotPath = captureScreenshot(page, "FinalPage");
            if (screenshotPath != null) {
                try {
                    test.addScreenCaptureFromPath(screenshotPath, "Final Page (Playwright)");
                } catch (IOException e) {
                    test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
                }
            }
            
            test.log(Status.PASS, "Playwright test completed successfully");
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            
            // Screenshot on failure
            String screenshotPath = captureScreenshot(page, "TestFailure");
            if (screenshotPath != null) {
                try {
                    test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
                } catch (IOException ex) {
                    test.log(Status.WARNING, "Failed to attach screenshot: " + ex.getMessage());
                }
            }
            
            Assert.fail("Test failed: " + e.getMessage());
        } finally {
            page.close();
        }
    }
    
    @Test
    public void testPlaywrightAdvancedFeatures() {
        test = extent.createTest("testPlaywrightAdvancedFeatures", "Demonstrate Playwright's advanced capabilities");
        
        Page page = browser.newPage();
        
        try {
            test.log(Status.INFO, "Testing Playwright advanced features");
            
            // Mobile emulation - Playwright can simulate mobile devices!
            page.setViewportSize(375, 667); // iPhone size
            test.log(Status.INFO, "Set viewport to mobile size");
            
            // Navigate with network interception capabilities
            page.navigate("https://httpbin.org/user-agent");
            
            // Get page content as JSON (Playwright can handle different content types)
            String content = page.textContent("pre");
            Assert.assertTrue(content.contains("Mozilla"), "Should contain user agent");
            test.log(Status.INFO, "User agent detected: " + content);
            
            // Screenshot with mobile viewport
            String screenshotPath = captureScreenshot(page, "MobileView");
            if (screenshotPath != null) {
                try {
                    test.addScreenCaptureFromPath(screenshotPath, "Mobile Viewport");
                } catch (IOException e) {
                    test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
                }
            }
            
        } finally {
            page.close();
        }
    }
}