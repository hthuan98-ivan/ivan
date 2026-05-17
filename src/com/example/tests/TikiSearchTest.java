package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.TikiHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TikiSearchTest extends BaseTest {

    @Test
    public void testSearchAndVerifyProductTitleTag() {
        TikiHomePage tikiHomePage = new TikiHomePage(driver);
        String keyword = "iphone"; // Sử dụng chữ thường để nhất quán

        // 1. Truy cập trang Tiki
        tikiHomePage.navigateToHomePage("https://tiki.vn");

        // 2. Chờ và đóng quảng cáo pop-up nếu có (sử dụng locator linh hoạt)
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            By adCloseButtonLocator = By.xpath("//*[@id=\"VIP_BUNDLE\"]/div[2]/div/picture[1]/img");
            WebElement adCloseButton = wait.until(ExpectedConditions.elementToBeClickable(adCloseButtonLocator));
            System.out.println("Đã tìm thấy quảng cáo pop-up. Đang đóng...");
            adCloseButton.click();
            System.out.println("Đã đóng quảng cáo.");
            Thread.sleep(500); 
        } catch (Exception e) {
            System.out.println("Không tìm thấy quảng cáo pop-up. Tiếp tục thực hiện tìm kiếm.");
        }

        // 3. Thực hiện tìm kiếm
        System.out.println("Bắt đầu nhập từ khóa và tìm kiếm...");
        tikiHomePage.enterSearchKeyword(keyword);
        tikiHomePage.clickSearchButton();

        // 4. **LOGIC NÂNG CẤP: Chờ và xác minh sản phẩm bằng locator đáng tin cậy hơn**
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // XPath này tìm một thẻ <h3> chứa 'iphone' bên trong một liên kết <a> sản phẩm.
        // Đây là cách xác minh ổn định hơn nhiều so với việc dựa vào class CSS.
        By productTitleLocator = By.xpath(
            "//a[contains(@href, '-p')]//h3[contains(translate(., 'IPHONE', 'iphone'), 'iphone')]"
        );

        try {
            System.out.println("Đang chờ tìm sản phẩm có thẻ h3 chứa '" + keyword + "'...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(productTitleLocator));
            
            System.out.println("-> PASS: Đã tìm thấy ít nhất một sản phẩm có thẻ h3 chứa '" + keyword + "'.");
            Assert.assertTrue(true, "Test case thành công: tìm thấy sản phẩm hợp lệ.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy sản phẩm nào có thẻ h3 chứa '" + keyword + "' trên trang kết quả với locator mới.");
        }
    }
}
