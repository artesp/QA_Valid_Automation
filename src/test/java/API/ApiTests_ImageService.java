package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class ApiTests_ImageService extends BaseTestAPI {

    String path = "/imageservicems/deutsche/imageservice";

    @Test
    public void post_ImageService_UploadDeArquivoJPG(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("spartanskull02.jpg"))
                .when()
                .post(path + "/upload")
                .then()
                .statusCode(200)
                .body(containsString("File Uploaded Success"))
        ;
    }

    @Test
    public void post_ImageService_UpLoadDeArquivoPNG(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("spartanskull03.png"))
                .when()
                .post(path + "/upload")
                .then()
                .statusCode(200)
                .body(containsString("File Uploaded Success"))
        ;
    }

    @Test
    public void post_ImageService_UploadDeArquivoJPEG(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("regua.jpeg"))
                .when()
                .post(path + "/upload")
                .then()
                .statusCode(200)
                .body(containsString("File Uploaded Success"))
        ;
    }

    @Test
    public void post_ImageService_UploadDeArquivoTIFFMaiorQueOEspecificado(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("spartanskull04.tiff"))
                .when()
                .post(path + "/upload")
                .then()
                .statusCode(500)
        ;
    }

    @Ignore
    @Test
    public void post_ImageService_UploadDeArquivoBMPMaiorQueOEspecificado(){
        given()
                .contentType("multipart/form-data")
                .multiPart("brandId", 17)
                .multiPart("file", fileToUpload("spartanskull05.bmp"))
                .when()
                .post(path + "/upload")
                .then()
                .statusCode(500)
        ;
    }


    @Test
    public void get_ImageService_DownloadDeArquivoJPG(){
        uploadImage("spartanskull02.jpg", 17);
        byte[] file = given()
                .pathParam("brandId", 17)
                .pathParam("image", "spartanskull02.jpg")
                .when()
                .get(path + "/download/{brandId}/{image}")
                .then()
                .statusCode(200)
                .extract().asByteArray();
        downloadLocally(file, ".jpg");
        assertTrue(file.length > 0);
        assertNotNull(file);
   }

    @Test
    public void get_ImageService_DownloadDeArquivoJPEG(){
        byte[] file = given()
                .pathParam("brandId", 17)
                .pathParam("imageName", "regua.jpeg")
                .when()
                .get(path + "/download/{brandId}/{imageName}")
                .then()
                .statusCode(200)
                .extract().asByteArray();
        downloadLocally(file, ".jpeg");
        assertTrue(file.length > 0);
        assertThat(file, notNullValue());
    }

    @Test
    public void get_ImageService_DownloadDeArquivoPNG(){
        byte[] file = given()
                .pathParam("brandId", 17)
                .pathParam("imageName", "spartanskull03.png")
                .when()
                .get(path + "/download/{brandId}/{imageName}")
                .then()
                .statusCode(200)
                .extract().asByteArray();
        downloadLocally(file, ".png");
        assertTrue(file.length > 0);
        assertThat(file, notNullValue());
    }

    @Test
    public void get_ImageService_DownloadDeArquivoInexistente(){
        given()
                .pathParam("brandId", 17)
                .pathParam("imageName", "spartanskull04.tiff")
                .when()
                .get(path + "/download/{brandId}/{imageName}")
                .then()
                .statusCode(500)
                .body(containsString("The specified key does not exist."))
        ;
    }

    @Test
    public void imageService_DownloadDeArquivoBMPInexistente(){
        given()
                .contentType(JSON)
                .pathParam("brandId", 17)
                .pathParam("fileName", "spartanskull05.bmp")
                .when()
                .get(path + "/download/{brandId}/{fileName}")
                .then()
                .statusCode(500)
                .body(containsString("The specified key does not exist."))
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
                .post(path + "/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body(containsString("File Uploaded Success"))
        ;
    }

}
