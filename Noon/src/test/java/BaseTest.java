
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import utilities.CSVFileManager;
import utilities.ExcelFileManager;
import utilities.JSONFileManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;
    SoftAssert softAssert;
    protected CSVFileManager csvFileManager;
    protected JSONFileManager jm;
    protected ExcelFileManager ex;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("disable-notifications");
//        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        softAssert = new SoftAssert();
        csvFileManager = new CSVFileManager("src/main/resources/fourth_scenario.csv");
        ex = new ExcelFileManager("src/main/resources/third_scenario.xlsx", "third_scenario");
        jm = new JSONFileManager("src/main/resources/second_scenario.json");

        goUrl();
    }

    public void goUrl() {
        driver.get("https://www.noon.com/egypt-en/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (driver != null && result.getStatus() == ITestResult.FAILURE) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                Path dir = Paths.get("target", "screenshots");   // مسار إخراج قياسي للتيستات
                Files.createDirectories(dir);
                String fileName = result.getMethod().getMethodName() + "_" + System.currentTimeMillis() + ".png";
                Path filePath = dir.resolve(fileName);
                Files.write(filePath, screenshot);

                Allure.addAttachment("Screenshot - " + result.getMethod().getMethodName(),
                        new ByteArrayInputStream(screenshot));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception ignored) {
                }
            }
        }
    }


}
