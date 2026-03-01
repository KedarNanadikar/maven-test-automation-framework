package com.example.selenium;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Simple test to verify Selenium integration with ExtentReports and Screenshots
 */
public class SeleniumIntegrationTest {
    
    private static ExtentReports extent;
    private static ExtentHtmlReporter htmlReporter;
    private ExtentTest test;
    
    @BeforeSuite
    public void setUpReport() {
        // Create timestamp for report
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/test-reports/ExtentReport_" + timestamp + ".html";
        
        // Create reports directory if it doesn't exist
        File reportDir = new File(System.getProperty("user.dir") + "/test-reports");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        
        // Initialize HTML reporter
        htmlReporter = new ExtentHtmlReporter(reportPath);
        htmlReporter.config().setDocumentTitle("Selenium Test Report");
        htmlReporter.config().setReportName("Automated Test Results");
        htmlReporter.config().setTheme(Theme.STANDARD);
        
        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }
    
    @AfterSuite
    public void tearDownReport() {
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
     * Take screenshot and return the file path
     */
    public String captureScreenshot(WebDriver driver, String testName) {
        try {
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String screenshotPath = System.getProperty("user.dir") + "/test-reports/screenshots/" + testName + "_" + timestamp + ".png";
            
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File(System.getProperty("user.dir") + "/test-reports/screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            FileUtils.copyFile(sourceFile, destFile);
            
            return screenshotPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Test
    public void testSeleniumApiAvailable() {
        test = extent.createTest("testSeleniumApiAvailable", "Test Selenium API availability");
        test.log(Status.INFO, "Starting Selenium API test");
        
        // Test that Selenium classes can be instantiated
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        
        Assert.assertNotNull(options, "ChromeOptions should be creatable");
        test.log(Status.PASS, "ChromeOptions created successfully");
    }
    
    @Test
    public void testWebDriverManagerApiAvailable() {
        test = extent.createTest("testWebDriverManagerApiAvailable", "Test WebDriverManager API availability");
        test.log(Status.INFO, "Starting WebDriverManager API test");
        
        // Test that WebDriverManager API is accessible
        WebDriverManager chromeManager = WebDriverManager.chromedriver();
        
        Assert.assertNotNull(chromeManager, "WebDriverManager should be accessible");
        test.log(Status.PASS, "WebDriverManager API accessible");
    }
    
    @Test
    public void testTestNGAssertions() {
        test = extent.createTest("testTestNGAssertions", "Test TestNG assertion methods");
        test.log(Status.INFO, "Starting TestNG assertions test");
        
        String expected = "Hello TestNG";
        String actual = "Hello TestNG";
        
        Assert.assertEquals(actual, expected, "TestNG assertions should work");
        test.log(Status.PASS, "assertEquals passed");
        
        Assert.assertTrue(true, "TestNG assertTrue should work");
        test.log(Status.PASS, "assertTrue passed");
        
        Assert.assertFalse(false, "TestNG assertFalse should work");
        test.log(Status.PASS, "assertFalse passed");
    }
    
    @Test(groups = {"web"})
    public void testGoogleSearchFlipkartAndClickFirstResult() {
        test = extent.createTest("testGoogleSearchFlipkartAndClickFirstResult", "Test Google search for Flipkart and click first result");
        
        WebDriver webDriver = null;
        WebDriverWait webWait = null;
        
        try {
            test.log(Status.INFO, "Starting Google Flipkart search test");
            
            // Setup ChromeDriver using WebDriverManager - force latest version for Chrome 135
            WebDriverManager.chromedriver()
                .clearDriverCache()
                .clearResolutionCache()
                .setup();
            test.log(Status.INFO, "WebDriverManager setup completed with cache cleared and latest driver");
            
            // Configure Chrome options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito"); // Run in incognito mode
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            
            webDriver = new ChromeDriver(options);
            webWait = new WebDriverWait(webDriver, 10);
            webDriver.manage().window().maximize();
            test.log(Status.INFO, "Chrome browser launched successfully");
            
            // Navigate to Google
            webDriver.get("https://www.google.com");
            test.log(Status.INFO, "Navigated to Google.com");
            
            // Take screenshot after loading Google
            String screenshotPath = captureScreenshot(webDriver, "GoogleHomePage");
            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath, "Google Home Page");
            }
            
            // Find and interact with search box
            WebElement searchBox = webWait.until(
                ExpectedConditions.presenceOfElementLocated(By.name("q"))
            );
            test.log(Status.INFO, "Search box located successfully");
            
            // Search for "flipkart"
            String searchTerm = "flipkart";
            searchBox.sendKeys(searchTerm);
            searchBox.submit();
            test.log(Status.INFO, "Search submitted for: " + searchTerm);
            
            // Wait for search results to load by checking URL change
            webWait.until(ExpectedConditions.urlContains("search?q="));
            Thread.sleep(2000); // Allow page to fully load
            test.log(Status.INFO, "Search results loaded");
            
            // Take screenshot of search results
            screenshotPath = captureScreenshot(webDriver, "SearchResults");
            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath, "Search Results Page");
            }
            
            // Find all search result links that mention Flipkart
            List<WebElement> searchResults = webWait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//a[contains(text(), 'Flipkart') or contains(text(), 'flipkart')]")
                )
            );
            
            Assert.assertTrue(searchResults.size() > 0, "Should have at least one search result");
            test.log(Status.INFO, "Found " + searchResults.size() + " search results");
            
            // Click on the first search result
            WebElement firstResult = searchResults.get(0);
            String firstResultUrl = firstResult.getAttribute("href");
            
            Assert.assertNotNull(firstResultUrl, "First result should have a valid URL");
            Assert.assertTrue(firstResultUrl.contains("flipkart"), 
                "First result URL should contain 'flipkart': " + firstResultUrl);
            
            test.log(Status.INFO, "First result URL: " + firstResultUrl);
            
            // Click the first result
            firstResult.click();
            test.log(Status.INFO, "Clicked on first search result");
            
            // Wait for page to load and verify we're on a different page
            Thread.sleep(3000); // Simple wait for page load
            
            String currentUrl = webDriver.getCurrentUrl();
            test.log(Status.INFO, "Current URL after click: " + currentUrl);
            
            // Take screenshot of final page
            screenshotPath = captureScreenshot(webDriver, "FinalPage");
            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath, "Final Page After Click");
            }
            
            Assert.assertFalse(currentUrl.contains("google.com/search"), 
                "Should have navigated away from Google search page");
            Assert.assertTrue(currentUrl.toLowerCase().contains("flipkart"), 
                "Should be on Flipkart website or related page: " + currentUrl);
            
            test.log(Status.PASS, "Test completed successfully - navigated to Flipkart page");
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed with exception: " + e.getMessage());
            
            // Take screenshot on failure
            if (webDriver != null) {
                String screenshotPath = captureScreenshot(webDriver, "TestFailure");
                if (screenshotPath != null) {
                    try {
                        test.addScreenCaptureFromPath(screenshotPath, "Screenshot on Failure");
                    } catch (IOException ex) {
                        test.log(Status.WARNING, "Failed to attach screenshot: " + ex.getMessage());
                    }
                }
            }
            
            Assert.fail("Test failed with exception: " + e.getMessage());
        } finally {
            if (webDriver != null) {
                webDriver.quit();
                test.log(Status.INFO, "Browser closed");
            }
        }
    }
}