package Core;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import ReportBuilder.ReportBuilder;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


import java.io.FileNotFoundException;

import static Core.DriverFactory.*;
import static ReportBuilder.ReportBuilder.clearDirectory;

public class BaseTest {

    @BeforeAll
    public static void classInitialize() {
        clearDirectory();
    }

    @BeforeEach
    public void testInitialize(){
        openBrowser(AddressEntity.getURL());
    }

    @AfterEach
    public void tearDown(){
        closeBrowser();
    }

    @AfterAll
    public static void tearDownClass() throws FileNotFoundException, DocumentException {
//        new ReportBuilder().pdfBuilderBatch();
    }

    private void openBrowser(String url) {
        getDriver().navigate().to(url);
    }

    private void closeBrowser(){
        killDriver();
    }


}
