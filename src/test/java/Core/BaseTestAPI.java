package Core;

import Assistant.AddressEntity;
import Assistant.SharedMethods;
import com.itextpdf.text.DocumentException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static ReportBuilder.ReportBuilder.clearDirectory;
import static ReportBuilder.ReportBuilder.clearIMGDownloadsDirectory;

public class BaseTestAPI {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;
    public static RequestSpecBuilder reqBuilder;
//    public static ResponseSpecBuilder resBuilder;

    public static String variableNameList;
    public static String variableName;
    public static String imageName;


    @BeforeAll
    public static void classInitialize(){
        clearDirectory();
        clearIMGDownloadsDirectory();
    }

    @BeforeEach
    public void testInitialize(){
        RestAssured.baseURI= AddressEntity.getBaseURI();
        RestAssured.basePath = AddressEntity.getBasePath();

        reqBuilder = new RequestSpecBuilder();
//        reqBuilder.log(LogDetail.ALL);
        reqSpec = reqBuilder.build();

        RestAssured.requestSpecification = reqSpec;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterAll
    public static void classCleanup() throws FileNotFoundException, DocumentException {
//        new ReportBuilder().pdfBuilderBatch();
    }

    public String createNameForListTest(){
        variableNameList = "testListAPI"+dateHours();
        return variableNameList;
    }

    public String createNameForImageTest(){
     imageName = "testImageAPI"+dateHours();
     return imageName;
    }

    public String createNameForVariableTest(){
        variableName = "testAPI"+dateHours();
        return variableName;
    }

    public Map<String, Object> params (String name, Integer companyId, Integer brandId, ArrayList<HashMap<String, Object>> variableList){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("companyId", companyId);
        parameters.put("brandId", brandId);
        parameters.put("variables", variableList);
        return parameters;
    }

    public Map<String, Object> params (String alias, Integer companyId, Integer brandId, String description, String name, Integer variableListID){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("alias", alias);
        parameters.put("companyId", companyId);
        parameters.put("brandId", brandId);
        parameters.put("description", description);
        parameters.put("name", name);
        parameters.put("variablesListId", variableListID);
        return parameters;
    }

    private String dateHours(){
        return new SharedMethods().returnDateHours();
    }

//    private File createFileCSVForTesting(){
//
//    }

    public String barCodesToTest_MultipleNumbers(){
       return new String ("{\n" +
               "    \"brandId\": 17,\n" +
               "    \"barcodes\": [\n" +
               "        \"84660000000099200820899942389254098312620001\",\n" +
               "        \"84660000000099200820899942389254098312620002\",\n" +
               "        \"84660000000099200820899942389254098312620003\",\n" +
               "        \"84660000000099200820899942389254098312620004\",\n" +
               "        \"84660000000099200820899942389254098312620005\",\n" +
               "        \"84660000000099200820899942389254098312620006\",\n" +
               "        \"84660000000099200820899942389254098312620007\",\n" +
               "        \"84660000000099200820899942389254098312620008\",\n" +
               "        \"84660000000099200820899942389254098312620009\",\n" +
               "        \"84660000000099200820899942389254098312620010\",\n" +
               "        \"84660000000099200820899942389254098312620011\"\n" +
               "    ]\n" +
               "}").toString();
    }

    public String barCodesToTest_OnlyOneNumber(){
        return new String("{\n" +
                "    \"brandId\": 17,\n" +
                "    \"barcodes\": [\n" +
                "        \"84660000000099200820899942389254098312620001\"\n" +
                "    ]\n" +
                "}");
    }

    public String barCodesTotest_CustomizeANumber(String customNumber){
        return new String("{\n" +
                "    \"brandId\": 17,\n" +
                "    \"barcodes\": [\n" +
                "      \""+customNumber+"\"\n" +
                "    ]\n" +
                "}");
    }

    public void downloadLocally(byte[] file, String extention) {

        OutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream("IMGs/Downloads/"+createNameForImageTest()+extention);
            fileOutputStream.write(file);
            fileOutputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }




}
