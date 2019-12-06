import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SupplierPage {

//    select supplier
    public static void selectSupplier(WebDriver driver){
        //List<WebElement> suppliers = driver.findElements(By.className("card-item"));
//        get suppliers length and select supplier arbitrary: to be implemented later
//        get 1st supplier
        //suppliers.get(0).click();
        driver.findElement(By.className("card-item")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void selectGift(WebDriver driver){
//        get gift length and select gift arbitrary: to be implemented later
//      get a gift
        WebElement gift;
        try{
            gift = driver.findElement(By.className("input-cash"));
            if (gift.isDisplayed()){
                gift.sendKeys(Constants.SUM + Keys.ENTER);
            } else {
                driver.findElement(By.className("card-price")).click();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
