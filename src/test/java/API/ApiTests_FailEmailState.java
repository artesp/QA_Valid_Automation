package API;

import Assistant.AddressEntity;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class ApiTests_FailEmailState extends BaseTestAPI {

    public ApiTests_FailEmailState() {
        setBasePath();
    }

    @Test
    @Description("Retorna lista com as falhas de entrega")
    @DisplayName("Listar Falhas de Entrega")
    public void listFailEmailState(){
        given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
        ;
    }


    public void setBasePath(){
        AddressEntity.setBasePath("campaignservicems/deutsche/failemailstate");
    }
}
