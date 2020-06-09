package SuiteApiTests;

import API.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApiTests_FileTransferService_File.class,
        ApiTests_FileTransferService_Target.class,
        ApiTests_FileTransferService_Download.class,
        ApiTests_FileTransferService_Application.class,
        ApiTests_CampaignService_PrintProduct.class,
        ApiTests_CampaignService_PrintState.class,
        ApiTests_CampaignService_PrintAnalytic.class,
        ApiTests_CampaignService_FailEmailState.class,
        ApiTests_CampaignService_State.class,
        ApiTests_BarCodeBuilderms.class,
        ApiTests_ImageService.class,
        ApiTests_VariableService.class
})
public class SuiteServiceAPI {
}
