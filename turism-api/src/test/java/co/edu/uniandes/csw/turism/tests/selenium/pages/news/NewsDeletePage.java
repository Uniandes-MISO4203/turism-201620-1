/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.turism.tests.selenium.pages.news;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.jboss.arquillian.graphene.Graphene.waitGui;

/**
 *
 * @author lm.ariza10
 */
public class NewsDeletePage {
    
    @FindBy(id = "confirm-delete")
    private WebElement confirmDeleteBtn;

    @FindBy(id = "cancel-delete")
    private WebElement cancelDeleteBtn;

    public void confirm() {
        waitGui().until().element(confirmDeleteBtn).is().visible();
        guardAjax(confirmDeleteBtn).click();
    }

    public void cancel() {
        waitGui().until().element(cancelDeleteBtn).is().visible();
        cancelDeleteBtn.click();
    }
}
