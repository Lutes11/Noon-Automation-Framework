package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        this.actions = new Actions(driver);
    }

    private final By searchBar = By.cssSelector("#search-input");
    private final By electronicsTabLocator = By.cssSelector("li[data-qa='btn_main_menu_Electronics']");
    private final By samsungBrandLocator = By.cssSelector("a[href=\"/egypt-en/electronics-and-mobiles/samsung/\"]");
    private final By beautyAndFragranceTabLocator = By.cssSelector("li[data-qa='btn_main_menu_Beauty & Fragrance']");
    private final By braunBrandLocator = By.cssSelector("a[href=\"/egypt-en/braun/\"]");
    private final By homeAndAppliancesTabLocator = By.cssSelector("li[data-qa='btn_main_menu_Home & Appliances']");
    private final By toshibaBrandLocator = By.cssSelector("a[href=\"/egypt-en/home-and-kitchen/home-appliances-31235/toshiba/?limit=50&sort%5Bby%5D=popularity&sort%5Bdir%5D=desc\"]");
    private final By toysTabLocator = By.cssSelector("li[data-qa='btn_main_menu_Toys & Games']");
    private final By nilcoBrandLocator = By.cssSelector("a[href='/egypt-en/toys-and-games/nilco/']");


    public ProductPage navigateToSamsungBrand() {
        actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(electronicsTabLocator))).pause(Duration.ofSeconds(5)).perform();
        actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(samsungBrandLocator))).click().perform();
        return new ProductPage(driver);
    }

    public ProductPage navigateToBraunBrand() {
        actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(beautyAndFragranceTabLocator))).pause(Duration.ofSeconds(5)).perform();
        actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(braunBrandLocator))).click().perform();
        return new ProductPage(driver);
    }

    public ProductPage navigateToToshibaBrand() {
        actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(homeAndAppliancesTabLocator))).pause(Duration.ofSeconds(5)).perform();
        actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(toshibaBrandLocator))).click().perform();
        return new ProductPage(driver);
    }

    public ProductPage navigateToNilcoBrand() {
        actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(toysTabLocator))).pause(Duration.ofSeconds(5)).perform();
        actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(nilcoBrandLocator))).click().perform();
        return new ProductPage(driver);
    }

    public ProductPage searchForProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar)).sendKeys(productName, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar)).clear();
        return new ProductPage(driver);
    }


}
