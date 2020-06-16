package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ApiTests_FileTransferService_Target extends BaseTestAPI {

    private int idTarget;
    String path = "filetransferms/deutsche";

    @Before
    public void each(){
        idTarget = genarateTargetForTest();
    }

    @After
    public void clearClenup(){
        clearBase(idTarget);
    }

    @Test
    public void get_FileTransferTarget_ListarTargets(){
        given()
                .when()
                .get(path + "/target")
                .then()
                .statusCode(200)
                .body("content.id", hasItem(idTarget))
        ;
    }

    @Test
    public void get_FileTransferTarget_ConsultarTargetPorId(){
        given()
                .when()
                .pathParam("id", idTarget )
                .get(path + "/target/{id}")
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
    public void post_FileTransferTarget_CriarTargetComSucesso(){
        assertTrue(idTarget != 0);
        assertTrue(idTarget > 0);
        assertNotNull(idTarget);
    }

    @Test
    public void put_FileTransferTarget_AlterarTarget(){
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
                .put(path + "/target/{id}")
                .then()
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
                .post(path + "/target")
                .then()
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
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        given()
                .contentType(JSON)
                .pathParam("id", id)
                .when()
                .delete(path + "/target/{id}")
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

}
