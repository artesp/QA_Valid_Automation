package TestClasses;

import Assistant.AddressEntity;
import Assistant.ConstantsAssistant;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;
import DeliveryCenter_Pages.Dashboard_Page;
import org.junit.Test;
import org.junit.jupiter.api.DynamicTest;


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
    public void DC_28_VerifyDetailsOfProcessedentAndDeliveredCharts(){
        page.enterInThePortal();
        assertThat(page.dashboardChartsExistsByText_Dashboard("Processados"), containsString("Processados"));
        assertThat(page.dashboardChartsExistsByText_Dashboard("Enviados"), containsString("Enviados"));
        assertThat(page.dashboardChartsExistsByText_Dashboard("Entregues"), containsString("Entregues"));
        assertTrue(page.checkButtonReport_Dashboard());
        assertTrue(page.checkbuttonFilter_Dashboard());
    }

    @Test
    public void DC28_VerifyDetailsOfProcessedCharts(){
        page.enterInThePortal();

        //Check in details of Processed
        page.clickButtonsDetailsOfCharts_Dashboard(ConstantsAssistant.DELIVERYCENTER_DETAILS_PROCESSED);
        page.scrollDownPage_Dashboard();
        assertThat(page.getTextOfElement_Dashboard("//p[@class='panel-process-number-txt']"), containsString("Enriquecidos"));
        page.hideDetailsOfCharts_Dashboard();
    }

    @Test
    public void DC28_VerifyDetailsOfSentCharts(){
        page.enterInThePortal();

        //Check in details of Sent
        page.clickButtonsDetailsOfCharts_Dashboard(ConstantsAssistant.DELIVERYCENTER_DETAILS_SENT);
        page.scrollDownPage_Dashboard();
        assertThat(page.dashboardChartsExistsByText_Dashboard("Falha permanente"), containsString("Falha permanente"));
        page.hideDetailsOfCharts_Dashboard();
    }

    @Test
    public void DC28VerifyDetailsOfDeliveredCharts(){
        page.enterInThePortal();

        //Check in details of Delivered
        page.clickButtonsDetailsOfCharts_Dashboard(ConstantsAssistant.DELIVERYCENTER_DETAILS_DELIVERED);
        page.scrollDownPage_Dashboard();
        assertThat(page.dashboardChartsExistsByText_Dashboard("Aberturas únicas"), containsString("Aberturas únicas"));
        page.hideDetailsOfCharts_Dashboard();
    }



    private void setUrl() {
        new AddressEntity().setURL(UrlSystemAssistant.URL_DEV_DATAPORTAL);
    }

}
