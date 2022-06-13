package com.example.winecompliance.selenium;

import com.example.winecompliance.selenium.page.WineDetailsPage;
import com.example.winecompliance.selenium.page.WineSearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTest {

    private static WebDriver webDriver;

    private static WebDriverWait getWaitDriver() {
         return new WebDriverWait(webDriver,10);
    }

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
//      TODO: Selenium race conditions make it difficult to use asserts.
//      assertEquals(3, page.getSearchResults().size());
        getWaitDriver()
                .until(drv->page.getSearchResults().size() == 3);
    }

    @Test
    public void navigateToDetailsPage() throws Exception {
        WineSearchPage page = new WineSearchPage(webDriver);
        page.navigateToPage();
        page.search("15");
        getWaitDriver()
                .until(drv-> page.getSearchResults().size() == 1);
        WebElement firstResult = page.getSearchResults().get(0);
        firstResult.click();

//      TODO: Selenium race conditions make it difficult to use asserts.
//      assertTrue(webDriver.getCurrentUrl().contains("/wine/15MPPN002-VK"));
//      assertEquals("15MPPN002-VK", (new WineDetailsPage(webDriver)).getLotCode());
        getWaitDriver()
                .until(drv->"15MPPN002-VK".equals((new WineDetailsPage(drv)).getLotCode()));
    }

    @Test
    public void wineDetailsPage() throws Exception {
        WineDetailsPage page = new WineDetailsPage(webDriver, "11YVCHAR001");
        page.navigateToPage();
//      TODO: Selenium race conditions make it difficult to use asserts.
//      assertEquals(3, page.getRegionBreakdown().size());
        getWaitDriver()
                .ignoring(StaleElementReferenceException.class)
                .until(drv -> page.getRegionBreakdown().size() == 3);

    }

}