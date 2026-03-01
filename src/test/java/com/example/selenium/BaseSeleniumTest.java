package com.example.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * Base test class for Selenium tests
 * Provides common setup and teardown functionality
 */
public class BaseSeleniumTest {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(String browser) {
        driver = createDriver(browser);
        wait = new WebDriverWait(driver, 10); // Selenium 3 syntax
        driver.manage().window().maximize();
    }
    
    private WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(chromeOptions);
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                return new FirefoxDriver(firefoxOptions);
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}