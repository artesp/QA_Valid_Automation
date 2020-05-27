package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ApiTests_BarCodeBuilderms extends BaseTestAPI {

    public ApiTests_BarCodeBuilderms() {
        setBasePath();
        setBaseURI();
    }


    @Test
    @Description("Post - Envia um Json com número de código de barras e linhas digitáveis")
    @DisplayName("Criar código de barras - BarCode - Vários números no Json")
    public void post_barcodebuilder_variosNumerosNaLista(){
        given()
                .contentType("application/json")
                .body(barCodesToTest_MultipleNumbers())
                .when()
                .post("/barcode")
                .then()
                .log().all()
                .statusCode(200)
                .body(containsString("-"))
        ;
    }

    @Test
    @Description("Post - Envia um Json com número de código de barras e linhas digitáveis")
    @DisplayName("Criar código de barras - BarCode - Um número no Json")
    public void post_barcodebuilder_umNumeroNaLista(){
        given()
                .contentType("application/json")
                .body(barCodesToTest_OnlyOneNumber())
                .when()
                .post("/barcode")
                .then()
                .log().all()
                .statusCode(200)
                .body(containsString("-"))
        ;
    }

    @Test
    @Description("Post - Envia um Json com número de código de barras e linhas digitáveis")
    @DisplayName("Criar código de barras - BarCode - Sequência de 44 posições no Json")
    public void post_barcodebuilder_umNumeroAleatorioNaLista(){
        given()
                .contentType("application/json")
                .body(barCodesTotest_CustomizeANumber("12345678901234567890123456789012345678901234"))
                .when()
                .post("/barcode")
                .then()
                .log().all()
                .statusCode(200)
                .body(containsString("-"))
        ;
    }

    @Test
    @Description("Get - Valida o protocolo")
    @DisplayName("Consultar procotolo - Get_Protocol")
    public void get_barcodebuilder_GetProtocol(){
        String protocol = generateProtocol();
        String status;
        do {
            status = checkStatus_GetProtocol(protocol);
        }while(status.equalsIgnoreCase("WAIT"));
        Assert.assertEquals("SUCCESS", status);
    }

    @Test
    @Description("Get - Download do arquivo existente no S3")
    @DisplayName("(ImageService)Download do PNG existente no S3")
    public void get_barcodebuilder_GetS3(){
        generateProtocol();
        byte[] file = given()
                .contentType("application/json")
                .when()
                .get("http://10.198.106.82/imageservicems/deutsche/imageservice/download/17/84660000000099200820899942389254098312620001.png")
                .then()
                .statusCode(200)
                .extract().asByteArray();
        downloadLocally(file, ".png");
        assertTrue(file.length > 0);
        assertThat(file, notNullValue());
    }




    /*@Test
    @Description("")
    @DisplayName("")
    public void test(){

    }*/

    private String generateProtocol(){
        Response response =
                (Response) given()
                        .contentType("application/json")
                        .body(barCodesToTest_OnlyOneNumber())
                        .when()
                        .post("/barcode")
                        .then()
                        .statusCode(200).extract();

        ResponseBody body = response.getBody();
        String protocol = body.asString().replaceAll("\"", "");
        return protocol;
    }

    private String checkStatus_GetProtocol(String protocol) {
        Response response =
                (Response) given()
                        .contentType("application/json")
                        .pathParam("protocol", protocol)
                        .when()
                        .get("/barcode/{protocol}")
                        .then()
                        .statusCode(200).extract();
        ResponseBody body = response.getBody();
        return new String(body.asString());
    }

    private void setBaseURI() {
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    public void setBasePath(){
        AddressEntity.setBasePath("barcodebuilderms/deutsche");
    }

}
