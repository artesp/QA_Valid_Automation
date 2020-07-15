package API;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class ApiTests_SocketLibs{

    /*
    * Testes devem ser executados subindo a aplicação em testes localmente
    * Projeto SocketlabsLib existente no respositório do bitbucket da Válid.
    * Url do repo: https://bitbucket.org/ValidSolutions/socketlabslib/src/development/
    * */

    @Before
    public void each(){
        RestAssured.baseURI="http://localhost:8887/socketlabs-libs";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @After
    public void clear(){
        clearEmail();
    }

    @Test
    public void get_Socketlibs_ConsultarEmailNaListaDeSuprimidos() {
        given()
                .contentType(ContentType.JSON)
                .param("emailAddress", "corujacardia@hotmail.com")
                .param("serverId", "11677")
                .when()
                .get("/supression")
                .then()
                .log().all()
                .statusCode(200)
                .body("emailAddress", is("CORUJACARDIA@HOTMAIL.COM"))
                .body("statusId", is("Complaint"))
                .body("status", is("Suppressed"))
                .body("suppressionReason", is("abuse"))
        ;
    }

    @Test
    public void get_Socketlibs_IncluirEmailNaListaDeSuprimidos() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("serverId", "11677")
                .body(bodySocketLibs())
                .when()
                .post("/supression")
                .then()
                .log().all()
                .statusCode(201)
        ;

        given()
                .contentType(ContentType.JSON)
                .param("emailAddress", "teste@testeLisbs.com")
                .param("serverId", "11677")
                .when()
                .get("/supression")
                .then()
                .statusCode(200)
                .body("emailAddress", is("teste@testeLisbs.com"))
                .body("statusId", is("CustomerRequest"))
                .body("status", is("Suppressed"))
                .body("suppressionReason", is("Suppression API Request"))
        ;

    }

    @Test
    public void get_Socketlibs_InserirEmailDuplicadoNaListaDeSuprimidos() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("serverId", "11677")
                .body(bodySocketLibs())
                .when()
                .post("/supression")
                .then()
                .log().all()
                .statusCode(201)
        ;

        given()
                .contentType(ContentType.JSON)
                .queryParam("serverId", "11677")
                .body(bodySocketLibs())
                .when()
                .post("/supression")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", containsString("Error in request with response HTTP/1.1 409 Conflict"))
        ;
    }

    @Test
    public void get_Socketlibs_ExcluirEmailNaListaDeSuprimidos() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("serverId", "11677")
                .body(bodySocketLibs())
                .when()
                .post("/supression")
                .then()
                .log().all()
                .statusCode(201)
        ;

        given()
                .contentType(ContentType.JSON)
                .param("emailAddress", "teste@testeLisbs.com")
                .param("serverId", "11677")
                .when()
                .delete("/supression")
                .then()
                .log().all()
                .statusCode(204)
        ;

    }


    private Map<Object, String> bodySocketLibs(){
        Map<Object, String> body = new HashMap<>();
        body.put("emailAddress", "teste@testeLisbs.com");
        return body;
    }

    private void clearEmail() {
        given()
                .contentType(ContentType.JSON)
                .param("emailAddress", "teste@testeLisbs.com")
                .param("serverId", "11677")
                .when()
                .delete("/supression")
        ;
    }






}
