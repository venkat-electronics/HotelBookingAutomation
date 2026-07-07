package com.hotel.pages;

import com.hotel.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultsPage extends BasePage {
    
    @FindBy(css = "[data-testid='property-card']")
    private List<WebElement> hotelCards;
    
    @FindBy(css = "[data-testid='title']")
    private List<WebElement> hotelTitles;
    
    @FindBy(css = "[data-testid='price-and-discounted-price']")
    private List<WebElement> hotelPrices;
    
    @FindBy(css = "[data-testid='rating-score']")
    private List<WebElement> ratings;
    
    @FindBy(css = "[data-testid='sort-bar']")
    private WebElement sortBar;
    
    @FindBy(linkText = "Price (lowest first)")
    private WebElement sortByPrice;
    
    @FindBy(css = "[data-testid='pagination']")
    private WebElement pagination;
    
    @FindBy(css = ".sr-hotel__name")
    private List<WebElement> hotelNames;
    
    public SearchResultsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public int getHotelCount() {
        return hotelCards.size();
    }
    
    public String getHotelTitle(int index) {
        if (index < hotelTitles.size()) {
            return getText(hotelTitles.get(index));
        }
        return null;
    }
    
    public String getHotelPrice(int index) {
        if (index < hotelPrices.size()) {
            return getText(hotelPrices.get(index));
        }
        return null;
    }
    
    public SearchResultsPage sortByPriceLowToHigh() {
        click(sortBar);
        click(sortByPrice);
        logger.info("Sorted results by price");
        return this;
    }
    
    public HotelDetailsPage selectHotel(int index) {
        if (index < hotelCards.size()) {
            click(hotelCards.get(index));
            logger.info("Selected hotel at index: {}", index);
        }
        return new HotelDetailsPage(driver);
    }
    
    public HotelDetailsPage selectHotelByName(String hotelName) {
        for (WebElement hotel : hotelNames) {
            if (getText(hotel).contains(hotelName)) {
                click(hotel);
                logger.info("Selected hotel: {}", hotelName);
                break;
            }
        }
        return new HotelDetailsPage(driver);
    }
    
    public boolean isHotelDisplayed(String hotelName) {
        for (WebElement hotel : hotelNames) {
            if (getText(hotel).contains(hotelName)) {
                return true;
            }
        }
        return false;
    }
}
