package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApiTests_FileTransferService_File extends BaseTestAPI {

    private int idFile;
    String path = "filetransferms/deutsche";

    @Before
    public void each(){
        idFile = uploadFileToTest(
                2,
                17,
                10,
                "DEUTSCHE_INCLUSAO_EMAIL_20200529_155700_002.zip");
    }

    @After
    public void clearClenup(){
        clearFiles(idFile);
    }

    @Test
    public void get_FileTransfer_ListarTodosOsArquivos(){
        given()
                .when()
                .get(path + "/file")
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
                .body("content.id", hasItem(idFile))
        ;
    }

    @Test
    public void get_FileTransfer_ConsultarArquivosPorId(){
        given()
                .pathParam("id", idFile)
                .when()
                .get(path + "/file/{id}")
                .then()
                .statusCode(200)
                .body("name", is("DEUTSCHE_INCLUSAO_EMAIL_20200529_155700_002.zip"))
                .body("fileType", is("ZIP"))
                .body("application", is("MAILING"))
        ;
    }

    @Test
    public void get_FileTransfer_ConsultarArquivoComIdInexistente(){
        given()
                .when()
                .get(path + "/file/9999")
                .then()
                .statusCode(500)
                .body("message", equalTo("no value present"))
        ;
    }

    @Test
    public void get_FileTransfer_ConsutarArquivoInformandoIdInvalido(){
        given()
                .when()
                .get(path + "/file/teste")
                .then()
                .statusCode(500)
                .body("error", is("Internal Server Error"))
                .body("message", containsString("java.lang.NumberFormatException"))
        ;
    }

    @Test
    public void post_FileTransfer_CriarArquivoComBrandId(){
        assertTrue(idFile > 0);
        assertNotNull(idFile);
    }

    @Test
    public void post_FileTransfer_CriarArquivoSemBrandId(){
        clearFiles(idFile);
        idFile = uploadFileToTest(
                2,
                10,
                "DEUTSCHE_INCLUSAO_EMAIL_20200529_155700_002.zip");
        assertTrue(idFile > 0);
        assertNotNull(idFile);
    }

    @Test
    public void put_FileTransfer_AlterarArquivoComBrandId(){
        given()
                .when()
                .contentType(JSON)
                .pathParam("id", idFile)
                .body(fileParameters(1,
                        17,
                        "2020-06-08",
                        "ArquivoAlterado",
                        10))
                .put(path + "/file/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idFile))
                .body("name", is("ArquivoAlterado"))
                .body("creationDate", is("2020-06-08"))
                .body("fileType", is("ZIP"))
                .body("application", is("REPORT"))
                .body("brandId", is(17))
        ;
    }

    @Test
    public void put_FileTransfer_AlterarArquivoSemBrandId(){
        clearFiles(idFile);
        idFile = uploadFileToTest(
                2,
                10,
                "DEUTSCHE_INCLUSAO_EMAIL_20200529_155700_002.zip");
        given()
                .when()
                .contentType(JSON)
                .pathParam("id", idFile)
                .body(fileParameters(1,
                        "2020-06-08",
                        "ArquivoAlterado",
                        10))
                .put(path + "/file/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idFile))
                .body("name", is("ArquivoAlterado"))
                .body("creationDate", is("2020-06-08"))
                .body("fileType", is("ZIP"))
                .body("application", is("REPORT"))
        ;
    }

    @Test
    public void post_FileTransfer_UploadDeArquivoSevenZip(){
        clearFiles(idFile);
        idFile = uploadFileToTest(
                2,
                17,
                10,
                "DEUTSCHE_BOLETOS_20200.7z");
        given()
                .pathParam("id", idFile)
                .when()
                .get(path + "/file/{id}")
                .then()
                .body("fileType", is("SEVEN_ZIP"))
                .body("name", is("DEUTSCHE_BOLETOS_20200.7z"))
        ;
    }

    @Test
    public void post_FileTransfer_UploadDeArquivoPDF(){
        clearFiles(idFile);
        idFile = uploadFileToTest(
                2,
                17,
                10,
                "DEUTSCHE_BOLETOS_20200103_002.pdf");
        given()
                .pathParam("id", idFile)
                .when()
                .get(path + "/file/{id}")
                .then()
                .body("fileType", is("PDF"))
                .body("name", is("DEUTSCHE_BOLETOS_20200103_002.pdf"))
        ;
    }

    @Test
    public void post_FileTransfer_UploadDeArquivoRET(){
        clearFiles(idFile);
        idFile = uploadFileToTest(
                2,
                17,
                10,
                "gra0402202001.RET");
        given()
                .pathParam("id", idFile)
                .when()
                .get(path + "/file/{id}")
                .then()
                .body("fileType", is("RET"))
                .body("name", is("gra0402202001.RET"))
        ;
    }





    private int uploadFileToTest(int applicationId, int brandId, int targetId, String fileName){
        Response response = (Response) given()
                .contentType("multipart/form-data")
                .multiPart("applicationId", applicationId)
                .multiPart("brandId", brandId)
                .multiPart("file", fileToUpload(fileName))
                .multiPart("targetId", targetId)
                .when()
                .post(path + "/file")
                .then()
                .statusCode(201)
                .body("application", is("MAILING"))
                .body("brandId", is(brandId))
                .body("name", is(fileName))
                .body("id", notNullValue())
                .extract();
        idFile = JsonPath.from(response.asString()).getInt("id");
        return idFile;
    }

    private int uploadFileToTest(int applicationId, int targetId, String fileName){
        Response response = (Response) given()
                .contentType("multipart/form-data")
                .multiPart("applicationId", applicationId)
                .multiPart("file", fileToUpload(fileName))
                .multiPart("targetId", targetId)
                .when()
                .post(path + "/file")
                .then()
                .statusCode(201)
                .body("application", is("MAILING"))
                .body("brandId", nullValue())
                .body("name", is(fileName))
                .body("id", notNullValue())
                .extract();
        idFile = JsonPath.from(response.asString()).getInt("id");
        return idFile;
    }

    private Map<String, Object> fileParameters(int appId, int brandId,String creationDate, String name, int targetId){
        Map<String, Object> param = new HashMap<>();
        param.put("applicationId", appId);
        param.put("brandId", brandId);
        param.put("creationDate", creationDate);
        param.put("name", name);
        param.put("targetId", targetId);
        return param;
    }

    private Map<String, Object> fileParameters(int appId,String creationDate, String name, int targetId){
        Map<String, Object> param = new HashMap<>();
        param.put("applicationId", appId);
        param.put("creationDate", creationDate);
        param.put("name", name);
        param.put("targetId", targetId);
        return param;
    }

    private String getFileExists(){
        ArrayList<String> response =
                given()
                        .when()
                        .get(path + "/file")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().path("content.name", String.valueOf(hasItem("atlas_plural_20200505.zip")));
        return String.valueOf(response);
    }

    private void clearFiles(int id){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        given()
                .contentType(JSON)
                .pathParam("id", id)
                .when()
                .delete(path + "/file/{id}")
                .then()
                .statusCode(204);
    }

    private File fileToUpload(String fileName){
        return new File("FilesToTransfer/"+fileName);
    }

}
