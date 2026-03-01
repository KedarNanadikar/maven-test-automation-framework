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

/**
 * Selenium test for Google search functionality
 */
public class GoogleSearchTest {
    
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
    public void testGoogleSearch() {
        // Navigate to Google
        driver.get("https://www.google.com");
        
        // Find search box and enter search term
        WebElement searchBox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );
        
        String searchTerm = "Selenium WebDriver";
        searchBox.sendKeys(searchTerm);
        searchBox.submit();
        
        // Wait for results and verify
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search")));
        
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains(searchTerm), 
            "Page title should contain search term: " + searchTerm);
    }
    
    @Test
    public void testGooglePageTitle() {
        driver.get("https://www.google.com");
        
        String expectedTitle = "Google";
        String actualTitle = driver.getTitle();
        
        Assert.assertEquals(actualTitle, expectedTitle, 
            "Google page title should be 'Google'");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}