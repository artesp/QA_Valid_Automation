package Core;

import Assistant.AddressEntity;
import Assistant.SharedMethods;
import Assistant.UrlSystemAssistant;
import ReportBuilder.ReportBuilder;
import com.itextpdf.text.DocumentException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

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


    @BeforeClass
    public static void classInitialize(){
        clearDirectory();
        clearIMGDownloadsDirectory();
    }

    @Before
    public void testInitialize(){
        RestAssured.baseURI= UrlSystemAssistant.APITEST_URI_DEV;
        //RestAssured.basePath = AddressEntity.getBasePath();

        reqBuilder = new RequestSpecBuilder();
        reqSpec = reqBuilder.build();

        RestAssured.requestSpecification = reqSpec;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterClass
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

    public void waitForAction(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
