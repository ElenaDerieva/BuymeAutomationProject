import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;


public class GeneralPage {

    //    get page url

    public static String pageUrl(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.className("main-container"))));
        return driver.getCurrentUrl();
    }


    //    wait until main container is loaded to get current page url
    public static void waitPage(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.className("main-container"))));
    }

    //    proceed to login page
    public static void goToLogIn(WebDriver driver){
        WebDriverWait waitLogin = new WebDriverWait(driver, 100);
        waitLogin.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[class='seperator-link']")));
        driver.findElement(By.cssSelector("span[class='seperator-link']")).click();
    }

    // wait login page is closed
    public static void loginClosed(WebDriver driver){
        WebDriverWait waitLogin = new WebDriverWait(driver, 100);
        waitLogin.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("ui-lightbox"))));
    }

    //    click search button
    public static void clickSearch(WebDriver driver){
        WebDriverWait waitSearch = new WebDriverWait(driver,100);
        waitSearch.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.partialLinkText("תמצאו לי מתנה"))));
        driver.findElement(By.partialLinkText("תמצאו לי מתנה")).click();
        System.out.println("button clicked");
    }

    //    add keyword
    public static void addKeyword(WebDriver driver, String keyword){
        WebDriverWait waitKeyField = new WebDriverWait(driver,100);
        waitKeyField.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("input[type='search']"))));
        driver.findElement(By.cssSelector("input[type='search']")).sendKeys(keyword);
        System.out.println("keyword added");
    }

//    scroll to the footer
    public static void showFooter(WebDriver driver){
        WebElement element = driver.findElement(By.className("footer-bottom"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

//    log out for further tests (if necessary)
    public static void logOut(WebDriver driver){
        Actions logOut = new Actions(driver);
        WebElement myAccountMenu = driver.findElement(By.cssSelector("li[class='dropdown']"));
        WebElement myExit = driver.findElement(By.partialLinkText("יציאה"));
        logOut.moveToElement(myAccountMenu).moveToElement(myExit).click().build().perform();
    }

//      take screenshot is a helper, it is placed here as the project was patched
//     with correct approach, both xmlReader and screenshot saver should be placed in helper class
    public static String takeScreenShot(WebDriver driver, String ImagesPath) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath+".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath+".png";
    }

}



