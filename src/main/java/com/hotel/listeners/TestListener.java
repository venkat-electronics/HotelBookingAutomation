package com.hotel.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.hotel.base.BaseTest;
import com.hotel.utils.ScreenshotUtil;

public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(">>>>> Starting test: " + result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(">>>>> Test PASSED: " + result.getName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(">>>>> Test FAILED: " + result.getName());
        try {
            String screenshotPath = ScreenshotUtil.captureScreenshot(
                BaseTest.getDriver(), 
                result.getName()
            );
            System.out.println("Screenshot captured: " + screenshotPath);
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(">>>>> Test SKIPPED: " + result.getName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("========================================");
        System.out.println("Test Suite Started: " + context.getName());
        System.out.println("========================================");
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("========================================");
        System.out.println("Test Suite Completed: " + context.getName());
        System.out.println("========================================");
    }
}
