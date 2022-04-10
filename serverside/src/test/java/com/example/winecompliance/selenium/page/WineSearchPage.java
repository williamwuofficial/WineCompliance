package com.example.winecompliance.selenium.page;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.util.List;

public class WineSearchPage extends BasePage {

    @FindBy(xpath = "//input[contains(@class,'search-input')]")
    private WebElement searchInput;

    @FindBy(xpath = "//li[contains(@class,'list-group-item')]")
    private List<WebElement> searchResults;

    public WineSearchPage(WebDriver driver) {
        super(driver);
        this.pageUrl = "http://localhost:8080";
    }

    public void search(String searchInput) {
        this.searchInput.sendKeys(searchInput);
    }

    public List<WebElement> getSearchResults() {
        return this.searchResults;
    }


}
