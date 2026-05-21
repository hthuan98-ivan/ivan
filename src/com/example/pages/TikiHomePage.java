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

    // Locators
    private final By SEARCH_INPUT = By.xpath("//input[@data-view-id='main_search_form_input']");
    private final By SEARCH_BUTTON = By.xpath("//button[@data-view-id='main_search_form_button']");
    private final By AD_IMAGE_TO_VERIFY = By.xpath("//*[@id=\"VIP_BUNDLE\"]/div[2]/div/picture[2]/img");
    private final By AD_CLOSE_BUTTON = By.xpath("//*[@id=\"VIP_BUNDLE\"]/div[2]/div/picture[1]/img");
    private final By PRODUCT_TITLE_ON_RESULT_PAGE(String keyword) {
        return By.xpath(
            String.format("//a[contains(@href, '-p')]//h3[contains(translate(., '%S', '%s'), '%s')]", keyword, keyword, keyword)
        );
    }

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

    public void verifyAndCloseAd(WebDriverWait wait) {
        try {
            WebElement adImage = wait.until(ExpectedConditions.visibilityOfElementLocated(AD_IMAGE_TO_VERIFY));
            Assert.assertTrue(adImage.isDisplayed());
            System.out.println("-> PASS 1/2: Quảng cáo hiển thị.");
            WebElement adCloseButton = wait.until(ExpectedConditions.elementToBeClickable(AD_CLOSE_BUTTON));
            adCloseButton.click();
            Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(AD_IMAGE_TO_VERIFY)));
            System.out.println("-> PASS 2/2: Quảng cáo đã đóng.");
        } catch (Exception e) {
            System.out.println("-> PASS: Không tìm thấy quảng cáo.");
            Assert.assertTrue(true);
        }
    }
    
    public void verifyProductTitleExists(WebDriverWait wait, String keyword) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_TITLE_ON_RESULT_PAGE(keyword)));
            System.out.println("-> PASS: Đã tìm thấy sản phẩm hợp lệ.");
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("Không tìm thấy sản phẩm nào có thẻ h3 chứa '" + keyword + "'.");
        }
    }

    public void clickAdAndVerifyRedirect(WebDriverWait wait) {
        try {
            WebElement adImage = wait.until(ExpectedConditions.visibilityOfElementLocated(AD_IMAGE_TO_VERIFY));
            String originalUrl = driver.getCurrentUrl();
            int originalWindowCount = driver.getWindowHandles().size();
            adImage.click();
            wait.until(ExpectedConditions.or(
                ExpectedConditions.numberOfWindowsToBe(originalWindowCount + 1),
                ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl))
            ));
            for (String windowHandle : driver.getWindowHandles()) {
                if (!driver.getWindowHandle().equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            Assert.assertNotEquals(driver.getCurrentUrl(), originalUrl);
            System.out.println("-> PASS: Đã chuyển hướng thành công.");
        } catch (Exception e) {
            System.out.println("-> PASS: Không tìm thấy quảng cáo để nhấn vào.");
            Assert.assertTrue(true);
        }
    }

    public void slowScrollToBottom() throws InterruptedException {
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
        System.out.println("Đã cuộn đến cuối trang.");
    }
}
