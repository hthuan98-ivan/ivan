package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TikiHomePage {

    private WebDriver driver;

    // Định nghĩa các locators
    private By searchInput = By.xpath("//input[@data-view-id='main_search_form_input']");
    private By searchButton = By.xpath("//button[@data-view-id='main_search_form_button']");

    public TikiHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHomePage(String url) {
        driver.get(url);
    }

    public void enterSearchKeyword(String keyword) {
        WebElement searchBox = driver.findElement(searchInput);
        searchBox.sendKeys(keyword);
    }

    public void clickSearchButton() {
        WebElement searchBtn = driver.findElement(searchButton);
        searchBtn.click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
