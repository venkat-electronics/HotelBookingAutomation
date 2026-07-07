package com.hotel.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.hotel.utils.ConfigReader;
import com.hotel.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.edge.EdgeDriver;


import java.time.Duration;

public class BaseTest {
    
    protected static WebDriver driver;
    protected static Logger logger = LogManager.getLogger(BaseTest.class);
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    protected static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    
    @BeforeSuite
    public void setupExtentReport() {
        String reportPath = ConfigReader.getProperty("report.path") + "HotelReport.html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Hotel Booking Automation Report");
        spark.config().setReportName("Test Execution Report");
        
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", ConfigReader.getProperty("browser"));
        extent.setSystemInfo("Environment", "QA");
        
        logger.info("Extent Report initialized");
    }
    
    @BeforeMethod
    public void setupDriver() {
        String browser = ConfigReader.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless", "false"));
        
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        
        driverThread.set(driver);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicit.wait", "10"))));
        driver.manage().timeouts().pageLoadTimeout(
            Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("page.load.timeout", "30"))));
        
        // Navigate to URL
        driver.get(ConfigReader.getProperty("app.url"));
        logger.info("Browser initialized and navigated to URL");
        
        // Create Extent Test
        ExtentTest test = extent.createTest(this.getClass().getSimpleName());
        testThread.set(test);
    }
    
    @AfterMethod
    public void teardown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", result.getName());
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
            testThread.get().fail(result.getThrowable());
            testThread.get().addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("Test passed: {}", result.getName());
            testThread.get().pass("Test executed successfully");
        }
        
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }
    
    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent Report generated");
        }
    }
    
    public static WebDriver getDriver() {
        return driverThread.get();
    }
    
    public static ExtentTest getTest() {
        return testThread.get();
    }
}
