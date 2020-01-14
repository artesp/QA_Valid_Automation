package API;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiTests {

    @Test
    public void testApi(){
        given()
                .when()
                .get("https://devinject.validsolutions.net/campaignservicems/deutsche/campaignstate?page=0&campaign.brandId=17&productId=109&campaign.startDate=2019-12-01%2000:00:00&campaign.startDate=2020-01-13%2023:59:59")
                .then()
                .log().all()
                .statusCode(200)
        ;
        assertEquals("", "");
    }

    @Test
    public void testAPI2(){
        given()
                .when()
                .then()
        ;
    }
}
