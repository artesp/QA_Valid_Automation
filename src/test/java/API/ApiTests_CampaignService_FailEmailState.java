package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class ApiTests_CampaignService_FailEmailState extends BaseTestAPI {

    String path = "campaignservicems/deutsche";

    @Test
    public void get_FailEmailState_ListarFalhasDeEntrega(){
        given()
                .when()
                .get(path + "/failemailstate")
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
        ;
    }
}
