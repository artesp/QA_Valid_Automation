package SuiteApiTests;

import API.ApiTests_CampaignService_PrintAnalytic;
import API.ApiTests_CampaignService_PrintProduct;
import API.ApiTests_CampaignService_PrintState;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApiTests_CampaignService_PrintProduct.class,
        ApiTests_CampaignService_PrintState.class,
        ApiTests_CampaignService_PrintAnalytic.class
})
public class SuiteApi { }
