package TestClasses;

import Assistant.AddressEntity;
import Assistant.IdAssistant;
import Assistant.PathAssistant;
import Assistant.UrlSystemAssistant;
import Core.BaseTest;
import EM_Pages.ListManagement_Page;
import EM_Pages.Login_Page;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;

public class EM_ListManagement_Test extends BaseTest {

    private Login_Page loginPage;
    private ListManagement_Page page;
    public EM_ListManagement_Test() {
        setUrl();
        loginPage = new Login_Page();
        page = new ListManagement_Page();
    }

    @Test
    public void EM14_Check_list_upload(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        page.nameOfList = page.typeText_ListInputName();
        page.typeText_ListInputDescription();
        page.uploadFileWithRecords_ListManagementModal();
        setUpTable();
        page.clickButtonLoad_NewListModal();
        String expected = "Lista criada com sucesso";
        String msg = page.getTextElementByXpath_ListManagement(PathAssistant.PATH_TOAST_CONTAINER);
        System.out.println(expected +" | "+msg );
        assertEquals(expected, msg);
    }

    @Test
    public void EM15_Validate_duplicate_name_list_Insertion(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        page.nameOfList = page.getTextElementByXpath_ListManagement(PathAssistant.PATH_FIRST_RECORD_GRIDVIEW);
        page.typeText_ListInputName(page.nameOfList);
        System.out.println(page.nameOfList);
        page.typeText_ListInputDescription();
        page.uploadFileWithRecords_ListManagementModal();
        setUpTable();
        page.clickButtonLoad_NewListModal();
        String expected = "Erro: A tabela já existe.";
        String msg = page.getTextElementByXpath_ListManagement(PathAssistant.PATH_TOAST_CONTAINER);
        page.waitForLoad_ListManagement(1000);
        System.out.println(expected +" | "+msg );
        assertEquals(expected, msg);
    }

    @Test
    public void EM22_Check_layout_Portuguese_Language(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        assertTrue(page.elementExist_ListManagement(IdAssistant.EM_ID_LIST_INPUT_NAME));
        assertTrue(page.elementExist_ListManagement(IdAssistant.EM_ID_LIST_INPUT_DESCRIPTION));
        assertTrue(page.elementExist_ListManagement(IdAssistant.EM_ID_LIST_INPUT_FILE));
    }

    @Test
    public void EM24_Required_Fields_Name(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        //page.nameOfList = page.typeText_ListInputName();
        page.typeText_ListInputDescription();
        page.uploadFileWithRecords_ListManagementModal();
        setUpTable();
        boolean isEnabled = page.elementIsEnabledEM(By.xpath(PathAssistant.PATH_BUTTON_LOAD));
        System.out.println("Elements is "+isEnabled);
        assertFalse(isEnabled);
    }

    @Test
    public void EM24_Required_Fields_Description(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        page.nameOfList = page.typeText_ListInputName();
        //page.typeText_ListInputDescription();
        page.uploadFileWithRecords_ListManagementModal();
        setUpTable();
        boolean isEnabled = page.elementIsEnabledEM(By.xpath(PathAssistant.PATH_BUTTON_LOAD));
        System.out.println("Elements is "+isEnabled);
        assertFalse(isEnabled);
    }

    @Test
    public void EM25_Upload_file_with_more_3_records(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        page.nameOfList = page.typeText_ListInputName();
        page.typeText_ListInputDescription();
        page.uploadFileWithRecords_ListManagementModal();
        setUpTable();
        page.clickButtonLoad_NewListModal();
        String expected = "Lista criada com sucesso";
        String msg = page.getTextElementByXpath_ListManagement(PathAssistant.PATH_TOAST_CONTAINER);
        System.out.println(expected +" | "+msg );
        assertEquals(expected, msg);
    }

    @Test
    public void EM27_Upload_file_with_no_records(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        page.nameOfList = page.typeText_ListInputName();
        page.typeText_ListInputDescription();
        page.uploadFileNoRecords_ListManagementModal();
        //setUpTable();
        page.clickButtonLoad_NewListModal();
        String expected = "não foi possivel realizar à requisição. Por favor verifique se todos os dados estão corretos.";
        String msg = page.getTextElementByXpath_ListManagement(PathAssistant.PATH_TOAST_CONTAINER);
        System.out.println(expected +" | "+msg );
        assertEquals(expected, msg);
    }

    @Test
    public void EM23_Upload_file_with_comma(){
        accessSystem();
        page.clickButtonMenu_ListManagement();
        page.clickButtonLoadList_ListManagement();
        page.nameOfList = page.typeText_ListInputName();
        page.typeText_ListInputDescription();
        page.uploadFileWithComma_ListManagementModal();
        setUpTable();
        page.clickButtonLoad_NewListModal();
        String expected = "Lista criada com sucesso";
        String msg = page.getTextElementByXpath_ListManagement(PathAssistant.PATH_TOAST_CONTAINER);
        System.out.println(expected +" | "+msg );
        assertEquals(expected, msg);
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
        new AddressEntity().setURL(UrlSystemAssistant.URL_HOM_EM);
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
