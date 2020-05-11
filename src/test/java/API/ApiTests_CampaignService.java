package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.sun.corba.se.impl.util.Version.asString;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiTests_CampaignService extends BaseTestAPI {


    public ApiTests_CampaignService() {
        setBaseURI();
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


    @Test
    @Description("Verificar a lista. Retorno apenas com Deutsche_Plural(ID 109)")
    @DisplayName("Filtrando Por Produto e Datas - Deve retornar apenas 'product_id 109'")
    public void retornoProduto109(){
        given()
                .param("productId","109")
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .body("content.findAll{it.productId != 109}", hasSize(0))
        ;
    }

    @Test
    @Description("Verificar retorno com data inicial maior que a final")
    @DisplayName("Filtrando por Data inicial maior que a final")
    public void dataInicialMaiorQueFinal(){
        given()
                .param("campaign.startDate","2020-01-15 00:00:00.00")
                .param("campaign.startDate","2020-01-01 00:00:00.00")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("content", hasSize(0))
        ;
    }

    @Test
    @Description("Verificar retorno com apenas uma data informada")
    @DisplayName("Informando apenas 1 filtro de data")
    public void naoInformarDataInicial(){
        given()
                .param("campaign.brandId","17")
//                .param("campaign.startDate", "")
                .param("campaign.startDate","2020-01-03 00:00:00.00")
                .when()
                .get()
                .then()
//                .log().all()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
                .body("sentDate.findAll{it.starsWith('2020-01-04')}", hasSize(0))
                .body("sentDate.findAll{it.starsWith('2020-01-02')}", hasSize(0))
        ;
    }





    /*  @Test
        @Description("")
        @DisplayName("")
        public void test(){

        }
    */


    private void setBaseURI() {
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    public void setBasePath(){
        AddressEntity.setBasePath("campaignservicems/deutsche/campaignstate");
    }
}
