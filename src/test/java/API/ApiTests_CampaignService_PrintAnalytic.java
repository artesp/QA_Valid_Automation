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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ApiTests_CampaignService_PrintAnalytic extends BaseTestAPI {

    private int idProduct;
    private int idPrint;
    String path = "campaignservicems/deutsche/";

    @Before
    public void each(){
        idPrint = generatePrintAnalyticForTest();
    }

    @After
    public void clearClenup(){
        clearBase(idPrint);
    }


    @Test
    public void post_PrintAnalytic_CriarRegistroNaTabela(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
    }

    @Test
    public void put_PrintAnalytic_AlterarRegistroNaTabela(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
        given()
                .contentType(JSON)
                .body(paramsPrintAnalytic("Rua do Alterado", "2020-05-27 00:00:00", 1,
                        "printTeste do Alterado", "outputFileTeste", 1, "2020-05-27 00:00:00",
                        "2020-05-27 00:00:00", idProduct, 1, 3, 1))
                .when()
                .pathParam("id", idPrint)
                .put(path + "print/{id}")
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
    public void get_PrintAnalytic_ListarRegistrosDaTabela(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
        given()
                .contentType(JSON)
                .when()
                .get(path + "print")
                .then()
                .statusCode(200)
                .body("content.id", hasItem(idPrint))
        ;
    }

    @Test
    public void get_PrintAnalytic_ConsultarRegistroPorId(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
        given()
                .contentType(JSON)
                .pathParam("id", idPrint)
                .when()
                .get(path + "print/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idPrint))
                .body("address", is("Hotel Continental"))
                .body("name", is("John Wick"))
        ;
    }

    @Test
    public void get_PrintAnalytic_ConsultarRegistrosPorIdInexistente(){
        given()
                .contentType(JSON)
                .when()
                .get(path + "print/999999")
                .then()
                .statusCode(404)
        ;
    }

    @Test
    public void delete_PrintAnalytic_DeletarRegistros(){
        assertTrue(idPrint > 0);
        assertNotNull(idPrint);
        /*Método de exclusão é chamado e validado no métudo @AfterEach*/
    }

    @Test
    public void delete_PrintAnalytic_DeletarRegistroIdInexistente(){
        given()
                .contentType(JSON)
                .when()
                .delete(path + "print/999999")
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
                .post(path + "print")
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
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        given()
                .contentType(JSON)
                .pathParam("id", id)
                .when()
                .delete(path + "print/{id}")
                .then()
                .statusCode(204);
    }

}
