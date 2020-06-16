package Core;

import Assistant.AddressEntity;
import com.itextpdf.text.DocumentException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.FileNotFoundException;

import static Core.DriverFactory.getDriver;
import static Core.DriverFactory.killDriver;
import static ReportBuilder.ReportBuilder.clearDirectory;

public class BaseTest {

    @BeforeClass
    public static void classInitialize() {
        clearDirectory();
    }

    @Before
    public void testInitialize(){
        openBrowser(new AddressEntity().getURL());
    }

    @After
    public void tearDown(){
        closeBrowser();
    }

    @AfterClass
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
