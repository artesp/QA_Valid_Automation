package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests_CampaignService_State extends BaseTestAPI {

    String path = "campaignservicems/deutsche";

    @Test
    public void get_CampaignServiceState_filtrandoPorProdutoEDatas(){
        given()
                .param("page","0")
                .param("campaign.brandId","17")
                .param("productId","94")
                .param("campaign.startDate","2019-02-01 20:00:00.00")
                .param("campaign.startDate","2020-06-10 17:40:00.00")
                .when()
                .get(path + "/campaignstate")
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
                .body("numberOfElements", greaterThan(0))
        ;
    }


    @Test
    public void get_CampaignServiceState_RetornoProduto109(){
        given()
                .param("productId","109")
                .when()
                .get(path + "/campaignstate")
                .then()
                .log().all()
                .statusCode(200)
                .body("content.findAll{it.productId != 109}", hasSize(0))
        ;
    }

    @Test
    public void get_CampaignServiceState_DataInicialMaiorQueFinal(){
        given()
                .param("campaign.startDate","2020-01-15 00:00:00.00")
                .param("campaign.startDate","2020-01-01 00:00:00.00")
                .when()
                .get(path + "/campaignstate")
                .then()
                .statusCode(200)
                .body("content", hasSize(0))
        ;
    }

    @Test
    public void get_CampaignServiceState_NaoInformarDataInicial(){
        given()
                .param("campaign.brandId","17")
//                .param("campaign.startDate", "")
                .param("campaign.startDate","2019-01-03 00:00:00.00")
                .when()
                .get(path + "/campaignstate")
                .then()
                .statusCode(200)
                .body("empty",is(true))
        ;
    }
}
