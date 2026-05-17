package com.example.tests;

import com.example.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class TikiScrollTest extends BaseTest {

    @Test
    public void testCloseAdAndSlowScrollToBottom() throws InterruptedException {
        // 1. Truy cập trang Tiki
        driver.get("https://tiki.vn/");
        System.out.println("Bắt đầu test case cuộn trang...");

        // 2. Kiểm tra và đóng quảng cáo nếu có
        try {
            // Chờ tối đa 5 giây để nút đóng quảng cáo xuất hiện và có thể nhấp được
            // Đây là một locator linh hoạt hơn, tìm bất kỳ thẻ div nào có class chứa 'Popup__Close'
            By adCloseButtonLocator = By.xpath("//div[contains(@class, 'Popup__Close')]");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement adCloseButton = wait.until(ExpectedConditions.elementToBeClickable(adCloseButtonLocator));
            
            System.out.println("Đã tìm thấy quảng cáo. Đang thử đóng...");
            adCloseButton.click();
            System.out.println("Đã đóng quảng cáo thành công.");
            // Chờ một chút để hiệu ứng đóng hoàn tất
            Thread.sleep(1000); 
        } catch (Exception e) {
            // Nếu không tìm thấy quảng cáo sau 5 giây, sẽ có exception, nhưng chúng ta chỉ cần ghi log và tiếp tục
            System.out.println("Không tìm thấy quảng cáo hoặc quảng cáo không thể đóng. Tiếp tục thực thi.");
        }

        // 3. Bắt đầu cuộn trang từ từ
        System.out.println("Bắt đầu cuộn trang...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long initialPageHeight = (long) js.executeScript("return document.body.scrollHeight");
        long currentScrollPosition = 0;
        int scrollStep = 150;
        int delay = 40;

        while (currentScrollPosition < initialPageHeight) {
            js.executeScript("window.scrollBy(0, " + scrollStep + ");");
            currentScrollPosition += scrollStep;
            initialPageHeight = (long) js.executeScript("return document.body.scrollHeight");
            Thread.sleep(delay);
        }

        System.out.println("Đã cuộn từ từ đến cuối trang. Test case hoàn thành.");
    }
}
