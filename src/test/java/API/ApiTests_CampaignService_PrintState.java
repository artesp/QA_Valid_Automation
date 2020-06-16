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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ApiTests_CampaignService_PrintState extends BaseTestAPI {

    private int idProduct;
    private int idPrintState;
    String path = "campaignservicems/deutsche/";

    @Before
    public void each(){
        idPrintState = generatePrintStateForTest();
    }

    @After
    public void clearClenup(){
        clearBase(idPrintState);
    }


    @Test
    public void post_PrintState_CriarRegistroComSucesso(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
    }

    @Test
    public void put_PrintState_AlterarRegistro(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
        given()
                .contentType(JSON)
                .body(paramsPrintState("2020-05-22 00:00:00", "2020-05-22 00:00:00", "2020-05-22 00:00:00",
                        "2020-05-22 00:00:00", "3", "inputFileAlterado", "2020-05-22 00:00:00",
                        "2020-05-22 00:00:00", idProduct, 2))
                .when()
                .pathParam("id", idPrintState)
                .put(path + "print-state/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idPrintState))
                .body("cifDate", is("2020-05-22 00:00:00"))
                .body("completedDate", is("2020-05-22 00:00:00"))
                .body("expeditionDate", is("2020-05-22 00:00:00"))
                .body("finishDate", is("2020-05-22 00:00:00"))
                .body("idSCP", is(3))
                .body("inputFile", is("inputFileAlterado"))
                .body("postDate", is("2020-05-22 00:00:00"))
                .body("processedDate", is("2020-05-22 00:00:00"))
                .body("productId", is(idProduct))
                .body("statusId", is(2))
        ;
    }

    @Test
    public void get_PrintState_ListarRegistros(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
        given()
                .contentType(JSON)
                .when()
                .get(path + "print-state")
                .then()
                .statusCode(200)
                .body("content.id", contains(idPrintState))
        ;
    }

    @Test
    public void get_PrintState_ConsultarRegistroPorId(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
        given()
                .contentType(JSON)
                .pathParam("id", idPrintState)
                .when()
                .get(path + "print-state/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idPrintState))
                .body("inputFile", is("inputFileTeste"))
        ;
    }

    @Test
    public void get_PrintState_ConsultarRegistroPorIdInexistente(){
        given()
                .contentType(JSON)
                .when()
                .get(path + "print-state/999999")
                .then()
                .statusCode(404)
        ;
    }

    @Test
    public void delete_PrintState_DeletarRegistro(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
        /*Método de exclusão é chamado e validado no métudo @AfterEach*/
    }

    @Test
    public void delete_PrintState_DeletarRegistroPorIdInexistente(){
        given()
                .contentType(JSON)
                .when()
                .delete(path + "print-state/999999")
                .then()
                .statusCode(404)
        ;
    }



    private int generatePrintStateForTest() {
        idProduct = new ApiTests_CampaignService_PrintProduct().generateProductForTest();
        Response response = (Response) given()
                .contentType(JSON)
                .body(paramsPrintState("2020-05-19 00:00:00", "2020-05-19 00:00:00", "2020-05-19 00:00:00",
                        "2020-05-19 00:00:00", "2", "inputFileTeste", "2020-05-19 00:00:00",
                        "2020-05-19 00:00:00", idProduct, 3))
                .when()
                .post(path + "print-state")
                .then()
                .statusCode(201)
                .body("cifDate", is("2020-05-19 00:00:00"))
                .body("completedDate", is("2020-05-19 00:00:00"))
                .body("expeditionDate", is("2020-05-19 00:00:00"))
                .body("finishDate", is("2020-05-19 00:00:00"))
                .body("idSCP", is(2))
                .body("inputFile", is("inputFileTeste"))
                .body("postDate", is("2020-05-19 00:00:00"))
                .body("processedDate", is("2020-05-19 00:00:00"))
                .body("productId", is(idProduct))
                .body("statusId", is(3))
                .extract();
        int id = JsonPath.from(response.asString()).getInt("id");
        return id;
    }

    private void clearBase(int id){
        deletePrintStateForTest(id);
        new ApiTests_CampaignService_PrintProduct().deleteProductForTest(idProduct);
    }

    private void deletePrintStateForTest(int id){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        given()
                .contentType(JSON)
                .pathParam("id", id)
                .when()
                .delete(path + "print-state/{id}")
                .then()
                .statusCode(204);
    }

    private Map<String, Object> paramsPrintState(
            String cifDate, String completedDate, String expeditionDate,
            String finishDate, String idSCP, String inputFile, String postDate,
            String processedDate, int productId, int statusId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cifDate", cifDate);
        parameters.put("completedDate", completedDate);
        parameters.put("expeditionDate", expeditionDate);
        parameters.put("finishDate", finishDate);
        parameters.put("idSCP", idSCP);
        parameters.put("inputFile", inputFile);
        parameters.put("postDate", postDate);
        parameters.put("processedDate", processedDate);
        parameters.put("productId", productId);
        parameters.put("statusId", statusId);
        parameters.put("totalCover", 0);
        parameters.put("totalDoc", 0);
        parameters.put("totalPage", 0);
        return parameters;
    }

}
