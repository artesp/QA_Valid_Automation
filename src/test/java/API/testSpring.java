package API;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class testSpring {

    @Test
    public void alluraSpringTestAPI(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/topicos")
                .then()
                .log().all()
                .statusCode(200)
        ;
    }
}
