package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class ApiTests_BarCodeBuilderms extends BaseTestAPI {

    String path = "barcodebuilderms/deutsche";

    @Test
    public void post_CriarCodigoDeBarras_VariosNumerosNaLista(){
        given()
                .contentType(ContentType.JSON)
                .body(barCodesToTest_MultipleNumbers())
                .when()
                .post(path + "/barcode")
                .then()
                .statusCode(200)
                .body(containsString("-"))
        ;
    }

    @Test
    public void post_CriarCodigoDeBarras_UmNumeroNaLista(){
        given()
                .contentType(ContentType.JSON)
                .body(barCodesToTest_OnlyOneNumber())
                .when()
                .post(path + "/barcode")
                .then()
                .statusCode(200)
                .body(containsString("-"))
        ;
    }

    @Test
    public void post_CriarCodigoDeBarras_umNumeroAleatorioNaLista(){
        given()
                .contentType(ContentType.JSON)
                .body(barCodesTotest_CustomizeANumber("12345678901234567890123456789012345678901234"))
                .when()
                .post(path + "/barcode")
                .then()
                .statusCode(200)
                .body(containsString("-"))
        ;
    }

    @Test
    public void get_CriarCodigoDeBarras_ConsultarProtocolo(){
        String protocol = generateProtocol();
        String status;
        do {
            status = checkStatus_GetProtocol(protocol);
        }while(status.equalsIgnoreCase("WAIT"));
        Assert.assertEquals("SUCCESS", status);
    }

    @Test
    public void get_CriarCodigoDeBarras_DownloadPNGExistenteNoS3(){
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
        assertNotNull(file);
    }


    private String generateProtocol(){
        Response response =
                (Response) given()
                        .contentType(ContentType.JSON)
                        .body(barCodesToTest_OnlyOneNumber())
                        .when()
                        .post(path + "/barcode")
                        .then()
                        .statusCode(200).extract();

        ResponseBody body = response.getBody();
        String protocol = body.asString().replaceAll("\"", "");
        return protocol;
    }

    private String checkStatus_GetProtocol(String protocol) {
        Response response =
                (Response) given()
                        .contentType(ContentType.JSON)
                        .pathParam("protocol", protocol)
                        .when()
                        .get(path + "/barcode/{protocol}")
                        .then()
                        .statusCode(200).extract();
        ResponseBody body = response.getBody();
        return new String(body.asString());
    }


    private Map<String, Object>  barCodesToTest_MultipleNumbers(){
        Map<String, Object> paramMultipleNumbers = new HashMap<>();
        paramMultipleNumbers.put("brandId", 17);
        paramMultipleNumbers.put("barcodes",
                new ArrayList<String>(Arrays.asList(
                        "84660000000099200820899942389254098312620001",
                        "84660000000099200820899942389254098312620002",
                        "84660000000099200820899942389254098312620003",
                        "84660000000099200820899942389254098312620004",
                        "84660000000099200820899942389254098312620001",
                        "84660000000099200820899942389254098312620002",
                        "84660000000099200820899942389254098312620003",
                        "84660000000099200820899942389254098312620004",
                        "84660000000099200820899942389254098312620005",
                        "84660000000099200820899942389254098312620006",
                        "84660000000099200820899942389254098312620007",
                        "84660000000099200820899942389254098312620008",
                        "84660000000099200820899942389254098312620009",
                        "84660000000099200820899942389254098312620010",
                        "84660000000099200820899942389254098312620011")));
        return paramMultipleNumbers;
    }

    private Map<String, Object> barCodesToTest_OnlyOneNumber(){
        Map<String, Object> paramOnlyNumber = new HashMap<>();
        paramOnlyNumber.put("brandId", 17);
        paramOnlyNumber.put("barcodes", new ArrayList<String>(Arrays.asList("84660000000099200820899942389254098312620001")));
        return paramOnlyNumber;
    }

    private Map<String, Object> barCodesTotest_CustomizeANumber(String customNumber){
        Map<String, Object> paramCustomizeNumber = new HashMap<>();
        paramCustomizeNumber.put("brandId", 17);
        paramCustomizeNumber.put("barcodes", new ArrayList<String>(Arrays.asList(customNumber)));
        return paramCustomizeNumber;
    }


}
