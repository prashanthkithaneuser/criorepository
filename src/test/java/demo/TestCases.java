package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */
   @Test
public void testCase01() throws InterruptedException {
    driver.get("https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");

    // Adding WebDriverWait to replace one Thread.sleep
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/input")));

    WebElement nameTab = driver.findElement(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/input"));
    System.out.println("clicked on Name Tab");
    nameTab.sendKeys("Crio Learner");

    WebElement secondTab = driver.findElement(By.xpath("//textarea[@class='KHxj8b tL9Q4c']"));
    secondTab.click();

    String message = "I want to be the best QA Engineer! " + System.currentTimeMillis();
    secondTab.sendKeys(message);

    WebElement radioBtn = driver.findElement(By.xpath("(//div[@class='AB7Lab Id5V1'])[1]"));
    radioBtn.click();

    int[] checkBoxIndexes = { 1, 2, 4 };

    for (int index : checkBoxIndexes) {
        WebElement checkBox = driver.findElement(By.xpath("(//div[@class='uHMk6b fsHoPb'])[" + index + "]"));
        checkBox.click();
    }

    WebElement chooseDropDown = driver.findElement(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div[2]"));
    chooseDropDown.click(); 

    System.out.println("drop down working fine");

    Thread.sleep(3000);

    WebElement chooseDropDownOption = driver.findElement(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[2]/div[3]"));
    chooseDropDownOption.click();

    // Calculating date 7 days before today
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String txtDateText = currentDate.minusDays(7).format(formatter);

    WebElement dateTab = driver.findElement(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[6]/div/div/div[2]/div/div/div[2]/div[1]/div/div[1]/input"));
    dateTab.sendKeys(txtDateText);

    WebElement hourTab = driver.findElement(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[7]/div/div/div[2]/div/div[1]/div[2]/div[1]/div/div[1]/input"));
    hourTab.sendKeys("07");

    WebElement minutesTab = driver.findElement(By.xpath("//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[7]/div/div/div[2]/div/div[3]/div/div[1]/div/div[1]/input"));
    minutesTab.sendKeys("30");
    Thread.sleep(3000);

    WebElement submitButton = driver.findElement(By.xpath("//span[text()='Submit']"));
    submitButton.click();
    Thread.sleep(5000);

    WebElement finalText = driver.findElement(By.xpath("//div[@class='vHW8K']"));
    System.out.println(finalText.getText());
}


    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
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
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}