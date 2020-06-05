package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests_CampaignReport extends BaseTestAPI {

    public ApiTests_CampaignReport() {
        setBaseURI();
        setBasePath();
    }


    @Test
    @Description("Get - Retorna registros informando o dia")
    @DisplayName("Retornar registros por dia")
    public void get_CampaignReport_Days(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("email", "rafael.dsilva@valid.com")
                .when()
                .get("/report")
                .then()
                .log().all()
                .statusCode(200)

        ;
    }




    private void setBaseURI() {
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    private void setBasePath(){
        AddressEntity.setBasePath("campaignreport/deutsche");
    }
}
