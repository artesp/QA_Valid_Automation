package DeliveryCenter_Pages;

import Assistant.ConstantsAssistant;
import Core.BasePage;
import DataPortal_Pages.DataPortal_Login_Page;
import org.openqa.selenium.By;

public class Models_Page extends BasePage {


    public DataPortal_Login_Page dataPortalLoginPage = new DataPortal_Login_Page();

    public void enterInDataPortal(){
        enterInDataPortal(ConstantsAssistant.DELIVERYCENTER_USER_ADM_DEUTSCHE, ConstantsAssistant.DELIVERYCENTER_USER_PSWD_DEUTSCHE);
    }




}
