package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class TikiProductDetailPage {

    private WebDriver driver;

    // **CẬP NHẬT**: Thay đổi locator từ By.id sang By.xpath để tìm theo văn bản của nút.
    private final By ADD_TO_CART_BUTTON = By.xpath("//button[@data-view-id='pdp_add_to_cart_button']");
    
    private final By ADDED_SUCCESS_POPUP = By.xpath("//div[contains(text(), 'Thêm vào giỏ hàng thành công')]");

    public TikiProductDetailPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Chờ và nhấp vào nút "Thêm vào giỏ hàng".
     * @param wait WebDriverWait instance
     */
    public void clickAddToCart(WebDriverWait wait) {
        try {
            System.out.println("Đang tìm nút 'Thêm vào giỏ' bằng văn bản...");
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_BUTTON));
            addToCartButton.click();
            System.out.println("Đã nhấp vào nút 'Thêm vào giỏ'.");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy hoặc không thể nhấp vào nút <button> có văn bản 'Thêm vào giỏ'.");
        }
    }

    /**
     * Xác minh rằng thông báo "Thêm vào giỏ hàng thành công" đã xuất hiện.
     * @param wait WebDriverWait instance
     */
    public void verifySuccessMessage(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ADDED_SUCCESS_POPUP));
            System.out.println("-> PASS: Đã xác nhận thông báo 'Thêm vào giỏ hàng thành công'.");
        } catch (Exception e) {
            Assert.fail("Không thấy thông báo 'Thêm vào giỏ hàng thành công' xuất hiện.");
        }
    }
}
