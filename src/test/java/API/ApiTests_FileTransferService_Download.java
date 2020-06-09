package API;

import Assistant.AddressEntity;
import Assistant.UrlSystemAssistant;
import Core.BaseTestAPI;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class ApiTests_FileTransferService_Download extends BaseTestAPI {


    public ApiTests_FileTransferService_Download() {
        setBaseURI();
        setBasePath();
    }





    private void setBaseURI() {
        AddressEntity.setBaseURI(UrlSystemAssistant.APITEST_URI_HOMOLOG);
    }

    private void setBasePath(){
        AddressEntity.setBasePath("filetransferms/deutsche");
    }


}
