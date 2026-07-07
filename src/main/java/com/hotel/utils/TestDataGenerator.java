package com.hotel.utils;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestDataGenerator {
    
    private static Faker faker = new Faker();
    
    public static String getFirstName() {
        return faker.name().firstName();
    }
    
    public static String getLastName() {
        return faker.name().lastName();
    }
    
    public static String getFullName() {
        return faker.name().fullName();
    }
    
    public static String getEmail() {
        return faker.internet().emailAddress();
    }
    
    public static String getPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }
    
    public static String getCity() {
        return faker.address().city();
    }
    
    public static String getCountry() {
        return faker.address().country();
    }
    
    public static String getDate(int daysInFuture) {
        Date futureDate = faker.date().future(daysInFuture, TimeUnit.DAYS);
        return new SimpleDateFormat("yyyy-MM-dd").format(futureDate);
    }
    
    public static String getCreditCardNumber() {
        return faker.finance().creditCard();
    }
    
    public static String getRandomNumber(int digits) {
        return faker.number().digits(digits);
    }
    
    public static BookingData getRandomBookingData() {
        return new BookingData(
            getFirstName(),
            getLastName(),
            getEmail(),
            getPhoneNumber(),
            getDate(30),
            getDate(35),
            2,
            1
        );
    }
    
    public static class BookingData {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String checkin;
        private String checkout;
        private int guests;
        private int rooms;
        
        public BookingData(String firstName, String lastName, String email, 
                          String phone, String checkin, String checkout, 
                          int guests, int rooms) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
            this.checkin = checkin;
            this.checkout = checkout;
            this.guests = guests;
            this.rooms = rooms;
        }
        
        // Getters
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getCheckin() { return checkin; }
        public String getCheckout() { return checkout; }
        public int getGuests() { return guests; }
        public int getRooms() { return rooms; }
    }
}
