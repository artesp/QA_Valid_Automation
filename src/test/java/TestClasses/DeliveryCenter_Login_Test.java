package TestClasses;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;

public class DeliveryCenter_Login_Test extends BaseTest {

    public DeliveryCenter_Login_Test() {
        setUrl();
    }


    private void setUrl() {
        new AddressEntity().setURL(UrlSystemAssistant.URL_DEV_DATAPORTAL);
    }
}
