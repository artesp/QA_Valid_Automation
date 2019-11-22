package API;

import io.restassured.RestAssured;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiTests {

    @Test
    public void testApi(){
        RestAssured.given()
                .when()
                .then()
        ;
        assertEquals("", "");
    }
}
