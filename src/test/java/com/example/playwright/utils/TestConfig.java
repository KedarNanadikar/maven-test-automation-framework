package com.example.playwright.utils;

import com.microsoft.playwright.BrowserType;

/**
 * Configuration Utility - Centralized test configuration
 * 
 * Benefits:
 * 1. Single place for all configuration
 * 2. Easy environment switching
 * 3. Consistent settings across tests
 * 4. Support for different browsers
 */
public class TestConfig {
    
    // Browser Configuration
    public static final boolean HEADLESS_MODE = false;  // Set to true for CI/CD
    public static final int SLOW_MO_DELAY = 500;        // Milliseconds between actions
    public static final int DEFAULT_TIMEOUT = 30000;    // 30 seconds
    
    // Browser Options
    public static BrowserType.LaunchOptions getBrowserOptions() {
        return new BrowserType.LaunchOptions()
            .setHeadless(HEADLESS_MODE)
            .setSlowMo(SLOW_MO_DELAY)
            .setTimeout(DEFAULT_TIMEOUT);
    }
    
    // Viewport Configurations
    public static class Viewport {
        public static final int DESKTOP_WIDTH = 1920;
        public static final int DESKTOP_HEIGHT = 1080;
        public static final int MOBILE_WIDTH = 375;
        public static final int MOBILE_HEIGHT = 667;
        public static final int TABLET_WIDTH = 768;
        public static final int TABLET_HEIGHT = 1024;
    }
    
    // Common URLs
    public static class URLs {
        public static final String GOOGLE = "https://www.google.com";
        public static final String EXAMPLE = "https://example.com";
    }
    
    // Test Data
    public static class SearchTerms {
        public static final String PLAYWRIGHT = "playwright automation";
        public static final String SELENIUM = "selenium webdriver";
        public static final String TESTING = "automated testing";
    }
    
    // File Paths
    public static class Paths {
        public static final String REPORTS_DIR = System.getProperty("user.dir") + "/test-reports";
        public static final String SCREENSHOTS_DIR = REPORTS_DIR + "/screenshots";
    }
}