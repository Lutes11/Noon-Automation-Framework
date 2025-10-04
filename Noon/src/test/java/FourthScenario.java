import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductPage;

public class FourthScenario extends BaseTest {

    @Test
    public void fourthScenario() {
        for (String[] row : csvFileManager.getRows()) {
            String query = row[0];
            String expectedMessage = row[1];

            ProductPage productPage = homePage.searchForProduct(query);

            String actualMessage = productPage.getNoResultsMessage();

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Message mismatch for query: " + query);
        }
    }
}

