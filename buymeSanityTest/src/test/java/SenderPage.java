import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SenderPage {

//    get selected tab header color
    public static String activeTabColor(WebDriver driver){
        return driver.findElement(By.className("highlighted")).getCssValue("color");
//        return driver.findElement(By.partialLinkText("למי לשלוח")).getCssValue("color");
    }

//    select radiobutton למישהו אחר
//    click on the readiobutton makes it selected independent on its initial state
    public static void selectOther(WebDriver driver){
       driver.findElement(By.cssSelector("label[data='forSomeone']")).click();
    }

//    select radiobutton option מיד אחרי התשלום
    public static void sendNow(WebDriver driver){
        driver.findElement(By.className("send-now")).click();
    }

//    set receiver name
    public static void setReceiver(WebDriver driver, String string){
        WebElement receiver = driver.findElements(By.cssSelector("input[data-parsley-group='main']")).get(0);
        receiver.clear();
        receiver.sendKeys(string);
    }

    //    set sender name
    public static void setSender(WebDriver driver, String string){
        WebElement sender = driver.findElements(By.cssSelector("input[data-parsley-group='main']")).get(1);
        sender.clear();
        sender.sendKeys(string);
    }

//    set reason
    public static void setReason(WebDriver driver, String value){
        Select selectReason = new Select(driver.findElement(By.cssSelector("select[class = 'ember-chosenselect']")));
        selectReason.selectByValue(value);
    }

    //    set blessing text
    public static void setBlessing(WebDriver driver, String string){
        WebElement textBlessing = driver.findElement(By.tagName("textarea"));
        textBlessing.clear();
        textBlessing.sendKeys(string);
    }

    //    upload picture from the disk
    public static void uploadPicture(WebDriver driver, String string){
        driver.findElement(By.cssSelector("input[name='fileUpload']")).sendKeys(string);
    }

//   set sender mail
    public static void setSenderMail(WebDriver driver, String string){
        driver.findElement(By.className("icon-envelope")).click();
        WebElement mailFiled = driver.findElement(By.cssSelector("input[type='email']"));
        mailFiled.clear();
        mailFiled.sendKeys(string + Keys.ENTER);
    }

    //  click pay
    public static void clickPay(WebDriver driver){
        driver.findElement(By.cssSelector("button[type='submit']")).submit();
    }

//    wait summary
    public static void waitSummary(WebDriver driver){
        WebDriverWait waitLogin = new WebDriverWait(driver, 100);
        waitLogin.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("payment-summary"))));

    }

}
