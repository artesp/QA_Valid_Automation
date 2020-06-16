package TestClasses;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;
import DeliveryCenter_Pages.Menu_Page;
import DeliveryCenter_Pages.Models_Page;
import org.junit.Test;

public class DeliveryCenter_Models_Test extends BaseTest {


    Models_Page page;
    Menu_Page menuPage;
    public DeliveryCenter_Models_Test() {
        setUrl();
        page = new Models_Page();
        menuPage = new Menu_Page();
    }

    @Test
    public void DC54_CreateEmailTemplate(){
        page.enterInDataPortal();
        menuPage.clickInMainMenuTogglebutton();
        menuPage.selectCriacaoConteudo();

    }

    @Test
    public void DC55_VerifyTheRequiredFields_Models_Email(){

    }


    private void setUrl() {
        new AddressEntity().setURL(UrlSystemAssistant.URL_DEV_DATAPORTAL);
    }

}
