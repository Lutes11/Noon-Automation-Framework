import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;

import java.util.List;
import java.util.Map;

public class SecondScenario extends BaseTest {
    @Test
    public void secondScenario() {
        List<String> targetProducts = jm.getStringList("products");

        ProductPage productPage = homePage.searchForProduct("Headphones");

        Map<String, Double> expectedProducts = productPage.addSpecificProductsToCart(targetProducts);

        Assert.assertEquals(productPage.getCartBadgeCount(), targetProducts.size(),
                "Cart badge count mismatch!");


        CartPage cartPage = productPage.openCart();

        Map<String, Double> actualProducts = cartPage.getCartItems();
        Assert.assertEquals(actualProducts, expectedProducts,
                "Cart items mismatch between expected and actual!");
    }
}
