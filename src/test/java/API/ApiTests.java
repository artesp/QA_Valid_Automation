package API;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiTests {

    @Test
    public void testApi(){
        given().auth()
                .form("admin@edp.com.br","Senha@123")
                .when()
                .get("https://datainject.validsolutions.net/campaignservicems/edp/customerview/5799")
                .then()
                .log().all()
                .statusCode(200)
        ;
        assertEquals("", "");
    }
}
