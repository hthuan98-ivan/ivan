package com.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Chú ý: Đảm bảo bạn đã cấu hình WebDriverManager hoặc đặt chromedriver
        // trong PATH của hệ thống để không cần dòng System.setProperty này.
        // System.setProperty("webdriver.chrome.driver", "/path/to/your/chromedriver");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
