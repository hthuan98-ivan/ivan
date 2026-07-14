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
    private final By FIRST_PRODUCT_LINK = By.xpath("(//a[contains(@href, '-p')])[1]");
    // **LOCATORS MỚI**
    private final By ADD_TO_CART_BUTTON_DETAIL_PAGE = By.xpath("//button[text()='Thêm vào giỏ']");
    private final By ADD_TO_CART_SUCCESS_POPUP = By.xpath("//div[text()='Thêm vào giỏ hàng thành công']");


    public TikiHomePage(WebDriver driver) { this.driver = driver; }
    public void navigateTo(String url) { driver.get(url); }
    public void enterSearchKeyword(String keyword) { driver.findElement(SEARCH_INPUT).sendKeys(keyword); }
    public void clickSearchButton() { driver.findElement(SEARCH_BUTTON).click(); }

    public void closeAdPopupIfPresent(WebDriverWait wait) { /* ... */ }
    public void verifyAndCloseAd(WebDriverWait wait) { /* ... */ }
    public void verifyProductTitleExists(WebDriverWait wait, String keyword) { /* ... */ }
    public void clickAdAndVerifyRedirect(WebDriverWait wait) { /* ... */ }
    public void slowScrollToBottom() throws InterruptedException { /* ... */ }
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

    /**
     * **PHƯƠNG THỨC MỚI**: Nhấp vào nút "Thêm vào giỏ" trên trang chi tiết.
     * @param wait WebDriverWait instance
     */
    public void clickAddToCartOnDetailPage(WebDriverWait wait) {
        try {
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_BUTTON_DETAIL_PAGE));
            addToCartButton.click();
        } catch (Exception e) {
            Assert.fail("Không tìm thấy hoặc không thể nhấp vào nút 'Thêm vào giỏ'.");
        }
    }

    /**
     * **PHƯƠNG THỨC MỚI**: Xác minh thông báo thành công xuất hiện.
     * @param wait WebDriverWait instance
     */
    public void verifyProductAddedToCartSuccessfully(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ADD_TO_CART_SUCCESS_POPUP));
            System.out.println("-> PASS: Đã xác nhận thông báo 'Thêm vào giỏ hàng thành công'.");
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("Không thấy thông báo 'Thêm vào giỏ hàng thành công' xuất hiện.");
        }
    }
}
