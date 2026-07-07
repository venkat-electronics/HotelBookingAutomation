package com.hotel.tests;

import com.hotel.base.BaseTest;
import com.hotel.pages.*;
import com.hotel.utils.ConfigReader;
import com.hotel.utils.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HotelBookingTest extends BaseTest {
    
    @Test(priority = 1)
    public void testCompleteBookingFlow() {
        // 1. Search for hotel
        HomePage homePage = new HomePage(driver);
        SearchResultsPage results = homePage.searchHotel(
            "Paris", "2026-08-15", "2026-08-20");
        
        // 2. Select hotel
        HotelDetailsPage hotelDetails = results.selectHotel(0);
        Assert.assertTrue(hotelDetails.isHotelDetailsDisplayed());
        logger.info("Hotel name: {}", hotelDetails.getHotelName());
        
        // 3. Go to booking
        BookingPage bookingPage = hotelDetails.clickBookNow();
        
        // 4. Fill booking details
        PaymentPage paymentPage = bookingPage.fillBookingDetails(
            TestDataGenerator.getFirstName(),
            TestDataGenerator.getLastName(),
            TestDataGenerator.getEmail(),
            TestDataGenerator.getPhoneNumber(),
            "Standard Room"
        );
        
        // 5. Make payment
        ConfirmationPage confirmation = paymentPage.makePayment(
            "4111111111111111",
            TestDataGenerator.getFirstName() + " " + TestDataGenerator.getLastName(),
            "12",
            "2028",
            "123"
        );
        
        // 6. Verify booking
        Assert.assertTrue(confirmation.isBookingConfirmed());
        Assert.assertNotNull(confirmation.getBookingNumber());
        logger.info("Booking confirmed! Booking number: {}", confirmation.getBookingNumber());
    }
    
    @Test(priority = 2)
    public void testBookingWithInvalidCard() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage results = homePage.searchHotel(
            "London", "2026-09-01", "2026-09-05");
        
        HotelDetailsPage hotelDetails = results.selectHotel(0);
        BookingPage bookingPage = hotelDetails.clickBookNow();
        
        PaymentPage paymentPage = bookingPage.fillBookingDetails(
            TestDataGenerator.getFirstName(),
            TestDataGenerator.getLastName(),
            TestDataGenerator.getEmail(),
            TestDataGenerator.getPhoneNumber(),
            "Standard Room"
        );
        
        // Try to pay with invalid card
        paymentPage.makePayment(
            "0000000000000000",  // Invalid card number
            TestDataGenerator.getFullName(),
            "13",  // Invalid month
            "2020",  // Expired year
            "123"
        );
        
        // Verify error message
        Assert.assertTrue(paymentPage.isPaymentErrorDisplayed());
        logger.info("Payment error validation successful");
    }
}
