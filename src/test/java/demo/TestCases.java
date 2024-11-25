package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    public static ChromeDriver driver;

    @Test
    public void testCase01() throws InterruptedException{

        //testCase01: Go to www.flipkart.com. Search "Washing Machine".
        //Sort by popularity and print the count of items with rating less than or equal to 4 stars.
        Wrappers test1 = new Wrappers();
        test1.FlipkartHomePage();
        test1.SearchTextBox("Washing Machine");
        test1.ClickSearchButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Popularity']")));
        test1.ClickPopularity();
        Thread.sleep(3000);
        int count = test1.CheckRatingLessThanOrEqualTo(4);
        System.out.println("count of items with rating less than or equal to 4 stars : "+ count);
    }

    @Test
    public void testCase02() throws InterruptedException{
        //testCase02: Search "iPhone", print the Titles and discount % of items with more than 17% discount
        Wrappers test2 = new Wrappers();
        test2.FlipkartHomePage();
        Thread.sleep(4000);
        test2.SearchTextBox("iPhone");
        test2.ClickSearchButton();
        test2.DiscountMoreThan(17);

    }

    @Test
    public void testCase03() throws InterruptedException{
        //testCase03: Search "Coffee Mug", select 4 stars and above, 
        //and print the Title and image URL of the 5 items with highest number of reviews

        Wrappers test3 = new Wrappers();
        test3.FlipkartHomePage();
        Thread.sleep(4000);
        test3.SearchTextBox("Coffee Mug");
        test3.ClickSearchButton();
        test3.SelectCustomerRating(4);
        test3.TopHighestNoOfReviews(5);
        
    }
    
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}