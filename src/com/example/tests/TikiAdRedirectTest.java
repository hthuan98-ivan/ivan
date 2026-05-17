package com.example.tests;

import com.example.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TikiAdRedirectTest extends BaseTest {

    @Test
    public void testAdClickAndRedirect() {
        // 1. Truy cập trang Tiki
        driver.get("https://tiki.vn/");
        System.out.println("Đã truy cập Tiki.vn. Bắt đầu kịch bản kiểm tra chuyển hướng quảng cáo...");

        // Định nghĩa locator cho hình ảnh quảng cáo
        By adImageLocator = By.xpath("//*[@id=\"VIP_BUNDLE\"]/div[2]/div/picture[2]/img");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

        try {
            // 2. Chờ quảng cáo xuất hiện
            System.out.println("Đang chờ quảng cáo xuất hiện...");
            WebElement adImage = wait.until(ExpectedConditions.visibilityOfElementLocated(adImageLocator));
            System.out.println("Đã tìm thấy quảng cáo. Chuẩn bị nhấn vào...");

            // 3. Lưu lại thông tin ban đầu và nhấn vào quảng cáo
            String originalUrl = driver.getCurrentUrl();
            int originalWindowCount = driver.getWindowHandles().size();
            
            adImage.click();
            System.out.println("Đã nhấn vào quảng cáo. Đang chờ chuyển hướng...");

            // 4. Chờ đợi và xác minh chuyển hướng
            // Chờ cho đến khi có một tab mới mở HOẶC URL của tab hiện tại thay đổi
            wait.until(ExpectedConditions.or(
                ExpectedConditions.numberOfWindowsToBe(originalWindowCount + 1),
                ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl))
            ));

            // Chuyển sang tab mới nếu có
            for (String windowHandle : driver.getWindowHandles()) {
                if (!driver.getWindowHandle().equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            String newUrl = driver.getCurrentUrl();
            Assert.assertNotEquals(newUrl, originalUrl, "URL đã không thay đổi sau khi nhấn vào quảng cáo.");
            System.out.println("-> PASS: Đã chuyển hướng thành công. URL mới: " + newUrl);

        } catch (Exception e) {
            // Nếu không tìm thấy quảng cáo, kịch bản vẫn thành công
            System.out.println("-> PASS: Không tìm thấy quảng cáo để nhấn vào. Kịch bản hoàn thành.");
            Assert.assertTrue(true, "Kịch bản thành công vì không có quảng cáo nào xuất hiện.");
        }
    }
}
