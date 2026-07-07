package com.hotel.tests;

import com.hotel.base.BaseTest;
import com.hotel.pages.HomePage;
import com.hotel.pages.SearchResultsPage;
import com.hotel.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HotelSearchTest extends BaseTest {
    
    @Test(priority = 1)
    public void testSearchHotel() {
        HomePage homePage = new HomePage(driver);
        
        String city = ConfigReader.getProperty("test.city", "Paris");
        String checkin = ConfigReader.getProperty("test.checkin.date", "2026-08-15");
        String checkout = ConfigReader.getProperty("test.checkout.date", "2026-08-20");
        
        SearchResultsPage results = homePage.searchHotel(city, checkin, checkout);
        
        int hotelCount = results.getHotelCount();
        Assert.assertTrue(hotelCount > 0, "No hotels found for: " + city);
        logger.info("Found {} hotels in {}", hotelCount, city);
    }
    
    @Test(priority = 2)
    public void testSearchHotelWithFilters() {
        HomePage homePage = new HomePage(driver);
        
        SearchResultsPage results = homePage.searchHotel(
            "London", "2026-09-01", "2026-09-05");
        
        results.sortByPriceLowToHigh();
        
        int hotelCount = results.getHotelCount();
        Assert.assertTrue(hotelCount > 0, "No hotels found");
        logger.info("Sorted results by price");
    }
    
    @Test(priority = 3)
    public void testValidateHotelDetails() {
        HomePage homePage = new HomePage(driver);
        
        SearchResultsPage results = homePage.searchHotel(
            "Dubai", "2026-10-01", "2026-10-07");
        
        String firstHotelName = results.getHotelTitle(0);
        Assert.assertNotNull(firstHotelName);
        logger.info("First hotel name: {}", firstHotelName);
    }
}
