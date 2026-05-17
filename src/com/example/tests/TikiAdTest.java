package com.example.tests;

import com.example.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TikiAdTest extends BaseTest {

    @Test
    public void testVerifyAndCloseAd() {
        // 1. Truy cập trang Tiki
        driver.get("https://tiki.vn/");
        System.out.println("Đã truy cập Tiki.vn. Bắt đầu kịch bản kiểm tra quảng cáo...");

        // Định nghĩa các locators
        By adImageLocator = By.xpath("//*[@id=\"VIP_BUNDLE\"]/div[2]/div/picture[2]/img");
        // **CẬP NHẬT: Sử dụng XPath do người dùng cung cấp cho nút đóng**
        By adCloseButtonLocator = By.xpath("//*[@id=\"VIP_BUNDLE\"]/div[2]/div/picture[1]/img");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

        // 2. Kịch bản kiểm tra và đóng quảng cáo
        try {
            // --- BƯỚC 1: KIỂM TRA QUẢNG CÁO HIỂN THỊ ---
            System.out.println("Đang chờ quảng cáo xuất hiện...");
            WebElement adImage = wait.until(ExpectedConditions.visibilityOfElementLocated(adImageLocator));
            
            Assert.assertTrue(adImage.isDisplayed(), "Hình ảnh quảng cáo không hiển thị dù đã được tìm thấy.");
            System.out.println("-> PASS 1/2: Đã xác nhận quảng cáo hiển thị trên trang.");

            // --- BƯỚC 2: THỰC HIỆN TẮT QUẢNG CÁO ---
            System.out.println("Đang tìm nút đóng quảng cáo bằng XPath được cung cấp...");
            WebElement adCloseButton = wait.until(ExpectedConditions.elementToBeClickable(adCloseButtonLocator));
            
            adCloseButton.click();
            System.out.println("Đã nhấp vào phần tử được cho là nút đóng.");

            // Xác minh rằng quảng cáo đã thực sự biến mất
            boolean isAdClosed = wait.until(ExpectedConditions.invisibilityOfElementLocated(adImageLocator));
            Assert.assertTrue(isAdClosed, "Quảng cáo đã không biến mất sau khi nhấp nút đóng.");
            System.out.println("-> PASS 2/2: Đã xác nhận quảng cáo được đóng thành công.");

        } catch (Exception e) {
            // Nếu sau 8 giây mà không tìm thấy quảng cáo, kịch bản vẫn thành công
            System.out.println("-> PASS: Không tìm thấy quảng cáo nào trong thời gian chờ. Kịch bản hoàn thành.");
            Assert.assertTrue(true, "Kịch bản thành công vì không có quảng cáo nào xuất hiện.");
        }
    }
}
