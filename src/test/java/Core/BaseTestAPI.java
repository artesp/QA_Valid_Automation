package Core;

import Assistant.AddressEntity;
import ReportBuilder.ReportBuilder;
import com.itextpdf.text.DocumentException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;

import static ReportBuilder.ReportBuilder.clearDirectory;

public class BaseTestAPI {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;
    public static RequestSpecBuilder reqBuilder;
//    public static ResponseSpecBuilder resBuilder;


    @BeforeAll
    public static void classInitialize(){
        clearDirectory();
    }

    @BeforeEach
    public void testInitialize(){
        RestAssured.baseURI= "https://devinject.validsolutions.net/";
        RestAssured.basePath = AddressEntity.getBasePath();

        reqBuilder = new RequestSpecBuilder();
        reqBuilder.log(LogDetail.ALL);
        reqSpec = reqBuilder.build();

        RestAssured.requestSpecification = reqSpec;
    }

    @AfterAll
    public static void classCleanup() throws FileNotFoundException, DocumentException {
//        new ReportBuilder().pdfBuilderBatch();
    }

}
