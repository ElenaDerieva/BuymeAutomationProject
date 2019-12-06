import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SanityTest {

//    declare driver
    private static WebDriver driver;
//    get timestamp for the current test
    private static final String testTimestamp = String.valueOf(System.currentTimeMillis());
//     create ExtentReports and attach reporter(s)
    private static ExtentReports extent ;
//     creates a toggle for the given test, adds all log events under it
    private static ExtentTest test ;
    private static final String REPORT_PATH = Constants.MAIN_PATH + "Results\\";

    @BeforeClass
    public static void beforeClass() {
//        set reporter
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(REPORT_PATH + "report_" + testTimestamp + ".html");

        // attach html reporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        // name the test, add description and system info
        test = extent.createTest("Assignment_10", "Create test report assignment");
        extent.setSystemInfo("Created_by", "Elena");

//      log results
        test.log(Status.INFO, "Before class run");

//        set properties for browsers
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Elena\\QATools\\chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Elena\\QATools\\geckodriver.exe");

//        get driver type from the config file
        String browser = XmlFileReader.getBrowserType();
//        initialise driver as per config file
        try {
            if (browser.equals("Chrome")) {
                driver = new ChromeDriver();
            } else if (browser.equals("FireFox")) {
                driver = new FirefoxDriver();
            } else {
//            use Chrome as a default browser
                driver = new ChromeDriver();
            }
            test.pass("Driver is connected");
            test.log(Status.INFO, browser + " browser driver is initialised and connected successfully");
        }catch (Exception e){
            test.log(Status.FATAL, "Driver Connection Failed! " + e.getMessage());
        }
//        maximize browser window
        driver.manage().window().maximize();
//        set page load timeout: 60s
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//        set implicit wait 10s
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    }

//    LANDING SCREEN

    @Test
    public void test11_loadLandingPage(){
//        goto URL read from the XLM file
        String url = XmlFileReader.getLandingURL();
        driver.get(url);
//        GeneralPage.waitPage(driver);
//        assert expected page is loaded
        try {
            Assert.assertEquals(Constants.LANDING_URL, GeneralPage.pageUrl(driver));
            test.log(Status.PASS,Constants.LANDING_URL + " page is loaded", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test11")).build());
        }catch (Exception e){
            test.log(Status.FAIL,Constants.LANDING_URL + " page is NOT loaded, exception: " + e.getMessage());
        }

    }

    @Test
    public void test12_goToLogin() {
        try {
//        goto login page
            GeneralPage.goToLogIn(driver);
//        wait until load
            LogInPage.waitPage(driver);
//        assert login URL
            Assert.assertThat(GeneralPage.pageUrl(driver), containsString(Constants.LOGIN));
            test.log(Status.PASS,"Login page is loaded", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test12")).build());
        }catch (Exception e) {
            test.log(Status.FAIL, "Login page  is NOT loaded, exception: " + e.getMessage());
        }
    }

//    SIGNUP AND LOGIN

    @Test
    public void test21_SignUp() {
        try {
//      switch to signup screen
            LogInPage.gotoSignUp(driver);
            test.log(Status.INFO,"Sign Up page is loaded", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test13")).build());
//        set valid credentials, click button
            LogInPage.setSignupCredentials(driver, Constants.SENDER, Constants.LOGIN_NAME, Constants.LOGIN_PASS);
            test.log(Status.INFO,"Sign Up form is completed", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test12")).build());
            LogInPage.clickLoginButton(driver);
//        wait response from the server: a user already exists: expected screen depends on credentials
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        for these credentials, a user already exists, it can be checked with the following assertion
            Assert.assertTrue(LogInPage.isSignUpExists(driver));
            test.log(Status.PASS,"The user exists", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test12")).build());
        }catch(Exception e){
            test.log(Status.FAIL,"Sign Up is failed, exception: " + e.getMessage());
        }

//        if a new user is created, the app automatically logs in and goes back to the main screen:
        try{
            if (GeneralPage.pageUrl(driver).equals(Constants.LANDING_URL)){
//            for the following testing, logout and proceed to the login screen
                GeneralPage.logOut(driver);
                GeneralPage.goToLogIn(driver);
                LogInPage.waitPage(driver);
            }
            test.log(Status.INFO,"New user, proceed back to the login screen");
        }catch (Exception e){
            test.log(Status.FAIL,"Fail to reload login screen, exception: " + e.getMessage());
        }
    }

    @Test
    public void test22_LogInNoCredentials() {
//      ensure the correct page is displayed
        try {
            LogInPage.gotoLogin(driver);
            test.log(Status.INFO, "Login screen is ready", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test22_1")).build());
//                    LogInPage.waitPage(driver);
//        click login button
            LogInPage.clickLoginButton(driver);
//        assert login error
            Assert.assertTrue(LogInPage.isLoginError(driver));
//        assert login screen is still displayed
            Assert.assertThat(GeneralPage.pageUrl(driver), containsString(Constants.LOGIN));
            test.log(Status.PASS,"The user is NOT logged in", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test22_2")).build());
        }catch (Exception e){
            test.log(Status.FAIL,"LogIn with no credentials is failed, exception: " + e.getMessage());
        }
    }

    @Test
    public void test23_LogIn() {
        try {
//          clear old credentials
            LogInPage.clearLoginCredentials(driver);
//        set valid credentials, click button
            LogInPage.setLoginCredentials(driver, Constants.LOGIN_NAME, Constants.LOGIN_PASS);
            test.log(Status.INFO, "Login credentials are set", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test23_1")).build());
            LogInPage.clickLoginButton(driver);
//        wait, assert page url
            GeneralPage.loginClosed(driver);
            Assert.assertEquals(GeneralPage.pageUrl(driver), Constants.LANDING_URL);
            test.log(Status.PASS,"The user is logged in", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test23_2")).build());
        }catch (Exception e){
            test.log(Status.FAIL,"LogIn is failed, exception: " + e.getMessage());
        }
    }

//    LANDING SCREEN SELECT OPTIONS

    @Test
     public void test31_SearchNoSettings(){
        try {
//        search with no selections
            GeneralPage.clickSearch(driver);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Assert.assertThat(GeneralPage.pageUrl(driver), containsString(Constants.SEARCH));
            test.log(Status.PASS,"Search is conducted", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test31_1")).build());
        }catch (Exception e){
            test.log(Status.FAIL,"Search with no settings is failed, exception: " + e.getMessage());
        }
    }

    @Test
    public void test32_snapshotFooter() {
        try {
            GeneralPage.showFooter(driver);
//        save screenshot to CaptureScreen.SCREENSHOT_PATH: if the old screenshot exists, it will be deleted
            CaptureScreen.saveScreenshot();
            test.log(Status.PASS,"The page is scrolled to the footer", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test32")).build());
            test.log(Status.INFO,"Screenshot is saved to " + REPORT_PATH);
//        go back to the landing screen
        }catch (Exception e){
            test.log(Status.FAIL,"Footer screenshot is not taken, exception: " + e.getMessage());
        }
//        refresh the page
        driver.navigate().back();
    }

    @Test
    public void test33_searchByKeyword(){
        try {
//      get page url
            String oldUrl = GeneralPage.pageUrl(driver);
            GeneralPage.addKeyword(driver, Constants.KEYWORD);
            GeneralPage.clickSearch(driver);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//      assert search page is displayed, and it differs from the previous one
            String newUrl = GeneralPage.pageUrl(driver);
            Assert.assertNotEquals(newUrl, oldUrl);
            Assert.assertThat(newUrl, containsString(Constants.SEARCH));
            test.log(Status.PASS,"Search result screen is displayed", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test32")).build());
        }catch (Exception e){
            test.log(Status.FAIL,"Search by keyword is not conducted, exception: " + e.getMessage());
        }
    }

//    SEARCH RESULT

    @Test
    public void test41_selectSupplier(){
//        driver.get("https://buyme.co.il/search?query=%D7%99%D7%99%D7%9F");

        try {
//      select supplier
            SupplierPage.selectSupplier(driver);
//        assert supplier page is opened
            Assert.assertThat(GeneralPage.pageUrl(driver), containsString(Constants.SUPPLIER));
            test.log(Status.PASS,"Supplier is selected", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test41")).build());
        }catch (Exception e){
            test.log(Status.FAIL,"Supplier is not selected, exception: " + e.getMessage());
        }
    }

    @Test
    public void test42_selectGift(){
        try {
//      select a gift
            SupplierPage.selectGift(driver);
//       check Sender screen page is opened
            String currentUrl = GeneralPage.pageUrl(driver);
            System.out.println(currentUrl);
            Assert.assertTrue(currentUrl.contains(Constants.GIFT1) || currentUrl.contains(Constants.GIFT2));
            test.log(Status.PASS,"A gift is selected", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test42")).build());
        }catch (Exception e){
            test.log(Status.FAIL,"A gift is not selected, exception: " + e.getMessage());
        }
    }

//    SENDER AND RECEIVER INFORMATION SCREEN

    @Test
    public void test51_checkHighlightColor(){
//        assert selected tab name color
        String selectedTabColor = SenderPage.activeTabColor(driver);
        try {
            Assert.assertEquals(Constants.COLOR, selectedTabColor);
            test.log(Status.PASS,"Active tab color is correct, " + Constants.COLOR);
        }catch(AssertionError e){
            test.log(Status.FAIL,"Active tab color is not correct, now: " + selectedTabColor + ", expected: " + Constants.COLOR);
        }
    }

    @Test
    public void test52_fillSenderForm(){
        try {
//        select radiobutton "other'
            SenderPage.selectOther(driver);
//        set text fields
            SenderPage.setReceiver(driver, Constants.RECEIVER);
            SenderPage.setSender(driver, Constants.SENDER);
            SenderPage.setBlessing(driver, Constants.BLESSING);
//        upload picture
            SenderPage.uploadPicture(driver, Constants.MAIN_PATH + Constants.PICTURE);
//       set radiobutton send now
            SenderPage.sendNow(driver);
//       set sender mail
            SenderPage.setSenderMail(driver, Constants.LOGIN_NAME);
            test.log(Status.INFO,"Sender form is set", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test52_1")).build());
//       proceed to payment
            SenderPage.clickPay(driver);
            SenderPage.waitSummary(driver);
//        System.out.println(GeneralPage.pageUrl(driver));
            Assert.assertThat(GeneralPage.pageUrl(driver), containsString(Constants.PAYMENT));
            test.log(Status.PASS,"Sender form is completed and sent", MediaEntityBuilder.createScreenCaptureFromPath(GeneralPage.takeScreenShot(driver, REPORT_PATH + testTimestamp + "_test52_2")).build());
        }catch (Exception e){
            test.log(Status.FAIL,"Sender form is not completed, exception: " + e.getMessage());
        }
    }

    @AfterClass
    public static void afterClass(){
        test.log(Status.INFO,"All tests run, the browser is closed");
        driver.quit();
// build and flush report !!important, writes to html
        extent.flush();
    }
}

