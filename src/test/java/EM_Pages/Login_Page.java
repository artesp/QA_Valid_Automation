package EM_Pages;

import Assistant.ConstantsAssistant;
import Assistant.IdAssistant;
import Assistant.PathAssistant;
import Core.BasePage;
import org.openqa.selenium.By;

public class Login_Page extends BasePage {

    public void clickButton_Login(){
        clickButton(By.id(IdAssistant.EM_ID_BUTTON_ENTER));
        waitForLoad(4000);
    }

    public void typeUser_Login(){
        typeText(By.id(IdAssistant.EM_ID_FIELD_USER), ConstantsAssistant.EM_USER_RAFAEL);
    }

    public void typePassword_Login(){
        typeText(By.id(IdAssistant.EM_ID_FIELD_PASSWORD), ConstantsAssistant.EM_PSWD_RAFAEL);
    }

    public void clickOnComboLanguage(){
        clickButton(By.xpath(PathAssistant.PATH_BUTTON_COMBO_LANGUAGE));
    }

    public void selectItemOnCombo_Login(){
        selectItenOnCombo(By.xpath(PathAssistant.PATH_LANGUAGE_PORTUGUESE));
    }

    public void checkBox_Login(){
        checkElement(By.xpath(PathAssistant.PATH_CHECKBOX_LOGIN));
    }

    public String getTextOfElement_Login(){
        return getTextOfElement(By.id(IdAssistant.EM_ID_MENU_COLLAPSE));
    }

    public void clickDropDown_Company(){
        clickDropDown(By.xpath(PathAssistant.PATH_CHOOSE_A_CAMPANY_MODAL));
    }

    public void selectValueDropDown_Company_CPFL(){
        selectValueDropDown(By.xpath(PathAssistant.PATH_CHOOSE_A_COMPANY_CPFL));
    }

    public void selectValueDropDown_Company_VALID(){
        selectValueDropDown(By.xpath(PathAssistant.PATH_CHOOSE_A_COMPANY_VALID));
    }

    public void clickButtonOK_Company(){
        boolean isEnabled = false;
        do {
            isEnabled = elementIsEnabled(By.id(IdAssistant.EM_ID_BUTTON_OK_COMPANY));
            System.out.println(isEnabled);
            if (isEnabled) {
                clickButton(By.id(IdAssistant.EM_ID_BUTTON_OK_COMPANY));
                waitForLoad(2000);
            }
        }while (isEnabled == false);
    }

}
