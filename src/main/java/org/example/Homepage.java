package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Homepage {
    protected static WebDriver driver;

    @BeforeMethod
    public void openBrowser() {
        //setting up chromedriver path from system
        System.setProperty("webdriver.chrome.driver","src\\test\\Resources\\Browser\\chromedriver.exe");
        //creating object for driver
        driver = new ChromeDriver();
        //maximise the browser window
        driver.manage().window().maximize();
        //applying implicitly wait to driver object
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        //open URL
        driver.get("https://demo.nopcommerce.com/");
    }
    @Test
    //test for registration verification
    public void verifyRegistration(){
        //timestamp for current time to create unique email value each time we run the program
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //click on registration button
        driver.findElement(By.linkText("Register")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //click on male option
        driver.findElement(By.xpath("//input[@id='gender-male']")).click();
        //creating web driver wait
        WebDriverWait wait=new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("register-button"))));
        //type first name
        driver.findElement(By.id("FirstName")).sendKeys("Bhumi");
        //type last name
        driver.findElement(By.id("LastName")).sendKeys("Patel");
        //select date of birth
        Select selectDay= new Select(driver.findElement(By.name("DateOfBirthDay")));
        selectDay.selectByVisibleText("5");
        //select date of month
        Select selectMonth=new Select(driver.findElement(By.name("DateOfBirthMonth")));
        selectMonth.selectByVisibleText("January");
        //select date of year
        Select selectYear= new Select(driver.findElement(By.name("DateOfBirthYear")));
        selectYear.selectByVisibleText("1912");
        //type the email with the timestamp
        driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("abpatel"+timestamp.getTime()+"@gmail.com");
        //type company name
        driver.findElement(By.xpath("//input[@name='Company']")).sendKeys("Acompany");
        //click on the newsletter
        driver.findElement(By.xpath("//input[@type='checkbox']")).click();
        //type password
        driver.findElement(By.id("Password")).sendKeys("abc123");
        //type confirm password
        driver.findElement(By.id("ConfirmPassword")).sendKeys("abc123");
        //click on registration
        driver.findElement(By.name("register-button")).click();
        //compare the actual and expected by using Assert
        String expectedTitleText="Your registration completed";
        String titleTextActual=driver.findElement(By.xpath("//div[@class='result']")).getText();
        System.out.println(titleTextActual);
        Assert.assertEquals(titleTextActual,expectedTitleText,"Your registration completed");

    }
    @Test
    //verify the Apple mac book price
    public void verifyAppleMacBookPrice(){
        //finding price element by locator
        driver.findElement(By.xpath("//div[@class='item-box'][2]//span[@class='price actual-price']"));
        //compare the expected and actual by using assert
        String expectedTitleText="$2000";
        String titleTextActual=driver.findElement(By.xpath("//div[@class='item-box'][2]//span[@class='price actual-price']")).getText();
        System.out.println(titleTextActual);
        Assert.assertEquals(titleTextActual,expectedTitleText,"Price not matched.");

    }
    @Test
    //Verify the built your own computer is display in shopping cart
    public void verifyBuiltOwnComputerDisplay(){
        //click on add to cart button
        driver.findElement(By.linkText("Build your own computer")).click();
        //select the processor
        Select selectProcessor= new Select(driver.findElement(By.id("product_attribute_1")));
        //select the RAM size
        selectProcessor.selectByVisibleText("2.2 GHz Intel Pentium Dual-Core E2200");
        Select selectRam= new Select(driver.findElement(By.id("product_attribute_2")));
        selectRam.selectByVisibleText("2 GB");
        //select  320 GB on HDD
        driver.findElement(By.id("product_attribute_3_6")).click();
        //select os
        driver.findElement(By.id("product_attribute_4_8")).click();
        driver.findElement(By.id("product_attribute_5_10"));
        //click on given option
        driver.findElement(By.id("product_attribute_5_11")).click();
        driver.findElement(By.id("product_attribute_5_12")).click();
        //click on add to cart button
        driver.findElement(By.id("add-to-cart-button-1")).click();
        //compare actual and expected by using assert
        String expectedTitleText="The product has been added to your shopping cart";
        String titleTextActual=driver.findElement(By.xpath("//div[@class='bar-notification success']")).getText();
        System.out.println(titleTextActual);
        Assert.assertEquals(titleTextActual,expectedTitleText,"Product is displayed");
        driver.findElement(By.xpath("//p[@class='content']/a[@href='/cart']")).click();
    }
   @Test
   //compare any two products in compare list
   public void verifyByCompareTwoProducts(){
        //click on compare button
        driver.findElement(By.xpath("//button[@onclick='return AjaxCart.addproducttocomparelist(\"/compareproducts/add/4\"),!1']")).click();
       try {
           Thread.sleep(5000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       //click on second product's compare button
       driver.findElement(By.xpath("//button[@onclick='return AjaxCart.addproducttocomparelist(\"/compareproducts/add/43\"),!1']")).click();
       //assert for compare two products
       String expectedTitleText="The product has been added to your product comparison";
       String titleTextActual=driver.findElement(By.xpath("//p[@class='content']")).getText();
       System.out.println(titleTextActual);
       Assert.assertEquals(titleTextActual,expectedTitleText,"Product is compare");
       // click on compare products
       driver.findElement(By.xpath("//p[@class='content']/a[@href='/compareproducts']")).click();
       driver.findElement(By.xpath("//div[@class='page-body']/a[@href='#']")).click();
       try {
           Thread.sleep(1000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       //click on clear list
       driver.findElement(By.xpath("//div[@class='page-body']/a[@href='#']")).click();
       // using assert for clear list
       String expectedTitleText1="You have no items to compare.";
       String titleTextActual1=driver.findElement(By.xpath("//div[@class='page-body']")).getText();
       System.out.println(titleTextActual1);
       Assert.assertEquals(titleTextActual1,expectedTitleText1,"No products in compare list");
    }
   @AfterMethod
    public void closeBrowser(){
       //close the URL
       driver.close();
    }



}
