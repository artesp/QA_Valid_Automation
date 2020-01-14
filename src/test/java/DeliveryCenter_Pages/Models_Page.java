package DeliveryCenter_Pages;

import Assistant.ConstantsAssistant;
import Core.BasePage;
import DataPortal_Pages.DataPortal_Login_Page;
import org.openqa.selenium.By;

public class Models_Page extends BasePage {


    public DataPortal_Login_Page dataPortalLoginPage = new DataPortal_Login_Page();

    public void enterInDataPortal(){
        waitForLoad(8000);
        dataPortalLoginPage.enterUser_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_ADM_DEUTSCHE);
        dataPortalLoginPage.enterPassword_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_PSWD_DEUTSCHE);
        dataPortalLoginPage.clickInButtonEnter_DataPortal();
    }






}
