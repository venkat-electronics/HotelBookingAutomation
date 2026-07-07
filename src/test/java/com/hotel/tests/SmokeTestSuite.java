package com.hotel.tests;

import com.hotel.base.BaseTest;
import com.hotel.pages.HomePage;
import com.hotel.pages.SearchResultsPage;
import com.hotel.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTestSuite extends BaseTest {
    
    @Test(priority = 1)
    public void testHomePageLoads() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
        logger.info("Home page loaded successfully");
    }
    
    @Test(priority = 2)
    public void testSearchFunctionality() {
        HomePage homePage = new HomePage(driver);
        String city = ConfigReader.getProperty("test.city", "Paris");
        SearchResultsPage results = homePage.searchHotel(city, "2026-08-15", "2026-08-20");
        
        int hotelCount = results.getHotelCount();
        Assert.assertTrue(hotelCount > 0, "Hotels should be found for: " + city);
        logger.info("Search functionality works. Found {} hotels", hotelCount);
    }
    
    @Test(priority = 3)
    public void testHotelDetailsPage() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage results = homePage.searchHotel("London", "2026-09-01", "2026-09-05");
        
        String hotelName = results.getHotelTitle(0);
        Assert.assertNotNull(hotelName, "Hotel name should not be null");
        logger.info("Hotel details page works. First hotel: {}", hotelName);
    }
}
