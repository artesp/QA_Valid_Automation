package DeliveryCenter_Pages;

import Assistant.PathAssistant;
import Core.BasePage;
import org.openqa.selenium.By;

public class Menu_Page extends BasePage{

    public void clickInMainMenuTogglebutton(){
        clickButton(By.id("toggle_nav_btn_web"));
    }

    public void selectCriacaoConteudo(){
        clickButton(By.xpath(PathAssistant.PATH_MENU_DELIVERYCENTER_CRIACAO_CONTEUDO));
    }
}
