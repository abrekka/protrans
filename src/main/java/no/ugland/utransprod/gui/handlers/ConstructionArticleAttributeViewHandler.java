package no.ugland.utransprod.gui.handlers;

import javax.swing.JButton;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.util.Util;

import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;

/**
 * Hjelpeklase for visning av attributter som skal settes for garasjetype ller
 * ordrelinje
 * @author atle.brekka
 */
public class ConstructionArticleAttributeViewHandler implements Closeable {
    /**
	 * 
	 */
    private ValidationResultModel validationResultModel = new DefaultValidationResultModel();

    /**
     * Henter ok-knapp
     * @param aWindow
     * @return knapp
     */
    public JButton getButtonOk(WindowInterface aWindow, ValidationResultModel aValidationResultModel) {
        JButton button = new CancelButton(aWindow, this, true, "Ok", IconEnum.ICON_OK, null, true);
        button.setName("ButtonOk");

        this.validationResultModel = aValidationResultModel != null ? aValidationResultModel
                : validationResultModel;
        return button;
    }

    /**
     * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean canClose(String actionString, WindowInterface window) {
        if (validationResultModel.hasErrors()) {
            Util.showErrorDialog(window, "Rett feil", "Alle feil må rettes først");
            return false;
        }
        return true;
    }

    /**
     * Henter feilrapportering
     * @return feilrapportering
     */
    public ValidationResultModel getValidationResultModel() {
        return validationResultModel;
    }
}
