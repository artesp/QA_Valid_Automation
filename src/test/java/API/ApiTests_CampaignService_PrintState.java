package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApiTests_CampaignService_PrintState extends BaseTestAPI {

    private int idProduct;
    private int idPrintState;
    public ApiTests_CampaignService_PrintState() {
        setBaseURI();
        setBasePath();
    }

    @BeforeEach
    public void each(){
        idPrintState = generatePrintStateForTest();
    }

    @AfterEach
    public void clearClenup(){
        clearBase(idPrintState);
    }


    @Test
    @Description("Post - Cria um item na Print-State")
    @DisplayName("Criar registro na tabela Print-State")
    public void post_PrintState_Create(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
    }

    @Test
    @Description("Put - Alterar um item na Print-State")
    @DisplayName("Alterar registro na tabela Print-State")
    public void put_PrintState_Update(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
        given()
                .contentType(JSON)
                .body(paramsPrintState("2020-05-22 00:00:00", "2020-05-22 00:00:00", "2020-05-22 00:00:00",
                        "2020-05-22 00:00:00", "3", "inputFileAlterado", "2020-05-22 00:00:00",
                        "2020-05-22 00:00:00", idProduct, 2))
                .when()
                .pathParam("id", idPrintState)
                .put("print-state/{id}")
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
    @Description("Get - Listar os itens Print-State")
    @DisplayName("Listar registros da tabela Print-State")
    public void get_PrintState_ReadList(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
        given()
                .contentType(JSON)
                .when()
                .get("print-state")
                .then()
                .statusCode(200)
                .log().all()
                .body("content.id", contains(idPrintState))
        ;
    }

    @Test
    @Description("Get - Consultar os itens Print-State por Id")
    @DisplayName("Consultar registros da tabela Print-State por Id")
    public void get_PrintState_ReadItenById(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
        given()
                .contentType(JSON)
                .pathParam("id", idPrintState)
                .when()
                .get("print-state/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idPrintState))
                .body("inputFile", is("inputFileTeste"))
        ;
    }

    @Test
    @Description("Get - Consultar os itens Print-State por Id inexistente")
    @DisplayName("Consultar registros da tabela Print-State por Id inexistente")
    public void get_PrintState_ReadItenByInexistentId(){
        given()
                .contentType(JSON)
                .when()
                .get("print-state/999999")
                .then()
                .statusCode(404)
        ;
    }

    @Test
    @Description("Delete - Deletar iten Print-State")
    @DisplayName("Delete registros da tabela Print-State")
    public void delete_PrintState_ItemById(){
        assertTrue(idPrintState > 0);
        assertNotNull(idPrintState);
        /*Método de exclusão é chamado e validado no métudo @AfterEach*/
    }

    @Test
    @Description("Delete - Deletar iten Print-State - Id inexistente")
    @DisplayName("Delete registros da tabela Print-State com id inexistente")
    public void delete_PrintState_IdNoExists(){
        given()
                .contentType(JSON)
                .when()
                .delete("print-state/999999")
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
                .post("print-state")
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
        given()
                .contentType(JSON)
                .pathParam("id", id)
                .when()
                .delete("print-state/{id}")
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

    /*@Test
    @Description("")
    @DisplayName("")
    public void test(){

    }*/

    private void setBaseURI() {
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    public void setBasePath(){
        AddressEntity.setBasePath("campaignservicems/deutsche/");
    }

}
