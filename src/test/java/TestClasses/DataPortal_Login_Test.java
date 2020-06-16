package TestClasses;

import Assistant.AddressEntity;
import Assistant.ConstantsAssistant;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;
import DataPortal_Pages.DataPortal_Login_Page;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;

public class DataPortal_Login_Test extends BaseTest {

    private DataPortal_Login_Page page;
    public DataPortal_Login_Test() {
        setUrl();
        page = new DataPortal_Login_Page();
    }

    @Test
    public void login_DataPortal(){
        page.waitForLoad_DataPortal(3000);
        page.enterUser_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_ADM_DEUTSCHE);
        page.enterPassword_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_PSWD_DEUTSCHE);
        page.clickInButtonEnter_DataPortal();
        page.waitForLoad_DataPortal(8000);
        assertTrue(page.elementExistsByText(By.className("txt-dark")));
        assertEquals("Dashboard", page.checkTextOfElementsByCssName_DataPortal("Dashboard", "txt-dark"));
    }

    private void setUrl() {
        new AddressEntity().setURL(UrlSystemAssistant.URL_DEV_DATAPORTAL);
    }


}
