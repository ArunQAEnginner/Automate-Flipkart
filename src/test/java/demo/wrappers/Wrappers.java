package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import demo.TestCases;

import java.text.NumberFormat;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    ChromeDriver driver = TestCases.driver;
    

    public void FlipkartHomePage() throws InterruptedException{
        driver.get("https://www.flipkart.com/");
    }

    public void SearchTextBox(String ProductName) throws InterruptedException{
        WebElement SearchBox = driver.findElement(By.xpath("//input[@title='Search for Products, Brands and More']"));
        SearchBox.sendKeys(ProductName);
    }

    public void ClickSearchButton(){
        WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Search for Products, Brands and More']")));
        WebElement SearchIcon = driver.findElement(By.xpath("//button[@aria-label='Search for Products, Brands and More']"));
        SearchIcon.click();
    }

    public void ClickPopularity(){
        WebElement Popularity = driver.findElement(By.xpath("//div[text()='Popularity']"));
        Popularity.click();
    }

    public int CheckRatingLessThanOrEqualTo(float Rating){
        List<WebElement> Ratings = driver.findElements(By.xpath("//div[@class='XQDdHH']"));
        int count = 0 ;
        for(WebElement rating : Ratings){
           if(Float.valueOf(rating.getText()) <= Rating){
                count++;
           }
        }

        return count;
    }

    public void DiscountMoreThan(int Disount) throws InterruptedException{
        List<WebElement> Products = driver.findElements(By.xpath("//div[@class='yKfJKb row']/div[2]//span"));
        
        for(WebElement Product: Products){
            String[] discount = Product.getText().split("%");

            if(Integer.valueOf(discount[0]) > Disount){
                WebElement ProductName = Product.findElement(By.xpath(".//ancestor::div[@class='col col-5-12 BfVC2z']/preceding-sibling::div/div[1]"));
                System.out.println("Title of item : "+ProductName.getText()+" Discount % of item : "+discount[0]);
            }
                          
        }
    }

    public void SelectCustomerRating(int Rating) throws InterruptedException{
        Thread.sleep(4000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if(Rating >= 4){
            WebElement CheckboxRating4AndAbove = driver.findElement(By.xpath("(//div[text()='Customer Ratings']/parent::div/following-sibling::div//label/div[1])[1]"));
            //js.executeScript("arguments[0].scrollIntoView();",CheckboxRating4AndAbove);
            Thread.sleep(4000);
            CheckboxRating4AndAbove.click();
            Thread.sleep(4000);

        }
        else if(Rating >= 3){
            WebElement CheckboxRating3AndAbove = driver.findElement(By.xpath("(//div[text()='Customer Ratings']/parent::div/following-sibling::div//label/div[1])[2]"));
            CheckboxRating3AndAbove.click();
        }
        else if (Rating >= 2) {
            WebElement CheckboxRating2AndAbove = driver.findElement(By.xpath("(//div[text()='Customer Ratings']/parent::div/following-sibling::div//label/div[1])[3]"));
            CheckboxRating2AndAbove.click();
        }
        else if(Rating >= 1){
            WebElement CheckboxRating1AndAbove = driver.findElement(By.xpath("(//div[text()='Customer Ratings']/parent::div/following-sibling::div//label/div[1])[4]"));
            CheckboxRating1AndAbove.click();
        }
    }

    public void TopHighestNoOfReviews(int NoOfProducts){
        List<WebElement> ReviewsCountOnProducts = driver.findElements(By.xpath("//span[@class='Wphh3N']"));
        
        HashSet<Integer> set = new HashSet<>();
        for(WebElement ReviewsCountOnProduct : ReviewsCountOnProducts){
        String ReviewCount = ReviewsCountOnProduct.getText().replaceAll("[(),]", "");
        int Reviewcount = Integer.parseInt(ReviewCount);
        
            set.add(Reviewcount);
        }

        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list,Collections.reverseOrder());

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);

        for(int i=0;i<NoOfProducts;i++){
            String usformat = numberFormat.format(list.get(i));
            String ProductTitle = driver.findElement(By.xpath("(//span[text()='("+usformat+")']/parent::div/preceding-sibling::a)[2]")).getText();
            String ImageUrl = driver.findElement(By.xpath("//span[text()='("+usformat+")']/parent::div/preceding-sibling::a//img")).getAttribute("src");
            System.out.println("Title : "+ProductTitle+" ImageUrl : "+ImageUrl);
        }
    }


}
