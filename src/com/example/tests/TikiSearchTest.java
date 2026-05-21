package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.TikiHomePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TikiSearchTest extends BaseTest {

    @Test(priority = 1)
    public void testSearchAndVerifyProduct() {
        // Sử dụng lại biến 'driver' trực tiếp từ BaseTest
        TikiHomePage homePage = new TikiHomePage(driver);
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String keyword = "iphone";

        homePage.navigateTo("https://tiki.vn");
        homePage.closeAdPopupIfPresent(shortWait);
        homePage.enterSearchKeyword(keyword);
        homePage.clickSearchButton();
        homePage.verifyProductTitleExists(longWait, keyword);
    }

    @Test(priority = 2)
    public void testSearchAndClickFirstProduct() {
        // Sử dụng lại biến 'driver' trực tiếp từ BaseTest
        TikiHomePage homePage = new TikiHomePage(driver);
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String keyword = "iphone";

        homePage.navigateTo("https://tiki.vn");
        homePage.closeAdPopupIfPresent(shortWait);
        homePage.enterSearchKeyword(keyword);
        homePage.clickSearchButton();

        String searchResultUrl = driver.getCurrentUrl();
        homePage.clickFirstProductInResults(longWait);

        try {
            longWait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(searchResultUrl)));
            String productDetailUrl = driver.getCurrentUrl();
            System.out.println("-> PASS: Đã chuyển hướng đến trang chi tiết sản phẩm thành công.");
            System.out.println("URL mới: " + productDetailUrl);
            Assert.assertNotEquals(productDetailUrl, searchResultUrl, "URL đã không thay đổi sau khi nhấp vào sản phẩm.");
        } catch (Exception e) {
            Assert.fail("Chuyển hướng đến trang chi tiết sản phẩm thất bại hoặc mất quá nhiều thời gian.");
        }
    }
}