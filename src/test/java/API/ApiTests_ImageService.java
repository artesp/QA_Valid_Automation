package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitPlatform.class)
public class ApiTests_ImageService extends BaseTestAPI {

    public ApiTests_ImageService() {
        setBaseURI();;
        setBasePath();
    }

    @Test
    @Description("Método  para upload de arquivo")
    @DisplayName("Realizar upload de arquivo de imagen valido JPG")
    public void imageService_upload_validFile_JPG(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("spartanskull02.jpg"))
                .when()
                .post("/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body(containsString("File Uploaded Success"))
        ;
    }

    @Test
    @Description("Método  para upload de arquivo")
    @DisplayName("Realizar upload de arquivo de imagen valido PNG")
    public void imageService_upload_validFile_PNG(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("spartanskull03.png"))
                .when()
                .post("/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body(containsString("File Uploaded Success"))
        ;
    }

    @Test
    @Description("Método  para upload de arquivo")
    @DisplayName("Realizar upload de arquivo de imagen valido JPEG")
    public void imageService_upload_validFile_JPEG(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("regua.jpeg"))
                .when()
                .post("/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body(containsString("File Uploaded Success"))
        ;
    }

    @Test
    @Description("Método  para upload de arquivo")
    @DisplayName("Realizar upload de arquivo de imagen valido TIFF maior que o especificado")
    public void imageService_upload_validFile_TIFF_greaterThanSpecified(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("spartanskull04.tiff"))
                .when()
                .post("/upload")
                .then()
                .log().all()
                .statusCode(500)
                .body(containsString("The maximum size was exceeded"))
        ;
    }

    @Test
    @Description("Método  para upload de arquivo")
    @DisplayName("Realizar upload de arquivo de imagen valido BMP maior que o especificado")
    public void imageService_upload_validFile_BMP_greaterThanSpecified(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("spartanskull05.bmp"))
                .when()
                .post("/upload")
                .then()
                .log().all()
                .statusCode(500)
                .body(containsString("The maximum size was exceeded"))
        ;
    }


    @Test
    @Description("Método  para download de arquivo")
    @DisplayName("Realizar download do arquivo de imagen - JPG")
    public void imageService_Download_validFile_JPG(){
        uploadImage("spartanskull02.jpg", 17);

        byte[] file = given()
                .when()
                .log().all()
                .get("/download/17/spartanskull02.jpg")
                .then()
                .statusCode(200)
                .extract().asByteArray();
        downloadLocally(file, ".jpg");
        assertTrue(file.length > 0);
        assertThat(file, notNullValue());
   }

    @Test
    @Description("Método  para download de arquivo")
    @DisplayName("Realizar download do arquivo de imagen - JPEG")
    public void imageService_Download_validFile_JPEG(){
        byte[] file = given()
                .when()
                .log().all()
                .get("/download/17/regua.jpeg")
                .then()
                .statusCode(200)
                .extract().asByteArray();
        downloadLocally(file, ".jpeg");
        assertTrue(file.length > 0);
        assertThat(file, notNullValue());
    }

    @Test
    @Description("Método  para download de arquivo")
    @DisplayName("Realizar download do arquivo de imagen - PNG")
    public void imageService_Download_validFile_PNG(){
        byte[] file = given()
                .when()
                .log().all()
                .get("/download/17/spartanskull03.png")
                .then()
                .statusCode(200)
                .extract().asByteArray();
        downloadLocally(file, ".png");
        assertTrue(file.length > 0);
        assertThat(file, notNullValue());
    }

    @Test
    @Description("Método  para download de arquivo")
    @DisplayName("Realizar download do arquivo de imagen inexistente - TIFF")
    public void imageService_Download_validFile_TIFF_Invalid(){
        given()
                .when()
                .log().all()
                .get("/download/17/spartanskull04.tiff")
                .then()
                .statusCode(500)
                .body(containsString("Inexistent file"))
        ;
    }

    @Test
    @Description("Método  para download de arquivo")
    @DisplayName("Realizar download do arquivo de imagen inexistente - BMP")
    public void imageService_Download_validFile_BMP_Invalid(){
        given()
                .when()
                .log().all()
                .get("/download/17/spartanskull05.bmp")
                .then()
                .statusCode(500)
                .body(containsString("Inexistent file"))
        ;
    }




    private File fileToUpload(String fileName){
        return new File("IMGs/"+fileName);
    }

    private void uploadImage(String imageName, int brandId){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", brandId)
                .multiPart("file", fileToUpload(imageName))
                .when()
                .post("/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body(containsString("File Uploaded Success"))
        ;
    }

    private void setBaseURI(){
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    private void setBasePath(){
        AddressEntity.setBasePath("/imageservicems/deutsche/imageservice");
    }

}
