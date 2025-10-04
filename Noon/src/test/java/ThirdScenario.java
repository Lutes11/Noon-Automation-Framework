import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ThirdScenario extends BaseTest {
    @Test
    public void thirdScenario() {
        int rows = ex.getRowCount();        // row 0 = headers
        if (rows <= 1) {
            throw new IllegalStateException("Excel has no data rows!");
        }

        Map<String, Double> expected = new LinkedHashMap<>();
        ProductPage lastPage = null;

        for (int r = 1; r < rows; r++) {
            String brand       = ex.getSpecificCellValue(r, 0).trim(); // brandNav
            String productName = ex.getSpecificCellValue(r, 1).trim(); // productName
            if (brand.isEmpty() || productName.isEmpty()) continue;

            // Navigate حسب البراند
            ProductPage page = switch (brand.toLowerCase()) {
                case "samsung" -> homePage.navigateToSamsungBrand();
                case "braun"   -> homePage.navigateToBraunBrand();
                case "toshiba" -> homePage.navigateToToshibaBrand();
                case "nilco"   -> homePage.navigateToNilcoBrand();
                default -> throw new IllegalArgumentException("Unknown brand: " + brand);
            };

            // ضيف المنتج واحتفظ بالاسم/السعر المتوقعين
            expected.putAll(page.addSpecificProductsToCart(List.of(productName)));
            lastPage = page;
        }

        Assert.assertNotNull(lastPage, "Navigation failed — no ProductPage visited.");

        Assert.assertEquals(lastPage.getCartBadgeCount(), expected.size(), "Cart badge mismatch!");

        CartPage cartPage = lastPage.openCart();
        Map<String, Double> actual = cartPage.getCartItems();

        expected.forEach((name, price) -> {
            Assert.assertTrue(actual.containsKey(name), "Missing in cart: " + name);
            Assert.assertEquals(actual.get(name), price, "Price mismatch for: " + name);
        });

        cartPage.checkout();
    }
}
