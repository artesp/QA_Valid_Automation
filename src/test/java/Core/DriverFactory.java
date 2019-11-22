package Core;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class DriverFactory {

    private static ChromeDriver driver;

    public static ChromeDriver getDriver(){
        if (driver == null){
            createDriver();
        }
        return driver;
    }

    private static void createDriver() {
        System.setProperty("webdriver.chrome.driver","/home/rafael/Drivers/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("start-maximized");
        options.addArguments("--js-flags=--expose-gc");
        options.addArguments("--enable-precise-memory-info");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
        options.addArguments("test-type=browser");
        options.addArguments("disable-infobars");
        options.addArguments("incognito");

        driver  = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    }

    public static void killDriver(){
        if (driver!=null){
            driver.manage().deleteAllCookies();
            driver.quit();
            driver=null;
        }
    }

}
