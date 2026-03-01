package com.example.playwright.pages;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.example.playwright.pages.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Search Results Page - Handles Google search results interactions
 * 
 * Contains all elements and actions specific to Google's search results page
 * 
 * Benefits:
 * 1. Organized result handling
 * 2. Easy result verification
 * 3. Flexible result selection
 * 4. Clear navigation methods
 */
public class SearchResultsPage extends BasePage {
    
    // Locators for search results
    private static final String SEARCH_RESULTS_SELECTOR = "h3";
    private static final String RESULT_LINKS_SELECTOR = "h3 a, a h3";
    private static final String SEARCH_STATS_SELECTOR = "#result-stats";
    private static final String SEARCH_BOX_SELECTOR = "input[name='q'], textarea[name='q']";
    
    public SearchResultsPage(Page page, ExtentTest test) {
        super(page, test);
    }
    
    /**
     * Verify we're on the search results page
     */
    public SearchResultsPage verifyOnResultsPage() {
        // Verify URL contains search parameters
        Assert.assertTrue(verifyUrlContains("search?q=", "Search results page verification"));
        
        // Verify search results are present
        Locator results = waitForElement(SEARCH_RESULTS_SELECTOR, "Search Results");
        Assert.assertTrue(results.count() > 0, "Should have search results");
        
        test.log(Status.PASS, "Successfully verified search results page loaded");
        
        // Take screenshot of search results
        attachScreenshot("SearchResults", "Search Results Page");
        
        return this;
    }
    
    /**
     * Get the number of search results
     */
    public int getResultCount() {
        Locator results = page.locator(SEARCH_RESULTS_SELECTOR);
        int count = results.count();
        test.log(Status.INFO, "Found " + count + " search results");
        return count;
    }
    
    /**
     * Get search statistics (e.g., "About 1,000,000 results")
     */
    public String getSearchStats() {
        try {
            Locator statsElement = page.locator(SEARCH_STATS_SELECTOR);
            if (statsElement.isVisible()) {
                String stats = getElementText(statsElement, "Search Statistics");
                return stats;
            }
        } catch (Exception e) {
            test.log(Status.INFO, "Search statistics not available");
        }
        return "Statistics not available";
    }
    
    /**
     * Get all search result titles
     */
    public List<String> getAllResultTitles() {
        List<String> titles = new ArrayList<>();
        Locator results = page.locator(SEARCH_RESULTS_SELECTOR);
        
        int count = results.count();
        for (int i = 0; i < count; i++) {
            String title = results.nth(i).textContent();
            if (title != null && !title.trim().isEmpty()) {
                titles.add(title.trim());
            }
        }
        
        test.log(Status.INFO, "Retrieved " + titles.size() + " search result titles");
        return titles;
    }
    
    /**
     * Click on the first search result
     */
    public void clickFirstResult() {
        Locator results = page.locator(RESULT_LINKS_SELECTOR);
        Assert.assertTrue(results.count() > 0, "Should have clickable search results");
        
        // Get first result details
        Locator firstResult = results.first();
        String resultText = getElementText(firstResult, "First Search Result");
        
        // Click the first result
        clickElement(firstResult, "First Search Result: " + resultText);
        
        // Wait for navigation
        waitForPageLoad();
        
        // Take screenshot of destination page
        attachScreenshot("DestinationPage", "Page after clicking search result");
        
        test.log(Status.PASS, "Successfully clicked first search result");
    }
    
    /**
     * Click on a specific search result by index (0-based)
     */
    public void clickResultByIndex(int index) {
        Locator results = page.locator(RESULT_LINKS_SELECTOR);
        Assert.assertTrue(results.count() > index, "Result index " + index + " should exist");
        
        Locator targetResult = results.nth(index);
        String resultText = getElementText(targetResult, "Search Result #" + (index + 1));
        
        clickElement(targetResult, "Search Result #" + (index + 1) + ": " + resultText);
        waitForPageLoad();
        
        test.log(Status.PASS, "Successfully clicked search result #" + (index + 1));
    }
    
    /**
     * Click on search result containing specific text
     */
    public boolean clickResultContaining(String searchText) {
        Locator results = page.locator(RESULT_LINKS_SELECTOR);
        
        for (int i = 0; i < results.count(); i++) {
            Locator result = results.nth(i);
            String resultText = result.textContent();
            
            if (resultText != null && resultText.toLowerCase().contains(searchText.toLowerCase())) {
                test.log(Status.INFO, "Found matching result: " + resultText);
                clickElement(result, "Matching Result: " + resultText);
                waitForPageLoad();
                
                attachScreenshot("MatchingResultPage", "Page from matching result");
                return true;
            }
        }
        
        test.log(Status.FAIL, "No search result found containing: " + searchText);
        return false;
    }
    
    /**
     * Verify search results contain expected term
     */
    public SearchResultsPage verifyResultsContain(String expectedTerm) {
        List<String> titles = getAllResultTitles();
        
        boolean found = false;
        for (String title : titles) {
            if (title.toLowerCase().contains(expectedTerm.toLowerCase())) {
                found = true;
                test.log(Status.PASS, "Found expected term '" + expectedTerm + "' in result: " + title);
                break;
            }
        }
        
        Assert.assertTrue(found, "Should find '" + expectedTerm + "' in search results");
        return this;
    }
    
    /**
     * Perform a new search from results page
     */
    public SearchResultsPage searchAgain(String newTerm) {
        Locator searchBox = waitForElement(SEARCH_BOX_SELECTOR, "Search Box");
        
        // Clear existing search and enter new term
        searchBox.clear();
        fillText(searchBox, newTerm, "Search Box");
        pressKey(searchBox, "Enter", "submit new search");
        
        // Wait for new results
        page.waitForURL("**/search?q=*");
        waitForPageLoad();
        
        test.log(Status.INFO, "Performed new search for: " + newTerm);
        return this;
    }
    
    /**
     * Go back to Google home page
     */
    public GooglePage goBackToHomePage() {
        page.goBack();
        waitForPageLoad();
        
        test.log(Status.INFO, "Navigated back to Google home page");
        return new GooglePage(page, test);
    }
}