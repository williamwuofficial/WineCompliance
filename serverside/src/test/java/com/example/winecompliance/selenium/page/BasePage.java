package com.example.winecompliance.selenium.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class BasePage {

    protected String pageUrl;

    protected WebDriver driver;

    protected BasePage(WebDriver driver, boolean waitForPageLoad) {
        this.driver = driver;
        if (waitForPageLoad && this.findSpinner() != null) {
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.ignoring(StaleElementReferenceException.class)
                .until(drv -> this.findSpinner() == null);
        }
        refreshElements();
    }

    private WebElement findSpinner() {
        try {
            return this.driver.findElement(By.xpath("//div[@class='spinner-border']"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    protected BasePage(WebDriver driver) {
        this(driver, false);
    }

    public void navigateToPage() throws MalformedURLException {
        this.driver.navigate().to(new URL(pageUrl));
    }

    protected void refreshElements() {
        PageFactory.initElements(
                this.driver,
                this
        );
    }

}
