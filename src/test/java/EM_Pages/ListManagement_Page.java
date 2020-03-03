package EM_Pages;

import Assistant.IdAssistant;
import Assistant.PathAssistant;
import Core.BasePage;
import org.openqa.selenium.By;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListManagement_Page extends BasePage {

    public String nameOfList = new String();

    public void clickButtonMenu_ListManagement(){
        clickButton(By.xpath(PathAssistant.PATH_MENU_LISTMANAGEMENT_PTBR));
        waitForLoad(2000);
    }

    public void clickButtonLoadList_ListManagement(){
        clickButton(By.xpath(PathAssistant.PATH_BUTTON_LOAD_NEW_LIST_PTBR));
    }

    public void clickButtonLoad_NewListModal(){
        clickButton(By.xpath(PathAssistant.PATH_BUTTON_LOAD));
        waitForLoad_ListManagement(2000);
    }

    public String typeText_ListInputName(){
        return new String (typeText(By.id(IdAssistant.EM_ID_LIST_INPUT_NAME), "List"+incrementListName()));
    }

    public void typeText_ListInputName(String text){
        typeText(By.id(IdAssistant.EM_ID_LIST_INPUT_NAME), text);
    }

    public void typeText_ListInputDescription(){
        typeText(By.id(IdAssistant.EM_ID_LIST_INPUT_DESCRIPTION), "Teste");
    }

    public void typeText_Observation(String text, String index){
        typeText(By.xpath("//body/div[@role='dialog']/div[@class='modal-dialog']" +
                "/form[@role='form']" +
                "//div[@class='modal-body modal-minHeight']/div[4]/div[@class='col-md-12']" +
                "/table[@class='table table-striped']/tbody/tr["+index+"]/td[4]" +
                "/input[@id='observation']"), text);
    }

    public void typeText_Search_ListManagement(){
        waitForLoad(3000);
        typeText(By.id("value"), nameOfList);
        waitForLoad(3000);
    }

    public boolean elementExistByText_ListManagement(String text){
        return new Boolean(elementExistByText(By.linkText(text)));
    }

    public boolean elementExist_ListManagement(String element){
        boolean exists = elementExists(By.id(element));
        return exists;
    }

    public boolean elementIsEnabledEM(By by){
        return elementIsEnabled(by);
    }

    public String getTextElementByXpath_ListManagement(String path){
        String text = getTextElementByXpath(path);
        return text;
    }

    public void clickButtonSelect_ListManagementModal(){
        clickButton(By.id(IdAssistant.EM_ID_LIST_INPUT_FILE));
    }

    public void uploadFileWithRecords_ListManagementModal(){
        uploadFileWithRecords();
    }

    public void uploadFileNoRecords_ListManagementModal(){
        uploadFileNoRecords();
    }

    public void uploadFileWithComma_ListManagementModal(){
        uploadFileWithComma();
    }

    public void clickDropDown_ListManagementModal(String positionLine){
        clickDropDown(By.xpath("//body/div[@role='dialog']//form[@role='form']//div[@class='modal-body modal-minHeight']" +
                "/div[4]/div[@class='col-md-12']/table[@class='table table-striped']" +
                "/tbody/tr["+positionLine+"]//select"));
    }

    public void selectValueDropDown_ListManagementModal(String positionLine, String option){
        selectValueDropDown(By.xpath("/html/body/div[6]/div/form/div/div[3]/div[4]/div/table/tbody" +
                "/tr["+positionLine+"]/td[3]/select" +
                "/option["+option+"]"));
    }

    public String incrementListName(){
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
        Date date = new Date();
        String increment = dateFormat.format(date);
        return increment;
    }

    public void waitForLoad_ListManagement(long time){
        waitForLoad(time);
    }

}
