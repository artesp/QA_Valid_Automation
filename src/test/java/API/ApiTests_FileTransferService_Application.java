package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests_FileTransferService_Application extends BaseTestAPI {

    String path = "filetransferms/deutsche";

    /*Existe apenas um GET para as requisições de Application*/

    @Test
    public void  get_FileTransferApplication_ListarAplicacoesConfiguradas(){
        given()
                .log().all()
                .when()
                .get(path + "/application")
                .then()
                .statusCode(200)
                .body("$", hasSize(4))
                .log().all()
                .body("findAll{it}.type", hasItems("REPORT", "MAILING", "CAMPAIGN", "BUNDLE"))
        ;
    }
}
