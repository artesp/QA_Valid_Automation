package DataPortal_Pages;

import Assistant.IdAssistant;
import Core.BasePage;
import org.openqa.selenium.By;

public class DataPortal_Login_Page extends BasePage {

    public void enterUser_DataPortal(String user){
        typeText(By.id(IdAssistant.DATAPORTAL_ID_EMAIL),user);
    }

    public void enterPassword_DataPortal(String password){
        typeText(By.id(IdAssistant.DATAPORTAL_ID_SENHA), password);
    }

    public void clickInButtonEnter_DataPortal(){
        clickButton(By.cssSelector("button"));
    }

    public boolean elementExistsByText(By by){
        boolean exists = elementExistByText(by);
        return exists;
    }

    public String checkTextOfElementsByCssName_DataPortal(String text, String className){
        String[] list = getElementsByPath(By.className(className));
        for (int i=0; i < list.length; i++){
            if (list[i].equalsIgnoreCase(text)){
                return text;
            }
        }
        return new String("Element not found!");
    }

    public void waitForLoad_DataPortal(long time){
        waitForLoad(time);
    }
}
