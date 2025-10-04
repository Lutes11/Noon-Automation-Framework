package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By cartItems = By.xpath("//div[@class=\"CartItemDesktop_cardWrapper__CXq9p\"]");
    private final By cartItemName = By.xpath(".//h1[@data-qa=\'cart-item-name\']");
    private final By cartItemPrice = By.xpath(".//b[@class=\"CartItemDesktop_unitPrice__rJ7Yh\"]");
    private final By checkoutButton = By.xpath("//button[@class=\"Button_button__GmNJL Button_primary__K3lZi\"]");
    public static final Logger log = LogManager.getLogger(CartPage.class);

    public Map<String, Double> getCartItems() {
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItems));

        return items.stream().collect(Collectors.toMap(
                item -> item.findElement(cartItemName).getText().trim(),
                item -> Double.parseDouble(item.findElement(cartItemPrice).getText().trim().replace("EGP", "").replace(",", "").trim())
        ));
    }

    public void checkout(){
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

}
