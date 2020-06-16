package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApiTests_CampaignService_PrintProduct extends BaseTestAPI {

    private int idProduct;
    String path = "campaignservicems/deutsche/";

    @Before
    public void each(){
        idProduct = generateProductForTest();
    }

    @After
    public void clearBase(){
        deleteProductForTest(idProduct);
    }


    @Test
    public void Get_Product_ConsultarProduto(){
        String name = getProductPrintNameGenerated(idProduct);
        assertNotNull(idProduct);
        assertNotNull(name);

        given()
                .when()
                .get(path + "product-print")
                .then()
                .statusCode(200)
                .body("content.name", hasItem(name))
        ;
    }

    @Test
    public void Get_Product_Print_ConsultarProdutoPorId(){
        String name = getProductPrintNameGenerated(idProduct);
        assertNotNull(idProduct);
        assertNotNull(name);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", idProduct)
                .when()
                .get(path + "product-print/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idProduct))
                .body("name", is(name))
        ;
    }

    @Test
    public void post_Product_Print_CriarProdutoSemNome(){
        given()
                .contentType(ContentType.JSON)
                .body(paramsProductPrint(null))
                .when()
                .post(path + "product-print")
                .then()
                .statusCode(400);
    }

    @Test
    public void post_Product_CriarProdutoComSucesso(){
        assertTrue(idProduct > 0);
        assertNotNull(idProduct);
    }

    @Test
    public void put_Product_AlterarProduto(){
        String name = getProductPrintNameGenerated(idProduct);
        assertNotNull(idProduct);
        assertNotNull(name);

        given()
                .contentType(ContentType.JSON)
                .body(paramsProductPrint("NomeAlterado"))
                .when()
                .pathParam("id", idProduct)
                .put(path + "product-print/{id}")
                .then()
                .statusCode(200)
        ;
    }

    @Test
    public void delete_Product_DeletarProduto(){
        assertTrue(idProduct > 0);
        assertNotNull(idProduct);
        /*Método de deleção é chamado pelo AfterEach*/
    }




    public int generateProductForTest() {
        String name = createNameForVariableTest();
        Response response = (Response) given()
                .contentType(ContentType.JSON)
                .body(paramsProductPrint(name))
                .when()
                .post(path + "product-print")
                .then()
                .statusCode(201).extract();
        int id = JsonPath.from(response.asString()).getInt("id");
        return id;
    }

    public String getProductPrintNameGenerated(int id){
        Response response = (Response) given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get(path + "product-print/{id}")
                .then()
                .statusCode(200).extract();
        return new String(JsonPath.from(response.asString()).getString("name"));
    }

    public void deleteProductForTest(int id){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete(path + "product-print/{id}")
                .then()
                .statusCode(204);
    }

    private Map<String, Object> paramsProductPrint(String name){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        return parameters;
    }



}
