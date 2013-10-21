package no.ugland.utransprod.gui.handlers;

import javax.swing.JButton;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.model.ExternalOrderLineAttributeModel;
import no.ugland.utransprod.model.ExternalOrderLineAttribute;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ExternalOrderManager;
import no.ugland.utransprod.util.UserUtil;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Håndterer eksterne ordrelinjeattributter
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderLineAttributeViewHandler
		extends
		AbstractViewHandlerShort<ExternalOrderLineAttribute, ExternalOrderLineAttributeModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userType
	 */
	public ExternalOrderLineAttributeViewHandler(UserType userType) {
		super("Attributter", null, false,
				userType, true);
	}

	/**
	 * Lager tekstfelt for navn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldName(PresentationModel presentationModel) {
		return BasicComponentFactory
				.createTextField(presentationModel
						.getModel(ExternalOrderLineAttributeModel.PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_NAME));
	}

	/**
	 * Lager tekstfelt for verdi
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldValue(PresentationModel presentationModel) {
		return BasicComponentFactory
				.createTextField(presentationModel
						.getModel(ExternalOrderLineAttributeModel.PROPERTY_EXTERNAL_ORDER_LINE_ATTRIBUTE_VALUE));
	}

	/**
	 * Lager avbrytknaoo
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonCancel(WindowInterface window) {
		return new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK,
				null, true);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Bestillinger");
	}

	/**
	 * @param handler
	 * @param object
	 * @param searching
	 * @return view
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
	 *      java.lang.Object, boolean)
	 */
	@Override
	protected AbstractEditView<ExternalOrderLineAttributeModel, ExternalOrderLineAttribute> getEditView(
			AbstractViewHandler<ExternalOrderLineAttribute, ExternalOrderLineAttributeModel> handler,
			ExternalOrderLineAttribute object, boolean searching) {
		return null;
	}

    @Override
    public CheckObject checkDeleteObject(ExternalOrderLineAttribute object) {
        return null;
    }

}
