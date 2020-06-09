package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitPlatform.class)
public class ApiTests_FileTransferService_Target extends BaseTestAPI {

    private int idTarget;

    public ApiTests_FileTransferService_Target() {
        setBaseURI();
        setBasePath();
    }

    @BeforeEach
    public void each(){
        idTarget = genarateTargetForTest();
    }

    @AfterEach
    public void clearClenup(){
        clearBase(idTarget);
    }

    @Test
    @Description("Get - Listar todos os targets configurados")
    @DisplayName("Listar todos os targets configurados")
    public void get_FileTransferTarget_listTargets(){
        given()
                .when()
                .get("/target")
                .then()
                .statusCode(200)
                .body("content.id", hasItem(idTarget))
        ;
    }

    @Test
    @Description("Get - Consultar target por id")
    @DisplayName("Consultar target por id")
    public void get_FileTransferTarget_searchById(){
        given()
                .when()
                .pathParam("id", idTarget )
                .get("/target/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idTarget))
                .body("protocol", is("SSH"))
                .body("name", is("repoTeste"))
                .body("address", is("127.0.0.1"))
                .body("repository", is("/tmp"))
                .body("username", is("rafael.dsilva"))
        ;
    }

    @Test
    @Description("Post - Criar target")
    @DisplayName("Criar target")
    public void post_FileTransferTarget_createTarget(){
        assertTrue(idTarget != 0);
        assertTrue(idTarget > 0);
        assertNotNull(idTarget);
    }

    @Test
    @Description("Put - Alterar target")
    @DisplayName("Alterar target")
    public void put_FileTransferTarget_updateTarget(){
        given()
                .when()
                .contentType(JSON)
                .pathParam("id", idTarget )
                .body(targetParans(
                        "127.0.0.0",
                        "TargetAlterado",
                        "senhaAlterada",
                        3,
                        "/home/rafael",
                        "rafael"))
                .log().all()
                .put("/target/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(idTarget))
                .body("protocol", is("FTP"))
                .body("name", is("TargetAlterado"))
                .body("address", is("127.0.0.0"))
                .body("repository", is("/home/rafael"))
                .body("username", is("rafael"))
        ;
    }

    private int genarateTargetForTest() {
        Response response = (Response) given()
                .contentType(JSON)
                .body(targetParans(
                        "127.0.0.1",
                        "repoTeste",
                        "password",
                        1,
                        "/tmp",
                        "rafael.dsilva"))
                .when()
                .post("/target")
                .then()
                .log().all()
                .statusCode(200)
                .body("protocol", is("SSH"))
                .body("name", is("repoTeste"))
                .body("address",is("127.0.0.1"))
                .body("repository", is("/tmp"))
                .body("username", is("rafael.dsilva"))
                .extract();
        idTarget = JsonPath.from(response.asString()).getInt("id");
        return idTarget;
    }

    private void clearBase(int id){
//        try {
//            sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        given()
                .contentType(JSON)
                .pathParam("id", id)
                .when()
                .delete("target/{id}")
                .then()
                .statusCode(200)
        ;
    }

    private Map<String, Object> targetParans(String address, String name, String pwd,
                                             int protocol, String repo, String usrName){
        Map<String, Object> param = new HashMap<>();
        param.put("address", address);
        param.put("name", name);
        param.put("password", pwd);
        param.put("protocol", protocol);
        param.put("repository", repo);
        param.put("username", usrName);
        return param;
    }

    private void setBaseURI() {
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    private void setBasePath(){
        AddressEntity.setBasePath("filetransferms/deutsche");
    }
}
