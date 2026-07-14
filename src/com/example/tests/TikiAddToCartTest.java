package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.TikiHomePage;
import com.example.pages.TikiProductDetailPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

// Đổi tên lớp để phù hợp với tên tệp mới
public class TikiAddToCartTest extends BaseTest {

    @Test
    public void testSearchScrollAndAddToCart() throws InterruptedException {
        // Khởi tạo các đối tượng cần thiết
        TikiHomePage homePage = new TikiHomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String keyword = "sách tiếng anh";

        // 1. Truy cập và tìm kiếm
        homePage.navigateTo("https://tiki.vn");
        homePage.enterSearchKeyword(keyword);
        homePage.clickSearchButton();

        // 2. Đợi 1 giây và cuộn xuống
        System.out.println("Đã tìm kiếm. Đang chờ 1 giây...");
        Thread.sleep(1000);
        System.out.println("Đang cuộn trang xuống...");
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");

        // 3. Nhấp vào sản phẩm đầu tiên
        homePage.clickFirstProductInResults(wait);

        // 4. Trên trang chi tiết, nhấp vào nút "Thêm vào giỏ"
        TikiProductDetailPage productPage = new TikiProductDetailPage(driver);
        productPage.clickAddToCart(wait);

        // 5. Nhấp thành công là PASS và kết thúc test.
        System.out.println("-> PASS: Đã nhấp thành công vào nút 'Thêm vào giỏ'. Test case kết thúc.");
        Assert.assertTrue(true, "Đã nhấp thành công vào nút 'Thêm vào giỏ'.");
    }
}
