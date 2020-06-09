package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(JUnitPlatform.class)
public class ApiTests_CampaignService_PrintAnalytic extends BaseTestAPI {

    private int idProduct;
    private int idPrint;

    public ApiTests_CampaignService_PrintAnalytic() {
        setBasePath();
        setBaseURI();
    }

    @BeforeEach
    public void each(){
        idPrint = generatePrintAnalyticForTest();
    }

    @AfterEach
    public void clearClenup(){
        clearBase(idPrint);
    }


    @Test
    @Description("Post - Criar item para Print - Analytic")
    @DisplayName("Criar registro na tabela Print - Analytic")
    public void post_PrintAnalytic_Create(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
    }

    @Test
    @Description("Put - Alterar um item na Print-Analytic")
    @DisplayName("Alterar registro na tabela Print-Analytic")
    public void put_PrintAnalytic_Update(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
        given()
                .contentType(JSON)
                .body(paramsPrintAnalytic("Rua do Alterado", "2020-05-27 00:00:00", 1,
                        "printTeste do Alterado", "outputFileTeste", 1, "2020-05-27 00:00:00",
                        "2020-05-27 00:00:00", idProduct, 1, 3, 1))
                .when()
                .pathParam("id", idPrint)
                .put("print/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idPrint))
                .body("address", is("Rua do Alterado"))
                .body("cifDate", is("2020-05-27 00:00:00"))
                .body("customerCode", is(1))
                .body("name", is("printTeste do Alterado"))
                .body("outputFile", is("outputFileTeste"))
                .body("postCode", is(1))
                .body("postDate", is("2020-05-27 00:00:00"))
                .body("processedDate", is("2020-05-27 00:00:00"))
                .body("productId", is(idProduct))
                .body("scpId", is(1))
                .body("statusId", is(3))
                .body("zipcode", is(1))
        ;
    }

    @Test
    @Description("Get - Listar os itens Print-Analytic")
    @DisplayName("Listar registros da tabela Print-Analytic")
    public void get_PrintAnalytic_ReadList(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
        given()
                .contentType(JSON)
                .when()
                .get("print")
                .then()
                .statusCode(200)
                .body("content.id", contains(idPrint))
        ;
    }

    @Test
    @Description("Get - Consultar os itens Print-Analytic por Id")
    @DisplayName("Consultar registros da tabela Print-Analytic por Id")
    public void get_PrintAnalytic_ReadItenById(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
        given()
                .contentType(JSON)
                .pathParam("id", idPrint)
                .when()
                .get("print/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idPrint))
                .body("address", is("Hotel Continental"))
                .body("name", is("John Wick"))
        ;
    }

    @Test
    @Description("Get - Consultar os itens Print-State por Id inexistente")
    @DisplayName("Consultar registros da tabela Print-State por Id inexistente")
    public void get_PrintAnalytic_ReadItenByInexistentId(){
        given()
                .contentType(JSON)
                .when()
                .get("print/999999")
                .then()
                .statusCode(404)
        ;
    }

    @Test
    @Description("Delete - Deletar iten Print-Analytic")
    @DisplayName("Delete registros da tabela Print-Analytic")
    public void delete_PrintAnalytic_ItemById(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
        /*Método de exclusão é chamado e validado no métudo @AfterEach*/
    }

    @Test
    @Description("Delete - Deletar iten Print-Analytic - Id inexistente")
    @DisplayName("Delete registros da tabela Print-Analytic com id inexistente")
    public void delete_PrintAnalytic_IdNoExists(){
        given()
                .contentType(JSON)
                .when()
                .delete("print/999999")
                .then()
                .statusCode(404)
        ;
    }



    private int generatePrintAnalyticForTest() {
        idProduct = new ApiTests_CampaignService_PrintProduct().generateProductForTest();
        Response response = (Response) given()
                .contentType(JSON)
                .body(paramsPrintAnalytic("Hotel Continental", "2020-05-26 00:00:00", 1,
                        "John Wick", "outputFileTeste", 1, "2020-05-26 00:00:00",
                        "2020-05-26 00:00:00", idProduct, 1, 3, 1))
                .when()
                .post("print")
                .then()
                .statusCode(201)
                .body("address", is("Hotel Continental"))
                .body("cifDate", is("2020-05-26 00:00:00"))
                .body("customerCode", is(1))
                .body("name", is("John Wick"))
                .body("outputFile", is("outputFileTeste"))
                .body("postCode", is(1))
                .body("postDate", is("2020-05-26 00:00:00"))
                .body("processedDate", is("2020-05-26 00:00:00"))
                .body("productId", is(idProduct))
                .body("scpId", is(1))
                .body("statusId", is(3))
                .body("zipcode", is(1))
                .extract();
        int id = JsonPath.from(response.asString()).getInt("id");
        return id;
    }

    private Map<String, Object> paramsPrintAnalytic(
            String address, String cifDate, int customerCode,
            String name, String outputFile, int postCode, String postDate,
            String processedDate, int productId, int scpId, int statusId,int zipcode) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("address", address);
        parameters.put("cifDate", cifDate);
        parameters.put("customerCode", customerCode);
        parameters.put("endPage", 0);
        parameters.put("name", name);
        parameters.put("outputFile", outputFile);
        parameters.put("postCode", postCode);
        parameters.put("postDate", postDate);
        parameters.put("processedDate", processedDate);
        parameters.put("productId", productId);
        parameters.put("scpId", scpId);
        parameters.put("startPage", 0);
        parameters.put("statusId", statusId);
        parameters.put("totalPage", 0);
        parameters.put("totalSheet", 0);
        parameters.put("zipcode", zipcode);
        return parameters;
    }



    private void clearBase(int id){
        deletePrintForTest(id);
        new ApiTests_CampaignService_PrintProduct().deleteProductForTest(idProduct);
    }

    private void deletePrintForTest(int id){
        given()
                .contentType(JSON)
                .pathParam("id", id)
                .when()
                .delete("print/{id}")
                .then()
                .statusCode(204);
    }

    private void setBaseURI() {
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    public void setBasePath(){
        AddressEntity.setBasePath("campaignservicems/deutsche/");
    }

     /*@Test
    @Description("")
    @DisplayName("")
    public void test(){

    }*/
}
