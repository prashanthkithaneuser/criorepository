package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.time.Duration;

public class Wrappers {

    public static void flipkartSeacrchBar(WebDriver driver, By locator, String textToEnter) {
        System.out.println("Sending Keys");
        Boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement inputBox = driver.findElement(locator);
            inputBox.clear();
            inputBox.sendKeys(textToEnter);
            inputBox.sendKeys(Keys.ENTER);
            success = true;
            System.out.println("moving to next step");

        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;
        }

    }

    public static void clickOnElementWrapper(WebDriver driver, By locator) {
        System.out.println("Clicking on Element ");
        Boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement clickableElement = driver.findElement(locator);
            clickableElement.click();
            success = true;

        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;

        }
    }

    public static Boolean searchRatingAndCount(WebDriver driver, By locator, double starRating) {
        System.out.println("Searching and printing count");
        int washingMachineCount = 0;
        Boolean success;
        try {
            List<WebElement> starRatingElements = driver.findElements(locator);
            for (WebElement starRatingElement : starRatingElements) {
                if (Double.parseDouble(starRatingElement.getText()) <= starRating) {
                    washingMachineCount++;
                }
            }
            System.out.println("Count of washing machine which has star rating less than or equal to " + starRating
                    + ": " + washingMachineCount);

            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;

        }
        return success;
    }

    public static Boolean titleAndDiscount(WebDriver driver, By locator, int discount) {
        Boolean success;
        try {
            List<WebElement> productRows = driver.findElements(locator);
            HashMap<String, String> iphoneTitleDiscountMap = new HashMap<>();
            for (WebElement productRow : productRows) {
                // Check if the productRow contains the discount element
                List<WebElement> discountElements = productRow.findElements(By.xpath(".//div[@class='UkUFwK']/span"));

                // Proceed only if the discount element is present
                if (!discountElements.isEmpty()) {
                    String discountPercent = discountElements.get(0).getText();
                    int discountValue = Integer.parseInt(discountPercent.replaceAll("[^\\d]", ""));
                    System.out.println(discountValue);

                    if (discountValue > discount) {
                        // Check if the title element exists
                        List<WebElement> titleElements = productRow.findElements(By.xpath(".//div[@class='KzDlHZ']"));

                        if (!titleElements.isEmpty()) {
                            String iphoneTitle = titleElements.get(0).getText();
                            iphoneTitleDiscountMap.put(discountPercent, iphoneTitle);
                            System.out.println("Found discount greater than " + discount + " percent");
                            System.out.println(iphoneTitle);
                        } else {
                            System.out.println("Title element not found for the product.");
                        }
                    }
                } else {
                    System.out.println("Discount element not found in this product row.");
                }
            }

            Thread.sleep(3000);

            for (Map.Entry<String, String> iphoneTitleDiscounts : iphoneTitleDiscountMap.entrySet()) {
                System.out.println("Iphone discount percentage :: " + iphoneTitleDiscounts.getKey()
                        + " and its tile :: " + iphoneTitleDiscounts.getValue());
            }
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! ");
            e.printStackTrace();
            success = false;
        }
        return success;

    }

    public static Boolean printTitleandImageURL(WebDriver driver, By locator) {
        Boolean success;
        try {
            List<WebElement> products = driver.findElements(locator);
Map<Integer, List<Map<String, String>>> coffeeMugs = new TreeMap<>(Collections.reverseOrder()); // List to handle duplicates

for (WebElement product : products) {
    try {
        // Extract title
        String title = product.findElement(By.xpath(".//a[@class='wjcEIp']")).getText();

        // Extract image URL
        String imageUrl = product.findElement(By.xpath(".//a[@class='wjcEIp']")).getAttribute("href");

        // Extract reviews
        String reviewText = product.findElement(By.xpath(".//span[@class='Wphh3N']")).getText(); 
        int reviews = extractNumberOfReviews(reviewText);

        // Store details in a map
        Map<String, String> productDetails = new HashMap<>();
        productDetails.put("title", title);
        productDetails.put("imageUrl", imageUrl);

        // Add product details to the coffeeMugs map
        coffeeMugs.computeIfAbsent(reviews, k -> new ArrayList<>()).add(productDetails);
    } catch (NoSuchElementException e) {
        System.err.println("An element was not found: " + e.getMessage());
    }
}

// Print the top 5 mugs based on the number of reviews
int count = 0;
for (Map.Entry<Integer, List<Map<String, String>>> entry : coffeeMugs.entrySet()) {
    for (Map<String, String> details : entry.getValue()) {
        if (count == 5) break; // Limit to top 5

        System.out.println("Title: " + details.get("title"));
        System.out.println("Image URL: " + details.get("imageUrl"));
        System.out.println("Reviews: " + entry.getKey());
        System.out.println("--------------------------------");
        count++;
    }
    if (count == 5) break; // Break outer loop if limit reached
}

        success = true;

        } catch (Exception e) {
            System.out.println("Exception Occured! ");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    private static int extractNumberOfReviews(String reviewText) {
        try {
            System.out.println("Extracted Review String: " + reviewText); // Debugging line
    
            // Use regex to extract the number from within parentheses
            String reviews = reviewText.replaceAll("[^0-9]", ""); // Keep only digits
            return Integer.parseInt(reviews);
        } catch (Exception e) {
            System.out.println("Error parsing reviews: " + e.getMessage());
            return 0;  // Return 0 if parsing fails
        }
    }
    

}