package TestClasses;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;
import DataPortal_Pages.DataPortal_Login_Page;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeliveryCenter_Models_Test extends BaseTest {

    private DataPortal_Login_Page page;
    public DeliveryCenter_Models_Test() {
        setUrl();
        page = new DataPortal_Login_Page();
    }

    @Test
    @Description("Check the creating email template")
    @DisplayName("Create email template")
    public void createEmailTemplate(){

    }

    @Test
    @Description("Check the required fields")
    @DisplayName("Verify the required fields - Models - Email")
    public void verifyTheRequiredFields_Email(){

    }



    private void setUrl() {
        AddressEntity.setUrl(UrlSystemAssistant.URL_PROD_DATAPORTAL);
    }

}
