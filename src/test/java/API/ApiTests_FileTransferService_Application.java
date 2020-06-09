package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(JUnitPlatform.class)
public class ApiTests_FileTransferService_Application extends BaseTestAPI {

    public ApiTests_FileTransferService_Application() {
        setBaseURI();
        setBasePath();
    }

    /*Existe apenas um GET para as requisições de Application*/

    @Test
    @Description("Get - Listar todas as aplicações configuradas. " +
                 "Existe apenas um GET para as requisições de Application")
    @DisplayName("Listar todas os aplicações configuradas")
    public void get_FileTransferApplication_listApplication(){
        given()
                .when()
                .get("/application")
                .then()
                .statusCode(200)
                .body("$", hasSize(4))
                .body("findAll{it}.type", hasItems("REPORT", "MAILING", "CAMPAIGN", "BUNDLE"))
        ;
    }

    private void setBaseURI() {
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    private void setBasePath(){
        AddressEntity.setBasePath("filetransferms/deutsche");
    }
}
