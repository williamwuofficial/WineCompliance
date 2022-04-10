package com.example.winecompliance.selenium.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WineDetailsPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement lotCode;

    @FindBy(xpath = "//h4")
    private WebElement description;

    @FindBy(xpath = "//table[contains(@class, 'region')]//tbody//tr")
    private List<WebElement> regionBreakdown;

    public WineDetailsPage(WebDriver driver, String lotCode) {
        super(driver, true);
        this.pageUrl = "http://localhost:8080/wine/" + lotCode;
    }

    public WineDetailsPage(WebDriver driver) {
        super(driver, true);
    }

    public String getLotCode() {
        return this.lotCode.getText();
    }

    public List<WebElement> getRegionBreakdown() {
        return this.regionBreakdown;
    }

}
