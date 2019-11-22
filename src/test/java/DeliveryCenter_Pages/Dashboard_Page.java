package DeliveryCenter_Pages;

import Assistant.ConstantsAssistant;
import Core.BasePage;
import DataPortal_Pages.DataPortal_Login_Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Dashboard_Page extends BasePage {

    DataPortal_Login_Page pageLoginDataPortal = new DataPortal_Login_Page();

    public void enterInThePortal(){
        waitForLoad(8000);
        pageLoginDataPortal.enterUser_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_ADM_DEUTSCHE);
        pageLoginDataPortal.enterPassword_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_PSWD_DEUTSCHE);
        pageLoginDataPortal.clickInButtonEnter_DataPortal();
        waitForLoad(11000);
    }

    public String dashboardChartsExistsByText_Dashboard(String text){
        String textElement = getTextOfElement(By.xpath("//h6[.='"+text+"']"));
        return textElement;
    }

    public boolean checkButtonReport_Dashboard(){
        boolean exists = elementExistByText(By.xpath("//button[text() = 'Relatórios ']"));
        return exists;
    }

    public boolean checkbuttonFilter_Dashboard(){
        boolean exists = elementExistByText(By.xpath("//button[@type='button']//span[.='Filtrar']"));
        return exists;
    }

    public void clickButtonsDetailsOfCharts_Dashboard(int index){
        waitForLoad(3000);
        List<WebElement> list = listSameElements(By.xpath("//mat-icon[contains(text(),'add_circle')]"));
        list.get(index).click();

    }

    public void hideDetailsOfCharts_Dashboard(){
        clickButton(By.xpath("//mat-icon[contains(text(),'remove_circle')]"));
    }

    public void scrollDownPage_Dashboard(){
        scrollDownPage();
        waitForLoad(3000);
    }

    public String getTextOfElement_Dashboard(String path){
        String text = getTextOfElement(By.xpath(path));
        return text;
    }






}
