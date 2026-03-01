# 🚀 Modern Web Automation Framework

A comprehensive test automation framework showcasing **Selenium WebDriver** and **Playwright** integration with **Page Object Model** architecture, **ExtentReports**, and **TestNG**.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Running Tests](#running-tests)
- [Test Reports](#test-reports)
- [Framework Comparison](#framework-comparison)
- [Best Practices Demonstrated](#best-practices-demonstrated)
- [Contributing](#contributing)

## ✨ Features

### 🎯 **Dual Framework Support**
- **Selenium WebDriver** - Traditional, mature web automation
- **Playwright** - Modern, fast, reliable web automation
- **Side-by-side comparison** of both frameworks

### 🏗️ **Advanced Architecture**
- **Page Object Model (POM)** - Clean, maintainable test structure
- **Base classes** for code reusability
- **Method chaining** for fluent test writing
- **Configuration management** for easy environment switching

### 📊 **Professional Reporting**
- **ExtentReports** with HTML output
- **Automatic screenshots** on test steps and failures
- **Detailed test logs** with timestamps
- **Mobile viewport testing** documentation

### 🌐 **Test Coverage**
- **Google Search** automation (both Selenium & Playwright)
- **DemoQA** comprehensive testing suite
- **Form interactions** and validations
- **Button interactions** (click, double-click, right-click)
- **Mobile responsive** testing

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 8+ | Programming Language |
| **Maven** | 3.6+ | Build & Dependency Management |
| **Selenium** | 3.141.59 | Web Automation |
| **Playwright** | 1.40.0 | Modern Web Automation |
| **TestNG** | 6.14.3 | Test Framework |
| **ExtentReports** | 3.1.5 | Test Reporting |
| **WebDriverManager** | 5.8.0 | Browser Driver Management |

## 📁 Project Structure

```
📦 maven-java-project
├── 📁 src/
│   ├── 📁 main/java/
│   │   └── 📁 com/example/
│   │       └── App.java
│   └── 📁 test/java/
│       └── 📁 com/example/
│           ├── 📁 playwright/
│           │   ├── 📁 base/
│           │   │   └── BaseTest.java           # Common test setup
│           │   ├── 📁 pages/
│           │   │   ├── 📁 base/
│           │   │   │   └── BasePage.java       # Reusable page methods
│           │   │   ├── GooglePage.java         # Google page interactions
│           │   │   ├── SearchResultsPage.java  # Search results handling
│           │   │   └── DemoQAPage.java         # DemoQA site interactions
│           │   ├── 📁 tests/
│           │   │   ├── ImprovedPlaywrightTests.java # Google tests (POM)
│           │   │   └── DemoQATests.java        # DemoQA comprehensive tests
│           │   └── 📁 utils/
│           │       └── TestConfig.java         # Configuration management
│           ├── 📁 selenium/
│           │   └── SeleniumIntegrationTest.java # Selenium comparison tests
│           └── AppTest.java
├── 📁 test-reports/                           # Generated test reports
├── 📄 pom.xml                                 # Maven configuration
├── 📄 testng.xml                              # TestNG suite configuration
└── 📄 README.md                               # This file
```

## 📋 Prerequisites

- **Java 8+** installed
- **Maven 3.6+** installed
- **Git** installed
- **Modern web browser** (Chrome 135+ recommended)
- **IDE** (VS Code, IntelliJ IDEA, or Eclipse)

## 🚀 Setup Instructions

### 1. **Clone Repository**
```bash
git clone https://github.com/YOUR_USERNAME/maven-java-project.git
cd maven-java-project
```

### 2. **Install Dependencies**
```bash
mvn clean compile
```

### 3. **Download Browser Binaries (Playwright)**
```bash
# Playwright will auto-download browsers on first run
# No manual setup required!
```

### 4. **Verify Setup**
```bash
mvn test-compile
```

## 🧪 Running Tests

### **All Tests**
```bash
mvn test
```

### **Selenium Tests Only**
```bash
mvn test -Dtest=SeleniumIntegrationTest
```

### **Playwright Tests Only**
```bash
mvn test -Dtest=ImprovedPlaywrightTests
```

### **DemoQA Tests**
```bash
mvn test -Dtest=DemoQATests
```

### **Specific Test Method**
```bash
mvn test -Dtest=DemoQATests#testTextBoxFormFunctionality
```

### **Mobile Viewport Tests**
```bash
mvn test -Dgroups=mobile
```

### **Integration Tests**
```bash
mvn integration-test
```

## 📊 Test Reports

After running tests, find reports in:

- **📁 test-reports/**
  - `ExtentReport_TIMESTAMP.html` - Selenium test reports
  - `PlaywrightReport_TIMESTAMP.html` - Playwright test reports
  - **📁 screenshots/** - Test execution screenshots

### **Opening Reports**
```bash
# Windows
start test-reports/PlaywrightReport_*.html

# Mac/Linux
open test-reports/PlaywrightReport_*.html
```

## ⚖️ Framework Comparison

| Feature | **Selenium** | **Playwright** | **Winner** |
|---------|-------------|---------------|-----------|
| **Setup Complexity** | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | Playwright |
| **Speed** | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | Playwright |
| **Auto-waiting** | ⭐⭐ | ⭐⭐⭐⭐⭐ | Playwright |
| **Community Support** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | Selenium |
| **Cross-browser** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | Tie |
| **Mobile Testing** | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | Playwright |
| **API Testing** | ⭐⭐ | ⭐⭐⭐⭐⭐ | Playwright |

## 🏆 Best Practices Demonstrated

### **1. Page Object Model (POM)**
- ✅ Separation of page logic and test logic
- ✅ Reusable page components
- ✅ Easy maintenance and scalability
- ✅ Method chaining for readable tests

### **2. Test Organization**
- ✅ Base classes for common functionality
- ✅ Logical test grouping with TestNG
- ✅ Priority-based test execution
- ✅ Mobile viewport testing

### **3. Reporting & Documentation**
- ✅ Comprehensive test reports with screenshots
- ✅ Detailed logging for debugging
- ✅ Automatic screenshot capture on failures
- ✅ Test step documentation

### **4. Code Quality**
- ✅ Clean, readable test methods
- ✅ Consistent naming conventions
- ✅ Proper exception handling
- ✅ Resource cleanup (browser instances)

## 🎯 Test Scenarios Covered

### **Google Search Tests**
- ✅ Home page navigation
- ✅ Search functionality
- ✅ Results verification
- ✅ Link clicking and navigation

### **DemoQA Tests**
- ✅ Home page category verification
- ✅ Form filling and submission
- ✅ Button interactions (single, double, right-click)
- ✅ Text box validation
- ✅ Mobile viewport testing
- ✅ Navigation between sections

## 🔧 Configuration

Modify test behavior in:
- **📄 testng.xml** - Test suite configuration
- **📄 TestConfig.java** - Framework settings
- **📄 pom.xml** - Dependencies and build configuration

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

**⭐ If you found this project helpful, please star the repository!**

## Project Structure

```
.
├── .github/
│   └── copilot-instructions.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── example/
│   │               └── App.java
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           ├── AppTest.java (JUnit)
│       │           ├── AppTestNG.java (TestNG)
│       │           └── selenium/
│       │               ├── BaseSeleniumTest.java
│       │               ├── GoogleSearchTest.java
│       │               └── WebElementTest.java
│       └── resources/
│           └── testng.xml
├── pom.xml
└── README.md
```

## Requirements

- Java 8 or higher
- Maven 3.6 or higher
- Chrome or Firefox browser (for Selenium tests)

## Building the Project

To compile the project:
```bash
mvn compile
```

To run JUnit tests:
```bash
mvn test
```

To run TestNG and Selenium integration tests:
```bash
mvn integration-test
```

To run the Google search web test (requires compatible Chrome browser):
```bash
# Enable the web test by editing testng.xml, then run:
mvn test -Dgroups=web
```

## ExtentReports Usage

ExtentReports are automatically generated during test execution:

```bash
# Run tests and generate HTML reports
mvn clean test
mvn clean integration-test

# Open generated report
# Navigate to: test-reports/ExtentReport_[timestamp].html
```

**Key Features**:
- Detailed test execution logs
- Screenshots captured at key steps
- Automatic failure screenshots
- Test timing and duration
- System information
- Beautiful HTML reporting

To run all tests:
```bash
mvn clean test integration-test
```

To package the project:
```bash
mvn package
```

## Running the Application

To run the main application:
```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```

Or use the exec plugin configuration:
```bash
mvn exec:java
```

## Development

This project uses:
- Maven for build management
- JUnit 5 for unit testing
- TestNG for integration and web testing
- Selenium WebDriver for web automation
- WebDriverManager for automatic driver management
- Java 8 as the target version

## Dependencies

### Core Dependencies
- **Selenium WebDriver 3.141.59** - Web automation framework
- **TestNG 6.14.3** - Testing framework
- **WebDriverManager 4.4.3** - Automatic browser driver management
- **JUnit 5.9.2** - Unit testing framework
- **ExtentReports 3.1.5** - HTML test reporting
- **Apache Commons IO 2.11.0** - File operations for screenshots

### Browser Support
- Chrome/Chromium (headless and regular mode)
- Firefox (headless and regular mode)
- Automatic driver download and management

## Test Execution Results
- ✅ JUnit unit tests: **2 tests passed**
- ✅ TestNG unit tests: **2 tests passed** 
- ✅ Selenium integration tests: **3 tests passed**
- ✅ Total: **5 tests passed, 0 failures**
- ✅ **ExtentReports**: HTML reports with screenshots generated in `/test-reports/`

## Test Reports & Screenshots

**ExtentReports Features**:
- 📊 HTML test reports with detailed execution logs
- 📷 Automatic screenshots on test failure
- 📷 Step-by-step screenshots for web tests
- 🔄 Timestamped reports and screenshots
- 📊 Test execution timeline and statistics

**Report Location**: `test-reports/ExtentReport_[timestamp].html`  
**Screenshots**: `test-reports/screenshots/`

## Notes
- Web browser tests are disabled by default (set `enabled="false"` in testng.xml)
- To enable browser tests, change `enabled="true"` in [src/test/resources/testng.xml](src/test/resources/testng.xml)
- All tests run in headless mode for CI/CD compatibility
- **New**: Added Google search test that searches for "Flipkart" and clicks the first result
- **New**: ExtentReports with screenshots automatically generated in `test-reports/` directory
- Browser compatibility depends on installed Chrome version and WebDriverManager compatibility
- ExtentReports include detailed logs, screenshots, and execution timeline