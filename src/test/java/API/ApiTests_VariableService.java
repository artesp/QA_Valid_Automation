package API;

import Core.BaseTestAPI;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests_VariableService extends BaseTestAPI {

   String path = "variableservice/deutsche";

    @Test
    public void get_VariableService_RetornarTodasAsListasDeVariaveis(){
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
    public void get_VariableService_ConsultarPorId(){
        int id = insertVariableListInBase();
        given()
                .when()
                .pathParam("idVariable", id)
                .get(path + "/variablelist/{idVariable}")
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
    public void post_VariableService_CriarVariavelNoPrimeiroNivelDaLista(){
        given()
                .contentType(ContentType.JSON)
                .body(params(createNameForListTest(), 8, 17, null))
                .when()
                .post(path + "/variablelist")
                .then()
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
    public void post_VariableService_CriarVariavelSemInformarCompanyId(){
        given()
                .contentType(ContentType.JSON)
                .body(params(createNameForListTest(), null, 17, null))
                .when()
                .post(path + "/variablelist")
                .then()
                .statusCode(500)
                .body("error", is("Internal Server Error"))
                .body("message", containsString("Request processing failed"))
        ;
    }

    @Test
    public void post_VariableService_CriarVariavelSemInformarBrandId(){
        given()
                .contentType(ContentType.JSON)
                .body(params(createNameForListTest(), 8, null, null))
                .when()
                .post(path + "/variablelist")
                .then()
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
    public void post_VariableService_CriarVariavelSemInformarNome(){
        given()
                .contentType(ContentType.JSON)
                .body(params(null, 8, 17, null))
                .when()
                .post(path + "/variablelist")
                .then()
                .statusCode(500)
                .body("error", is("Internal Server Error"))
                .body("message", containsString("Request processing failed"))
        ;
    }

    @Test
    public void put_VariableService_AlterarVariavelNoPrimeiroNivelDaLista(){
        int id = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Nome Alterado\",\n" +
                        "\"brandId\": 20,\n" +
                        "\"companyId\": 10,\n" +
                        "\"variables\":[]}")
                .when()
                .pathParam("id", id)
                .put(path + "/variablelist/{id}")
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
    public void put_VariableService_AlterarVariavelCompanyIdNaoInformado(){
        int id = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Nome Alterado\",\n" +
                        "\"brandId\": 20,\n" +
                        "\"companyId\":,\n" +
                        "\"variables\":[]}")
                .when()
                .pathParam("id", id)
                .put(path + "/variablelist/{id}")
                .then()
                .statusCode(400)
        ;
        deleteVariableListInBase(id);
    }

    @Test
    public void put_VariableService_AlterarVariavelComBrandIdNaoInformado(){
        int id = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Nome Alterado\",\n" +
                        "\"brandId\":,\n" +
                        "\"companyId\": 8,\n" +
                        "\"variables\":[]}")
                .when()
                .pathParam("id", id)
                .put(path + "/variablelist/{id}")
                .then()
                .statusCode(400)
        ;
        deleteVariableListInBase(id);
    }

    @Test
    public void post_VariableService_DeletarListaDeVariavel(){
        int id = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("id", id)
                .delete(path + "/variablelist/{id}")
                .then()
                .statusCode(200)
        ;
    }


    @Test
    public void get_VariableService_ListarTodasAsVariaveis(){
        given()
                .when()
                .get(path + "/variable")
                .then()
                .log().all()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
                .body("content.variables", hasSize(greaterThan(0)))
        ;
    }

    @Test
    public void get_VariableService_ConsultarVariavelPorId(){
        int idVariable = insertVariableInBase();
        given()
                .when()
                .pathParam("idVariable", idVariable)
                .get(path + "/variable/{idVariable}")
                .then()
                .statusCode(200)
                .body("name", is(variableName))
                .body("companyId", is(8))
                .body("brandId", is(17))
                .body("id", notNullValue())
        ;
    }


    private int insertVariableInBase() { ;
        int idListVariable = insertVariableListInBase();
        given()
                .contentType(ContentType.JSON)
                .body(params("testAPI", 8, 17, "TestAPI", createNameForVariableTest(), 0))
                .when()
                .post(path + "/variable")
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
                .post(path + "/variablelist")
                .then()
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
                        .get(path + "/{context}")
                        .then()
                        .extract().path("content.findAll{it.name.startsWith('"+name+"')}.id");
        int result = ids.get(0);
        return result;
    }

    private void deleteVariableListInBase(int id){
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("id", id)
                .delete(path + "/variablelist/{id}")
                .then()
                .statusCode(200)
        ;
    }

    private void deleteListOfItens(){
        ArrayList<Integer> list =
                given()
                        .when()
                        .get(path + "/variablelist")
                        .then()
                        .extract().path("content.findAll{it.name.startsWith('testAPI')}.id")
                ;
        for (Integer id:list) {
            deleteVariableListInBase(id);
        }
    }
}
