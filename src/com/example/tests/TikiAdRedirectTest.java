package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.TikiHomePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class TikiAdRedirectTest extends BaseTest {

    @Test
    public void testAdClickAndRedirectScenario() {
        // Sử dụng lại biến 'driver' trực tiếp từ BaseTest
        TikiHomePage homePage = new TikiHomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

        homePage.navigateTo("https://tiki.vn");
        homePage.clickAdAndVerifyRedirect(wait);
    }
}
