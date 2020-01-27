package Assistant;

import DataPortal_Pages.DataPortal_Login_Page;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SharedMethods {

//    DataPortal_Login_Page dataPortalLoginPage = new DataPortal_Login_Page();

    /*public void enterIntheSystem_DataPortal(){
        dataPortalLoginPage.enterUser_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_ADM_DEUTSCHE);
        dataPortalLoginPage.enterPassword_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_PSWD_DEUTSCHE);
    }*/

    public String returnDateHours(){
        Date _date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        String date = formatter.format(_date);
        return date;
    }

}
