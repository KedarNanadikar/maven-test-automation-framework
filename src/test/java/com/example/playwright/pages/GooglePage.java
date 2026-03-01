package com.example.playwright.pages;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.example.playwright.pages.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Google Home Page - Page Object Model implementation
 * 
 * Contains all elements and actions specific to Google's home page
 * 
 * Benefits:
 * 1. Centralized locators - easy to maintain
 * 2. Reusable methods across tests
 * 3. Clear separation of page logic
 * 4. Easy to update when page changes
 */
public class GooglePage extends BasePage {
    
    // Page URL
    private static final String GOOGLE_URL = "https://www.google.com";
    
    // Locators - centralized and easy to maintain
    private static final String SEARCH_BOX_SELECTOR = "input[name='q'], textarea[name='q'], #APjFqb";
    private static final String SEARCH_BUTTON_SELECTOR = "input[value='Google Search'], button[aria-label='Google Search']";
    private static final String LUCKY_BUTTON_SELECTOR = "input[value=\"I'm Feeling Lucky\"]";
    
    public GooglePage(Page page, ExtentTest test) {
        super(page, test);
    }
    
    /**
     * Navigate to Google home page
     */
    public GooglePage open() {
        navigateToUrl(GOOGLE_URL, "Google Home Page");
        
        // Verify we're on Google
        Assert.assertTrue(getPageTitle().contains("Google"), "Should be on Google home page");
        test.log(Status.PASS, "Successfully loaded Google home page");
        
        // Take screenshot of home page
        attachScreenshot("GoogleHomePage", "Google Home Page Loaded");
        
        return this; // Method chaining
    }
    
    /**
     * Search for a term using the search box
     */
    public SearchResultsPage searchFor(String searchTerm) {
        test.log(Status.INFO, "Starting search for: " + searchTerm);
        
        // Find and interact with search box
        Locator searchBox = waitForElement(SEARCH_BOX_SELECTOR, "Search Box");
        
        // Verify search box is visible
        Assert.assertTrue(isElementVisible(searchBox, "Search Box"), "Search box should be visible");
        
        // Enter search term
        fillText(searchBox, searchTerm, "Search Box");
        
        // Press Enter to search
        pressKey(searchBox, "Enter", "submit search");
        
        // Wait for results page to load
        page.waitForURL("**/search?q=*");
        waitForPageLoad();
        
        test.log(Status.PASS, "Search completed successfully for: " + searchTerm);
        
        // Return SearchResultsPage object for method chaining
        return new SearchResultsPage(page, test);
    }
    
    /**
     * Click "I'm Feeling Lucky" button
     */
    public void clickImFeelingLucky() {
        Locator luckyButton = waitForElement(LUCKY_BUTTON_SELECTOR, "I'm Feeling Lucky button");
        clickElement(luckyButton, "I'm Feeling Lucky button");
        
        waitForPageLoad();
        test.log(Status.INFO, "Clicked I'm Feeling Lucky button");
    }
    
    /**
     * Verify Google page elements are present
     */
    public GooglePage verifyPageElements() {
        test.log(Status.INFO, "Verifying Google page elements");
        
        Locator searchBox = page.locator(SEARCH_BOX_SELECTOR);
        Assert.assertTrue(isElementVisible(searchBox, "Search Box"), "Search box should be present");
        
        // Verify page title
        String title = getPageTitle();
        Assert.assertTrue(title.contains("Google"), "Page title should contain 'Google'");
        
        test.log(Status.PASS, "All Google page elements verified successfully");
        return this;
    }
    
    /**
     * Get search suggestions (if any appear)
     */
    public void getSearchSuggestions(String partialTerm) {
        Locator searchBox = waitForElement(SEARCH_BOX_SELECTOR, "Search Box");
        fillText(searchBox, partialTerm, "Search Box");
        
        // Wait a moment for suggestions
        page.waitForTimeout(1000);
        
        Locator suggestions = page.locator("ul[role='listbox'] li");
        if (suggestions.count() > 0) {
            test.log(Status.INFO, "Found " + suggestions.count() + " search suggestions");
            
            // Log first few suggestions
            for (int i = 0; i < Math.min(3, suggestions.count()); i++) {
                String suggestionText = suggestions.nth(i).textContent();
                test.log(Status.INFO, "Suggestion " + (i + 1) + ": " + suggestionText);
            }
        } else {
            test.log(Status.INFO, "No search suggestions appeared");
        }
    }
}