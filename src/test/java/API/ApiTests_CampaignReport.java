package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests_CampaignReport extends BaseTestAPI {

    String path = "campaignreport/deutsche";

    @Test
    public void get_CampaignReport_RetornarRegistrosOffLine(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("email", "rafael.dsilva@valid.com")
                .when()
                .get(path + "/report")
                .then()
                .statusCode(202)
        ;
    }
}
