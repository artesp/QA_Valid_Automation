package Assistant;


public class PathAssistant {

    //XPath for combo language itens.
    public static final String PATH_LANGUAGE_ENGLISH = "//li[@id='ui-select-choices-0']/div[3]" +
            "                                            /span[@class='ui-select-choices-row-inner']";

    public static final String PATH_LANGUAGE_PORTUGUESE = "//li[@id='ui-select-choices-0']/div[4]" +
                                                           "/span[@class='ui-select-choices-row-inner']";

    public static final String PATH_LANGUAGE_SPANISH = "//li[@id='ui-select-choices-0']" +
                                                        "/div[5]/span[@class='ui-select-choices-row-inner']";

    //public static String PATH_COMBO_FIELDTYPE = "//body/div[@role='dialog']//form[@role='form']//div[@class='modal-body modal-minHeight']/div[4]/div[@class='col-md-12']/table[@class='table table-striped']/tbody/tr[1]/td[3]";

    //public static String PATH_VALUE_DROPDOWN = "/html/body/div[6]/div/form/div/div[3]/div[4]/div/table/tbody/tr["+IndexEntity.getIndex()+"]/td[3]/select/option[3]";

    //XPath for combo button
    public static final String PATH_BUTTON_COMBO_LANGUAGE = "//div[@id='login-select-language']//span[@role='button']";

    //XPath for checkBox login
    public static final String PATH_CHECKBOX_LOGIN = "//body[@class='ng-scope']" +
                                                      "/div[3]" +
                                                      "/div[@class='container ng-scope']" +
                                                      "/div[@class='row']" +
                                                      "/div" +
                                                      "//label[@class='ng-binding']" +
                                                      "/span";

    //XPath for main menu List Management
    public static final String PATH_MENU_LISTMANAGEMENT_PTBR = "//p[text()[contains(.,'Gerenciamento de Lista')]]";
    public static final String PATH_MENU_LISTMANAGEMENT_ENGlISH = "//p[text()[contains(.,'List Management')]]";

    //Buttons
    public static final String PATH_BUTTON_LOAD_NEW_LIST_PTBR = "//button[text()[contains(.,'Carregar Nova Lista')]]";
    public static final String PATH_BUTTON_LOAD_NEW_LIST_ENGLISH = "//button[text()[contains(.,'Load New List')]]";
    public static final String PATH_BUTTON_LOAD = "//*[@type='submit']";

    //Choose a company modal
    public static final String PATH_CHOOSE_A_CAMPANY_MODAL = "//div[@id='company-select-modal-chooser']" +
                                                             "//span[@role='button']";
    public static final String PATH_CHOOSE_A_COMPANY_CPFL = "//*[contains( text(), 'CPFL')]";
    public static final String PATH_CHOOSE_A_COMPANY_VALID = "//*[contains( text(), 'Valid')]";

    //Validation messages
    public static final String PATH_TOAST_CONTAINER =
            "//div[@id='toast-container']" +
            "/div[@role='button']" +
            "/div[@class='toast-message']" +
            "/div[@class='ng-binding ng-scope']";

    //First record of the gridview List Management screen
    public static final String PATH_FIRST_RECORD_GRIDVIEW =
            "//table[@id='list-management-table']" +
                    "/tbody[3]" +
                    "/tr[1]" +
                    "/td[1]" +
                    "/a[@class='ng-binding ng-scope']";

    //Submit button on the login page of DataPortal
    public static final String PATH_SUBMIT_BUTTON_DATAPORTAL_LOGIN = "//*[@type='submit']";

}
