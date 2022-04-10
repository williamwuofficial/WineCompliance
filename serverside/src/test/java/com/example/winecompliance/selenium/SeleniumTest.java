package com.example.winecompliance.selenium;

import com.example.winecompliance.selenium.page.WineDetailsPage;
import com.example.winecompliance.selenium.page.WineSearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTest {

    private static WebDriver webDriver;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
//        options.addArguments("--headless");
//        options.addArguments("--disable-gpu");
        webDriver = new ChromeDriver(options);
    }

    @AfterAll
    static void teardown() {
        webDriver.quit();
    }

    @Test
    public void searchResults() throws Exception {
        WineSearchPage page = new WineSearchPage(webDriver);
        page.navigateToPage();
        page.search("1");
        assertEquals(3, page.getSearchResults().size());
    }

    @Test
    public void navigateToDetailsPage() throws Exception {
        WineSearchPage page = new WineSearchPage(webDriver);
        page.navigateToPage();
        page.search("15");
        WebElement firstResult = page.getSearchResults().get(0);
        firstResult.click();
        assertTrue(webDriver.getCurrentUrl().contains("/wine/15MPPN002-VK"));
        assertEquals("15MPPN002-VK", (new WineDetailsPage(webDriver)).getLotCode());
    }

    @Test
    public void wineDetailsPage() throws Exception {
        WineDetailsPage page = new WineDetailsPage(webDriver, "11YVCHAR001");
        page.navigateToPage();
        assertEquals(3, page.getRegionBreakdown().size());
    }

}