package API;

import Assistant.AddressEntity;
import Assistant.SharedMethods;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests_VariableService extends BaseTestAPI {

    public ApiTests_VariableService() {
        setBasePath();
    }

    @Test
    @Description("Método que lista todas as variáveis existentes")
    @DisplayName("Listar Variáveis")
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
    @DisplayName("Consultar item de variáveis por id")
    public void listVariables_SearchValidItemByID(){

        String idVariable = insertVariableListInBase();

        given()
                .when()
                .get("/variablelist/'"+ idVariable+"'")
                .then()
                .log().all()
                .statusCode(200)
//               .body("name", is("string"))
//                .body("content.findAll{it.id != 2}.size()", hasSize(0))
        ;
    }



    private void insertVariableInBase() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"alias\": \"test_API\",\n" +
                        "\"brandId\": 17,\n" +
                        "\"companyId\": 8,\n" +
                        "\"description\": \"test_API\",\n" +
                        "\"name\": \"test_API"+dateHours()+"\",\n" +
                        "\"variableListId\": 2\n" +
                        "}")
                .when()
                .post("/variable")
                .then()
                .log().all()
                .statusCode(200)
        ;
    }

    private String insertVariableListInBase(){
        String name = "testAPI"+dateHours();
        given()
                .contentType(ContentType.JSON)
                .body("{ \"brandId\": \"17\",\n" +
                        "\"companyId\": \"8\",\n" +
                        "\"name\": \""+name+"\",\n" +
                        "\"variables\": []}")
                .when()
                .post("/variablelist")
                .then()
                .log().all()
                .statusCode(200)
        ;
        String id = returnInsertedItem(name);
        return id;
    }

    private String returnInsertedItem(String name){
        ArrayList<String>ids =
                given()
                        .pathParam("name",name)
                        .when()
                        .get("/variablelist/{name}")
                        .then()
                        .extract().path("name.findAll{it.startsWith('"+name+"')}");
        String result = ids.get(0);
        return result;
    }

    private String dateHours(){
        return new SharedMethods().returnDateHours();
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
    */

    public void setBasePath(){
        AddressEntity.setBasePath("variableservicems/deutsche");
    }
}
