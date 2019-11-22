package TestClasses;

import Assistant.AddressEntity;
import Assistant.ConstantsAssistant;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;
import DeliveryCenter_Pages.Dashboard_Page;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class DeliveryCenter_Dashboard_Test extends BaseTest {

    Dashboard_Page page;
    public DeliveryCenter_Dashboard_Test() {
        setUrl();
        page = new Dashboard_Page();
    }

    @Test
    @Description("Validate the elements displaying on Dashboard page")
    @DisplayName("DC-28: Verify details of Processed, Sent and Delivered charts")
    public void dashboardVerifyProcessedSentAndDeliveredAndButtons(){
        page.enterInThePortal();
        assertThat(page.dashboardChartsExistsByText_Dashboard("Processados"), containsString("Processados"));
        assertThat(page.dashboardChartsExistsByText_Dashboard("Enviados"), containsString("Enviados"));
        assertThat(page.dashboardChartsExistsByText_Dashboard("Entregues"), containsString("Entregues"));
        assertTrue(page.checkButtonReport_Dashboard());
        assertTrue(page.checkbuttonFilter_Dashboard());
    }

    @Test
    @Description("Validate the elements displaying on the Processed details")
    @DisplayName("DC-28: Verify details of Processed charts")
    public void dashboardVerifyDetailsProcessed(){
        page.enterInThePortal();

        //Check in details of Processed
        page.clickButtonsDetailsOfCharts_Dashboard(ConstantsAssistant.DELIVERYCENTER_DETAILS_PROCESSED);
        page.scrollDownPage_Dashboard();
        assertThat(page.getTextOfElement_Dashboard("//p[@class='panel-process-number-txt']"), containsString("Enriquecidos"));
        page.hideDetailsOfCharts_Dashboard();
    }

    @Test
    @Description("Validate the elements displaying on the Sent details")
    @DisplayName("DC-28: Verify details of Sent charts")
    public void dashboardVerifyDetailsSent(){
        page.enterInThePortal();

        //Check in details of Sent
        page.clickButtonsDetailsOfCharts_Dashboard(ConstantsAssistant.DELIVERYCENTER_DETAILS_SENT);
        page.scrollDownPage_Dashboard();
        assertThat(page.dashboardChartsExistsByText_Dashboard("Falha permanente"), containsString("Falha permanente"));
        page.hideDetailsOfCharts_Dashboard();
    }

    @Test
    @Description("Validate the elements displaying on the Delivered details")
    @DisplayName("DC-28: Verify details of Delivered charts")
    public void dashboardVerifyDetailsDelivered(){
        page.enterInThePortal();

        //Check in details of Delivered
        page.clickButtonsDetailsOfCharts_Dashboard(ConstantsAssistant.DELIVERYCENTER_DETAILS_DELIVERED);
        page.scrollDownPage_Dashboard();
        assertThat(page.dashboardChartsExistsByText_Dashboard("Aberturas únicas"), containsString("Aberturas únicas"));
        page.hideDetailsOfCharts_Dashboard();
    }



    private void setUrl() {
        AddressEntity.setUrl(UrlSystemAssistant.URL_PROD_DATAPORTAL);
    }

}
