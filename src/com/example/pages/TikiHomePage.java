package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class TikiHomePage {

    private WebDriver driver;

    // ... (các locators và methods khác) ...
    private final By SEARCH_INPUT = By.xpath("//input[@data-view-id='main_search_form_input']");
    private final By SEARCH_BUTTON = By.xpath("//button[@data-view-id='main_search_form_button']");
    private final By AD_IMAGE_TO_VERIFY = By.xpath("//*[@id=\"VIP_BUNDLE\"]/div[2]/div/picture[2]/img");
    private final By AD_CLOSE_BUTTON = By.xpath("//*[@id=\"VIP_BUNDLE\"]/div[2]/div/picture[1]/img");
    private final By PRODUCT_TITLE_ON_RESULT_PAGE(String keyword) {
        return By.xpath(
            String.format("//a[contains(@href, '-p')]//h3[contains(translate(., '%S', '%s'), '%s')]", keyword, keyword, keyword)
        );
    }
    // **LOCATOR MỚI**: Tìm sản phẩm đầu tiên trong lưới kết quả
    private final By FIRST_PRODUCT_LINK = By.xpath("(//a[contains(@href, '-p')])[1]");


    public TikiHomePage(WebDriver driver) { this.driver = driver; }
    public void navigateTo(String url) { driver.get(url); }
    public void enterSearchKeyword(String keyword) { driver.findElement(SEARCH_INPUT).sendKeys(keyword); }
    public void clickSearchButton() { driver.findElement(SEARCH_BUTTON).click(); }

    public void closeAdPopupIfPresent(WebDriverWait wait) {
        try {
            WebElement adCloseButton = wait.until(ExpectedConditions.elementToBeClickable(AD_CLOSE_BUTTON));
            System.out.println("Đã tìm thấy quảng cáo pop-up. Đang đóng...");
            adCloseButton.click();
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Không tìm thấy quảng cáo pop-up. Bỏ qua.");
        }
    }

    public void verifyAndCloseAd(WebDriverWait wait) { /* ... */ }
    public void verifyProductTitleExists(WebDriverWait wait, String keyword) { /* ... */ }
    public void clickAdAndVerifyRedirect(WebDriverWait wait) { /* ... */ }
    public void slowScrollToBottom() throws InterruptedException { /* ... */ }

    /**
     * **PHƯƠNG THỨC MỚI**: Chờ kết quả hiển thị và nhấp vào sản phẩm đầu tiên.
     * @param wait WebDriverWait instance
     */
    public void clickFirstProductInResults(WebDriverWait wait) {
        try {
            System.out.println("Đang chờ sản phẩm đầu tiên trong kết quả tìm kiếm...");
            WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(FIRST_PRODUCT_LINK));
            System.out.println("Đã tìm thấy sản phẩm đầu tiên. Đang nhấp vào...");
            firstProduct.click();
        } catch (Exception e) {
            Assert.fail("Không thể tìm thấy hoặc nhấp vào sản phẩm đầu tiên trong kết quả tìm kiếm.");
        }
    }
}