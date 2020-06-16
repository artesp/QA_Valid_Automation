package TestClasses;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;
import EM_Pages.Login_Page;
import org.hamcrest.CoreMatchers;
import org.junit.Test;


import static org.junit.Assert.*;

public class EM_Login_Test extends BaseTest {

    private Login_Page page;

    public EM_Login_Test() {
        setUrl();
        page = new Login_Page();
    }

    @Test
    public void login_Success(){
        page.typeUser_Login();
        page.typePassword_Login();
        page.clickOnComboLanguage();
        page.selectItemOnCombo_Login();
        page.checkBox_Login();
        page.clickButton_Login();
        String expected = page.getTextOfElement_Login();
        assertEquals(expected, CoreMatchers.containsString("Bem-vindo,"));
    }

    private void setUrl() {
        new AddressEntity().setURL(UrlSystemAssistant.URL_PROD_EM);
    }
}
