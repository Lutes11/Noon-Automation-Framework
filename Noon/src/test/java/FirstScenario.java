import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ProductPage;

public class FirstScenario extends BaseTest {
    @DataProvider(name = "priceRanges")
    public Object[][] priceRanges() {
        return new Object[][]{
                {"1000", "3000"},
        };
    }

    @Test(dataProvider = "priceRanges")
    public void firstScenarioPriceFilter(String min, String max) {
        ProductPage productPage = homePage.navigateToSamsungBrand();
        Assert.assertEquals(productPage.getCategoryPageTitle(), "Samsung");
        productPage.filterPrice(min, max);
        productPage.validatePriceAcrossAllPages(min, max);

    }


    @Test
    public void firstScenarioSorting() {
        ProductPage productPage = homePage.navigateToSamsungBrand();
        Assert.assertEquals(productPage.getCategoryPageTitle(), "Samsung");
        productPage.filterPrice("1000", "3000");
        productPage.sortBestRated();
        productPage.validateSortingAcrossAllPages();

    }
}
