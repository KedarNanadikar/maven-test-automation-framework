package com.example.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Selenium test for various web element interactions
 */
public class WebElementTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        
        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10); // Selenium 3 syntax
        
        driver.manage().window().maximize();
    }
    
    @Test
    public void testElementPresence() {
        driver.get("https://www.google.com");
        
        // Test if search box is present
        WebElement searchBox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );
        
        Assert.assertTrue(searchBox.isDisplayed(), "Search box should be displayed");
        Assert.assertTrue(searchBox.isEnabled(), "Search box should be enabled");
    }
    
    @Test
    public void testElementAttributes() {
        driver.get("https://www.google.com");
        
        WebElement searchBox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );
        
        // Test element attributes
        String nameAttribute = searchBox.getAttribute("name");
        Assert.assertEquals(nameAttribute, "q", "Search box name attribute should be 'q'");
        
        String tagName = searchBox.getTagName();
        Assert.assertEquals(tagName, "input", "Search box tag should be 'input'");
    }
    
    @Test
    public void testMultipleElements() {
        driver.get("https://www.google.com");
        
        // Find multiple elements (links)
        List<WebElement> links = driver.findElements(By.tagName("a"));
        
        Assert.assertTrue(links.size() > 0, "Page should contain at least one link");
        
        // Check if we can get text from links
        int visibleLinks = 0;
        for (WebElement link : links) {
            if (link.isDisplayed() && !link.getText().trim().isEmpty()) {
                visibleLinks++;
            }
        }
            
        Assert.assertTrue(visibleLinks > 0, "Should have at least one visible link with text");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}