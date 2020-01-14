package TestClasses;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;
import DeliveryCenter_Pages.Menu_Page;
import DeliveryCenter_Pages.Models_Page;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeliveryCenter_Models_Test extends BaseTest {


    Models_Page page;
    Menu_Page menuPage;
    public DeliveryCenter_Models_Test() {
        setUrl();
        page = new Models_Page();
        menuPage = new Menu_Page();
    }

    @Test
    @Description("Check the creating email template")
    @DisplayName("DC-54: Create email template")
    public void createEmailTemplate(){
        page.enterInDataPortal();
        menuPage.clickInMainMenuTogglebutton();
        menuPage.selectCriacaoConteudo();

    }

    @Test
    @Description("Check the required fields")
    @DisplayName("DC-55: Verify the required fields - Models - Email")
    public void verifyTheRequiredFields_Email(){

    }


    private void setUrl() {
        AddressEntity.setUrl(UrlSystemAssistant.URL_DEV_DATAPORTAL);
    }

}
