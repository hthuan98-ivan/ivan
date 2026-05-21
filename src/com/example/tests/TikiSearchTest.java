package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.TikiHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class TikiSearchTest extends BaseTest {

    @Test
    public void testSearchAndVerifyProduct() {
        // Khởi tạo các đối tượng cần thiết
        TikiHomePage homePage = new TikiHomePage(driver);
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String keyword = "iphone";

        // 1. Truy cập trang Tiki
        homePage.navigateTo("https://tiki.vn");

        // 2. Đóng quảng cáo (nếu có)
        homePage.closeAdPopupIfPresent(shortWait);

        // 3. Thực hiện tìm kiếm
        homePage.enterSearchKeyword(keyword);
        homePage.clickSearchButton();

        // 4. Xác minh kết quả
        homePage.verifyProductTitleExists(longWait, keyword);
    }
}
