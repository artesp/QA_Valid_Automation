package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests_FileTransferService extends BaseTestAPI {

    public ApiTests_FileTransferService() {
        setBaseURI();
        setBasePath();
    }


    @Test
    @Description("Método que lista todas arquivos transferidos")
    @DisplayName("Retornar todos os arquivos transferidos")
    public void list_FileTransfer(){
        given()
                .when()
                .get("/file")
                .then()
                .log().all()
                .statusCode(200)
                .body("content", hasSize(greaterThan(0)))
        ;
    }

    @Test
    @Description("Método que retorna detalhes de um arquivo transferido por id existente")
    @DisplayName("Retornar arquivos transferidos por Id")
    public void search_FileTransfer_WithAnExistingId(){
        given()
                .when()
                .get("/file/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("fileType", equalTo("TXT"))
                .body("application", equalTo("MAILING"))
        ;
    }

    @Test
    @Description("Método que retorna detalhes de um arquivo transferido por id inexistente")
    @DisplayName("Verificar retorno com id inexistente.")
    public void search_FileTransfer_WithAnNotExistingId(){
        given()
                .when()
                .get("/file/9999")
                .then()
                .log().all()
                .statusCode(500)
                .body("message", equalTo("no value present"))
        ;
    }

    @Test
    @Description("Método que retorna detalhes de um arquivo transferido por id existente")
    @DisplayName("Informar uma String no parametro Id")
    public void search_FileTransfer_WithStringOnId(){
        given()
                .when()
                .get("/file/teste")
                .then()
                .log().all()
                .statusCode(500)
                .body("error", equalTo("Internal Server Error"))
                .body("message", containsString("java.lang.NumberFormatException"))
        ;
    }

    @Test
    @Description("Método realiza o upload do arquivo")
    @DisplayName("Upload de arquivo informando BrandId")
    public void search_FileTransfer_CreateFileTransferWithBrandID(){
        given()
                .contentType("multipart/form-data")
                .multiPart("applicationId", 2)
                .multiPart("brandId", 21)
                .multiPart("file", fileToUpload("recordsWithComma.csv"))
                .multiPart("targetId", 5)
                .when()
                .post("/file")
                .then()
                .log().all()
                .statusCode(201)
//                .body("error", equalTo("Internal Server Error"))
//                .body("message", containsString("java.lang.NumberFormatException"))
        ;
    }


    private File fileToUpload(String fileName){
        return new File("CSV/"+fileName);
    }

    private void setBaseURI() {
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    private void setBasePath(){
        AddressEntity.setBasePath("filetransferms/deutsche");
    }

}
