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
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitPlatform.class)
public class ApiTests_CampaignService_PrintProduct extends BaseTestAPI {

    private int idProduct;
    public ApiTests_CampaignService_PrintProduct() {
        setBasePath();
        setBaseURI();
    }

    @BeforeEach
    public void each(){
        idProduct = generateProductForTest();
    }

    @AfterEach
    public void clearBase(){
        deleteProductForTest(idProduct);
    }


    @Test
    @Description("Get - Recupera Lista de Produtos")
    @DisplayName("Consulta de Produto na Base de Dados")
    public void Get_Product_Print(){
        String name = getProductPrintNameGenerated(idProduct);
        assertNotNull(idProduct);
        assertNotNull(name);

        given()
                .when()
                .get("product-print")
                .then()
                .statusCode(200)
                .body("content.name", hasItem(name))
        ;
    }

    @Test
    @Description("Get - Recupera Lista de Produtos por Id")
    @DisplayName("Consulta de Produto na Base de Dados por Id")
    public void Get_Product_Print_ById(){
        String name = getProductPrintNameGenerated(idProduct);
        assertNotNull(idProduct);
        assertNotNull(name);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", idProduct)
                .when()
                .get("product-print/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idProduct))
                .body("name", is(name))
        ;
    }

    @Test
    @Description("Post - Criar um item name igual a null")
    @DisplayName("Cria Produto na Base de Dados sem informar o nome")
    public void post_Product_Print_WithoutName(){
        given()
                .contentType(ContentType.JSON)
                .body(paramsProductPrint(null))
                .when()
                .post("product-print")
                .then()
                .statusCode(400);
    }

    @Test
    @Description("Post - Criar um item de Produto")
    @DisplayName("Cria Produto na Base de Dados")
    public void post_Product_Print(){
        assertTrue(idProduct > 0);
        assertNotNull(idProduct);
    }

    @Test
    @Description("Put - Altera um item de Produto")
    @DisplayName("Alterar Produto na Base de Dados")
    public void put_Product_Print(){
        String name = getProductPrintNameGenerated(idProduct);
        assertNotNull(idProduct);
        assertNotNull(name);

        given()
                .contentType(ContentType.JSON)
                .body(paramsProductPrint("NomeAlterado"))
                .when()
                .pathParam("id", idProduct)
                .put("product-print/{id}")
                .then()
                .statusCode(200)
        ;
    }

    @Test
    @Description("Delete - Deleta um item de Produto")
    @DisplayName("Deletar Produto na Base de Dados")
    public void delete_Product_Print(){
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
                .post("product-print")
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
                .get("product-print/{id}")
                .then()
                .statusCode(200).extract();
        return new String(JsonPath.from(response.asString()).getString("name"));
    }

    public void deleteProductForTest(int id){
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete("product-print/{id}")
                .then()
                .statusCode(204);
    }

    private Map<String, Object> paramsProductPrint(String name){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
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
