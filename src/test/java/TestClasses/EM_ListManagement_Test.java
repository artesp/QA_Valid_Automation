package TestClasses;

import Assistant.AddressEntity;
import Assistant.IdAssistant;
import Assistant.PathAssistant;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;
import EM_Pages.ListManagement_Page;
import EM_Pages.Login_Page;
import io.qameta.allure.Description;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EM_ListManagement_Test extends BaseTest {

    private Login_Page loginPage;
    private ListManagement_Page page;
    public EM_ListManagement_Test() {
        setUrl();
        loginPage = new Login_Page();
        page = new ListManagement_Page();
    }

    @Test
    @Description("Validate list upload functionality")
    @DisplayName("EM-14: Check List Upload - List Management")
    public void EM_14_Check_list_upload(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        page.nameOfList = page.typeText_ListInputName();
        page.typeText_ListInputDescription();
        page.uploadFile_ListManagementModal();
        setUpTable();
        page.clickButtonLoad_NewListModal();
        String expected = "Lista criada com sucesso";
        String msg = page.getTextElementByXpath_ListManagement(PathAssistant.PATH_TOAST_CONTAINER);
        System.out.println(expected +" | "+msg );
        assertEquals(expected, msg);
    }

    @Test
    @Description("The system should not allow duplicate names")
    @DisplayName("EM-15: Validate insertion of list with duplicate name")
    public void EM_15_Validate_duplicate_name_list_Insertion(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        page.nameOfList = page.getTextElementByXpath_ListManagement(PathAssistant.PATH_FIRST_RECORD_GRIDVIEW);
        page.typeText_ListInputName(page.nameOfList);
        System.out.println(page.nameOfList);
        page.typeText_ListInputDescription();
        page.uploadFile_ListManagementModal();
        setUpTable();
        page.clickButtonLoad_NewListModal();
        String expected = "Erro: A tabela já existe.";
        String msg = page.getTextElementByXpath_ListManagement(PathAssistant.PATH_TOAST_CONTAINER);
        page.waitForLoad_ListManagement(1000);
        System.out.println(expected +" | "+msg );
        assertEquals(expected, msg);
    }

    @Test
    @Description("Check the layout of screen elements with system language other than the default")
    @DisplayName("EM-22: Check layout with selected Portuguese language.")
    public void EM_22_Check_layout_Portuguese_Language(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        assertTrue(page.elementExist_ListManagement(IdAssistant.EM_ID_LIST_INPUT_NAME));
        assertTrue(page.elementExist_ListManagement(IdAssistant.EM_ID_LIST_INPUT_DESCRIPTION));
        assertTrue(page.elementExist_ListManagement(IdAssistant.EM_ID_LIST_INPUT_FILE));
    }

    private void setUpTable(){
        page.clickDropDown_ListManagementModal("1");
        page.selectValueDropDown_ListManagementModal("1","3");
        page.typeText_Observation("Teste", "1");

        page.clickDropDown_ListManagementModal("2");
        page.selectValueDropDown_ListManagementModal("2","2");
        page.typeText_Observation("Teste", "2");

        page.clickDropDown_ListManagementModal("3");
        page.selectValueDropDown_ListManagementModal("3","2");
        page.typeText_Observation("Teste", "3");

        page.clickDropDown_ListManagementModal("4");
        page.selectValueDropDown_ListManagementModal("4","2");
        page.typeText_Observation("Teste", "4");

        page.clickDropDown_ListManagementModal("5");
        page.selectValueDropDown_ListManagementModal("5","2");
        page.typeText_Observation("Teste", "5");
    }

    private void setUrl() {
        AddressEntity.setUrl(UrlSystemAssistant.URL_DEV_EM);
    }

    private void accessSystem(){
        loginPage.typeUser_Login();
        loginPage.typePassword_Login();
        loginPage.clickOnComboLanguage();
        loginPage.selectItemOnCombo_Login();
        loginPage.checkBox_Login();
        loginPage.clickButton_Login();
        //loginPage.clickDropDown_Company();
        //loginPage.selectValueDropDown_Company_CPFL();
        //loginPage.clickButtonOK_Company();
    }
}
