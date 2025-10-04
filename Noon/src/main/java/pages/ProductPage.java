package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.wait.ignoring(org.openqa.selenium.StaleElementReferenceException.class);
    }

    private final By pageBreadcrumb = By.xpath("//span[@class='Breadcrumb_active__nQtO3']");
    private final By priceFilterButton = By.xpath("//h3[normalize-space()='Price']");
    private final By minPriceField = By.cssSelector("input[name='min']");
    private final By maxPriceField = By.cssSelector("input[name='max']");
    private final By submitButton = By.cssSelector("button[type='submit']");
    private final By sortBar = By.xpath("//button[@class='DesktopSortDropdown_trigger__ky6e0']");
    private final By sortBestRated = By.xpath("//a[text()=\"Best Rated\"]");
    private final By productsCards = By.xpath("//div[@data-qa='plp-product-box']");
    private final By productName = By.xpath(".//h2[@data-qa='plp-product-box-name']");
    private final By productPrice = By.xpath(".//strong[contains(@class,'Price_amount')]");
    private final By productRate = By.xpath(".//div[@class='RatingPreviewStar_textCtr__sfsJG']");
    private final By nextButtonEnabled = By.cssSelector("a[aria-label='Next page']:not([aria-disabled='true'])");    private final By addToCartButton = By.xpath(".//button[@class=\"QuickAtc_atcCta__vfhXG\"]");
    private final By cartBadge = By.xpath("//span[@class='CartLinkBadge_counter__LiGCD']");
    private final By openCart = By.xpath("//a[@href='/egypt-en/cart/']");
    public  final By noResultsMessage = By.xpath("//div[@class=\"EmptyState_container__wQ12q\"]/child::h3");
    public static final Logger log = LogManager.getLogger(ProductPage.class);

    public String getCategoryPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageBreadcrumb)).getText();
    }

    public void filterPrice(String minPrice, String maxPrice) {
        wait.until(ExpectedConditions.elementToBeClickable(priceFilterButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(minPriceField)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(minPriceField)).sendKeys(minPrice);
        wait.until(ExpectedConditions.visibilityOfElementLocated(maxPriceField)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(maxPriceField)).sendKeys(maxPrice);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        waitForResults();
    }

    public void sortBestRated() {
        wait.until(ExpectedConditions.elementToBeClickable(sortBar)).click();
        wait.until(ExpectedConditions.elementToBeClickable(sortBestRated)).click();
        waitForResults();
    }

    List<WebElement> cards() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productsCards));
    }

    public void validatePricesInRange(String min, String max) {

        boolean allPricesInRange = cards().stream().allMatch(card -> {
            String priceText = card.findElement(productPrice).getText().trim()
                    .replace("EGP", "")
                    .replace(",", "");
            double price = Double.parseDouble(priceText);
            double minimum = Double.parseDouble(min);
            double maximum = Double.parseDouble(max);
            return price >= minimum && price <= maximum;
        });

        if (!allPricesInRange) {
            throw new AssertionError("Some products have prices outside the range " + min + " - " + max);
        }
    }

    public void validateSortedByRating() {

        List<Double> ratings = cards().stream()
                .map(card -> {
                    var elements = card.findElements(productRate);
                    return elements.isEmpty() ? null : Double.parseDouble(elements.get(0).getText().trim());
                })
                .filter(Objects::nonNull)
                .toList();

        for (int i = 0; i < ratings.size() - 1; i++) {
            if (ratings.get(i) < ratings.get(i + 1)) {
                throw new AssertionError("Products are not sorted by rating descending!");
            }
        }
    }


    public void validatePriceAcrossAllPages(String min, String max) {
        do {
            validatePricesInRange(min, max);

            List<WebElement> next = driver.findElements(nextButtonEnabled);

            if (next.isEmpty()) {
                break;
            }

            next.get(0).click();
            waitForResults();

        } while (true);
    }

    public void validateSortingAcrossAllPages() {
        do {
            validateSortedByRating();
            List<WebElement> next = driver.findElements(nextButtonEnabled);

            if (next.isEmpty()) {
                break;
            }

            next.get(0).click();
            waitForResults();
        } while (true);
    }


    private void waitForResults() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productsCards));
    }

    public Map<String, Double> addSpecificProductsToCart(List<String> targetProducts) {
        Map<String, Double> expectedProducts = new HashMap<>();

        while (true) {
            List<WebElement> cards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productsCards));

            for (WebElement card : cards) {
                String name = card.findElement(productName).getText().trim();

                if (targetProducts.contains(name) && !expectedProducts.containsKey(name)) {
                    double price = Double.parseDouble(card.findElement(productPrice).getText().trim().replace("EGP", "").replace(",", "").trim());
                    expectedProducts.put(name, price);

                    card.findElement(addToCartButton).click();
                }
            }

            if (expectedProducts.size() == targetProducts.size()) break;

            List<WebElement> nextBtns = driver.findElements(nextButtonEnabled);
            if (nextBtns.isEmpty() || !nextBtns.get(0).isDisplayed()) break;
            nextBtns.get(0).click();
            waitForResults();
        }

        return expectedProducts;
    }

    public int getCartBadgeCount() {
        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText().trim());
    }

    public CartPage openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(openCart)).click();
        return new CartPage(driver);
    }

    public String getNoResultsMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage)).getText().trim();
    }

    public boolean areProductsDisplayed() {
        return !driver.findElements(productsCards).isEmpty();
    }


}
