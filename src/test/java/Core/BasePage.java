package Core;

import Assistant.IdAssistant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static Core.DriverFactory.getDriver;

public class BasePage {


//    private String pathFile = "/home/rafael/Project/QA_Valid_Automation/src/test/java/CSV/Pasta1.csv";

    private URL url = Thread.currentThread().getContextClassLoader().getResource("CSV/Pasta1.csv");
    private String pathFile = url.getPath();

    private JavascriptExecutor js = getDriver();

    protected void clickButton(By by){
        getDriver().findElement(by).click();
    }

    protected String typeText(By by, String text){
        getDriver().findElement(by).sendKeys(text);
        return new String (text);
    }

    protected void checkElement(By by){
        getDriver().findElement(by).click();
    }

    protected void selectItenOnCombo(By by){
        getDriver().findElement(by).click();
    }

    protected void clickDropDown(By by){
        getDriver().findElement(by).click();
    }

    protected void selectValueDropDown(By by){
        getDriver().findElement(by).click();
    }

    protected boolean elementIsEnabled(By by){
        boolean isEnabled = getDriver().findElement(by).isEnabled();
        return isEnabled;
    }

    protected void selectListValueDropDown(By by){
        List<WebElement>listValues = getDriver().findElements(by);
        for (WebElement item:listValues) {
            item.click();
            break;
        }
    }

    protected String getTextOfElement(By by){
        return new String(getDriver().findElement(by).getText());
    }

    protected boolean elementExistByText(By by){
        try {
            String text = getDriver().findElement(by).getText();
            if(!text.equalsIgnoreCase(null)){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    protected String[] getElementsByPath(By by){
        ArrayList arrayList = (ArrayList) getDriver().findElements(by);
        String[] listStrings = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++){
            listStrings[i] = getDriver().findElement(by).getText();
        }
        return listStrings;
    }

    protected boolean elementExists(By by){
        try {
            getDriver().findElement(by).isDisplayed();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    protected String getTextElementByXpath(String path){
        WebElement webElement = getDriver().findElementByXPath(path);
        String actualText = webElement.getText();
        return actualText;
    }

    protected void uploadFile(){
        WebElement upload = getDriver().findElement(By.id(IdAssistant.EM_ID_LIST_INPUT_FILE));
        upload.sendKeys(pathFile);
        waitForLoad(2000);
    }

    protected List<WebElement> listSameElements(By by){
        List<WebElement>list = getDriver().findElements(by);
        return list;
    }

    protected void scrollDownPage(){
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    protected void scrollUpPage(){
        js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
    }

    protected void waitForLoad(long time){
        try {
            Thread.sleep(time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}