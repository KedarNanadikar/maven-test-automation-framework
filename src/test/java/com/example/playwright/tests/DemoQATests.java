package com.example.playwright.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.example.playwright.base.BaseTest;
import com.example.playwright.pages.DemoQAPage;
import com.microsoft.playwright.Page;

/**
 * DemoQA Test Suite - Comprehensive testing of DemoQA practice site
 * 
 * This demonstrates various web automation scenarios:
 * 1. Form interactions and validations
 * 2. Button click variations (single, double, right-click)
 * 3. Element verification and navigation
 * 4. Complex user workflows
 * 
 * Benefits of Page Object Model here:
 * - Clean, readable test methods
 * - Reusable page components
 * - Easy maintenance when DemoQA updates
 * - Clear separation of test logic vs page logic
 */
public class DemoQATests extends BaseTest {
    
    @Test(priority = 1)
    public void testDemoQAHomePageNavigation() {
        test = extent.createTest("testDemoQAHomePageNavigation", 
                                "Verify DemoQA home page loads and categories are visible");
        
        Page page = createNewPage();
        
        try {
            test.log(Status.INFO, "Starting DemoQA home page navigation test");
            
            DemoQAPage demoQAPage = new DemoQAPage(page, test);
            
            // Navigate and verify main page elements
            demoQAPage
                .open()                      // Load DemoQA home page
                .verifyMainCategories();     // Verify all 6 category cards are visible
            
            test.log(Status.PASS, "DemoQA home page navigation test completed successfully!");
            
        } finally {
            page.close();
        }
    }
    
    @Test(priority = 2)
    public void testTextBoxFormFunctionality() {
        test = extent.createTest("testTextBoxFormFunctionality", 
                                "Test text box form filling and submission with validation");
        
        Page page = createNewPage();
        
        try {
            test.log(Status.INFO, "Starting text box form functionality test");
            
            // Test data
            String fullName = "John Doe";
            String email = "john.doe@example.com";
            String currentAddress = "123 Main Street, City, State 12345";
            String permanentAddress = "456 Oak Avenue, Town, Country 67890";
            
            DemoQAPage demoQAPage = new DemoQAPage(page, test);
            
            // Complete form workflow
            demoQAPage
                .open()                                          // Load home page
                .goToTextBox()                                   // Navigate to text box section
                .fillTextBoxForm(fullName, email,                // Fill form with test data
                               currentAddress, permanentAddress)
                .submitTextBoxForm()                             // Submit form
                .verifyFormOutput(fullName, email);             // Verify output contains expected data
            
            test.log(Status.PASS, "Text box form functionality test completed successfully!");
            
        } finally {
            page.close();
        }
    }
    
    @Test(priority = 3)
    public void testButtonInteractions() {
        test = extent.createTest("testButtonInteractions", 
                                "Test different types of button click interactions");
        
        Page page = createNewPage();
        
        try {
            test.log(Status.INFO, "Starting button interactions test");
            
            DemoQAPage demoQAPage = new DemoQAPage(page, test);
            
            // Test all button interactions
            demoQAPage
                .open()                     // Load home page
                .goToButtons()              // Navigate to buttons section
                .testDoubleClick()          // Test double-click functionality
                .testRightClick()           // Test right-click functionality
                .testDynamicClick();        // Test regular click functionality
            
            test.log(Status.PASS, "Button interactions test completed successfully!");
            
        } finally {
            page.close();
        }
    }
    
    @Test(priority = 4)
    public void testFormValidationWithInvalidData() {
        test = extent.createTest("testFormValidationWithInvalidData", 
                                "Test form behavior with invalid email format");
        
        Page page = createNewPage();
        
        try {
            test.log(Status.INFO, "Starting form validation test with invalid data");
            
            // Test data with invalid email
            String fullName = "Jane Smith";
            String invalidEmail = "not-an-email";  // Invalid email format
            String currentAddress = "789 Test Street";
            String permanentAddress = "321 Demo Avenue";
            
            DemoQAPage demoQAPage = new DemoQAPage(page, test);
            
            demoQAPage
                .open()
                .goToTextBox()
                .fillTextBoxForm(fullName, invalidEmail, currentAddress, permanentAddress);
            
            // Note: DemoQA doesn't have strict email validation, but this demonstrates
            // how you would test validation in a real application
            test.log(Status.INFO, "Form filled with invalid email format: " + invalidEmail);
            
            // Take screenshot to document the state
            demoQAPage.attachScreenshot("InvalidEmailTest", "Form with invalid email format");
            
            test.log(Status.PASS, "Form validation test with invalid data completed!");
            
        } finally {
            page.close();
        }    
    }
    
    @Test(priority = 5)
    public void testCompleteUserWorkflow() {
        test = extent.createTest("testCompleteUserWorkflow", 
                                "Test complete user journey across multiple sections");
        
        Page page = createNewPage();
        
        try {
            test.log(Status.INFO, "Starting complete user workflow test");
            
            DemoQAPage demoQAPage = new DemoQAPage(page, test);
            
            // Simulate a complete user journey
            test.log(Status.INFO, "Step 1: Explore home page");
            demoQAPage
                .open()
                .verifyMainCategories();
            
            test.log(Status.INFO, "Step 2: Test form functionality");
            demoQAPage
                .goToTextBox()
                .fillTextBoxForm("Automation Tester", "tester@automation.com", 
                               "Test Address 1", "Test Address 2")
                .submitTextBoxForm()
                .verifyFormOutput("Automation Tester", "tester@automation.com");
            
            test.log(Status.INFO, "Step 3: Test button interactions");
            demoQAPage
                .goToButtons()
                .testDoubleClick()
                .testRightClick()
                .testDynamicClick();
            
            test.log(Status.PASS, "Complete user workflow test finished successfully!");
            
        } finally {
            page.close();
        }
    }
    
    @Test(priority = 6, groups = {"mobile"})
    public void testDemoQAInMobileView() {
        test = extent.createTest("testDemoQAInMobileView", 
                                "Test DemoQA functionality in mobile viewport");
        
        Page page = createNewPage();
        
        try {
            // Set mobile viewport
            page.setViewportSize(375, 667);
            test.log(Status.INFO, "Set viewport to mobile size for responsive testing");
            
            DemoQAPage demoQAPage = new DemoQAPage(page, test);
            
            // Test core functionality in mobile view
            demoQAPage.open();
            demoQAPage.attachScreenshot("MobileHomePage", "DemoQA home page in mobile view");
            demoQAPage.verifyMainCategories();
            demoQAPage.goToTextBox();
            demoQAPage.attachScreenshot("MobileTextBox", "Text box section in mobile view");
            demoQAPage.fillTextBoxForm("Mobile User", "mobile@test.com", "Mobile Address", "Mobile Permanent");
            demoQAPage.submitTextBoxForm();
            demoQAPage.attachScreenshot("MobileFormSubmitted", "Form submitted in mobile view");
            
            test.log(Status.PASS, "Mobile viewport test completed successfully!");
            
        } finally {
            page.close();
        }
    }
    
    @Test(priority = 7)
    public void testNavigationBetweenSections() {
        test = extent.createTest("testNavigationBetweenSections", 
                                "Test navigation flow between different DemoQA sections");
        
        Page page = createNewPage();
        
        try {
            test.log(Status.INFO, "Starting navigation between sections test");
            
            DemoQAPage demoQAPage = new DemoQAPage(page, test);
            
            // Test navigation flow
            demoQAPage.open();
            demoQAPage.clickElementsCategory();    // Go to Elements
            demoQAPage.attachScreenshot("ElementsCategory", "Elements category opened");
            demoQAPage.goToTextBox();              // Go to Text Box sub-section
            demoQAPage.goToButtons();              // Go to Buttons sub-section
            demoQAPage.testDoubleClick();         // Perform an action to verify we're in the right place
            
            test.log(Status.PASS, "Navigation between sections test completed successfully!");
            
        } finally {
            page.close();
        }
    }
}