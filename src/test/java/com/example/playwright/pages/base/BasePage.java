package com.example.playwright.pages.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Base Page Class - Contains common page functionality
 * 
 * Benefits:
 * 1. Reusable methods across all pages
 * 2. Consistent screenshot handling
 * 3. Common wait strategies
 * 4. Standardized logging
 */
public abstract class BasePage {
    
    protected final Page page;
    protected final ExtentTest test;
    
    public BasePage(Page page, ExtentTest test) {
        this.page = page;
        this.test = test;
    }
    
    /**
     * Navigate to a specific URL with logging and verification
     */
    protected void navigateToUrl(String url, String pageName) {
        page.navigate(url);
        test.log(Status.INFO, "Navigated to " + pageName + ": " + url);
    }
    
    /**
     * Wait for element to be visible and log the action
     */
    protected Locator waitForElement(String selector, String elementName) {
        Locator element = page.locator(selector);
        element.waitFor(); // Playwright auto-waits
        test.log(Status.INFO, elementName + " is visible and ready");
        return element;
    }
    
    /**
     * Click element with logging
     */
    protected void clickElement(Locator element, String elementName) {
        element.click();
        test.log(Status.INFO, "Clicked on " + elementName);
    }
    
    /**
     * Fill text field with logging
     */
    protected void fillText(Locator element, String text, String fieldName) {
        element.fill(text);
        test.log(Status.INFO, "Entered '" + text + "' in " + fieldName);
    }
    
    /**
     * Press key with logging
     */
    protected void pressKey(Locator element, String key, String action) {
        element.press(key);
        test.log(Status.INFO, "Pressed " + key + " to " + action);
    }
    
    /**
     * Get element text with logging
     */
    protected String getElementText(Locator element, String elementName) {
        String text = element.textContent();
        test.log(Status.INFO, elementName + " text: " + text);
        return text;
    }
    
    /**
     * Verify element is visible
     */
    protected boolean isElementVisible(Locator element, String elementName) {
        boolean isVisible = element.isVisible();
        if (isVisible) {
            test.log(Status.PASS, elementName + " is visible as expected");
        } else {
            test.log(Status.FAIL, elementName + " is not visible");
        }
        return isVisible;
    }
    
    /**
     * Take screenshot with automatic naming and path management
     */
    public String captureScreenshot(String screenshotName) {
        try {
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String fileName = "POM_" + screenshotName + "_" + timestamp + ".png";
            String screenshotPath = System.getProperty("user.dir") + "/test-reports/screenshots/" + fileName;
            
            // Create screenshots directory
            File screenshotDir = new File(System.getProperty("user.dir") + "/test-reports/screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            // Take screenshot
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
            test.log(Status.INFO, "Screenshot captured: " + fileName);
            
            return screenshotPath;
        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Attach screenshot to ExtentReports
     */
    public void attachScreenshot(String screenshotName, String description) {
        String screenshotPath = captureScreenshot(screenshotName);
        if (screenshotPath != null) {
            try {
                test.addScreenCaptureFromPath(screenshotPath, description);
            } catch (IOException e) {
                test.log(Status.WARNING, "Failed to attach screenshot to report: " + e.getMessage());
            }
        }
    }
    
    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        page.waitForLoadState();
        test.log(Status.INFO, "Page loaded completely");
    }
    
    /**
     * Get current page URL
     */
    protected String getCurrentUrl() {
        String url = page.url();
        test.log(Status.INFO, "Current URL: " + url);
        return url;
    }
    
    /**
     * Get page title
     */
    protected String getPageTitle() {
        String title = page.title();
        test.log(Status.INFO, "Page title: " + title);
        return title;
    }
    
    /**
     * Verify URL contains expected text
     */
    protected boolean verifyUrlContains(String expectedText, String description) {
        String currentUrl = getCurrentUrl();
        boolean contains = currentUrl.contains(expectedText);
        
        if (contains) {
            test.log(Status.PASS, description + " - URL verification passed");
        } else {
            test.log(Status.FAIL, description + " - URL verification failed. Expected: " + expectedText + ", Actual: " + currentUrl);
        }
        
        return contains;
    }
}