package API;

import Assistant.AddressEntity;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class ApiTests_CampaignState extends BaseTestAPI {


    public ApiTests_CampaignState() {
        setBasePath();
    }

    @Test
    @Description("Filtrando por Produto Deutsche_Plural(ID 109) e Data 01/12/2019 até 15/01/2020")
    @DisplayName("Filtrando Por Produto e Datas - statusCode 200")
    public void filtrandoPorProdutoEDatas(){
        given()
                .param("page","0")
                .param("campaign.brandId","17")
                .param("productId","109")
                .param("campaign.startDate","2019-12-01 20:00:00.00")
                .param("campaign.startDate","2020-01-15 17:40:00.00")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
                .body("numberOfElements", greaterThan(0))
        ;
    }

    public void setBasePath(){
        AddressEntity.setBasePath("campaignservicems/deutsche/campaignstate");
    }
}
