package com.example.playwright.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.example.playwright.base.BaseTest;
import com.example.playwright.pages.GooglePage;
import com.example.playwright.pages.SearchResultsPage;
import com.microsoft.playwright.Page;

/**
 * Modern Playwright Tests using Page Object Model
 * 
 * COMPARE THIS TO THE OLD STRUCTURE:
 * 
 * OLD WAY (All-in-One):
 * - 300+ lines in one file
 * - Mixed concerns (page logic + test logic)
 * - Hard to maintain
 * - Difficult to reuse
 * 
 * NEW WAY (POM Structure):
 * - Clean, focused test methods
 * - Reusable page components
 * - Easy to maintain and extend
 * - Clear separation of concerns
 */
public class ImprovedPlaywrightTests extends BaseTest {
    
    @Test
    public void testGoogleSearchWithPageObjects() {
        test = extent.createTest("testGoogleSearchWithPageObjects", 
                                "Demonstrate improved structure with Page Object Model");
        
        // Create new page for test isolation
        Page page = createNewPage();
        
        try {
            test.log(Status.INFO, "Starting Google search test with Page Object Model");
            
            // LOOK HOW CLEAN AND READABLE THIS IS!
            GooglePage googlePage = new GooglePage(page, test);
            
            SearchResultsPage resultsPage = googlePage
                .open()                                    // Navigate to Google
                .verifyPageElements()                      // Verify page loaded correctly
                .searchFor("playwright automation");      // Perform search
            
            resultsPage
                .verifyOnResultsPage()                     // Verify results loaded
                .verifyResultsContain("playwright")       // Verify relevant results
                .clickFirstResult();                       // Click first result
            
            test.log(Status.PASS, "Page Object Model test completed successfully!");
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            
            // Screenshot on failure - use page's screenshot capability
            GooglePage googlePage = new GooglePage(page, test);
            googlePage.captureScreenshot("TestFailure");
            
            throw e; // Re-throw to fail the test
        } finally {
            page.close(); // Clean up page resources
        }
    }
    
    @Test
    public void testMultipleSearches() {
        test = extent.createTest("testMultipleSearches", 
                                "Test multiple searches using reusable page objects");
        
        Page page = createNewPage();
        
        try {
            GooglePage googlePage = new GooglePage(page, test);
            
            // First search
            SearchResultsPage resultsPage = googlePage
                .open()
                .searchFor("selenium automation");
            
            resultsPage
                .verifyOnResultsPage()
                .verifyResultsContain("selenium");
            
            // Second search from same page
            resultsPage
                .searchAgain("playwright vs selenium")
                .verifyResultsContain("playwright")
                .verifyResultsContain("selenium");
            
            test.log(Status.PASS, "Multiple search test completed successfully!");
            
        } finally {
            page.close();
        }
    }
    
    @Test
    public void testSearchAndReturn() {
        test = extent.createTest("testSearchAndReturn", 
                                "Test search and navigation back to home page");
        
        Page page = createNewPage();
        
        try {
            GooglePage googlePage = new GooglePage(page, test);
            
            // Search and then go back
            googlePage
                .open()
                .searchFor("java testing frameworks")
                .verifyOnResultsPage()
                .goBackToHomePage()         // This returns GooglePage!
                .verifyPageElements();      // Verify we're back on Google
            
            test.log(Status.PASS, "Search and return test completed successfully!");
            
        } finally {
            page.close();
        }
    }
    
    @Test
    public void testSpecificResultSelection() {
        test = extent.createTest("testSpecificResultSelection", 
                                "Test clicking specific search results");
        
        Page page = createNewPage();
        
        try {
            GooglePage googlePage = new GooglePage(page, test);
            
            SearchResultsPage resultsPage = googlePage
                .open()
                .searchFor("github playwright");
            
            // Try to click result containing "github"
            boolean found = resultsPage
                .verifyOnResultsPage()
                .clickResultContaining("github");
            
            if (found) {
                test.log(Status.PASS, "Successfully found and clicked GitHub result!");
            } else {
                // Fallback: click first result
                resultsPage.clickFirstResult();
                test.log(Status.INFO, "GitHub result not found, clicked first result instead");
            }
            
        } finally {
            page.close();
        }
    }
    
    @Test(groups = {"mobile"})
    public void testMobileViewSearch() {
        test = extent.createTest("testMobileViewSearch", 
                                "Test search functionality in mobile viewport");
        
        Page page = createNewPage();
        
        try {
            // Set mobile viewport - demonstrates configuration flexibility
            page.setViewportSize(375, 667);
            test.log(Status.INFO, "Set viewport to mobile size (375x667)");
            
            GooglePage googlePage = new GooglePage(page, test);
            
            googlePage
                .open()
                .searchFor("mobile web testing")
                .verifyOnResultsPage();
            
            // Take screenshot in mobile viewport
            GooglePage mobilePage = new GooglePage(page, test);
            mobilePage.captureScreenshot("MobileSearchResults");
            
            test.log(Status.PASS, "Mobile viewport test completed successfully!");
            
        } finally {
            page.close();
        }
    }
}