package API;

import Assistant.AddressEntity;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests_VariableService extends BaseTestAPI {

    public ApiTests_VariableService() {
        setBasePath();
    }

    @Test
    @Description("Método que lista todas as listas de variáveis existentes")
    @DisplayName("Retornar todas as listas de Variáveis")
    public void listVariables(){
        given()
                .when()
                .get("/variablelist")
                .then()
                .log().all()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
                .body("content.variables", hasSize(greaterThan(0)))
        ;

    }

    @Test
    @Description("Consultando um item existente na lista")
    @DisplayName("Consultar item da VariableList por id")
    public void listVariables_SearchValidItemByID(){
        int id = insertVariableListInBase();
        given()
                .when()
                .pathParam("idVariable", id)
                .get("/variablelist/{idVariable}")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is(variableNameList))
                .body("companyId", is(8))
                .body("brandId", is(17))
                .body("id", notNullValue())
        ;
        deleteVariableListInBase(id);
    }

    @Test
    @Description("Testando método de criação de Lista variáveis")
    @DisplayName("Criar item no primeiro nível da lista de variáveis")
    public void listVariables_CreateVariable(){
        given()
                .contentType(ContentType.JSON)
                .body(params(createNameForListTest(), 8, 17, null))
                .when()
                .post("/variablelist")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", is(variableNameList))
                .body("brandId", is(17))
                .body("companyId", is(8))
        ;
        int id = returnInsertedItemIDByName(variableNameList, "variablelist");
        deleteVariableListInBase(id);
    }

    @Test
    @Description("Testando método de criação de Lista variáveis")
    @DisplayName("Criar item sem informar companyId")
    public void listVariables_WithoutCompanyId(){
        given()
                .contentType(ContentType.JSON)
                .body(params(createNameForListTest(), null, 17, null))
                .when()
                .post("/variablelist")
                .then()
                .log().body()
                .statusCode(500)
                .body("error", is("Internal Server Error"))
                .body("message", containsString("Request processing failed"))
        ;
    }

    @Test
    @Description("Testando método de criação de Lista variáveis")
    @DisplayName("Criar item sem informar brandId")
    public void listVariables_WithoutBrandId(){
        given()
                .contentType(ContentType.JSON)
                .body(params(createNameForListTest(), 8, null, null))
                .when()
                .post("/variablelist")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", is(variableNameList))
                .body("brandId", nullValue())
                .body("companyId", is(8))
        ;
        int id = returnInsertedItemIDByName(variableNameList, "variablelist");
        deleteVariableListInBase(id);
    }

    @Test
    @Description("Testando método de criação de Lista variáveis")
    @DisplayName("Criar item sem informar Name")
    public void listVariables_WithoutName(){
        given()
                .contentType(ContentType.JSON)
                .body(params(null, 8, 17, null))
                .when()
                .post("/variablelist")
                .then()
                .log().body()
                .statusCode(500)
                .body("error", is("Internal Server Error"))
                .body("message", containsString("Request processing failed"))
        ;
    }

    @Test
    @Description("Testando método de alteração de lista variáveis")
    @DisplayName("Alterar item no primeiro nível da lista de variáveis")
    public void listVariables_UpdateVariableList(){
        int id = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Nome Alterado\",\n" +
                        "\"brandId\": 20,\n" +
                        "\"companyId\": 10,\n" +
                        "\"variables\":[]}")
                .when()
                .pathParam("id", id)
                .put("/variablelist/{id}")
                .then()
//                .log().body()
                .statusCode(200)
                .body("name", is("Nome Alterado"))
                .body("companyId", is(10))
                .body("brandId", is(20))
        ;
        deleteVariableListInBase(id);
    }


    @Test
    @Description("Testando método de alteração de lista variáveis")
    @DisplayName("Alterar CompanyId para não informado")
    public void listVariables_UpdateCompanyIdToEmpty(){
        int id = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Nome Alterado\",\n" +
                        "\"brandId\": 20,\n" +
                        "\"companyId\":,\n" +
                        "\"variables\":[]}")
                .when()
                .pathParam("id", id)
                .put("/variablelist/{id}")
                .then()
                .statusCode(400)
        ;
        deleteVariableListInBase(id);
    }

    @Test
    @Description("Testando método de alteração de lista variáveis")
    @DisplayName("Alterar BrandId para não informado")
    public void listVariables_UpdateBrandIdToEmpty(){
        int id = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Nome Alterado\",\n" +
                        "\"brandId\":,\n" +
                        "\"companyId\": 8,\n" +
                        "\"variables\":[]}")
                .when()
                .pathParam("id", id)
                .put("/variablelist/{id}")
                .then()
                .statusCode(400)
        ;
        deleteVariableListInBase(id);
    }

    @Test
    @Description("Testando método de Deleção de lista variáveis")
    @DisplayName("Deletar item da lista de variáveis")
    public void listVariables_DeleteItemVariableList(){
        int id = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("id", id)
                .delete("/variablelist/{id}")
                .then()
                .statusCode(200)
        ;
    }


    @Test
    @Description("Método que lista todas as variáveis existentes")
    @DisplayName("Listar todas as variáveis")
    public void variables_ListAllVariable(){
        given()
                .when()
                .get("/variable")
                .then()
                .log().all()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
                .body("content.variables", hasSize(greaterThan(0)))
        ;
    }

    @Test
    @Description("Consultando um item existente na lista")
    @DisplayName("Consultar variável por id")
    public void variables_SearchValidItemByID(){
        int idVariable = insertVariableInBase();
        given()
                .when()
                .pathParam("idVariable", idVariable)
                .get("/variable/{idVariable}")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is(variableName))
                .body("companyId", is(8))
                .body("brandId", is(17))
                .body("id", notNullValue())
        ;
    }


//    @Test
//    public void cleanTestItens(){
//        deleteListOfItens();
//    }


    private int insertVariableInBase() { ;
    int idListVariable = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .body(params("testAPI", 8, 17, "TestAPI", createNameForVariableTest(), 0))
                .when()
                .post("/variable")
                .then()
//                .log().all()
                .statusCode(200)
        ;
        int id=returnInsertedItemIDByName(variableName, "variable");
        return id;
    }

    private int insertVariableListInBase(){
        given()
                .contentType(ContentType.JSON)
                .body(params(createNameForListTest(), 8, 17, null))
                .when()
                .post("/variablelist")
                .then()
//                .log().all()
                .statusCode(200)
        ;
        int id = returnInsertedItemIDByName(variableNameList, "variablelist");
        return id;
    }

    private int returnInsertedItemIDByName(String name, String context){
        ArrayList<Integer>ids =
                given()
                        .when()
                        .pathParam("context", context)
                        .get("/{context}")
                        .then()
                        .log().body()
                        .extract().path("content.findAll{it.name.startsWith('"+name+"')}.id");
        int result = ids.get(0);
        return result;
    }

    private void deleteVariableListInBase(int id){
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("id", id)
                .delete("/variablelist/{id}")
                .then()
                .statusCode(200)
        ;
    }

    private void deleteListOfItens(){
        ArrayList<Integer> list =
                given()
                        .when()
                        .get("/variablelist")
                        .then()
                        .extract().path("content.findAll{it.name.startsWith('testAPI')}.id")
                ;
        for (Integer id:list) {
            deleteVariableListInBase(id);
        }
    }



      /*  @Test
        @Description("")
        @DisplayName("")
        public void test(){
                given()
                .when()
                .get()
                .then()
            ;
        }

        {
            "name":"Nome Alterado",
            "brandId": 20,
            "companyId": 10,
            "variables":"[]"
        }
    */

    public void setBasePath(){
        AddressEntity.setBasePath("variableservicems/deutsche");
    }
}
