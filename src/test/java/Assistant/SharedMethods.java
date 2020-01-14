package Assistant;

import DataPortal_Pages.DataPortal_Login_Page;

public class SharedMethods {

    DataPortal_Login_Page dataPortalLoginPage = new DataPortal_Login_Page();

    public void enterIntheSystem_DataPortal(){
        dataPortalLoginPage.enterUser_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_ADM_DEUTSCHE);
        dataPortalLoginPage.enterPassword_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_PSWD_DEUTSCHE);
    }

}
