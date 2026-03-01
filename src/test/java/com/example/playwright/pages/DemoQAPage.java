package com.example.playwright.pages;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.example.playwright.pages.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * DemoQA Page - Practice site for web automation
 * 
 * DemoQA.com contains various elements for testing:
 * 1. Text boxes and forms
 * 2. Buttons and checkboxes
 * 3. Alerts and modals
 * 4. Drag & drop functionality
 * 5. Tables and accordions
 */
public class DemoQAPage extends BasePage {
    
    // Page URL
    private static final String DEMOQA_URL = "https://demoqa.com";
    
    // Main category cards
    private static final String ELEMENTS_CARD = "//h5[text()='Elements']";
    private static final String FORMS_CARD = "//h5[text()='Forms']";
    private static final String ALERTS_CARD = "//h5[text()='Alerts, Frame & Windows']";
    private static final String WIDGETS_CARD = "//h5[text()='Widgets']";
    private static final String INTERACTIONS_CARD = "//h5[text()='Interactions']";
    private static final String BOOK_STORE_CARD = "//h5[text()='Book Store Application']";
    
    // Elements section - Text Box
    private static final String TEXT_BOX_MENU = "//span[text()='Text Box']";
    private static final String FULL_NAME_INPUT = "#userName";
    private static final String EMAIL_INPUT = "#userEmail";
    private static final String CURRENT_ADDRESS_INPUT = "#currentAddress";
    private static final String PERMANENT_ADDRESS_INPUT = "#permanentAddress";
    private static final String SUBMIT_BUTTON = "#submit";
    private static final String OUTPUT_DIV = "#output";
    
    // Elements section - Buttons
    private static final String BUTTONS_MENU = "//span[text()='Buttons']";
    private static final String DOUBLE_CLICK_BTN = "#doubleClickBtn";
    private static final String RIGHT_CLICK_BTN = "#rightClickBtn";
    private static final String DYNAMIC_CLICK_BTN = "//button[text()='Click Me']";
    
    // Messages
    private static final String DOUBLE_CLICK_MESSAGE = "#doubleClickMessage";
    private static final String RIGHT_CLICK_MESSAGE = "#rightClickMessage";
    private static final String DYNAMIC_CLICK_MESSAGE = "#dynamicClickMessage";
    
    public DemoQAPage(Page page, ExtentTest test) {
        super(page, test);
    }
    
    /**
     * Navigate to DemoQA home page
     */
    public DemoQAPage open() {
        navigateToUrl(DEMOQA_URL, "DemoQA Practice Site");
        
        // Verify we're on DemoQA by checking if URL contains demoqa
        String currentUrl = getCurrentUrl();
        Assert.assertTrue(currentUrl.toLowerCase().contains("demoqa"), "Should be on DemoQA website");
        test.log(Status.PASS, "Successfully loaded DemoQA home page");
        
        // Take screenshot of home page
        attachScreenshot("DemoQAHomePage", "DemoQA Home Page Loaded");
        
        return this;
    }
    
    /**
     * Verify all main category cards are visible
     */
    public DemoQAPage verifyMainCategories() {
        test.log(Status.INFO, "Verifying main category cards");
        
        String[] categories = {"Elements", "Forms", "Alerts, Frame & Windows", 
                              "Widgets", "Interactions", "Book Store Application"};
        
        for (String category : categories) {
            Locator categoryCard = page.locator("//h5[text()='" + category + "']");
            Assert.assertTrue(isElementVisible(categoryCard, category + " Card"), 
                            category + " card should be visible");
        }
        
        test.log(Status.PASS, "All main category cards verified successfully");
        return this;
    }
    
    /**
     * Click on Elements category
     */
    public DemoQAPage clickElementsCategory() {
        Locator elementsCard = waitForElement(ELEMENTS_CARD, "Elements Category Card");
        clickElement(elementsCard, "Elements Category");
        
        waitForPageLoad();
        attachScreenshot("ElementsSection", "Elements section opened");
        
        return this;
    }
    
    /**
     * Navigate to Text Box section
     */
    public DemoQAPage goToTextBox() {
        clickElementsCategory();
        
        Locator textBoxMenu = waitForElement(TEXT_BOX_MENU, "Text Box Menu Item");
        clickElement(textBoxMenu, "Text Box Menu");
        
        waitForPageLoad();
        test.log(Status.INFO, "Navigated to Text Box section");
        
        return this;
    }
    
    /**
     * Fill text box form with user details
     */
    public DemoQAPage fillTextBoxForm(String fullName, String email, 
                                     String currentAddress, String permanentAddress) {
        test.log(Status.INFO, "Filling text box form with user details");
        
        // Fill Full Name
        Locator fullNameField = waitForElement(FULL_NAME_INPUT, "Full Name Field");
        fillText(fullNameField, fullName, "Full Name");
        
        // Fill Email
        Locator emailField = waitForElement(EMAIL_INPUT, "Email Field");
        fillText(emailField, email, "Email");
        
        // Fill Current Address
        Locator currentAddressField = waitForElement(CURRENT_ADDRESS_INPUT, "Current Address Field");
        fillText(currentAddressField, currentAddress, "Current Address");
        
        // Fill Permanent Address
        Locator permanentAddressField = waitForElement(PERMANENT_ADDRESS_INPUT, "Permanent Address Field");
        fillText(permanentAddressField, permanentAddress, "Permanent Address");
        
        test.log(Status.PASS, "Text box form filled successfully");
        
        return this;
    }
    
    /**
     * Submit the text box form
     */
    public DemoQAPage submitTextBoxForm() {
        Locator submitButton = waitForElement(SUBMIT_BUTTON, "Submit Button");
        clickElement(submitButton, "Submit Button");
        
        // Wait for output to appear
        Locator output = waitForElement(OUTPUT_DIV, "Form Output");
        Assert.assertTrue(isElementVisible(output, "Form Output"), "Output should be visible after form submission");
        
        attachScreenshot("FormSubmitted", "Form submitted with output displayed");
        test.log(Status.PASS, "Form submitted successfully with output displayed");
        
        return this;
    }
    
    /**
     * Verify form output contains expected values
     */
    public DemoQAPage verifyFormOutput(String expectedName, String expectedEmail) {
        Locator output = waitForElement(OUTPUT_DIV, "Form Output");
        String outputText = getElementText(output, "Form Output");
        
        Assert.assertTrue(outputText.contains(expectedName), "Output should contain name: " + expectedName);
        Assert.assertTrue(outputText.contains(expectedEmail), "Output should contain email: " + expectedEmail);
        
        test.log(Status.PASS, "Form output verification completed successfully");
        
        return this;
    }
    
    /**
     * Navigate to Buttons section
     */
    public DemoQAPage goToButtons() {
        clickElementsCategory();
        
        Locator buttonsMenu = waitForElement(BUTTONS_MENU, "Buttons Menu Item");
        clickElement(buttonsMenu, "Buttons Menu");
        
        waitForPageLoad();
        test.log(Status.INFO, "Navigated to Buttons section");
        attachScreenshot("ButtonsSection", "Buttons section loaded");
        
        return this;
    }
    
    /**
     * Test double click button
     */
    public DemoQAPage testDoubleClick() {
        Locator doubleClickBtn = waitForElement(DOUBLE_CLICK_BTN, "Double Click Button");
        
        // Perform double click
        doubleClickBtn.dblclick();
        test.log(Status.INFO, "Performed double click on button");
        
        // Verify message appears
        Locator message = waitForElement(DOUBLE_CLICK_MESSAGE, "Double Click Message");
        Assert.assertTrue(isElementVisible(message, "Double Click Message"), 
                         "Double click message should appear");
        
        String messageText = getElementText(message, "Double Click Message");
        Assert.assertTrue(messageText.contains("double click"), "Message should confirm double click");
        
        test.log(Status.PASS, "Double click test completed successfully");
        
        return this;
    }
    
    /**
     * Test right click button
     */
    public DemoQAPage testRightClick() {
        Locator rightClickBtn = waitForElement(RIGHT_CLICK_BTN, "Right Click Button");
        
        // Perform right click
        rightClickBtn.click(new Locator.ClickOptions().setButton(com.microsoft.playwright.options.MouseButton.RIGHT));
        test.log(Status.INFO, "Performed right click on button");
        
        // Verify message appears
        Locator message = waitForElement(RIGHT_CLICK_MESSAGE, "Right Click Message");
        Assert.assertTrue(isElementVisible(message, "Right Click Message"), 
                         "Right click message should appear");
        
        String messageText = getElementText(message, "Right Click Message");
        Assert.assertTrue(messageText.contains("right click"), "Message should confirm right click");
        
        test.log(Status.PASS, "Right click test completed successfully");
        
        return this;
    }
    
    /**
     * Test dynamic click button
     */
    public DemoQAPage testDynamicClick() {
        Locator dynamicClickBtn = waitForElement(DYNAMIC_CLICK_BTN, "Dynamic Click Button");
        clickElement(dynamicClickBtn, "Dynamic Click Button");
        
        // Verify message appears
        Locator message = waitForElement(DYNAMIC_CLICK_MESSAGE, "Dynamic Click Message");
        Assert.assertTrue(isElementVisible(message, "Dynamic Click Message"), 
                         "Dynamic click message should appear");
        
        String messageText = getElementText(message, "Dynamic Click Message");
        Assert.assertTrue(messageText.contains("dynamic click"), "Message should confirm dynamic click");
        
        test.log(Status.PASS, "Dynamic click test completed successfully");
        attachScreenshot("AllButtonsClicked", "All button interactions completed");
        
        return this;
    }
    
    /**
     * Navigate to Forms section
     */
    public DemoQAPage clickFormsCategory() {
        Locator formsCard = waitForElement(FORMS_CARD, "Forms Category Card");
        clickElement(formsCard, "Forms Category");
        
        waitForPageLoad();
        test.log(Status.INFO, "Navigated to Forms section");
        
        return this;
    }
    
    /**
     * Navigate to Alerts section
     */
    public DemoQAPage clickAlertsCategory() {
        Locator alertsCard = waitForElement(ALERTS_CARD, "Alerts Category Card");
        clickElement(alertsCard, "Alerts Category");
        
        waitForPageLoad();
        test.log(Status.INFO, "Navigated to Alerts section");
        
        return this;
    }
}