import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LogInPage {

    private static final String LOGIN = "כניסה";
//    private static final String TO_LOGIN = "לכניסה";
    private static final String SIGNUP = "הרשמה";
//    private static final String TO_SIGNUP = "להרשמה";

    //    wait until main container is loaded to get current page url
    public static void waitPage(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.className("ui-lightbox"))));
    }


    //    switch to login page
    public static void gotoLogin(WebDriver driver) {
        if (!driver.findElement(By.className("lightbox-head")).getText().equals(LOGIN)) {
            driver.findElement(By.className("text-btn")).click();
        }
    }

    //      switch to signUp page
    public static void gotoSignUp(WebDriver driver) {
        if (!driver.findElement(By.className("lightbox-head")).getText().equals(SIGNUP)) {
            driver.findElement(By.className("text-btn")).click();
        }
    }

    //    click LOGIN button
    public static void clickLoginButton(WebDriver driver) {
        driver.findElement(By.cssSelector("button[type = 'submit']")).submit();
    }

    //      check login Errors
    public static Boolean isLoginError(WebDriver driver) {
        List<WebElement> errors = driver.findElements(By.className("parsley-required"));
        return errors.get(0).isDisplayed() || errors.get(1).isDisplayed();
    }

//    clear login credentials
    public static void clearLoginCredentials(WebDriver driver) {
        driver.findElement(By.cssSelector("input[type = 'email']")).clear();
        driver.findElement(By.cssSelector("input[type = 'password']")).clear();
    }

    //    set login credentials
    public static void setLoginCredentials(WebDriver driver, String name, String pass) {
        driver.findElement(By.cssSelector("input[type = 'email']")).sendKeys(name);
        driver.findElement(By.cssSelector("input[type = 'password']")).sendKeys(pass);
    }

    //    set signup credentials
    public static void setSignupCredentials(WebDriver driver, String alias, String name, String pass) {
        driver.findElement(By.cssSelector("input[type = 'text']")).sendKeys(alias);
        driver.findElement(By.cssSelector("input[type = 'email']")).sendKeys(name);
        driver.findElements(By.cssSelector("input[type = 'password']")).get(0).sendKeys(pass);
        driver.findElements(By.cssSelector("input[type = 'password']")).get(1).sendKeys(pass);
        driver.findElements(By.cssSelector("input[type = 'checkbox']")).get(0).click();
    }

    //    check signUp exists
    public static Boolean isSignUpExists(WebDriver driver) {
        return driver.findElement(By.className("login-error")).isDisplayed();
    }
}








//    public static void setMail(WebDriver driver,String mail){
//        driver.findElement(By.id("ember1094")).sendKeys(mail);
//    }
//
//    public static void setPass(WebDriver driver,String pass){
//        driver.findElement(By.id("ember1096")).sendKeys(pass);
//    }
////    select save credentials
//    public static void saveCredentials(WebDriver driver){
//        WebElement saveCred = driver.findElement(By.id("ember1097"));
//        if (!saveCred.isSelected()){
//            saveCred.click();
//        }
//    }
//
//    public static void logIn(WebDriver driver) {
//        driver.findElement(By.partialLinkText("BUYME")).click();
//    }
//


